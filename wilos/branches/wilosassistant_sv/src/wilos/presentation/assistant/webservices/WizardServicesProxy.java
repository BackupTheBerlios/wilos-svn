/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.presentation.assistant.webservices;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.text.Format;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.xml.namespace.QName;

import wilos.business.webservices.WizardServices;
import wilos.business.webservices.WizardServicesService;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.section.Section;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.presentation.assistant.control.ExceptionManager;
import wilos.presentation.assistant.control.WizardControler;
import wilos.utils.Constantes;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
            	} 
            	else {
                	WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));            	
                    WizardServices port = service.getWizardServicesPort();
                    XStream xstream = new XStream();
                    String result = port.getParticipant(login,password);
                      /*File xstreamFile = new File ("xstream.xml");
                      PrintStream out = new PrintStream(new FileOutputStream(xstreamFile), false, "UTF-8");
                      out.println(result);*/

                      //System.out.println(result);
                    myParticipant = (Participant) xstream.fromXML(result);
            	}
            }
            catch (java.lang.Exception e) {
        		new ExceptionManager(e);
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
            	new ExceptionManager(e);
            }
        }
   
        public static byte [] getGuidanceAttachmentContent(String idGuidance) {
			byte [] fileContent = null;
			
        	try { 
            	if (!login.equalsIgnoreCase(testIHMLoginString)) {
            		WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));            	
                    WizardServices port = service.getWizardServicesPort();
                    fileContent = port.getGuidanceAttachment(login, password, idGuidance);
            	}
            }
            catch (java.lang.Exception e) {
            	new ExceptionManager(e);
            }
            return fileContent;
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
            	new ExceptionManager(e);
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
            	new ExceptionManager(e);
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
            	new ExceptionManager(e);
            }
        }
    

        
        public static void setRemainingTimeByTask(String taskGuid,float newTime) {
            try { 
            	if (!login.equalsIgnoreCase(testIHMLoginString)) {
            		WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));            	
                    WizardServices port = service.getWizardServicesPort();
                    port.setRemainingTimeByTask(login, password, taskGuid, newTime);
            	}
            }
            catch (java.lang.Exception e) {
                new ExceptionManager(e);
            }
        }

        public static void setAccomplishedTimeByTask(String taskGuid,float newTime) {
            try { 
            	if (!login.equalsIgnoreCase(testIHMLoginString)) {
            		WizardServicesService service = new WizardServicesService(new URL(address+ENDPOINT), new QName(URLWebService, nameWebService));            	
                    WizardServices port = service.getWizardServicesPort();
                    port.setAccomplishedTimeByTask(login, password, taskGuid, newTime);
            	}
            }
            catch (java.lang.Exception e) {
                new ExceptionManager(e);
            }
        }
        	
        
        private static Participant getParticipantExample () {
        	// cancel the background refresh thread for the sample  
        	WizardControler.getInstance().cancelrefreshThread() ;
        	Participant p = new Participant();
            p.setName("testSansBD");
            
            //  ajout d'activity
            
            ConcreteIteration aConcreteIteration = new ConcreteIteration () ;
            Iteration anIteration = new Iteration () ;
            anIteration.setGuid("1");
            anIteration.setPresentationName("Iteration 1");
            anIteration.setDescription("Le projet test");
            anIteration.setAlternatives("Ce sont les Alternatives");
            anIteration.setMainDescription("Ceci est la Main Description");
            anIteration.setPurpose("Voici le purpose");
            anIteration.setHowToStaff("Voici le How To Staff");
            
            aConcreteIteration.setConcreteName(anIteration.getPresentationName());
            aConcreteIteration.setIteration(anIteration);
            ConcreteIteration aConcreteIteration2 = new ConcreteIteration () ;
            Iteration anIteration2 = new Iteration () ;
            anIteration2.setGuid("2");
            anIteration2.setPresentationName("Iteraton 2");
            anIteration2.setDescription("Une iteration temporaire");
            anIteration2.setAlternatives("Ce sont les Alternatives");
            anIteration2.setMainDescription("Ceci est la Main Description");
            anIteration2.setPurpose("Voici le purpose");
            anIteration2.setHowToStaff("Voici le How To Staff");
            aConcreteIteration2.setConcreteName(anIteration2.getPresentationName());
            aConcreteIteration2.setIteration(anIteration2);
            
            ConcreteRoleDescriptor crd2 = new ConcreteRoleDescriptor  () ;
            RoleDescriptor rd2 = new RoleDescriptor () ;
            rd2.setGuid("3");
            RoleDefinition rde2 = new RoleDefinition () ;
            rd2.setPresentationName("Visiteur");
            rd2.setDescription("Le gars qui visite lors de l'it 2");
            rd2.setRoleDefinition(rde2);
            rde2.setName(rd2.getPresentationName());
            rde2.setDescription(rd2.getDescription());
            crd2.setConcreteName(rd2.getPresentationName());
            crd2.setRoleDescriptor(rd2);
            crd2.addSuperConcreteActivity(aConcreteIteration2);
            
            p.addConcreteRoleDescriptor(crd2);
            
            ConcreteActivity aConcreteActivity = new ConcreteActivity();
            Activity anActivity = new Activity () ;
            anActivity.setGuid("4");
            ConcretePhase aConcretePhase = new ConcretePhase();
            Phase aPhase = new Phase();
            aPhase.setGuid("5");
            anActivity.addSuperActivity(anIteration);
            aPhase.addSuperActivity(anIteration);
            
            aConcreteActivity.addSuperConcreteActivity(aConcreteIteration);
            aConcretePhase.addSuperConcreteActivity(aConcreteIteration);
            
            anActivity.setPresentationName("Activite de developpement");
            anActivity.setDescription("Tout le processus de developpement");
            anActivity.setAlternatives("Ce sont les Alternatives");
            anActivity.setMainDescription("Ceci est la Main Description");
            anActivity.setPurpose("Voici le purpose");
            anActivity.setHowToStaff("Voici le How To Staff");
            aConcreteActivity.setConcreteName(anActivity.getPresentationName());
            aConcreteActivity.setActivity(anActivity);
            
            aPhase.setPresentationName("Phase de fin de projet");
            aPhase.setDescription("Tout ce qu'on fait apres le projet");
            aPhase.setAlternatives("Ce sont les Alternatives");
            aPhase.setMainDescription("Ceci est la Main Description");
            aPhase.setPurpose("Voici le purpose");
            aPhase.setHowToStaff("Voici le How To Staff");
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
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    			
            // task desc
            aTmpTask.setGuid("6");
            aTmpTask.setPresentationName("Coder le programme Partie I");
            aTmpTask.setDescription("Un grand moment de solitude");
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);
            
            // task def
            
            aTmpTaskDef.setName("Coder le programme");
            aTmpTaskDef.setDescription("Un grand moment de solitude");
            aTmpStep.setGuid("7");
            aTmpStep.setName("Ecrire la premiere ligne");
            aTmpStep.setDescription("Un grand moment de joie");
            aTmpTaskDef.addStep(aTmpStep);
            
            // step
            
            aTmpStep = new Step();
            aTmpStep.setGuid("8");
            aTmpStep.setName("Ecrire la seconde ligne");
            aTmpStep.setDescription("Ca marche plus");
            
            // Role
            aTmpRole.setGuid("9");
            aTmpRole.setPresentationName("Developper");
            aTmpRole.setDescription("Un gars qui developpe");

            // Concrete Role
            
            aTmpConcreteRole.setConcreteName(aTmpRole.getPresentationName());
            aTmpConcreteRole.setRoleDescriptor(aTmpRole);
            
            aTmpTaskDef.addStep(aTmpStep);
            
            aTmpTask.addTaskDefinition(aTmpTaskDef);
            
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcreteRole.addPrimaryConcreteTaskDescriptor(aTmpConcrete);
            p.addConcreteRoleDescriptor(aTmpConcreteRole);
            
            // --------- nouveau
            
            // task desc
            
            aTmpTask = new TaskDescriptor();
            aTmpTask.setGuid("10");
            aTmpTask.setPresentationName("Aimer son programme");
            aTmpTask.setDescription("Un grand moment d'amour tout le monde sait tres bien qu'un developper aime son programme de toutes maniere ne pas aimer son programme est un crime. <br> Aimer ou ne pas aimer est un programme telle est la questin apres tout est ce que le developpeur ne laisse pas une partie de son ame dans son programme. <br> le developpeur ne fait qu'un avec son logiciel il entretient avec lui une union charnelle");
            
            // concrete task
            
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName(aTmpTask.getPresentationName());
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.FINISHED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
            
            // guide
            
            CheckList g1 = new CheckList();
            g1.setName("good_requirements");
            g1.setType(Guidance.checklist);

            Section s1 = new Section();
            s1.setName("Correct");
            s1.setDescription("Does the requirement correctly specify a true need, desire, or obligation?");
            
            Section s2 = new Section();
            s2.setName("Complete");
            s2.setDescription("Is the requirement stated as a complete sentence?");
            
            Section s3 = new Section();
            s3.setName("Clear");
            s3.setDescription("Is the requirement unambiguous and not confusing?");
            
            Section s4 = new Section();
            s4.setName("Consistent");
            s4.setDescription("Is the requirement in conflict with other requirements?");
            
            Set<Section> setSect = new HashSet<Section>();
            setSect.add(s1);
            setSect.add(s2);
            setSect.add(s3);
            setSect.add(s4);
            
            g1.setSections(setSect);
            
            Guidance g2 = new Guidance();
            g2.setName("guide 2");
            g2.setDescription("description du guide 2");
            g2.setType(Guidance.concept);
            
            Guidance g3 = new Guidance();
            g3.setName("guide 3");
            g3.setDescription("description du guide 3");
            g3.setType(Guidance.guideline);
            
            Set<Guidance> sgl = new HashSet<Guidance>();
            sgl.add(g1);
            sgl.add(g2);
            sgl.add(g3);
    			
            // on ajoute ces guides pour test au role visiteur
            rde2.setGuidances(sgl);
            aConcretePhase.getPhase().setGuidances(sgl);
            
            // task def
            
            aTmpTaskDef = new TaskDefinition();
            aTmpTaskDef.setName("Aimer son programme");
            aTmpTaskDef.setDescription("Un grand moment d'amour");
            aTmpTaskDef.setGuidances(sgl);
            
            // step
            
            aTmpStep = new Step();
            aTmpStep.setGuid("14");
            aTmpStep.setName("Debugger son programme");
            aTmpStep.setDescription("Une imcomprehension croissante");
            aTmpTaskDef.addStep(aTmpStep);
            
            aTmpStep = new Step();
            aTmpStep.setGuid("15");
            aTmpStep.setName("Trouver la solution");
            aTmpStep.setDescription("Ca marche !!");
            aTmpTaskDef.addStep(aTmpStep);
            
            aTmpTask.addTaskDefinition(aTmpTaskDef);
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);
            
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcreteRole.addPrimaryConcreteTaskDescriptor(aTmpConcrete);
        
            
            // ------- nouveau
            
            // task desc
            aTmpTask = new TaskDescriptor();
            aTmpTask.setGuid("15");
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
            aTmpConcrete.setRemainingTime(12);
                   
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcreteRole.addPrimaryConcreteTaskDescriptor(aTmpConcrete);
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);
            
            // affectation du role dans l activty
            aTmpConcreteRole.addSuperConcreteActivity(aConcreteActivity);
            aTmpRole.addSuperActivity(anActivity);
            
            // ----- nouveau
            
            
            aTmpRole = new RoleDescriptor();
            
            aTmpTask = new TaskDescriptor();

            
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName("concr_faire_essais");
            aTmpConcrete.setAccomplishedTime((float) 42.9);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.SUSPENDED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    		
            // nouveau role donc nouveau concrete role =>
            aTmpConcreteRole = new ConcreteRoleDescriptor ();
           // role
            aTmpRole.setGuid("18");
            aTmpRole.setPresentationName("Tester");
            aTmpRole.setDescription("Faire des essais, en gros");
            
            // concrete role
            
            aTmpConcreteRole.setConcreteName(aTmpRole.getPresentationName());
            aTmpConcreteRole.setRoleDescriptor(aTmpRole);
            p.addConcreteRoleDescriptor(aTmpConcreteRole);
            
            // task desc
            aTmpTask.setGuid("35");
            aTmpTask.setPresentationName("Tester le programme");
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);
            
            // task def
            
            aTmpTaskDef = new TaskDefinition();
            aTmpTaskDef.setName("Ecrire le test");
            aTmpTaskDef.setDescription("Je devais faire quoi?");
            
            // step
            
            aTmpStep = new Step();
            aTmpStep.setGuid("36");
            aTmpStep.setName("Lancer le test");
            aTmpStep.setDescription("Mais qu'est ce qui se passe?");
            aTmpTaskDef.addStep(aTmpStep);
            
            aTmpStep = new Step();
            aTmpStep.setGuid("37");
            aTmpStep.setName("Trouver la solution");
            aTmpStep.setDescription("Ca marche !!");
            aTmpTaskDef.addStep(aTmpStep);
            
            aTmpTask.addTaskDefinition(aTmpTaskDef);
            
            aTmpTask.setDescription("Un grand moment de solitude");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcreteRole.addPrimaryConcreteTaskDescriptor(aTmpConcrete);
            
            // ---------- nouveau
            
            
            aTmpTask = new TaskDescriptor();

            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setConcreteName("concr_detester le prog");
            aTmpConcrete.setAccomplishedTime(42);
            aTmpConcrete.setPlannedFinishingDate(new Date());
            aTmpConcrete.setState(Constantes.State.FINISHED);
            aTmpConcrete.setPlannedStartingDate(new Date());
            aTmpConcrete.setPlannedTime(24);
    			
            aTmpTask.setGuid("39");
            aTmpTask.setPresentationName("Detester le programme");
            aTmpTask.setDescription("Un grand moment de haine");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpTask.addConcreteTaskDescriptor(aTmpConcrete);
            aTmpConcrete.setConcreteName(aTmpTask.getPresentationName());

            // ------------ nouveau
            
            aTmpTask = new TaskDescriptor();
            aTmpTask.setGuid("40");
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
            aTmpRole.setGuid("40");
            aTmpRole.setPresentationName("Conceptualisateur");
            aTmpConcreteRole.setConcreteName(aTmpRole.getPresentationName());
            
            aTmpTask = new TaskDescriptor();
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpTask.setGuid("42");
            aTmpTask.setPresentationName("Conceptualiser les concepts du programme");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcrete.setTaskDescriptor(aTmpTask);
            aTmpConcrete.setConcreteName(aTmpTask.getPresentationName());
            aTmpConcreteRole.addPrimaryConcreteTaskDescriptor(aTmpConcrete);
            
            aTmpTask = new TaskDescriptor();
            aTmpTask.setGuid("43");
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpTask.setPresentationName("Rever du programme");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcrete.setTaskDescriptor(aTmpTask);
            aTmpConcrete.setConcreteName(aTmpTask.getPresentationName());
            aTmpConcreteRole.addPrimaryConcreteTaskDescriptor(aTmpConcrete);
            
            aTmpTask = new TaskDescriptor();
            aTmpTask.setGuid("44");
            aTmpConcrete = new ConcreteTaskDescriptor();
            aTmpConcrete.setState(Constantes.State.READY);
            aTmpTask.setPresentationName("Faire le cafe concept");
            aTmpRole.addPrimaryTask(aTmpTask);
            aTmpConcrete.setTaskDescriptor(aTmpTask);
            aTmpConcrete.setConcreteName(aTmpTask.getPresentationName());
            aTmpConcreteRole.addPrimaryConcreteTaskDescriptor(aTmpConcrete);
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
            aTmpTask.setGuid("42");
            aTmpRole.setGuid("45");
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
            aTmpConcrete.setConcreteName(aTmpTask.getPresentationName());
            aTmpConcreteRole.addPrimaryConcreteTaskDescriptor(aTmpConcrete);
            
            
            p.addConcreteRoleDescriptor(aTmpConcreteRole);
            
            // affectation du role dans l activty
            aTmpConcreteRole.addSuperConcreteActivity(aConcretePhase);
            aTmpRole.addSuperActivity(aPhase);
           
            return p;
        }
}
