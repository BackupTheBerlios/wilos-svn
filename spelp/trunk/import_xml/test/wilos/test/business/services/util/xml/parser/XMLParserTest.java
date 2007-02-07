package wilos.test.business.services.util.xml.parser;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;
import wilos.business.services.util.xml.parser.XMLParser;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

public class XMLParserTest extends TestCase {
	public static File pathScrum = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "scrum.xml"); 
	public static File pathOPenUP =new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "sortieEPF.xml");
	public static File pathMonTest =new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "monTest.xml");
	public static File pathFileError = new File("noFile");
	public static File pathScrumWithArte = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "scrum_with_ArteF.xml"); 
	public static File pathEmptyFile = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "emptyFile.xml"); 
	
	/**
	 * Checks that the ProcessReturned is null if the file doesnt exist
	 *
	 */
	public void testReturnNullIfFileError() {		
		Process theProcess;
		theProcess = XMLParser.getProcess(pathFileError);
		assertTrue(theProcess == null);
	}
	
	/**
	 * Checks that the ProcessReturned is null if the file is empty
	 *
	 */
	public void testReturnsNullIfFileEmpty() {
		Process theProcess;
		theProcess = XMLParser.getProcess(pathEmptyFile);
		assertTrue(theProcess == null);
	}
	
	/**
	 * Checks that the Regular Files (Scrum and OpenUp) contain one Process
	 *
	 */
	public void testFindsProcessInRegularFiles() {
		Process theProcess;
		
		theProcess = XMLParser.getProcess(pathScrum);
		assertTrue(theProcess != null);
		
		theProcess = XMLParser.getProcess(pathOPenUP);
		assertTrue(theProcess != null);
	}
	
	/**
	 * Checks that the OpenUp Process has the right Name and the right Presentation Name
	 *
	 */
	public void testProcessFromOpenUPHasTheRightNames() {
		Process theProcess;
		
		theProcess = XMLParser.getProcess(pathOPenUP);
		assertTrue(theProcess.getPresentationName().equals("OpenUP/Basic Lifecycle"));
		assertTrue(theProcess.getName().equals("openup_basic_process"));
	}
	
	/**
	 * Checks that the Scrum Process has the right Attributes 
	 *
	 */
	public void testProcessFromScrumHasTheRightAttributes() {
		Process theScrumProcess;
		
		theScrumProcess = XMLParser.getProcess(pathScrum);
		assertTrue( theScrumProcess.getPresentationName().equals("Cycle de vie Scrum") );
		assertTrue( theScrumProcess.getName().equals("Scrum") );
		assertTrue( theScrumProcess.getIsPlanned() );
		assertTrue( ! theScrumProcess.getIsOptional() );
		assertTrue( theScrumProcess.getIsRepeatable() );
		assertTrue( ! theScrumProcess.getIsOngoing() );
		assertTrue( theScrumProcess.getPrefix().equals("") );
		assertTrue( theScrumProcess.getDescription().equals("Les phases, les sprints et les t�ches dans la production d'une release") );
		assertTrue( theScrumProcess.getGuid().equals("_9llsAQAvEdubGMceRDupFQ") );
		assertTrue( ! theScrumProcess.getHasMultipleOccurrences() );
		assertTrue( ! theScrumProcess.getIsEvenDriven() );		
	}
	
	/**
	 * Checks that the Scrum Process Contains two top-level Phases
	 *
	 */
	public void testProcessFromScrumFileContains2Phases() {
		Process process;
		Iterator<BreakdownElement> itBde;
		// We expect 2 Phases and no oteration
		int nbExpectedPhases = 2;
		int nbExpectedIterations = 0;

		process =  XMLParser.getProcess(pathScrum);
		// The Process must contains more than 0 Bde
		assertTrue(process.getBreakdownElements().size() > 0);
		
		int nbPhases = 0;
		int nbIterations = 0;
		itBde = process.getBreakdownElements().iterator();
		// For each Bde in the Process
		while (itBde.hasNext()) {
			if (itBde.next() instanceof Phase) {
				nbPhases += 1;
			}
			else if (itBde.next() instanceof Iteration) {
				nbPhases += 1;
			}
		}
		// And now... the checks !!!
		assertTrue(nbPhases == nbExpectedPhases);
		assertTrue(nbIterations == nbExpectedIterations);
		
	}
	
	/**
	 * Checks that the phases from Scrum contain BDEs
	 * 
	 * Always good due to the implementation done by the Woops
	 */
	public void testPhasesFromScrumContainBreakDownElements() {
		Iterator<BreakdownElement> itAct;
		Process process;

		process = XMLParser.getProcess(pathScrum);
		itAct = process.getBreakdownElements().iterator();
		
		assertTrue(itAct.hasNext());
		while (itAct.hasNext()) {
			BreakdownElement tmpBDE = itAct.next();
			assertTrue(tmpBDE instanceof Activity);
			
			assertTrue(((Activity) tmpBDE).getBreakdownElements().size() > 0);
		}
	}
	
	public void testPhase1FromScrumContainsRoleDescriptors() {
		Iterator<BreakdownElement> itAct;
		HashSet<BreakdownElement> bdeSet;
		Iterator<BreakdownElement> itBde;
		boolean isThereARoleDesc;
		
		Process process;

		process = XMLParser.getProcess(pathScrum);
		itAct = process.getBreakdownElements().iterator();
		
		// Only the first Phase has role Descriptors !!!
		assertTrue(itAct.hasNext());
		Activity tmpAct = (Activity) itAct.next();
		if (! tmpAct.getPresentationName().equals("Phase de pr�paration")) {
			assertTrue(itAct.hasNext());
			tmpAct = (Activity) itAct.next();
		}
		
		if (tmpAct instanceof Phase) {
			bdeSet = (HashSet<BreakdownElement>) tmpAct.getBreakdownElements();
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
	
	public void testPhase1FromScrumContainsTaskDescriptors() {
		Iterator<BreakdownElement> itAct;
		HashSet<BreakdownElement> bdeSet;
		Iterator<BreakdownElement> itBde;
		boolean isThereATaskDesc;
		
		Process scrumProcess;
		
		scrumProcess = XMLParser.getProcess(pathScrum);
		itAct = scrumProcess.getBreakdownElements().iterator();
		
		// Only the first Phase has role Descriptors !!!
		assertTrue(itAct.hasNext());
		Activity tmpAct = (Activity) itAct.next();
		if (! tmpAct.getPresentationName().equals("Phase de pr�paration")) {
			assertTrue(itAct.hasNext());
			tmpAct = (Activity) itAct.next();
		}
	
		if (tmpAct instanceof Phase) {
			bdeSet = (HashSet<BreakdownElement>) tmpAct.getBreakdownElements();
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
	
	public void testPhase2FromScrumContains1Iteration() {
		Iterator<BreakdownElement> itAct;
		HashSet<BreakdownElement> actSet;
		Iterator<BreakdownElement> itAct2;
		
		Process scrumProcess;
		
		scrumProcess = XMLParser.getProcess(pathScrum);
		
		// Iterator on the set of the two Phases of Scrum
		itAct = scrumProcess.getBreakdownElements().iterator();
		
		// We work on the second Phase
		assertTrue(itAct.hasNext());
		Activity tmpAct = (Activity) itAct.next();
		if (tmpAct.getPresentationName().equals("Phase de pr�paration")) {
			assertTrue(itAct.hasNext());
			tmpAct = (Activity) itAct.next();
		}
		
		Phase secondPhase = (Phase) tmpAct;
		// We get the set of activities of the second Phase
		actSet = (HashSet<BreakdownElement>) secondPhase.getBreakdownElements();
		// And an iterator on it
		itAct2 = actSet.iterator();
		
		// There is only one Iteration in the second Phase
		assertTrue(itAct2.hasNext());
		assertTrue(itAct2.next() instanceof Iteration);
		assertTrue(! itAct2.hasNext());
	}
	
	public void testPhase2IterationFromScrumContainsAllExpected() {
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
		
		Process scrumProcess;
		
		scrumProcess = XMLParser.getProcess(pathScrum);

		// Iterator on the set of the two Phases of Scrum
		itAct = scrumProcess.getBreakdownElements().iterator();
		
		// We work on the second Phase
		assertTrue(itAct.hasNext());
		Activity tmpAct = (Activity) itAct.next();
		if (tmpAct.getPresentationName().equals("Phase de pr�paration")) {
			assertTrue(itAct.hasNext());
			tmpAct = (Activity) itAct.next();
		}
		
		Phase secondPhase = (Phase) tmpAct;
		// We get the set of activities of the second Phase
		secondPhaseActivities = (HashSet<BreakdownElement>) secondPhase.getBreakdownElements();
		// And an iterator on it
		secondPhaseActivitiesIterator = secondPhaseActivities.iterator();
		
		assertTrue(secondPhaseActivitiesIterator.hasNext());
		
		Iteration secondPhaseIteration = (Iteration) secondPhaseActivitiesIterator.next();
		
		Iterator<BreakdownElement> it;
		
		it = secondPhaseIteration.getBreakdownElements().iterator();
		
		int i = 0;
		while (it.hasNext()) {
			String tmpString = ((BreakdownElement) it.next()).getName() ;
			assertTrue(expectedResults.contains( tmpString ));
			i += 1;
		}
		assertTrue(i == expectedNumber);
	}
	
	public void testOpenUPContains4Activities() {
		Iterator<BreakdownElement> itAct;
		BreakdownElement bde;
		
		Process openUpProcess;
		openUpProcess = XMLParser.getProcess(pathOPenUP);
		
		// Iterator on the set of the four Phases of OpenUP
		itAct = openUpProcess.getBreakdownElements().iterator();
		
		// Activity 1
		assertTrue(itAct.hasNext());
		bde = itAct.next();
		assertTrue(bde.getClass().getSimpleName().equals("Activity"));
		
		// Activity 2
		assertTrue(itAct.hasNext());
		bde = itAct.next();
		assertTrue(bde.getClass().getSimpleName().equals("Activity"));
		
		// Activity 3
		assertTrue(itAct.hasNext());
		bde = itAct.next();
		assertTrue(bde.getClass().getSimpleName().equals("Activity"));
		
		// Activity 4
		assertTrue(itAct.hasNext());
		bde = itAct.next();
		assertTrue(bde.getClass().getSimpleName().equals("Activity"));
		
		assertFalse(itAct.hasNext());		
	}
	
	public void testOpenUPTopLevelActivitiesContainActivities() {
		Iterator<BreakdownElement> itTopLevelAct;
		Activity topLevelActivity;
		final int nbMiniSndLevelActivities = 4;
		final int nbMaxiSndLevelActivities = 6;
		
		Process openUpProcess;
		openUpProcess = XMLParser.getProcess(pathOPenUP);
		// Iterator on the set of the four Phases of OpenUP
		itTopLevelAct = openUpProcess.getBreakdownElements().iterator();
		
		// Activity 1
		while (itTopLevelAct.hasNext()) {
			topLevelActivity = (Activity) itTopLevelAct.next();
			assertTrue(topLevelActivity.getBreakdownElements().size() >= nbMiniSndLevelActivities);
			assertTrue(topLevelActivity.getBreakdownElements().size() <= nbMaxiSndLevelActivities);
		}
	}
	
	public void testOpenUPInitiateProjectContainsExpectedRoleDescriptors() {
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
		
		Process openUpProcess;
		openUpProcess = XMLParser.getProcess(pathOPenUP);

		// Iterator on the set of the four Phases of OpenUP
		itTopLevelAct = openUpProcess.getBreakdownElements().iterator();
		
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
		
		itSecondLevelAct = topLevelActivity.getBreakdownElements().iterator();
		
		rentreDansInitiateProject = false;
		while (itSecondLevelAct.hasNext()) {
			secondLevelActivity = (Activity) itSecondLevelAct.next();
			if (secondLevelActivity.getPresentationName().equals("Initiate Project")) {
				rentreDansInitiateProject = true;
				BdeIterator = secondLevelActivity.getBreakdownElements().iterator();
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
	
	public void testOpenUPManageRequirementsContainsExpectedTaskDescriptors() {
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
		
		Process openUpProcess;
		openUpProcess = XMLParser.getProcess(pathOPenUP);
		
		// Iterator on the set of the four Phases of OpenUP
		itTopLevelAct = openUpProcess.getBreakdownElements().iterator();
		
		topLevelActivity = null;
		// We want the third Phase : Construction Iteration
		while (itTopLevelAct.hasNext()) {
			topLevelActivity = (Activity) itTopLevelAct.next();
			if (topLevelActivity.getPresentationName().equals("Construction Iteration [1..n]")) {
				break;
			}
		}
		
		itSecondLevelAct = topLevelActivity.getBreakdownElements().iterator();
		
		rentreDansManageRequirements = false;
		while (itSecondLevelAct.hasNext()) {
			secondLevelActivity = (Activity) itSecondLevelAct.next();
			if (secondLevelActivity.getPresentationName().equals("Manage Requirements")) {
				rentreDansManageRequirements = true;
				BdeIterator = secondLevelActivity.getBreakdownElements().iterator();
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
			itTopLevelAct = theTestedProcess.getBreakdownElements().iterator();
			
			topLevelActivity = null;
			// We want the third Phase : Construction Iteration
			while (itTopLevelAct.hasNext()) {
				topLevelActivity = (Activity) itTopLevelAct.next();
				if (topLevelActivity.getPresentationName().equals("Construction Iteration [1..n]")) {
					break;
				}
			}
			
			itSecondLevelAct = topLevelActivity.getBreakdownElements().iterator();
			
			rentreDansManageRequirements = false;
			while (itSecondLevelAct.hasNext()) {
				secondLevelActivity = (Activity) itSecondLevelAct.next();
				if (secondLevelActivity.getPresentationName().equals("Manage Requirements")) {
					rentreDansManageRequirements = true;
					BdeIterator = secondLevelActivity.getBreakdownElements().iterator();
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
	
	public void testScrumWithArteWorks() {
		Process p;
		p = XMLParser.getProcess(pathScrumWithArte );
		assertTrue(p != null);
	}	
	
	public void testPhase2IterationFromScrumWithArteContainsAllExpected() {
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
		
		int expectedNumber = 11;
		
		Process process = XMLParser.getProcess(pathScrumWithArte);
		// Iterator on the set of the two Phases of Scrum
		itAct = process.getBreakdownElements().iterator();
		
		// We work on the second Phase
		BreakdownElement tmpAct = itAct.next();
		
		assertTrue(tmpAct instanceof Phase);
		if (tmpAct.getPresentationName().equals("Phase de pr�paration")) {
			assertTrue(itAct.hasNext());
			tmpAct = itAct.next();
		}
		
		Phase secondPhase = (Phase) tmpAct;
		// We get the set of activities of the second Phase
		secondPhaseActivities = (HashSet<BreakdownElement>) secondPhase.getBreakdownElements();
		// And an iterator on it
		secondPhaseActivitiesIterator = secondPhaseActivities.iterator();
		
		assertTrue(secondPhaseActivitiesIterator.hasNext());
		BreakdownElement tmpBde =  secondPhaseActivitiesIterator.next();
		
		while (! (tmpBde instanceof Iteration) && secondPhaseActivitiesIterator.hasNext()) {
			tmpBde =  secondPhaseActivitiesIterator.next();
		}
		
		assertTrue(tmpBde instanceof Iteration);
		Iteration secondPhaseIteration = (Iteration) tmpBde;
		
		Iterator<BreakdownElement> it;
		
		it = secondPhaseIteration.getBreakdownElements().iterator();
		
		int i = 0;
		while (it.hasNext()) {
			assertTrue(expectedResults.contains( ((BreakdownElement) it.next()).getName() ) );
			i += 1;
		}
		
		assertTrue(i == expectedNumber);
	}
	
	public void testOpenUPContainsGuidances() {
		Process theTestedProcess;
		Iterator<BreakdownElement> itTopLevelAct;
		Iterator<BreakdownElement> itSecondLevelAct;
		Activity topLevelActivity,secondLevelActivity;
		boolean rentreDansManageRequirements;
		int nbTaskDescriptors, nbGuidances = 0;
		Iterator<BreakdownElement> BdeIterator;
		BreakdownElement tmpBde;
		
		Set<Guidance> listGuides = null;
		
		HashSet<String> expectedResults = new HashSet<String>();
		expectedResults.add("Find and Outline Requirements");
		expectedResults.add("Detail Requirements");
		expectedResults.add("Create Test Cases");
		
		theTestedProcess = XMLParser.getProcess(pathOPenUP);
		assertNotNull(theTestedProcess);
		if (theTestedProcess != null) {
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = theTestedProcess.getBreakdownElements().iterator();
			
			topLevelActivity = null;
			// We want the third Phase : Construction Iteration
			while (itTopLevelAct.hasNext()) {
				topLevelActivity = (Activity) itTopLevelAct.next();
				if (topLevelActivity.getPresentationName().equals("Construction Iteration [1..n]")) {
					break;
				}
			}
			
			itSecondLevelAct = topLevelActivity.getBreakdownElements().iterator();
			
			rentreDansManageRequirements = false;
			while (itSecondLevelAct.hasNext()) {
				secondLevelActivity = (Activity) itSecondLevelAct.next();
				if (secondLevelActivity.getPresentationName().equals("Manage Requirements")) {
					rentreDansManageRequirements = true;
					BdeIterator = secondLevelActivity.getBreakdownElements().iterator();
					nbTaskDescriptors = 0;
					while (BdeIterator.hasNext()) {
						tmpBde = BdeIterator.next();
						if (tmpBde instanceof TaskDescriptor) {
							nbTaskDescriptors++;
							tmpBde = (TaskDescriptor) tmpBde;
							//td = (TaskDescriptor) tmpBde;
							assertNotNull(tmpBde);
							
							TaskDefinition g = ((TaskDescriptor)tmpBde).getTaskDefinition();
							
							listGuides = g.getGuidances();
							if (!listGuides.isEmpty()) {
								if (tmpBde.getName().equals("detail_requirements")) {
									Iterator<Guidance> itGuide = listGuides.iterator();
																		
									while (itGuide.hasNext()) {										
										nbGuidances++;
										itGuide.next();
									}
									assertTrue(nbGuidances == 13);
								}
							}
							
							assertTrue(expectedResults.contains( tmpBde.getPresentationName() ) );
						}
					}
					assertTrue(nbTaskDescriptors == 3);
				}
			}
			assertTrue(rentreDansManageRequirements);
		}		
	}
	
	
	public void testOpenUPTaskDescriptorContainsDependency() {
		Process theTestedProcess = null;
		Iterator<BreakdownElement> itTopLevelAct,itSecondLevelAct,BdeIterator;
		Activity topLevelActivity,secondLevelActivity;
		boolean rentreDansDevelopSolution = false;
		int nbTaskDescriptors = 0;
		int nbSuccessor = 0;
		BreakdownElement tmpBde = null;
		
		WorkBreakdownElement tmpWBde = null;
		Set<WorkBreakdownElement> listSuccessor = null;
		
		Iterator<WorkBreakdownElement> itSuccessor;
		
		theTestedProcess = XMLParser.getProcess(pathOPenUP);
		
		assertNotNull(theTestedProcess);
		if (theTestedProcess != null) {
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = theTestedProcess.getBreakdownElements().iterator();
			
			topLevelActivity = null;
			// We want the third Phase : Construction Iteration
			while (itTopLevelAct.hasNext()) {
				topLevelActivity = (Activity) itTopLevelAct.next();
				if (topLevelActivity.getPresentationName().equals("Construction Iteration [1..n]")) {
					break;
				}
			}
			
			itSecondLevelAct = topLevelActivity.getBreakdownElements().iterator();
			
			rentreDansDevelopSolution = false;
			while (itSecondLevelAct.hasNext()) {
				secondLevelActivity = (Activity) itSecondLevelAct.next();
				if (secondLevelActivity.getPresentationName().equals("Develop Solution (for requirement) (within context)")) {
					rentreDansDevelopSolution = true;
					BdeIterator = secondLevelActivity.getBreakdownElements().iterator();
					nbTaskDescriptors = 0;
					while (BdeIterator.hasNext()) {
						tmpBde = BdeIterator.next();					
					
						if (tmpBde instanceof TaskDescriptor) {
							nbTaskDescriptors++;
							tmpBde = (TaskDescriptor) tmpBde;
							assertNotNull(tmpBde);
							
							// recherche de la taskDescriptor "Refine the architecture"
							// qui est la predecesseur de la Task Descriptor "Design the solution"
							if (tmpBde.getPresentationName().equals("Refine the Architecture")) {
								// on est dans la taskDescriptor qui est le predecesseur d'un autre tache
								// on va verifie que le succeseur de cette TD est
								// la taskDescriptor Design the solution
								listSuccessor = ((WorkBreakdownElement)tmpBde).getSuccessors();
								itSuccessor = listSuccessor.iterator();
								while (itSuccessor.hasNext()) {
									nbSuccessor++;
									tmpWBde = itSuccessor.next();
								}
								assertTrue(nbSuccessor == 1);
								assertTrue(tmpWBde.getPresentationName().equals("Design the Solution"));								
								assertTrue(((WorkBreakdownElement)tmpWBde).getPredecessors().iterator().next().getPresentationName().equals("Refine the Architecture"));
							}
														
						}
					}					
				}		
			}
			assertTrue(rentreDansDevelopSolution);
			assertTrue(nbTaskDescriptors == 5);
		}
	}
	
	public void testOpenUPActivityContainsDependency() {
		Process theTestedProcess = null;
		Iterator<BreakdownElement> itTopLevelAct,itSecondLevelAct;
		Activity topLevelActivity,secondLevelActivity;
		boolean rentreDansInitiateProject = false;
		int nbSuccessor = 0;

		
		WorkBreakdownElement tmpWBde = null;
		Set<WorkBreakdownElement> listSuccessor = null;
		
		Iterator<WorkBreakdownElement> itSuccessor;
		
		theTestedProcess = XMLParser.getProcess(pathOPenUP);
		
		assertNotNull(theTestedProcess);
		if (theTestedProcess != null) {
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = theTestedProcess.getBreakdownElements().iterator();
			
			topLevelActivity = null;
			// We want the third Phase : Construction Iteration
			while (itTopLevelAct.hasNext()) {
				topLevelActivity = (Activity) itTopLevelAct.next();
				if (topLevelActivity.getPresentationName().equals("Inception Iteration [1..n]")) {
					break;
				}
			}			
			
			itSecondLevelAct = topLevelActivity.getBreakdownElements().iterator();			
			rentreDansInitiateProject = false;
			while (itSecondLevelAct.hasNext()) {
				secondLevelActivity = (Activity) itSecondLevelAct.next();
				if (secondLevelActivity.getPresentationName().equals("Initiate Project")) {
					rentreDansInitiateProject = true;
					
					listSuccessor = ((WorkBreakdownElement)secondLevelActivity).getSuccessors();
					itSuccessor = listSuccessor.iterator();
					while (itSuccessor.hasNext()) {
						nbSuccessor++;
						tmpWBde = itSuccessor.next();					
					}
					assertTrue(nbSuccessor == 2);
					assertTrue(tmpWBde.getPredecessors().iterator().next().getPresentationName().equals("Initiate Project"));
				}
			}
			assertTrue(rentreDansInitiateProject);
		}
	}
	
	public void testOpenUPDefineVisionContainsExpectedGuidances() {
		Process openUpProcess = XMLParser.getProcess(pathOPenUP);
		Activity inceptionIterationAct = null;
		Activity initiateProjectAct = null;
		TaskDescriptor defineVisionTDesc = null;
		TaskDefinition defineVisionTDef = null;
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = openUpProcess.getBreakdownElements().iterator();
		while (itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			if (tmpBde.getPresentationName().equals("Inception Iteration [1..n]")) {
				inceptionIterationAct = (Activity) tmpBde;
			}
		}
		
		assertTrue(inceptionIterationAct != null);
		
		// Second Step, Stop on Initiate Project
		Iterator<BreakdownElement> itSndLevelAct = inceptionIterationAct.getBreakdownElements().iterator();
		while (itSndLevelAct.hasNext()) {
			BreakdownElement tmpBde = itSndLevelAct.next();
			if (tmpBde.getPresentationName().equals("Initiate Project")) {
				initiateProjectAct = (Activity) tmpBde;
			}
		}
		
		assertTrue(initiateProjectAct != null);
		
		// Third Step, Stop on Define Vision
		Iterator<BreakdownElement> itThdLevelAct = initiateProjectAct.getBreakdownElements().iterator();
		while (itThdLevelAct.hasNext()) {
			BreakdownElement tmpBde = itThdLevelAct.next();
			if (tmpBde.getPresentationName().equals("Define Vision")) {
				defineVisionTDesc = (TaskDescriptor) tmpBde;
			}
		}
		
		assertTrue(defineVisionTDesc != null);
		defineVisionTDef = defineVisionTDesc.getTaskDefinition();
		assertTrue(defineVisionTDef != null);
		
		// Fourth Step, Check Number, Names and Types of the Guidances
		int nbGuidances = 0;
		HashSet<String> expectedGuidances = new HashSet<String>();
		expectedGuidances.add("Vision");
		expectedGuidances.add("Qualities of Good Requirements");
		expectedGuidances.add("Requirements");
		expectedGuidances.add("Requirements Gathering Techniques");
		expectedGuidances.add("Effective Requirement Reviews");
		Iterator<Guidance> itGuidances = defineVisionTDef.getGuidances().iterator();
		while (itGuidances.hasNext()) {
			Guidance tmpGuidance;
			tmpGuidance = itGuidances.next();
			nbGuidances++;
			
			assertTrue(expectedGuidances.contains(tmpGuidance.getPresentationName()));
			
			if (tmpGuidance.getName().equals("Vision")) {
				assertTrue(tmpGuidance.getType().equals(Guidance.checklist));
			}
			else if (tmpGuidance.getName().equals("Requirements")) {
				assertTrue(tmpGuidance.getType().equals(Guidance.concept));
			}
			else if (tmpGuidance.getName().equals("Effective Requirement Reviews")) {
				assertTrue(tmpGuidance.getType().equals(Guidance.guideline));
			}
		}
		
		assertTrue(nbGuidances == 5);
	}
	
	
	public void testOpenUPInceptionIterationContainsExpectedGuidances() {
		Process openUpProcess = XMLParser.getProcess(pathOPenUP);
		Activity inceptionIterationAct = null;
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = openUpProcess.getBreakdownElements().iterator();
		while (itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			if (tmpBde.getPresentationName().equals("Inception Iteration [1..n]")) {
				inceptionIterationAct = (Activity) tmpBde;
			}
		}
		
		assertTrue(inceptionIterationAct != null);
		
		// Second Step, Check Number, Names and Types of the Guidances
		int nbGuidances = 0;
		HashSet<String> expectedGuidances = new HashSet<String>();
		expectedGuidances.add("Inception Phase");
		
		Iterator<Guidance> itGuidances = inceptionIterationAct.getGuidances().iterator();
		while (itGuidances.hasNext()) {
			Guidance tmpGuidance;
			tmpGuidance = itGuidances.next();
			nbGuidances++;
			
			assertTrue(expectedGuidances.contains(tmpGuidance.getPresentationName()));
			
			if (tmpGuidance.getName().equals("Inception Phase")) {
				assertTrue(tmpGuidance.getType().equals(Guidance.concept));
			}
		}
		
		assertTrue(nbGuidances == 1);
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
	
	

}

