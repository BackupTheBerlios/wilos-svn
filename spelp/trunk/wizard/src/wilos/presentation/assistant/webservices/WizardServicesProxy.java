package wilos.presentation.assistant.webservices;

import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import wilos.business.webservices.WizardServices;
import wilos.business.webservices.WizardServicesService;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.utils.Constantes;

import com.thoughtworks.xstream.XStream;

public class WizardServicesProxy {
   	public static String ENDPOINT = "/WizardServices?wsdl";
    public static String URLWebService = "http://webservices.business.wilos/";
    public static String nameWebService = "WizardServicesService";
    private static String testIHMLoginString = "";
        
    private static String login = "";
    private static String password = "";
    private static String address = "";
    
    public static String getAddress() {
		return address;
	}

	public static String getLogin() {
		return login;
	}

	public static String getPassword() {
		return password;
	}

	public static void setIdentificationParamaters(String aLogin, String aPassword, String anAddress) {
    	WizardServicesProxy.login = aLogin;
    	WizardServicesProxy.password = aPassword;
    	WizardServicesProxy.address = anAddress;
    }
    
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
            Participant myParticipant = getParticipant();
            if (myParticipant!= null) {
                for (RoleDescriptor rd : myParticipant.getRolesListForAProject()) {
                    myRoleListe.add(rd);
                }
            }
            return myRoleListe;
	}
        
        public static Participant getParticipant()
        {
            Participant myParticipant = null;
            try { 
            	if (login.equalsIgnoreCase(testIHMLoginString)) {
            		myParticipant = getParticipantExample();
            	} else {
                	WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));            	
                    WizardServices port = service.getWizardServicesPort();
                    //myWilosUser = port.getWilosUser(login,password);
                      XStream xstream = new XStream(); 
                      String result = port.getParticipant(login,password);
                      System.out.println(result);
                    myParticipant = (Participant) xstream.fromXML(result);
            	}
            }
            catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            return myParticipant;
        }
        
        public static  void startConcreteTaskDescriptor (String theConcreteTaskId) {
            try { 
            	if (!login.equalsIgnoreCase(testIHMLoginString)) {
            		WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));            	
                    WizardServices port = service.getWizardServicesPort();
                    port.startConcreteTaskDescriptor(login, password, theConcreteTaskId);
            	}
            }
            catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
        
        private static Participant getParticipantExample () {
        	Participant p = new Participant();
            p.setName("testSansBD");
            
            RoleDescriptor aTmpRole;
            TaskDescriptor aTmpTask;
            Step aTmpStep;
            TaskDefinition aTmpTaskDef;
            ConcreteTaskDescriptor aTmpConcrete;

            aTmpRole = new RoleDescriptor();
            aTmpTask = new TaskDescriptor();
            aTmpTaskDef = new TaskDefinition();
            aTmpStep = new Step();
            aTmpConcrete = new ConcreteTaskDescriptor();
            
            
            
            
            
            
            
            aTmpConcrete.setConcreteName("Coder le programme Partie I");
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.CREATED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    			
            aTmpRole.setName("Developper");
            aTmpRole.setDescription("Un gars qui developpe");

            aTmpTask.setName("Coder le programme");
            aTmpTask.setDescription("Un grand moment de solitude");
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);

          
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
            
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName("Aimer le programme avec envie");
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    			
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
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);
            
            aTmpRole.addPrimaryTask(aTmpTask);
        
            
            
            
            
            
            
            
            aTmpTask = new TaskDescriptor();

            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName("Trouver le balai");
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.STARTED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    			
            aTmpTask.setName("Passer le balai");
            aTmpTask.setDescription("Et c'est plus propre");        
            aTmpRole.addPrimaryTask(aTmpTask);

            p.addToRoleDescriptor(aTmpRole);
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);

            
            
            
            
            
            
            
            
            aTmpRole = new RoleDescriptor();
            aTmpTask = new TaskDescriptor();

            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName("concr_faire_essais");
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.SUSPENDED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    			
            aTmpRole.setName("Tester");
            aTmpRole.setDescription("Faire des essais, en gros");
            aTmpTask.setName("Tester le programme");
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);

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

            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName("concr_detester le prog");
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.FINISHED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    			
            aTmpTask.setName("Detester le programme");
            aTmpTask.setDescription("Un grand moment de haine");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);

            
            
            
            
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
