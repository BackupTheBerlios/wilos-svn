/*
 * WizardServices.java
 *
 * Created on 23 novembre 2006, 17:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.business.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wilos.business.services.assistant.AssistantService;
import wilos.business.services.wilosuser.LoginService;
import wilos.business.transfertobject.ParticipantTO;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;
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
         
       wu =  as.getParticipantTO(getAuthentifiedParticipant(login,password).getLogin());       
	   System.out.println("Le user "+wu.getName()+" est logge");

       XStream xstream = new XStream();
       result= xstream.toXML(wu);
       return result;
    }
    
    @WebMethod
    public void startConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
		System.out.println("APPEL DE METHODE : startConcreteTaskDescriptor");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est loggé");
    		as.startConcreteTaskDescriptor(id);
    	}
    }
    
    @WebMethod
    public void suspendConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
		System.out.println("APPEL DE METHODE : suspendConcreteTaskDescriptor");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est loggé");
    		as.suspendConcreteTaskDescriptor(id);
    	}
    }
    
    @WebMethod
    public void resumeConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
		System.out.println("APPEL DE METHODE : resumeConcreteTaskDescriptor");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est loggé");
    		as.resumeConcreteTaskDescriptor(id);
    	}
    }
    
    @WebMethod
    public void stopConcreteTaskDescriptor (@WebParam(name="login") String login, @WebParam(name="password")  String password, @WebParam(name="id") String id) throws Exception {
		System.out.println("APPEL DE METHODE : stopConcreteTaskDescriptor");
    	if (getAuthentifiedParticipant (login, password)!=null) {
    		System.out.println("le Participant est loggé");
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
}
