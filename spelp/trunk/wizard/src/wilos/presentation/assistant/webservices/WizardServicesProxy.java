package wilos.presentation.assistant.webservices;

import com.thoughtworks.xstream.XStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;

import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.process.Process;
import wilos.business.webservices.*;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;

public class WizardServicesProxy {
        public static String ENDPOINT = "/WizardServices?wsdl";
        public static String URLWebService = "http://webservices.business.wilos/";
        public static String nameWebService = "WizardServicesService";
    
	public static ArrayList<RoleDescriptor> getRolesByUser(String login, String password, String address) {	
            ArrayList<RoleDescriptor> myRoleListe = new  ArrayList<RoleDescriptor>();
            /*List<Process> pros = getAllProcess(login, password,adresseServeur);
            for (Process pro : pros) {
                for (BreakdownElement bre: pro.getBreakDownElements()) {
                    if (bre instanceof RoleDescriptor) {
                        myRoleListe.add((RoleDescriptor)bre);
                    }
                }
            }*/
            Participant myParticipant = getParticipant(login,password,address);
            if (myParticipant!= null) {
                for (RoleDescriptor rd : myParticipant.getRolesListForAProject()) {
                    myRoleListe.add(rd);
                }
            }
            return myRoleListe;
	}
        
        private static Participant getParticipant(String login, String password, String address)
        {
            Participant myParticipant = null;
            try { 
            	if (login.equalsIgnoreCase("testIHM")) {
            		myParticipant = getParticipantExample();
            	} else {
                	WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));            	
                    WizardServices port = service.getWizardServicesPort();
                    //myWilosUser = port.getWilosUser(login,password);
                      XStream xstream = new XStream(); 
                      xstream.alias("wilos.business.transfertobject.ParticipantTO",Participant.class);
                      xstream.alias("wilos.business.transfertobject.RoleDescriptorTO",RoleDescriptor.class);
                      xstream.alias("wilos.business.transfertobject.TaskDescriptorTO",TaskDescriptor.class);
                      xstream.alias("wilos.business.transfertobject.TaskDefinitionTO",TaskDefinition.class);
                      xstream.alias("wilos.business.transfertobject.StepTO",Step.class);
                      String result = port.getParticipant(login,password);
                      System.out.println(result);
                    myParticipant = (Participant)xstream.fromXML(result);
            	}
            }
            catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            return myParticipant;
        }
        
        private static List<Process> getAllProcess(String login, String password, String address) {
             ArrayList<Process> pros = new  ArrayList<Process>();
             try { 
            	 WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));  
                // Call Web Service Operation
            	 WizardServices port = service.getWizardServicesPort();
                java.util.List<String> result = port.getAllProcess(login, password);
                XStream xstream = new XStream();     
                for (String strxml : result) {
                    pros.add((Process)xstream.fromXML(strxml));
                }

            } catch (java.lang.Exception ex) {
                ex.printStackTrace();
            }
             return pros;
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
