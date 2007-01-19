package wilos.test.business.services.util.xml.parser;


import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;
import wilos.business.services.util.xml.parser.XMLParser;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

public class XMLParserTest extends TestCase {
	public static File pathScrum = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "scrum.xml"); 
	public static File pathOPenUP =new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "sortieEPF.xml");
	public static File pathMonTest =new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "monTest.xml");
	public static File fileError = new File("prout");

	public void testReturnEmptyIfFileEmpty() {
		Set<Process> processes;
		processes = XMLParser.getAllProcesses(fileError);
		assertTrue(processes.size() == 0);
	}
	
	public void testFindsProcessInRegularFiles() {
		Set<Process> processes;
		
		processes= XMLParser.getAllProcesses(pathScrum);
		assertTrue(processes.size() == 1);
		
		processes = XMLParser.getAllProcesses(pathOPenUP);
		assertTrue(processes.size() == 1);
	}
	
	public void testProcessFromOpenUPHasTheRightName() {
		HashSet<Process> processes;
		Iterator<Process> it;
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathOPenUP);
		it = processes.iterator();
		assertTrue(it.hasNext());
		assertTrue(it.next().getPresentationName().equals("OpenUP/Basic Lifecycle"));
	}
	
	public void testProcessFromScrumHasTheRightName() {
		HashSet<Process> processes;
		Iterator<Process> it;
		Process theScrumProcess;
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
		it = processes.iterator();
		assertTrue(it.hasNext());
		theScrumProcess = it.next();
		assertTrue( theScrumProcess.getPresentationName().equals("Cycle de vie Scrum") );
		assertTrue( theScrumProcess.getName().equals("Scrum") );
		assertTrue( theScrumProcess.getIsPlanned() );
		assertTrue( ! theScrumProcess.getIsOptional() );
		assertTrue( theScrumProcess.getIsRepeatable() );
		assertTrue( ! theScrumProcess.getIsOngoing() );
		assertTrue( theScrumProcess.getPrefix().equals("") );
		assertTrue( theScrumProcess.getDescription().equals("Les phases, les sprints et les tâches dans la production d'une release") );
		assertTrue( theScrumProcess.getGuid().equals("_9llsAQAvEdubGMceRDupFQ") );
		assertTrue( ! theScrumProcess.getHasMultipleOccurrences() );
		assertTrue( ! theScrumProcess.getIsEvenDriven() );		
	}
	
	public void testProcessFromScrumFileContainsPhases() {
		HashSet<Process> processes;
		Iterator<Process> it;
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
		it = processes.iterator();
		assertTrue(it.hasNext());
		while (it.hasNext()) {
			assertTrue(it.next().getBreakDownElements().size() > 0);
		}
		
	}
	
	/**
	 * 
	 * passe dans tous les cas a cause de l'implementation faite par Woops
	 */
	public void testPhasesFromScrumContainBreakDownElements() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<BreakdownElement> itAct;
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
		itProc = processes.iterator();
		assertTrue(itProc.hasNext());
		while (itProc.hasNext()) {
			itAct = itProc.next().getBreakDownElements().iterator();
			
			assertTrue(itAct.hasNext());
			while (itAct.hasNext()) {
				BreakdownElement tmpBDE = itAct.next();
				assertTrue(tmpBDE instanceof Activity);
				
				assertTrue(((Activity) tmpBDE).getBreakDownElements().size() > 0);
			}
		}
	}
	
	public void testPhase1FromScrumContainsRoleDescriptors() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<BreakdownElement> itAct;
		Activity at;
		HashSet<BreakdownElement> bdeSet;
		Iterator<BreakdownElement> itBde;
		boolean isThereARoleDesc;
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
		itProc = processes.iterator();
		assertTrue(itProc.hasNext());
		while (itProc.hasNext()) {
			itAct = itProc.next().getBreakDownElements().iterator();
			
				// Only the first Phase has role Descriptors !!!
				assertTrue(itAct.hasNext());
				Activity tmpAct = (Activity) itAct.next();
				if (! tmpAct.getPresentationName().equals("Phase de préparation")) {
					assertTrue(itAct.hasNext());
					tmpAct = (Activity) itAct.next();
				}
				
				if (tmpAct instanceof Phase) {
					bdeSet = (HashSet<BreakdownElement>) tmpAct.getBreakDownElements();
					itBde = bdeSet.iterator();
					
					//assertTrue(itBde.hasNext());
					isThereARoleDesc = false;
					while (itBde.hasNext()) {
						if (itBde.next() instanceof RoleDescriptor)
							isThereARoleDesc = true;
					}
					assertTrue(isThereARoleDesc);
				}
				else {
					fail();
				}
		}
	}
	
	public void testPhase1FromScrumContainsTaskDescriptors() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<BreakdownElement> itAct;
		Activity at;
		HashSet<BreakdownElement> bdeSet;
		Iterator<BreakdownElement> itBde;
		boolean isThereATaskDesc;
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
		itProc = processes.iterator();
		assertTrue(itProc.hasNext());
		while (itProc.hasNext()) {
			itAct = itProc.next().getBreakDownElements().iterator();
			
				// Only the first Phase has role Descriptors !!!
				assertTrue(itAct.hasNext());
				Activity tmpAct = (Activity) itAct.next();
				if (! tmpAct.getPresentationName().equals("Phase de préparation")) {
					assertTrue(itAct.hasNext());
					tmpAct = (Activity) itAct.next();
				}
			
				if (tmpAct instanceof Phase) {
					bdeSet = (HashSet<BreakdownElement>) tmpAct.getBreakDownElements();
					itBde = bdeSet.iterator();
					
					//assertTrue(itBde.hasNext());
					isThereATaskDesc = false;
					while (itBde.hasNext()) {
						if (itBde.next() instanceof TaskDescriptor)
							isThereATaskDesc = true;
					}
					assertTrue(isThereATaskDesc);
				}
		}
	}
	
	public void testPhase2FromScrumContains1Iteration() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<BreakdownElement> itAct;
		HashSet<BreakdownElement> actSet;
		Iterator<BreakdownElement> itAct2;
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
		itProc = processes.iterator();
		assertTrue(itProc.hasNext());
		while (itProc.hasNext()) {
			// Iterator on the set of the two Phases of Scrum
			itAct = itProc.next().getBreakDownElements().iterator();
			
//			 We work on the second Phase
			assertTrue(itAct.hasNext());
			Activity tmpAct = (Activity) itAct.next();
			if (tmpAct.getPresentationName().equals("Phase de préparation")) {
				assertTrue(itAct.hasNext());
				tmpAct = (Activity) itAct.next();
			}
			
			Phase secondPhase = (Phase) tmpAct;
			// We get the set of activities of the second Phase
			actSet = (HashSet<BreakdownElement>) secondPhase.getBreakDownElements();
			// And an iterator on it
			itAct2 = actSet.iterator();
			
			// There is only one Iteration in the second Phase
			assertTrue(itAct2.hasNext());
			assertTrue(itAct2.next() instanceof Iteration);
			assertTrue(! itAct2.hasNext());
		}
	}
	
	public void testPhase2IterationFromScrumContainsAllExpected() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<BreakdownElement> itAct;
		HashSet<BreakdownElement> secondPhaseActivities;
		Iterator<BreakdownElement> secondPhaseActivitiesIterator;
		
		HashSet<String> expectedResults = new HashSet<String>();
		expectedResults.add("Retrospective");
		expectedResults.add("Product Owner");
		expectedResults.add("ScrumMaster");
		expectedResults.add("Update product backlog");
		expectedResults.add("StakeHolder");
		expectedResults.add("Team");
		expectedResults.add("Manage problems");
		expectedResults.add("Review sprint");
		expectedResults.add("Scrum daily meeting");
		expectedResults.add("Daily work");
		expectedResults.add("Plan sprint"); 
		
		int expectedNumber = expectedResults.size();
		
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
		itProc = processes.iterator();
		assertTrue(itProc.hasNext());
		while (itProc.hasNext()) {
			// Iterator on the set of the two Phases of Scrum
			itAct = itProc.next().getBreakDownElements().iterator();
			
//			 We work on the second Phase
			assertTrue(itAct.hasNext());
			Activity tmpAct = (Activity) itAct.next();
			if (tmpAct.getPresentationName().equals("Phase de préparation")) {
				assertTrue(itAct.hasNext());
				tmpAct = (Activity) itAct.next();
			}
			
			Phase secondPhase = (Phase) tmpAct;
			// We get the set of activities of the second Phase
			secondPhaseActivities = (HashSet<BreakdownElement>) secondPhase.getBreakDownElements();
			// And an iterator on it
			secondPhaseActivitiesIterator = secondPhaseActivities.iterator();
			
			assertTrue(secondPhaseActivitiesIterator.hasNext());
			
			Iteration secondPhaseIteration = (Iteration) secondPhaseActivitiesIterator.next();
			
			Iterator<BreakdownElement> it;
			
			it = secondPhaseIteration.getBreakDownElements().iterator();
			
			int i = 0;
			while (it.hasNext()) {
				String tmpString = ((BreakdownElement) it.next()).getName() ;
				assertTrue(expectedResults.contains( tmpString ));
				i += 1;
			}
			assertTrue(i == expectedNumber);
		}
	}
	
	public void testOpenUPContains4Activities() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<BreakdownElement> itAct;
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathOPenUP);
		itProc = processes.iterator();
		assertTrue(itProc.hasNext());
		while (itProc.hasNext()) {
			// Iterator on the set of the four Phases of OpenUP
			itAct = itProc.next().getBreakDownElements().iterator();
			
			// Activity 1
			assertTrue(itAct.hasNext());
			itAct.next();
			
//			 Activity 2
			assertTrue(itAct.hasNext());
			itAct.next();
			
//			 Activity 3
			assertTrue(itAct.hasNext());
			itAct.next();
			
//			 Activity 4
			assertTrue(itAct.hasNext());
			itAct.next();
		}
		
	}
	
	public void testOpenUPTopLevelActivitiesContainActivities() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<Activity> itTopLevelAct;
		Activity topLevelActivity;
		final int nbMiniSndLevelActivities = 4;
		final int nbMaxiSndLevelActivities = 6;
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathOPenUP);
		itProc = processes.iterator();
		assertTrue(itProc.hasNext());
		while (itProc.hasNext()) {
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = itProc.next().getActivities().iterator();
			
			// Activity 1
			while (itTopLevelAct.hasNext()) {
				topLevelActivity = itTopLevelAct.next();				
				assertTrue(topLevelActivity.getActivities().size() >= nbMiniSndLevelActivities);
				assertTrue(topLevelActivity.getActivities().size() <= nbMaxiSndLevelActivities);
			}
		}
	}
	
	public void testOpenUPInitiateProjectContainsExpectedRoleDescriptors() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<BreakdownElement> itTopLevelAct;
		Iterator<BreakdownElement> itSecondLevelAct;
		Activity topLevelActivity;
		Activity secondLevelActivity;
		boolean rentreDansInitiateProject;
		int nbRoleDescriptors;
		Iterator<BreakdownElement> BdeIterator;
		BreakdownElement tmpBde;
		
		HashSet<String> expectedResults = new HashSet<String>();
		expectedResults.add("Stakeholder");
		expectedResults.add("Project Manager");
		expectedResults.add("Analyst");
		expectedResults.add("Tester");
		expectedResults.add("Architect");
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathOPenUP);
		itProc = processes.iterator();
		assertTrue(itProc.hasNext());
		while (itProc.hasNext()) {
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = itProc.next().getBreakDownElements().iterator();
			
			// Activity 1
			assertTrue(itTopLevelAct.hasNext());
			
			topLevelActivity = null;
			// We want the third Phase : Construction Iteration
			while (itTopLevelAct.hasNext()) {
				topLevelActivity = (Activity) itTopLevelAct.next();
				if (topLevelActivity.getPresentationName().equals("Inception Iteration [1..n]")) {
					break;
				}
			}
			
			itSecondLevelAct = topLevelActivity.getBreakDownElements().iterator();
			
			rentreDansInitiateProject = false;
			while (itSecondLevelAct.hasNext()) {
				secondLevelActivity = (Activity) itSecondLevelAct.next();
				if (secondLevelActivity.getPresentationName().equals("Initiate Project")) {
					rentreDansInitiateProject = true;
					BdeIterator = secondLevelActivity.getBreakDownElements().iterator();
					nbRoleDescriptors = 0;
					while (BdeIterator.hasNext()) {
						tmpBde = BdeIterator.next();
						if (tmpBde instanceof RoleDescriptor) {
							nbRoleDescriptors++;
							tmpBde = (RoleDescriptor) tmpBde;
							assertTrue(expectedResults.contains( tmpBde.getPresentationName() ) );
							//System.out.println(tmpBde.getPresentationName());
						}
					}
					assertTrue(nbRoleDescriptors == 5);
				}
			}
			assertTrue(rentreDansInitiateProject);
		}		
	}
	
	public void testOpenUPManageRequirementsContainsExpectedTaskDescriptors() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<BreakdownElement> itTopLevelAct;
		Iterator<BreakdownElement> itSecondLevelAct;
		Activity topLevelActivity,secondLevelActivity;
		boolean rentreDansManageRequirements;
		int nbTaskDescriptors;
		Iterator<BreakdownElement> BdeIterator;
		BreakdownElement tmpBde;
		
		HashSet<String> expectedResults = new HashSet<String>();
		expectedResults.add("Find and Outline Requirements");
		expectedResults.add("Detail Requirements");
		expectedResults.add("Create Test Cases");
		
		processes = (HashSet<Process>) XMLParser.getAllProcesses(pathOPenUP);
		itProc = processes.iterator();
		assertTrue(itProc.hasNext());
		while (itProc.hasNext()) {
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = itProc.next().getBreakDownElements().iterator();
			
			topLevelActivity = null;
			// We want the third Phase : Construction Iteration
			while (itTopLevelAct.hasNext()) {
				topLevelActivity = (Activity) itTopLevelAct.next();
				if (topLevelActivity.getPresentationName().equals("Construction Iteration [1..n]")) {
					break;
				}
			}
			
			itSecondLevelAct = topLevelActivity.getBreakDownElements().iterator();
			
			rentreDansManageRequirements = false;
			while (itSecondLevelAct.hasNext()) {
				secondLevelActivity = (Activity) itSecondLevelAct.next();
				if (secondLevelActivity.getPresentationName().equals("Manage Requirements")) {
					rentreDansManageRequirements = true;
					BdeIterator = secondLevelActivity.getBreakDownElements().iterator();
					nbTaskDescriptors = 0;
					while (BdeIterator.hasNext()) {
						tmpBde = BdeIterator.next();
						if (tmpBde instanceof TaskDescriptor) {
							nbTaskDescriptors++;
							tmpBde = (TaskDescriptor) tmpBde;
							assertTrue(expectedResults.contains( tmpBde.getPresentationName() ) );
						}
					}
					assertTrue(nbTaskDescriptors == 3);
				}
			}
			assertTrue(rentreDansManageRequirements);
		}		
	}
	

	
	public void testOpenUPManageRequirementsContainsExpectedTaskDescriptors2() {
		Process theTestedProcess;
		Iterator<BreakdownElement> itTopLevelAct;
		Iterator<BreakdownElement> itSecondLevelAct;
		Activity topLevelActivity,secondLevelActivity;
		boolean rentreDansManageRequirements;
		int nbTaskDescriptors;
		Iterator<BreakdownElement> BdeIterator;
		BreakdownElement tmpBde;
		
		HashSet<String> expectedResults = new HashSet<String>();
		expectedResults.add("Find and Outline Requirements");
		expectedResults.add("Detail Requirements");
		expectedResults.add("Create Test Cases");
		
		theTestedProcess = XMLParser.getProcess(pathOPenUP);
		assertNotNull(theTestedProcess);
		if (theTestedProcess != null) {
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = theTestedProcess.getBreakDownElements().iterator();
			
			topLevelActivity = null;
			// We want the third Phase : Construction Iteration
			while (itTopLevelAct.hasNext()) {
				topLevelActivity = (Activity) itTopLevelAct.next();
				if (topLevelActivity.getPresentationName().equals("Construction Iteration [1..n]")) {
					break;
				}
			}
			
			itSecondLevelAct = topLevelActivity.getBreakDownElements().iterator();
			
			rentreDansManageRequirements = false;
			while (itSecondLevelAct.hasNext()) {
				secondLevelActivity = (Activity) itSecondLevelAct.next();
				if (secondLevelActivity.getPresentationName().equals("Manage Requirements")) {
					rentreDansManageRequirements = true;
					BdeIterator = secondLevelActivity.getBreakDownElements().iterator();
					nbTaskDescriptors = 0;
					while (BdeIterator.hasNext()) {
						tmpBde = BdeIterator.next();
						if (tmpBde instanceof TaskDescriptor) {
							nbTaskDescriptors++;
							tmpBde = (TaskDescriptor) tmpBde;
							assertTrue(expectedResults.contains( tmpBde.getPresentationName() ) );
						}
					}
					assertTrue(nbTaskDescriptors == 3);
				}
			}
			assertTrue(rentreDansManageRequirements);
		}		
	}
	
	/*
	public void testGetProcess(){
		Process p;		
//		 test avec un fichier XML a nous
		try {
			p = XMLParser.getProcess(pathMonTest );
			RoleDescriptor rdXML ;
			RoleDefinition rdfXML ;
			TaskDescriptor tdXML ;
			TaskDefinition tdfXML ;
			
			RoleDefinition notreRdef = new RoleDefinition() ;
			notreRdef.setGuid("1");
			notreRdef.setName("Product Owner");
			notreRdef.setDescription("petite description");
			
			RoleDescriptor notreRdesc = new RoleDescriptor() ;
			notreRdesc.setName("ScrumMaster");
			notreRdesc.setGuid("5");
			notreRdesc.setIsOptional(false);
			notreRdesc.setIsPlanned(true);
			notreRdesc.addRoleDefinition(notreRdef);
			notreRdesc.setPrefix("");
			
			Step s1 = new Step();
			s1.setGuid("3");
			s1.setName("Definir le but du sprint");
			s1.setDescription("Ceci est la Description de ma Section.");
			
			TaskDefinition notreTdef = new TaskDefinition() ;
			notreTdef.setGuid("2");
			notreTdef.setName("Plan sprint");
			notreTdef.setDescription("Planification du court terme");
			notreTdef.addStep(s1);
			
			TaskDescriptor notreTdesc = new TaskDescriptor () ;
			notreTdesc.setGuid("4");
			notreTdesc.setName("Plan sprint");
			notreTdesc.setHasMultipleOccurrences(false);
			notreTdesc.setIsPlanned(false);
			notreTdesc.setPrefix("");
			notreTdesc.setIsEvenDriven(false);
			notreTdesc.setIsOngoing(false);
			notreTdesc.setIsRepeatable(false);
			notreTdesc.addMainRole(notreRdesc);
			notreTdesc.addAdditionalRole(notreRdesc);
			notreTdesc.addTaskDefinition(notreTdef);
			
			for (BreakdownElement t : p.getBreakDownElements()){
				if (t instanceof TaskDescriptor){
					TaskDescriptor td = (TaskDescriptor) t ;
					tdXML = td ;
					tdfXML = td.getTaskDefinition();
					assertTrue(td.getGuid().equals(notreTdesc.getGuid() )) ;
					assertTrue(td.getPrefix().equals(notreTdesc.getPrefix() )) ;
					assertTrue(td.getDescription().equals(notreTdesc.getDescription() )) ;
					assertTrue(td.getIsEvenDriven().equals(notreTdesc.getIsEvenDriven() )) ;
					assertTrue(td.getIsOngoing().equals(notreTdesc.getIsOngoing()) ) ;
					assertTrue(td.getIsOptional().equals(notreTdesc.getIsOptional()) ) ;
					assertTrue(td.getIsPlanned().equals(notreTdesc.getIsPlanned()) ) ;
					assertTrue(td.getIsRepeatable().equals(notreTdesc.getIsRepeatable()) ) ;
					assertTrue(td.getTaskDefinition().getGuid().equals(notreTdesc.getTaskDefinition().getGuid()));
					assertTrue(td.getTaskDefinition().getName().equals(notreTdesc.getTaskDefinition().getName()));
					assertTrue(td.getHasMultipleOccurrences().equals(notreTdesc.getHasMultipleOccurrences()));
					assertTrue(td.getMainRole().getGuid().equals(notreTdesc.getMainRole().getGuid()));
					for (RoleDescriptor stmp : td.getAdditionalRoles()){
						assertTrue(stmp.getGuid().equals(notreRdesc.getGuid()));
					}
					for (TaskDescriptor stmp : td.getTaskDefinition().getTaskDescriptors()){
						assertTrue(stmp.equals(tdXML));
						assertTrue(stmp.getGuid().equals(notreTdesc.getGuid()));
						assertTrue(stmp.getDescription().equals(notreTdesc.getDescription()));
						assertTrue(stmp.getName().equals(notreTdesc.getName()));
						assertTrue(stmp.getTaskDefinition().getGuid().equals(notreTdesc.getTaskDefinition().getGuid()));
					}
					for (Step stmp : td.getTaskDefinition().getSteps()){
						assertTrue(stmp.getGuid().equals(s1.getGuid()));
						assertTrue(stmp.getDescription().equals(s1.getDescription()));
						assertTrue(stmp.getName().equals(s1.getName()));
						assertTrue(stmp.getTaskDefinition().getGuid().equals(s1.getTaskDefinition().getGuid()));
						
					}
				}
				else if (t instanceof RoleDescriptor) {
					RoleDescriptor td = (RoleDescriptor) t ;
					rdXML = td ;
					rdfXML = td.getRoleDefinition();
					assertTrue(td.getGuid().equals(notreRdesc.getGuid() )) ;
					assertTrue(td.getDescription().equals(notreRdesc.getDescription() )) ;
					assertTrue(td.getIsOptional().equals(notreRdesc.getIsOptional() )) ;
					assertTrue(td.getIsPlanned().equals(notreRdesc.getIsPlanned() )) ;
					assertTrue(td.getName().equals(notreRdesc.getName() )) ;
					assertTrue(td.getPrefix().equals(notreRdesc.getPrefix() )) ;
					assertTrue(td.getRoleDefinition().getGuid().equals(notreRdesc.getRoleDefinition().getGuid()));
					assertTrue(td.getRoleDefinition().getDescription().equals(notreRdesc.getRoleDefinition().getDescription()));
					assertTrue(td.getRoleDefinition().getName().equals(notreRdesc.getRoleDefinition().getName()));
					for (RoleDescriptor def : td.getRoleDefinition().getRoleDescriptors()){
						assertTrue(def.equals(rdXML)); 
					}
				}
			}
			
		} catch (Exception e) {
			fail();
		}
		try {
			p = XMLParser.getProcess(fileError);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		File[] f = new File[]{
				pathScrum, pathOPenUP
		};
		for (int i = 0 ; i < f.length ; i++){
			
			//System.out.println(">>>>>>>"+f[i].getName());;
			try {
				p = XMLParser.getProcess(f[i]);
				assertTrue(p.getBreakDownElements().size() != 0);
				if (f[i].getName().equals("scrum.xml")){
					// nombre de role dans scrum.xml
					//assertTrue(RoleList.size() == 4) ;
					// nombre de task dans scrum.xml
					//assertTrue(TasksList.size() == 8) ;
					// nombre de role descriptor dans scrum.xml
					assertTrue(p.getBreakDownElements().size() == 35); 
				}
				else if(f[i].getName().equals("sortieEPF.xml")){
					// nombre de role dans sortieEPF.xml
					//assertTrue(RoleList.size() == 13) ;
					// nombre de task dans sortieEPF.xml
					//assertTrue(TasksList.size() == 23) ;
					// nombre de role descriptor dans sortieEPF.xml
					assertTrue(p.getBreakDownElements().size() == 58);
				}
				// nombre de taskdef dans les taskdescriptor
				Vector<TaskDefinition > vectorTaskDef = new Vector () ;
				Vector<Step> vectorStep = new Vector() ;
				Vector<RoleDefinition > vectorRoleDef = new Vector () ;
				for (BreakdownElement t : p.getBreakDownElements()){
					if (t instanceof TaskDescriptor){
						TaskDescriptor td = (TaskDescriptor) t ;
						TaskDefinition tdef = td.getTaskDefinition() ;
						if (tdef != null){
							vectorTaskDef.add(td.getTaskDefinition());
							for (Step s : td.getTaskDefinition().getSteps()){
								//System.out.println(s.getIdEPF()) ;
								vectorStep.add(s);
							}
						}
					}
					else if (t instanceof RoleDescriptor){
						RoleDescriptor td = (RoleDescriptor) t ;
						RoleDefinition tdef = td.getRoleDefinition() ;
						if (tdef != null){
							vectorRoleDef.add(td.getRoleDefinition());
						}
					}
				}
				assertTrue(vectorTaskDef.size()!=0);
				assertTrue(vectorStep.size()!=0);
				assertTrue(vectorRoleDef.size()!=0);
			} catch (Exception e) {
				fail();
			}
		}
	}
	
}
			//assertTrue(TasksList.size() == 8) ;
					// nombre de role descriptor dans scrum.xml
					assertTrue(p.getBreakDownElements().size() == 35); 
				}
				else if(f[i].getName().equals("sortieEPF.xml")){
					// nombre de role dans sortieEPF.xml
					//assertTrue(RoleList.size() == 13) ;
					// nombre de task dans sortieEPF.xml
					//assertTrue(TasksList.size() == 23) ;
					// nombre de role descriptor dans sortieEPF.xml
					assertTrue(p.getBreakDownElements().size() == 58);
				}
				// nombre de taskdef dans les taskdescriptor
				Vector<TaskDefinition > vectorTaskDef = new Vector () ;
				Vector<Step> vectorStep = new Vector() ;
				Vector<RoleDefinition > vectorRoleDef = new Vector () ;
				for (BreakdownElement t : p.getBreakDownElements()){
					if (t instanceof TaskDescriptor){
						TaskDescriptor td = (TaskDescriptor) t ;
						TaskDefinition tdef = td.getTaskDefinition() ;
						if (tdef != null){
							vectorTaskDef.add(td.getTaskDefinition());
							for (Step s : td.getTaskDefinition().getSteps()){
								//System.out.println(s.getIdEPF()) ;
								vectorStep.add(s);
							}
						}
					}
					else if (t instanceof RoleDescriptor){
						RoleDescriptor td = (RoleDescriptor) t ;
						RoleDefinition tdef = td.getRoleDefinition() ;
						if (tdef != null){
							vectorRoleDef.add(td.getRoleDefinition());
						}
					}
				}
				assertTrue(vectorTaskDef.size()!=0);
				assertTrue(vectorStep.size()!=0);
				assertTrue(vectorRoleDef.size()!=0);
			} catch (Exception e) {
				fail();
			}
		}
	}*/
	
	
	public void testOpenUPContainsGuidances() {
		
	}
	
}
