package wilos.presentation.assistant.webservices;

import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import wilos.business.webservices.WizardServices;
import wilos.business.webservices.WizardServicesService;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.phase.Phase;
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
   
        

        public static  void stopConcreteTaskDescriptor (String theConcreteTaskId) {
            try { 
            	if (!login.equalsIgnoreCase(testIHMLoginString)) {
            		WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));            	
                    WizardServices port = service.getWizardServicesPort();
                    port.stopConcreteTaskDescriptor(login, password, theConcreteTaskId);
            	}
            }
            catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }

        public static  void suspendConcreteTaskDescriptor (String theConcreteTaskId) {
            try { 
            	if (!login.equalsIgnoreCase(testIHMLoginString)) {
            		WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));            	
                    WizardServices port = service.getWizardServicesPort();
                    port.suspendConcreteTaskDescriptor(login, password, theConcreteTaskId);
            	}
            }
            catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }

        public static  void resumeConcreteTaskDescriptor (String theConcreteTaskId) {
            try { 
            	if (!login.equalsIgnoreCase(testIHMLoginString)) {
            		WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));            	
                    WizardServices port = service.getWizardServicesPort();
                    port.resumeConcreteTaskDescriptor(login, password, theConcreteTaskId);
            	}
            }
            catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    
        
        
        private static Participant getParticipantExample () {
        	Participant p = new Participant();
            p.setName("testSansBD");
            
            //  ajout d'activity
            
            ConcreteActivity aConcreteActivity = new ConcreteActivity();
            Activity anActivity = new Activity () ;
            
            ConcretePhase aConcretePhase = new ConcretePhase();
            Phase aPhase = new Phase();
            
            anActivity.setPresentationName("Activite de developpement");
            anActivity.setDescription("Tout le processus de developpement");
            aConcreteActivity.setConcreteName(anActivity.getPresentationName());
            aConcreteActivity.setActivity(anActivity);
            
            aPhase.setPresentationName("Phase de fin de projet");
            aPhase.setDescription("Tout ce qu'on fait apres le projet");
            aConcretePhase.setConcreteName(aPhase.getPresentationName());
            aConcretePhase.setPhase(aPhase);
            
            ConcreteRoleDescriptor aTmpConcreteRole ;
            RoleDescriptor aTmpRole;
            TaskDescriptor aTmpTask;
            Step aTmpStep;
            TaskDefinition aTmpTaskDef;
            ConcreteTaskDescriptor aTmpConcrete;

            aTmpConcreteRole = new ConcreteRoleDescriptor() ;
            aTmpRole = new RoleDescriptor();
            aTmpTask = new TaskDescriptor();
            aTmpTaskDef = new TaskDefinition();
            aTmpStep = new Step();
            aTmpConcrete = new ConcreteTaskDescriptor();
            
             // concreteTask
            
            aTmpConcrete.setConcreteName("Coder le programme Partie I");
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.CREATED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    			
            // task desc
            
            aTmpTask.setPresentationName("Coder le programme Partie I");
            aTmpTask.setDescription("Un grand moment de solitude");
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);
            
            // task def
            
            aTmpTaskDef.setName("Coder le programme");
            aTmpTaskDef.setDescription("Un grand moment de solitude");
            
            aTmpStep.setName("Ecrire la premiere ligne");
            aTmpStep.setDescription("Un grand moment de joie");
            aTmpTaskDef.addStep(aTmpStep);
            
            // step
            
            aTmpStep = new Step();
            aTmpStep.setName("Ecrire la seconde ligne");
            aTmpStep.setDescription("Ca marche plus");
            
            // Role
            
            aTmpRole.setPresentationName("Developper");
            aTmpRole.setDescription("Un gars qui developpe");

            // Concrete Role
            
            aTmpConcreteRole.setConcreteName(aTmpRole.getPresentationName());
            aTmpConcreteRole.setRoleDescriptor(aTmpRole);
            
            aTmpTaskDef.addStep(aTmpStep);
            
            aTmpTask.addTaskDefinition(aTmpTaskDef);
            
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcreteRole.addConcreteTaskDescriptor(aTmpConcrete);
            p.addConcreteRoleDescriptor(aTmpConcreteRole);
            
            // --------- nouveau
            
            // task desc
            
            aTmpTask = new TaskDescriptor();
            aTmpTask.setPresentationName("Aimer son programme");
            aTmpTask.setDescription("Un grand moment d'amour tout le monde sait tres bien qu'un developper aime son programme de toutes maniere ne pas aimer son programme est un crime. <br> Aimer ou ne pas aimer est un programme telle est la questin apres tout est ce que le developpeur ne laisse pas une partie de son ame dans son programme. <br> le developpeur ne fait qu'un avec son logiciel il entretient avec lui une union charnelle");
            
            // concrete task
            
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName(aTmpTask.getPresentationName());
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
            
            // guide
            
            Guidance g1 = new Guidance();
            g1.setName("guide 1");
            g1.setDescription("description du guide 1");
            
            Guidance g2 = new Guidance();
            g2.setName("guide 2");
            g2.setDescription("description du guide 2");
            
            Guidance g3 = new Guidance();
            g3.setName("guide 3");
            g3.setDescription("description du guide 3");
            
            Set<Guidance> sgl = new HashSet<Guidance>();
            sgl.add(g1);
            sgl.add(g2);
            sgl.add(g3);
    			
            // task def
            
            aTmpTaskDef = new TaskDefinition();
            aTmpTaskDef.setName("Aimer son programme");
            aTmpTaskDef.setDescription("Un grand moment d'amour");
            aTmpTaskDef.setGuidances(sgl);
            
            // step
            
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
            aTmpConcreteRole.addConcreteTaskDescriptor(aTmpConcrete);
        
            
            // ------- nouveau
            
            // task desc
            aTmpTask = new TaskDescriptor();
            aTmpTask.setPresentationName("Passer le balai");
            aTmpTask.setDescription("Et c'est plus propre");
            
            // concrete task
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName("Trouver le balai");
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.STARTED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
                   
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcreteRole.addConcreteTaskDescriptor(aTmpConcrete);
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);
            
            // affectation du role dans l activty
            aTmpConcreteRole.addSuperConcreteActivity(aConcreteActivity);
            aTmpRole.addSuperActivity(anActivity);
            
            // ----- nouveau
            
            
            aTmpRole = new RoleDescriptor();
            
            aTmpTask = new TaskDescriptor();

            
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName("concr_faire_essais");
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.SUSPENDED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    		
            // nouveau role donc nouveau concrete role =>
            aTmpConcreteRole = new ConcreteRoleDescriptor ();
           // role
            
            aTmpRole.setPresentationName("Tester");
            aTmpRole.setDescription("Faire des essais, en gros");
            
            // concrete role
            
            aTmpConcreteRole.setConcreteName(aTmpRole.getPresentationName());
            aTmpConcreteRole.setRoleDescriptor(aTmpRole);
            p.addConcreteRoleDescriptor(aTmpConcreteRole);
            
            // task desc
            
            aTmpTask.setPresentationName("Tester le programme");
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);
            
            // task def
            
            aTmpTaskDef = new TaskDefinition();
            aTmpTaskDef.setName("Ecrire le test");
            aTmpTaskDef.setDescription("Je devais faire quoi?");
            
            // step
            
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
            aTmpConcreteRole.addConcreteTaskDescriptor(aTmpConcrete);
            
            // ---------- nouveau
            
            
            aTmpTask = new TaskDescriptor();

            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName("concr_detester le prog");
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.FINISHED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    			
            aTmpTask.setPresentationName("Detester le programme");
            aTmpTask.setDescription("Un grand moment de haine");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);

            // ------------ nouveau
            
            aTmpTask = new TaskDescriptor();
            aTmpTask.setPresentationName("Passer la serpilliere");
            aTmpTask.setDescription("Un grand moment de solitude");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);

            // affectation du role dans l activty
            aTmpConcreteRole.addSuperConcreteActivity(aConcreteActivity);
            aTmpRole.addSuperActivity(anActivity);
            
            // ------------ nouveau
            
            aTmpRole = new RoleDescriptor();
            aTmpConcreteRole = new ConcreteRoleDescriptor () ;
            aTmpConcreteRole.addRoleDescriptor(aTmpRole);
            
            aTmpTask = new TaskDescriptor();
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpRole.setPresentationName("Conceptualisateur");
            aTmpConcreteRole.setConcreteName(aTmpRole.getPresentationName());
            
            aTmpTask = new TaskDescriptor();
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpTask.setPresentationName("Conceptualiser les concepts du programme");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcrete.setTaskDescriptor(aTmpTask);
            aTmpConcreteRole.addConcreteTaskDescriptor(aTmpConcrete);
            
            aTmpTask = new TaskDescriptor();
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpTask.setPresentationName("Rever du programme");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcrete.setTaskDescriptor(aTmpTask);
            aTmpConcreteRole.addConcreteTaskDescriptor(aTmpConcrete);
            
            aTmpTask = new TaskDescriptor();
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpTask.setPresentationName("Faire le cafe concept");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcrete.setTaskDescriptor(aTmpTask);
            aTmpConcreteRole.addConcreteTaskDescriptor(aTmpConcrete);
            p.addConcreteRoleDescriptor(aTmpConcreteRole);
            
            // affectation du role dans l activty
            aTmpConcreteRole.addSuperConcreteActivity(aConcreteActivity);
            aTmpRole.addSuperActivity(anActivity);
            // ---------- nouveau
            
            aTmpConcrete = new ConcreteTaskDescriptor() ;
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpTask = new TaskDescriptor() ;
            aTmpTaskDef = new TaskDefinition();
            aTmpRole = new RoleDescriptor () ;
            aTmpConcreteRole = new ConcreteRoleDescriptor() ;
            
            aTmpRole.setPresentationName("Developper");
            aTmpRole.setDescription("Un gars qui developpe");
            
            aTmpConcreteRole.setConcreteName(aTmpRole.getPresentationName());
            aTmpConcreteRole.setRoleDescriptor(aTmpRole);
            
           
            
            aTmpTaskDef.setName("sortir avec l equipe");
            aTmpTaskDef.setDescription("Apres l effort le reconfort");
            
            
            aTmpTask.setPresentationName(aTmpTaskDef.getName());
            aTmpTask.setDescription(aTmpTaskDef.getDescription());
            aTmpRole.addPrimaryTask(aTmpTask);
            
            aTmpConcrete.setTaskDescriptor(aTmpTask);
            aTmpConcreteRole.addConcreteTaskDescriptor(aTmpConcrete);
            
            
            p.addConcreteRoleDescriptor(aTmpConcreteRole);
            
            // affectation du role dans l activty
            aTmpConcreteRole.addSuperConcreteActivity(aConcretePhase);
            aTmpRole.addSuperActivity(aPhase);
           
            return p;
        }
}
