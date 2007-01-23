/*
 * WizardServices.java
 *
 * Created on 23 novembre 2006, 17:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.business.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.assistant.AssistantService;
import wilos.business.services.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.process.ProcessService;
import wilos.business.services.role.RoleService;
import wilos.business.services.wilosuser.LoginService;
import wilos.business.services.wilosuser.ParticipantService;
import wilos.business.transfertobject.ParticipantTO;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author toine 
 */
@WebService()
public class WizardServices {
	ApplicationContext ctx;
	LoginService ls;
	AssistantService as;
	
	public WizardServices() {
       // Getback the application context from the spring configuration file
       ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
       // Show what is in the factory
       System.out.println("factory => "+ctx);
       // Get the LoginService Singleton for managing Activity data  
       ls = (LoginService) ctx.getBean("LoginService");
       as = (AssistantService) ctx.getBean("AssistantService");
	}
	
    @WebMethod
    public String getParticipant(@WebParam(name="login") String login, @WebParam(name="password")  String password) throws Exception
    {
       String result = "";
       Participant wu = new Participant();
       System.out.println("APPEL DE LA METHODE getWilosUser");
       System.out.println("LOGIN : "+login);
       System.out.println("PASS : "+password);
       
        if(login.equals("testSansBD")) {            
            wu = new ParticipantTO(getParticipantExample());
        } else {             
	                wu =  as.getParticipantTO(getAuthentifiedParticipant(login,password).getLogin());       
	                System.out.println("Le user "+wu.getName()+" est logge");
        }
        XStream xstream = new XStream();
        result= xstream.toXML(wu);
       return result;
    }
    
    @WebMethod
    public void startConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		as.startConcreteTaskDescriptor(id);
    	}
    }
    
    private Participant getAuthentifiedParticipant (String login,  String password) throws Exception {
    	Participant result = null;
        WilosUser tmpwu = ls.getAuthentifiedUser(login,password);
       if (tmpwu == null)
       {
           throw new Exception("le wilos user n'existe pas");
       }
       else
       {
           if (!(tmpwu instanceof Participant))
           {
                 throw new Exception("le wilos user n'est pas un particpant");
           }
           else
           {
        	   result = (Participant) tmpwu;
           }
       }
       return result;
    }
    
    private static Participant getParticipantExample () {
    	Participant p = new Participant();
        p.setName("testSansBD");
        
        RoleDescriptor aTmpRole;
        TaskDescriptor aTmpTask;
        Step aTmpStep;
        TaskDefinition aTmpTaskDef;

        aTmpRole = new RoleDescriptor();
        aTmpTask = new TaskDescriptor();
        aTmpTaskDef = new TaskDefinition();
        aTmpStep = new Step();
			
        aTmpRole.setName("Developper");
        aTmpRole.setDescription("Un gars qui developpe");

        aTmpTask.setName("Coder le programme");
        aTmpTask.setDescription("Un grand moment de solitude");

        aTmpTaskDef.setName("Coder le programme");
        aTmpTaskDef.setDescription("Un grand moment de solitude");
        
        aTmpStep.setName("Ecrire la premiere ligne");
        aTmpStep.setDescription("Un grand moment de joie");
        aTmpTaskDef.addStep(aTmpStep);
        
        aTmpStep = new Step();
        aTmpStep.setName("Ecrire la seconde ligne");
        aTmpStep.setDescription("Ca marche plus");
        aTmpTaskDef.addStep(aTmpStep);
        
        aTmpTask.addTaskDefinition(aTmpTaskDef);
        
        aTmpRole.addPrimaryTask(aTmpTask);
        
        aTmpTask = new TaskDescriptor();
        aTmpTask.setName("Aimer son programme");
        aTmpTask.setDescription("Un grand moment d'amour");
        
        aTmpTaskDef = new TaskDefinition();
        aTmpTaskDef.setName("Aimer son programme");
        aTmpTaskDef.setDescription("Un grand moment d'amour");
        
        aTmpStep = new Step();
        aTmpStep.setName("Debugger son programme");
        aTmpStep.setDescription("Une imcomprehension croissante");
        aTmpTaskDef.addStep(aTmpStep);
        
        aTmpStep = new Step();
        aTmpStep.setName("Trouver la solution");
        aTmpStep.setDescription("Ca marche !!");
        aTmpTaskDef.addStep(aTmpStep);
        
        aTmpTask.addTaskDefinition(aTmpTaskDef);
        
        aTmpRole.addPrimaryTask(aTmpTask);
        
        aTmpTask = new TaskDescriptor();
        aTmpTask.setName("Passer le balai");
        aTmpTask.setDescription("Et c'est plus propre");        
        aTmpRole.addPrimaryTask(aTmpTask);

        p.addToRoleDescriptor(aTmpRole);

        aTmpRole = new RoleDescriptor();
        aTmpTask = new TaskDescriptor();
        aTmpRole.setName("Tester");
        aTmpRole.setDescription("Faire des essais, en gros");
        aTmpTask.setName("Tester le programme");
        
        aTmpTaskDef = new TaskDefinition();
        aTmpTaskDef.setName("Ecrire le test");
        aTmpTaskDef.setDescription("Je devais faire quoi?");
        
        aTmpStep = new Step();
        aTmpStep.setName("Lancer le test");
        aTmpStep.setDescription("Mais qu'est ce qui se passe?");
        aTmpTaskDef.addStep(aTmpStep);
        
        aTmpStep = new Step();
        aTmpStep.setName("Trouver la solution");
        aTmpStep.setDescription("Ca marche !!");
        aTmpTaskDef.addStep(aTmpStep);
        
        aTmpTask.addTaskDefinition(aTmpTaskDef);
        
        aTmpTask.setDescription("Un grand moment de solitude");
        aTmpRole.addPrimaryTask(aTmpTask);
        aTmpTask = new TaskDescriptor();
        aTmpTask.setName("Detester le programme");
        aTmpTask.setDescription("Un grand moment de haine");
        aTmpRole.addPrimaryTask(aTmpTask);
        aTmpTask = new TaskDescriptor();
        aTmpTask.setName("Passer la serpilliere");
        aTmpTask.setDescription("Un grand moment de solitude");
        aTmpRole.addPrimaryTask(aTmpTask);

        p.addToRoleDescriptor(aTmpRole);

        aTmpRole = new RoleDescriptor();
        aTmpTask = new TaskDescriptor();
        aTmpRole.setName("Conceptualisateur");
        aTmpTask = new TaskDescriptor();
        aTmpTask.setName("Conceptualiser les concepts du programme");
        aTmpRole.addPrimaryTask(aTmpTask);
        aTmpTask = new TaskDescriptor();
        aTmpTask.setName("Rever du programme");
        aTmpRole.addPrimaryTask(aTmpTask);
        aTmpTask = new TaskDescriptor();
        aTmpTask.setName("Faire le cafe concept");
        aTmpRole.addPrimaryTask(aTmpTask);
        
        p.addToRoleDescriptor(aTmpRole);
        
        return p;
    }
}
