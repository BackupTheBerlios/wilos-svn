/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/

package wilos.test.business.services.util.xml.parser;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import junit.framework.TestCase;
import wilos.business.services.util.xml.parser.XMLParser;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.section.Section;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkOrder;

public class XMLParserTest extends TestCase {
	public static File pathScrum = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "scrum.xml"); 
	public static File pathOPenUP =new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "sortieEPF.xml");
	public static File pathMonTest =new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "monTest.xml");
	public static File pathScrumFillerTest =new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "scrum_FillerTest.xml");
	public static File pathFileError = new File("noFile");
	public static File pathScrumWithArte = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "scrum_with_ArteF.xml"); 
	public static File pathEmptyFile = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "emptyFile.xml"); 
	public static File pathItil = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "itil.xml");
	public static File crashingScrum = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "crashingScrum.xml");
	public static File pathXP = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "xp.xml");
	public static File pathWrongFile = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "WrongFile.xml");
	public static File pathOpenUpCrashing = new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "openup2_crashing.xml");
	
	private static Process  OpenUPProcess = null;
	private static Process  OpenUPProcessCrashing = null;
	private static Process  ScrumProcess = null;
	
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
		
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		assertTrue(ScrumProcess != null);
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		assertTrue(OpenUPProcess != null);
		
		theProcess = XMLParser.getProcess(pathScrumWithArte );
		assertTrue(theProcess != null);
		
		theProcess = XMLParser.getProcess(pathItil);
		assertTrue(theProcess != null);
	}
	
	/**
	 * Checks that the OpenUp Process has the right Name and the right Presentation Name
	 *
	 */
	public void testProcessFromOpenUPHasTheRightNames() {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		
		assertTrue(OpenUPProcess.getPresentationName().equals("OpenUP/Basic Lifecycle"));
		assertTrue(OpenUPProcess.getName().equals("openup_basic_process"));
	}
	
	/**
	 * Checks that the Scrum Process has the right Attributes 
	 *
	 */
	public void testProcessFromScrumHasTheRightAttributes() {
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		
		ScrumProcess = XMLParser.getProcess(pathScrum);
		assertTrue( ScrumProcess.getPresentationName().equals("Cycle de vie Scrum") );
		assertTrue( ScrumProcess.getName().equals("Scrum") );
		assertTrue( ScrumProcess.getIsPlanned() );
		assertTrue( ! ScrumProcess.getIsOptional() );
		assertTrue( ScrumProcess.getIsRepeatable() );
		assertTrue( ! ScrumProcess.getIsOngoing() );
		assertTrue( ScrumProcess.getPrefix().equals("") );
		assertTrue( ScrumProcess.getDescription().equals("Les phases, les sprints et les tâches dans la production d'une release") );
		assertTrue( ScrumProcess.getGuid().equals("_9llsAQAvEdubGMceRDupFQ") );
		assertTrue( ! ScrumProcess.getHasMultipleOccurrences() );
		assertTrue( ! ScrumProcess.getIsEvenDriven() );		
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
		
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		
		process =  ScrumProcess;
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
		
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		process = ScrumProcess;
		itAct = process.getBreakdownElements().iterator();
		
		assertTrue(itAct.hasNext());
		while (itAct.hasNext()) {
			BreakdownElement tmpBDE = itAct.next();
			assertTrue(tmpBDE instanceof Activity);
			
			assertTrue(((Activity) tmpBDE).getBreakdownElements().size() > 0);
		}
	}
	
	/**
	 * Checks that the phase called "Phase de préparation" From Scrum contains roleDescriptors
	 *
	 */
	public void testPhase1FromScrumContainsRoleDescriptors() {
		Iterator<BreakdownElement> itAct;
		Iterator<BreakdownElement> itBde;
		boolean isThereARoleDesc;
		
		Process process;
		
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		process = ScrumProcess;
		itAct = process.getBreakdownElements().iterator();
		
		// Only the first Phase has role Descriptors !!!
		assertTrue(itAct.hasNext());
		Activity tmpAct = (Activity) itAct.next();
		if (! tmpAct.getPresentationName().equals("Phase de préparation")) {
			assertTrue(itAct.hasNext());
			tmpAct = (Activity) itAct.next();
		}
		
		assertTrue(tmpAct instanceof Phase);
		
		itBde = tmpAct.getBreakdownElements().iterator();
		//assertTrue(itBde.hasNext());
		isThereARoleDesc = false;
		// For each Bde in the phase
		while (itBde.hasNext()) {
			if (itBde.next() instanceof RoleDescriptor)
				isThereARoleDesc = true;
		}
		// The main test of this function
		assertTrue(isThereARoleDesc);

	}
	
	/**
	 * Checks that the phase called "Phase de pr�paration" From Scrum contains taskDescriptors
	 *
	 */
	public void testPhase1FromScrumContainsTaskDescriptors() {
		Iterator<BreakdownElement> itAct;
		Iterator<BreakdownElement> itBde;
		boolean isThereATaskDesc;
		
		Process scrumProcess;
		
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		scrumProcess = ScrumProcess;
		itAct = scrumProcess.getBreakdownElements().iterator();
		
		// Only the first Phase has role Descriptors !!!
		assertTrue(itAct.hasNext());
		Activity tmpAct = (Activity) itAct.next();
		// We want to get the right Phase (only two Phases in this process)
		if (! tmpAct.getPresentationName().equals("Phase de préparation")) {
			assertTrue(itAct.hasNext());
			tmpAct = (Activity) itAct.next();
		}
	
		assertTrue(tmpAct instanceof Phase);
		
		// We'll work on each Bde of the Phase
		itBde = tmpAct.getBreakdownElements().iterator();
		isThereATaskDesc = false;
		while (itBde.hasNext()) {
			if (itBde.next() instanceof TaskDescriptor)
				isThereATaskDesc = true;
		}
		// The main test of this function
		assertTrue(isThereATaskDesc);
	}
	
	/**
	 * Checks that the phase called "Phase des sprints" From Scrum contains 1 Iteration
	 *
	 */
	public void testPhase2FromScrumContains1Iteration() {
		Iterator<BreakdownElement> itAct;
		Iterator<BreakdownElement> itAct2;
		Process scrumProcess;
		
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		scrumProcess = ScrumProcess;
		
		// Iterator on the set of the two Phases of Scrum
		itAct = scrumProcess.getBreakdownElements().iterator();
		
		// We work on the second Phase
		assertTrue(itAct.hasNext());
		Activity tmpAct = (Activity) itAct.next();
		// We want to get the right Phase (only two Phases in this process)
		if (! tmpAct.getPresentationName().equals("Phase des sprints")) {
			assertTrue(itAct.hasNext());
			tmpAct = (Activity) itAct.next();
		}
		
		Phase secondPhase = (Phase) tmpAct;
		// We work on each activity of the second Phase
		itAct2 = secondPhase.getBreakdownElements().iterator();
		
		// There must be only one Iteration in the second Phase
		assertTrue(itAct2.hasNext());
		assertTrue(itAct2.next() instanceof Iteration);
		assertTrue(! itAct2.hasNext());
	}
	
	/**
	 * Checks that the iteration of the phase called "Phase des sprints" From Scrum 
	 * contains all Expected Elements
	 */
	public void testPhase2IterationFromScrumContainsAllExpected() {
		Iterator<BreakdownElement> itAct;
		Iterator<BreakdownElement> secondPhaseActivitiesIterator;
		
		// Here are the Names of all the expected Elements of the Iteration
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
		
		// The number of expected elements
		int expectedNumber = expectedResults.size();
		
		Process scrumProcess;
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		scrumProcess = ScrumProcess;

		// Iterator on the set of the two Phases of Scrum
		itAct = scrumProcess.getBreakdownElements().iterator();
		
		// We work on the second Phase
		assertTrue(itAct.hasNext());
		Activity tmpAct = (Activity) itAct.next();
		if (! tmpAct.getPresentationName().equals("Phase des sprints")) {
			assertTrue(itAct.hasNext());
			tmpAct = (Activity) itAct.next();
		}
		
		Phase secondPhase = (Phase) tmpAct;
		// We 'll get the Iteration : the only BDE of the second phase
		secondPhaseActivitiesIterator = secondPhase.getBreakdownElements().iterator();

		assertTrue(secondPhaseActivitiesIterator.hasNext());
		
		Iteration secondPhaseIteration = (Iteration) secondPhaseActivitiesIterator.next();
		
		// Now we get the iterator on the BDEs of the Iteration
		Iterator<BreakdownElement> itBde = secondPhaseIteration.getBreakdownElements().iterator();
		
		// Now we check that we have all that we want
		int i = 0;
		while (itBde.hasNext()) {
			String tmpString = ((BreakdownElement) itBde.next()).getName() ;
			assertTrue(expectedResults.contains( tmpString ));
			i += 1;
		}
		assertTrue(i == expectedNumber);
	}
	
	/**
	 * Checks that OpenUp contains 4 Top-level activities
	 *
	 */
	public void testOpenUPContains4Activities() {
		Iterator<BreakdownElement> itAct;
		BreakdownElement bde;
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		
		// Iterator on the set of the four Phases of OpenUP
		itAct = OpenUPProcess.getBreakdownElements().iterator();
		
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
	
	/**
	 * Checks that each Top-level Activity from Scrum contains Activities
	 *
	 */
	public void testOpenUPTopLevelActivitiesContainActivities() {
		Iterator<BreakdownElement> itTopLevelAct;
		Activity topLevelActivity;
		// Each Top-Level Activity From Scrum Contains between 4 and 6 activities
		final int nbMiniSndLevelActivities = 4;
		final int nbMaxiSndLevelActivities = 6;
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		// Iterator on the set of the four Phases of OpenUP
		itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		
		// Activity 1
		while (itTopLevelAct.hasNext()) {
			topLevelActivity = (Activity) itTopLevelAct.next();
			assertTrue(topLevelActivity.getBreakdownElements().size() >= nbMiniSndLevelActivities);
			assertTrue(topLevelActivity.getBreakdownElements().size() <= nbMaxiSndLevelActivities);
		}
	}
	
	/**
	 * Checks that the Initiate Project Activity from OpenUp Process contains ExpectedRoleDescriptors()
	 *
	 */
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
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}

		// Iterator on the set of the four Phases of OpenUP
		itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		
		// Used to avoid Exception if error
		assertTrue(itTopLevelAct.hasNext());
		
		topLevelActivity = null;
		// We want the firts Phase : Inception Iteration
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
			// We go into Initiate Project
			if (secondLevelActivity.getPresentationName().equals("Initiate Project")) {
				rentreDansInitiateProject = true;
				BdeIterator = secondLevelActivity.getBreakdownElements().iterator();
				nbRoleDescriptors = 0;
				// For each Bde of Initiate Project
				while (BdeIterator.hasNext()) {
					tmpBde = BdeIterator.next();
					// If its a roleDesc
					if (tmpBde instanceof RoleDescriptor) {
						// Increase counter
						nbRoleDescriptors++;
						// Our list must contains the presentationName of the roleDesc
						assertTrue(expectedResults.contains( tmpBde.getPresentationName() ) );
					}
				}
				assertTrue(nbRoleDescriptors == 5);
			}
		}
		// Did we find Initiate Project ?
		assertTrue(rentreDansInitiateProject);	
	}
	
	/**
	 * Checks that the Activty Manage Requirements from OpenUp contains expected TaskDescriptors
	 *
	 */
	public void testOpenUPManageRequirementsContainsExpectedTaskDescriptors() {
		Iterator<BreakdownElement> itTopLevelAct;
		Iterator<BreakdownElement> itSecondLevelAct;
		Activity topLevelActivity,secondLevelActivity;
		boolean rentreDansManageRequirements;
		int nbTaskDescriptors;
		Iterator<BreakdownElement> BdeIterator;
		BreakdownElement tmpBde;
		
		// The Presentation Name of the Expected Task Descriptors
		HashSet<String> expectedResults = new HashSet<String>();
		expectedResults.add("Find and Outline Requirements");
		expectedResults.add("Detail Requirements");
		expectedResults.add("Create Test Cases");
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		
		// Iterator on the set of the four Phases of OpenUP
		itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		
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
			// We want to go into Manage Requirements
			if (secondLevelActivity.getPresentationName().equals("Manage Requirements")) {
				rentreDansManageRequirements = true;
				BdeIterator = secondLevelActivity.getBreakdownElements().iterator();
				nbTaskDescriptors = 0;
				// For each BDE of Manage Requirements
				while (BdeIterator.hasNext()) {
					tmpBde = BdeIterator.next();
					// If it is a TaskDesc
					if (tmpBde instanceof TaskDescriptor) {
						// increase counter
						nbTaskDescriptors++;
						// Checks that the presentationName is in our list
						assertTrue(expectedResults.contains( tmpBde.getPresentationName() ) );
					}
				}
				// Did we find the right number of TaskDesc ?
				assertTrue(nbTaskDescriptors == 3);
			}
		}
		// Did we find Manage Requirements ?
		assertTrue(rentreDansManageRequirements);	
	}	
	
	/**
	 * Checks that the Iteration from the second Phase From Scrum contains all expected
	 *
	 */
	public void testPhase2IterationFromScrumWithArteContainsAllExpected() {
		Iterator<BreakdownElement> itAct;
		TreeSet<BreakdownElement> secondPhaseActivities;
		Iterator<BreakdownElement> secondPhaseActivitiesIterator;
		
		// The Names of the expectedElements
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
		// Number of expected elements
		int expectedNumber = 11;
		
		Process process = XMLParser.getProcess(pathScrumWithArte);
		// Iterator on the set of the two Phases of Scrum
		itAct = process.getBreakdownElements().iterator();
		
		// We work on the second Phase
		BreakdownElement tmpAct = itAct.next();
		
		assertTrue(tmpAct instanceof Phase);
		if (tmpAct.getPresentationName().equals("Phase de préparation")) {
			assertTrue(itAct.hasNext());
			tmpAct = itAct.next();
		}
		
		Phase secondPhase = (Phase) tmpAct;
		// We get the set of activities of the second Phase
		secondPhaseActivities = (TreeSet<BreakdownElement>) secondPhase.getBreakdownElements();
		// And an iterator on it
		secondPhaseActivitiesIterator = secondPhaseActivities.iterator();
		
		assertTrue(secondPhaseActivitiesIterator.hasNext());
		BreakdownElement tmpBde =  secondPhaseActivitiesIterator.next();
		
		while (! (tmpBde instanceof Iteration) && secondPhaseActivitiesIterator.hasNext()) {
			tmpBde =  secondPhaseActivitiesIterator.next();
		}
		
		assertTrue(tmpBde instanceof Iteration);
		// Now we got the Iteration on which we'll work
		Iteration secondPhaseIteration = (Iteration) tmpBde;		
		
		int i = 0;
		for (BreakdownElement Bde : secondPhaseIteration.getBreakdownElements()) {
			// Is this name in our list ?
			assertTrue(expectedResults.contains(Bde.getName()));
			i += 1;
		}
		// Did we get the right number of element ?
		assertTrue(i == expectedNumber);
	}
	
	/**
	 * Checks that the taksDef Details Requirements from OpenUP contains the right number of guidances
	 *
	 */
	public void testOpenUPContainsGuidances() {
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
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}

		if (OpenUPProcess != null) {
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
			
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
										Guidance aGuidance = itGuide.next();
										if ( ! aGuidance.getType().equals(Guidance.example) &&
												! aGuidance.getType().equals(Guidance.template) &&
												 ! aGuidance.getType().equals(Guidance.checklist)) {
											assertTrue(aGuidance.getDescription().length() > 0);	
										}
									}									
									// 5 Concepts, 5 Guidelines, 2 Templates and 3 checkList
									assertTrue(nbGuidances == 15);									
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
	
	
	public void testOpenUPTaskDescriptorsRunDeveloperTestContainsOnePredecessor() {
		Iterator<BreakdownElement> itTopLevelAct,itSecondLevelAct,BdeIterator;
		Activity topLevelActivity,secondLevelActivity;
		int nbTaskDescriptors = 0;
		BreakdownElement tmpBde = null;
		Set<WorkBreakdownElement> listPredecessor = null;
		Iterator<WorkBreakdownElement> itPredecessor;
		int nbPredecessor = 0;
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		
		assertNotNull(OpenUPProcess);
		itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		topLevelActivity = null;
		
		// We want the third Phase : Elaboration Iteration
		while (itTopLevelAct.hasNext()) {
			topLevelActivity = (Activity) itTopLevelAct.next();
			if (topLevelActivity.getPresentationName().equals("Elaboration Iteration [1..n]")) {
				break;
			}			
		}
		
		itSecondLevelAct = topLevelActivity.getBreakdownElements().iterator();
		while (itSecondLevelAct.hasNext()) {
			secondLevelActivity = (Activity) itSecondLevelAct.next();
			if (secondLevelActivity.getPresentationName().equals("Develop Solution (for requirement) (within context)")) {
				BdeIterator = secondLevelActivity.getBreakdownElements().iterator();
				nbTaskDescriptors = 0;
				
				while (BdeIterator.hasNext()) {
					tmpBde = BdeIterator.next();					
				
					if (tmpBde instanceof TaskDescriptor) {
						nbTaskDescriptors++;
						tmpBde = (TaskDescriptor) tmpBde;
						assertNotNull(tmpBde);
						
						if (tmpBde.getPresentationName().equals("Run Developer Tests")) {
							// on est dans la taskDescriptor qui est le predecesseur d'un autre tache
							// on va verifie que le succeseur de cette TD est
							// la taskDescriptor Design the solution
							
							assertTrue(((WorkBreakdownElement)tmpBde).getPredecessors().size() == 1);
							listPredecessor = new TreeSet<WorkBreakdownElement>();
							for (WorkOrder wo: ((WorkBreakdownElement)tmpBde).getPredecessors()) {
								listPredecessor.add(wo.getSuccessor());
								assertTrue(wo.getLinkType().equals("finishToStart"));
							}
			
							itPredecessor = listPredecessor.iterator();

							WorkBreakdownElement tmpWBde = null;
							while (itPredecessor.hasNext()) {
								nbPredecessor++;
								tmpWBde = itPredecessor.next();
							}
							assertTrue(nbPredecessor == 1);
							assertTrue(tmpWBde.getPredecessors().iterator().next().getPredecessor().getPresentationName().equals("Implement Developer Tests"));
							
							
						}
					}
				}
			}
				
		}
		
	}
	
	public void testOpenUPTaskDescriptorDesignSolutionContainsPredecessor() {
		Iterator<BreakdownElement> itTopLevelAct,itSecondLevelAct,BdeIterator;
		Activity topLevelActivity,secondLevelActivity;
		boolean rentreDansDevelopSolution = false;
		int nbTaskDescriptors = 0;
		int nbSuccessor = 0;
		BreakdownElement tmpBde = null;
		
		WorkBreakdownElement tmpWBde = null;
		TreeSet<WorkBreakdownElement> listSuccessor = null;
		
		Iterator<WorkBreakdownElement> itSuccessor;
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		
		assertNotNull(OpenUPProcess);
		if (OpenUPProcess != null) {
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
			
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
								
								assertTrue(((WorkBreakdownElement)tmpBde).getSuccessors().size() == 1);
								listSuccessor = new TreeSet<WorkBreakdownElement>();
								for (WorkOrder wo: ((WorkBreakdownElement)tmpBde).getSuccessors()) {
									listSuccessor.add(wo.getSuccessor());
									assertTrue(wo.getLinkType().equals("finishToFinish"));
								}
								
				
								itSuccessor = listSuccessor.iterator();
								while (itSuccessor.hasNext()) {
									nbSuccessor++;
									tmpWBde = itSuccessor.next();
								}
								assertTrue(nbSuccessor == 1);
								assertTrue(tmpWBde.getPresentationName().equals("Design the Solution"));
								assertTrue(tmpWBde.getPredecessors().iterator().next().getPredecessor().getPresentationName().equals("Refine the Architecture"));								
							}														
						}
					}					
				}		
			}
			assertTrue(rentreDansDevelopSolution);
			assertTrue(nbTaskDescriptors == 5);
		}
	}
	
	public void testOpenUPActivityInitiateProjectContainsTwoSuccessors() {
		Iterator<BreakdownElement> itTopLevelAct,itSecondLevelAct;
		Activity topLevelActivity,secondLevelActivity;
		boolean rentreDansInitiateProject = false;
		int nbSuccessor = 0;
		
		WorkBreakdownElement tmpWBde = null;
		TreeSet<WorkBreakdownElement> listSuccessor = null;
		
		Iterator<WorkBreakdownElement> itSuccessor;
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		
		assertNotNull(OpenUPProcess);
		if (OpenUPProcess != null) {
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
			
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
					// we are in the Activity : Initiate Project
					rentreDansInitiateProject = true;
					
					assertTrue(((WorkBreakdownElement)secondLevelActivity).getSuccessors().size() == 2);

					listSuccessor = new TreeSet<WorkBreakdownElement>();
					// For each successor of the Initiate Project 
					for (WorkOrder wo: ((WorkBreakdownElement)secondLevelActivity).getSuccessors()) {
						listSuccessor.add(wo.getSuccessor());
						assertTrue(wo.getLinkType().equals("finishToFinish"));
					}
					
					itSuccessor = listSuccessor.iterator();
					while (itSuccessor.hasNext()) {
						nbSuccessor++;
						tmpWBde = itSuccessor.next();
						// there are two successors of the "Initiate Project" Activity in the Inception Iteration [1..n]
						assertTrue(tmpWBde.getPredecessors().iterator().next().getPredecessor().getPresentationName().equals("Initiate Project"));
					}
					assertTrue(nbSuccessor == 2);					
				}
			}
			assertTrue(rentreDansInitiateProject);
		}
	}
	
	public void testOpenUPDefineVisionContainsExpectedGuidances() {
		Activity inceptionIterationAct = null;
		Activity initiateProjectAct = null;
		TaskDescriptor defineVisionTDesc = null;
		TaskDefinition defineVisionTDef = null;
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
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
		// This one is a Template, and also a CheckList : Be careful when adding CheckLists !
		expectedGuidances.add("Vision");
		//expectedGuidances.add("Qualities of Good Requirements");
		expectedGuidances.add("Requirements");
		expectedGuidances.add("Requirements Gathering Techniques");
		expectedGuidances.add("Effective Requirement Reviews");
		Iterator<Guidance> itGuidances = defineVisionTDef.getGuidances().iterator();
		while (itGuidances.hasNext()) {
			Guidance tmpGuidance;
			tmpGuidance = itGuidances.next();
			nbGuidances++;
			
			// the checklist contains not presentation name attribut
			if (! tmpGuidance.getType().equals(Guidance.checklist)) {
				assertTrue(expectedGuidances.contains(tmpGuidance.getPresentationName()));
			}
			
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
		assertTrue(nbGuidances == 6);
	}
	
	
	public void testOpenUPInceptionIterationContainsExpectedGuidances() {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity inceptionIterationAct = null;
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
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
	
	/**
	 * Checks That the Support N1 Role From Itil2 Contains the expected Guidances
	 * 
	 * Note : Example is not treated
	 */
	public void testThatSupportN1RoleDefinitionFromItil2ContainsTheExpectedGuidances() {
		Process itilProcess = XMLParser.getProcess(pathItil);
		RoleDefinition supportN1Roledefinition = null;
		
		HashMap<String, String> expectedGuidances = new HashMap<String, String>();
		expectedGuidances.put("New Checklist", Guidance.checklist);
		expectedGuidances.put("New Concept", Guidance.concept);
		expectedGuidances.put("New Guideline", Guidance.guideline);
		expectedGuidances.put("New Supporting Material", Guidance.supportingMaterial);
		expectedGuidances.put("New Whitepaper", Guidance.whitepaper);
		
		int nbExpectedGuidances = expectedGuidances.size();
		
		// The goal of this loops is to get the Support N1 RoleDefinition
		for (BreakdownElement bde : itilProcess.getBreakdownElements()) {
			if (bde instanceof Activity) {
				for (BreakdownElement bde2 : ((Activity) bde).getBreakdownElements()) {
					if (bde2 instanceof RoleDescriptor) {
						// Il will be done two times, but we don't care, the result will be the same
						if (bde2.getName().equals("sn1")) {
							supportN1Roledefinition = ((RoleDescriptor) bde2).getRoleDefinition();
						}
					}
				}
			}
		}

		assertNotNull(supportN1Roledefinition);
		// Now we work on the guidances we have
		int nbGuidances = 0;
		for (Guidance aGuidance : supportN1Roledefinition.getGuidances()) {
			if ( expectedGuidances.get(aGuidance.getPresentationName()) != null) {
				// The PresentationName and the type must match
				assertTrue(expectedGuidances.get(aGuidance.getPresentationName()).equals(aGuidance.getType()));
				nbGuidances++;
			}
		}
		// We must have the right number of guidances
		assertTrue(nbGuidances == nbExpectedGuidances);
	}
	
	/**
	 * Checks that the RoleDefinition Analyst from OpenUp refers four RoleDescriptors
	 *
	 */
	public void testThatTheRoleDefinionAnalystFromOpenUpRefersTheRightRoleDescriptors() {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		
		// We want to get a RoleDescriptor with Analyst as PresentationName
		RoleDescriptor anAnalystRoleDescriptor = null;
		// Trough TopLevelActivities
		for (BreakdownElement topLevelAct : OpenUPProcess.getBreakdownElements()) {
			if (topLevelAct.getPresentationName().equals("Inception Iteration [1..n]")) {
				assertTrue(topLevelAct instanceof Activity);
				// Trough SndLevelActivities
				for (BreakdownElement sndLevelAct : ((Activity)topLevelAct).getBreakdownElements()) {
					if (sndLevelAct.getPresentationName().equals("Initiate Project")) {
						// Now, it s one of the BreakdownElements of Initiate Project
						for (BreakdownElement lowLevelBde : ((Activity)sndLevelAct).getBreakdownElements() ) {
							if (lowLevelBde instanceof RoleDescriptor && 
										lowLevelBde.getPresentationName().equals("Analyst")) {
								anAnalystRoleDescriptor = ((RoleDescriptor) lowLevelBde);
							}
						}
					}
				}
			}
		}
		
		// check that the Role Descriptor is not null
		assertNotNull(anAnalystRoleDescriptor);
		// check that the Role Descriptor refers the RoleDefinition
		assertNotNull(anAnalystRoleDescriptor.getRoleDefinition());
		
		// Now we get the RoleDefinition
		RoleDefinition theAnalystRoleDefinition = anAnalystRoleDescriptor.getRoleDefinition();
		
		// The Guids of the RoleDescriptors
		HashSet<String> analystRoleDescriptorsGUIDs = new HashSet<String>();
		analystRoleDescriptorsGUIDs.add("_eFeO0EbpEduLBN1xMBngqw");
		analystRoleDescriptorsGUIDs.add("_VNpT0FY5EdqI9sTOt2pjHw");
		analystRoleDescriptorsGUIDs.add("_DT8oADk-EduFTqg5hiiQIw");
		analystRoleDescriptorsGUIDs.add("_wGSUwFY6EdqI9sTOt2pjHw");
		
		int nbAnalystRDs = 0;
		for (RoleDescriptor anAnalystRD : theAnalystRoleDefinition.getRoleDescriptors()) {
			assertTrue(analystRoleDescriptorsGUIDs.contains(anAnalystRD.getGuid()));
			nbAnalystRDs += 1;
		}
		assertTrue(nbAnalystRDs == 4);
	}	
	
	
	public void testOpenUPTaskDescriptorDefineVisionContainsCheckList() {
		
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity inceptionIterationAct = null;
		Activity initiateProject = null;
		TaskDescriptor defineVision = null;
		TaskDefinition definitionVision = null;
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		while (itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			if (tmpBde.getPresentationName().equals("Inception Iteration [1..n]")) {
				inceptionIterationAct = (Activity) tmpBde;
			}
		}
		
		assertTrue(inceptionIterationAct != null);
		
		// get the Activity Initiate Project
		Iterator<BreakdownElement> itSecondLevelAct = inceptionIterationAct.getBreakdownElements().iterator();
		while (itSecondLevelAct.hasNext()) {
			BreakdownElement tmpBde = itSecondLevelAct.next();
			if (tmpBde.getPresentationName().equals("Initiate Project")) {
				initiateProject = (Activity) tmpBde;
			}
		}
		
		assertTrue(initiateProject != null);
		
		
		Iterator<BreakdownElement> itTaskDescriptor = initiateProject.getBreakdownElements().iterator();
		while (itTaskDescriptor.hasNext()) {
			BreakdownElement tmpBde = itTaskDescriptor.next();
			if (tmpBde.getPresentationName().equals("Define Vision")) {
				defineVision = (TaskDescriptor) tmpBde;
			}
		}
		
		assertTrue(defineVision != null);
		
		definitionVision = (TaskDefinition)defineVision.getTaskDefinition();
		
		assertTrue(definitionVision != null);
		
		int nbSectionsGoodRequirements = 0;
		int nbSectionsVision = 0;
		
		Iterator<Guidance> itGuidance =definitionVision.getGuidances().iterator() ;
		int nbCheckList = 0;
		while (itGuidance.hasNext()) {
			Guidance tmpGuidance = itGuidance.next();			
				if (tmpGuidance instanceof CheckList) {
					nbCheckList++;
					
					// test if the checklist is good requirement
					if (tmpGuidance.getName().equals("good_requirements")) {
						// check the different sections of this checklist
						Iterator<Section> itSectionsRequirement = ((CheckList) tmpGuidance).getSections().iterator();
						while (itSectionsRequirement.hasNext()) {
							itSectionsRequirement.next();	
							nbSectionsGoodRequirements++;												
						}
						assertTrue(nbSectionsGoodRequirements == 8);
					}
					// test if the checklist is the vision
					if (tmpGuidance.getName().equals("vision")) {
						// check the different sections of the checklist
						Iterator<Section> itSectionsVision = ((CheckList) tmpGuidance).getSections().iterator();
						while (itSectionsVision.hasNext()) {
							itSectionsVision.next();
							nbSectionsVision++;												
						}	
						assertTrue(nbSectionsVision == 10);
					}
			}
		}
		assertTrue(nbCheckList == 2);
	}
	
	
	public void testOpenUPDefineVisionTaskDefinitionContainsATemplateCalledVision () {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity inceptionIterationAct = null;
		Activity initiateProject = null;
		TaskDescriptor defineVision = null;
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		while (itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			if (tmpBde.getPresentationName().equals("Inception Iteration [1..n]")) {
				inceptionIterationAct = (Activity) tmpBde;
			}
		}
		
		assertTrue(inceptionIterationAct != null);
		
		// get the Activity Initiate Project
		Iterator<BreakdownElement> itSecondLevelAct = inceptionIterationAct.getBreakdownElements().iterator();
		while (itSecondLevelAct.hasNext()) {
			BreakdownElement tmpBde = itSecondLevelAct.next();
			if (tmpBde.getPresentationName().equals("Initiate Project")) {
				initiateProject = (Activity) tmpBde;
			}
		}
		
		assertTrue(initiateProject != null);
		
		
		Iterator<BreakdownElement> itTaskDescriptor = initiateProject.getBreakdownElements().iterator();
		while (itTaskDescriptor.hasNext()) {
			BreakdownElement tmpBde = itTaskDescriptor.next();
			if (tmpBde.getPresentationName().equals("Define Vision")) {
				defineVision = (TaskDescriptor) tmpBde;
			}
		}
		
		assertTrue(defineVision != null);
		
		assertTrue(defineVision.getTaskDefinition() != null);
		
		int cptTemplateVisionFound = 0;
		for (Guidance aGuidance : defineVision.getTaskDefinition().getGuidances()) {
			if (aGuidance.getType().equals(Guidance.template)) {
				assertTrue(aGuidance.getPresentationName().equals("Vision"));
				assertTrue(aGuidance.getAttachment().equals("openup_basic/guidances/templates/resources/vision.dot"));
				assertTrue(aGuidance.getDescription().equals(""));
				cptTemplateVisionFound += 1;
			}
		}
		assertTrue(cptTemplateVisionFound == 1);
	}
	
	public void testOpenUPPlanTheProjectTaskDefinitionContains2ExamplesAnd3Templates () {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity inceptionIterationAct = null;
		Activity initiateProject = null;
		TaskDescriptor planTheProject = null;
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		while (itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			if (tmpBde.getPresentationName().equals("Inception Iteration [1..n]")) {
				inceptionIterationAct = (Activity) tmpBde;
			}
		}
		
		assertTrue(inceptionIterationAct != null);
		
		// get the Activity Initiate Project
		Iterator<BreakdownElement> itSecondLevelAct = inceptionIterationAct.getBreakdownElements().iterator();
		while (itSecondLevelAct.hasNext()) {
			BreakdownElement tmpBde = itSecondLevelAct.next();
			if (tmpBde.getPresentationName().equals("Initiate Project")) {
				initiateProject = (Activity) tmpBde;
			}
		}
		
		assertTrue(initiateProject != null);
		
		// get The Task Plan the Project
		Iterator<BreakdownElement> itTaskDescriptor = initiateProject.getBreakdownElements().iterator();
		while (itTaskDescriptor.hasNext()) {
			BreakdownElement tmpBde = itTaskDescriptor.next();
			if (tmpBde.getPresentationName().equals("Plan the Project")) {
				planTheProject = (TaskDescriptor) tmpBde;
			}
		}
		
		assertTrue(planTheProject != null);
		
		assertTrue(planTheProject.getTaskDefinition() != null);
		
		int cptExampleFound = 0;
		int cptTemplateFound = 0;
		for (Guidance aGuidance : planTheProject.getTaskDefinition().getGuidances()) {
			if (aGuidance.getType().equals(Guidance.example) && aGuidance.getPresentationName().equals("Project Plan")) {
				assertTrue(aGuidance.getAttachment().equals("openup_basic/guidances/examples/resources/project_plan.doc"));
				assertTrue(aGuidance.getDescription().equals(""));
				cptExampleFound += 1;
			}
			if (aGuidance.getType().equals(Guidance.example) && aGuidance.getPresentationName().equals("Work Items List")) {
				assertTrue(aGuidance.getAttachment().equals("openup_basic/guidances/examples/resources/ex_work_items_list.xls"));
				assertTrue(aGuidance.getDescription().equals(""));
				cptExampleFound += 1;
			}
			if (aGuidance.getType().equals(Guidance.template) && aGuidance.getPresentationName().equals("Work Items List")) {
				assertTrue(aGuidance.getAttachment().equals("openup_basic/guidances/templates/resources/work_items_list.xls"));
				assertTrue(aGuidance.getDescription().equals(""));
				cptTemplateFound += 1;
			}
			if (aGuidance.getType().equals(Guidance.template) && aGuidance.getPresentationName().equals("Project Plan")) {
				assertTrue(aGuidance.getAttachment().equals("openup_basic/guidances/templates/resources/project_plan.dot"));
				assertTrue(aGuidance.getDescription().equals(""));
				cptTemplateFound += 1;
			}
			if (aGuidance.getType().equals(Guidance.template) && aGuidance.getPresentationName().equals("Risk List")) {
				assertTrue(aGuidance.getAttachment().equals("openup_basic/guidances/templates/resources/risk_list.xls"));
				assertTrue(aGuidance.getDescription().equals(""));
				cptTemplateFound += 1;
			}
		}
		// Do we have what we want ?
		assertTrue(cptExampleFound == 2);
		assertTrue(cptTemplateFound == 3);
	}
	
	
	
	
	
	/**
	 * Checks that CrashingScrum cointains a not null Process
	 *
	 */
	public void testThatCrashingScrumDoesntCrash() {
		Process theProcess;
		theProcess = XMLParser.getProcess(crashingScrum);
		assertTrue(theProcess != null);
	}
	
	/**
	 * Checks that CrashingScrum contains two Phases and a roledesciptor.
	 * Also Checks that the presentationNames are right and the the role descriptor
	 * id linked to a role definition
	 *
	 */
	public void testThatCrashingScrumContainsTheTwoExpectedPhasesAndARoleDescriptor() {
		Process theProcess;
		theProcess = XMLParser.getProcess(crashingScrum);
		int nbPhases, nbRoleDescriptors, nbBdes;
		
		// The expected presentationNames
		HashSet<String> presentationNames = new HashSet<String>();
		presentationNames.add("Phase de préparation");
		presentationNames.add("Phase des sprints");
		presentationNames.add("Directeur Produit");
		
		// Counters init
		nbPhases = 0;
		nbRoleDescriptors = 0;
		nbBdes = 0;
		for (BreakdownElement bde : theProcess.getBreakdownElements()) {
			if (bde instanceof Phase) {
				nbPhases++;
			}
			if (bde instanceof RoleDescriptor) {
				nbRoleDescriptors++;
				assertTrue( ((RoleDescriptor) bde).getRoleDefinition() != null  );
			}
			nbBdes += 1;
			
			assertTrue(presentationNames.contains(bde.getPresentationName()));
		}
		
		// Do we have 1 RD and 2 Phases and only 3 Bdes ?
		assertTrue(nbRoleDescriptors == 1);
		assertTrue(nbPhases == 2);
		assertTrue(nbBdes == 3);
	}
	
	public void testTmpPbApostrophes() {
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		Phase thePhase = null;
		RoleDescriptor theRoleDesc = null;
		RoleDefinition theRoleDef;

		for (BreakdownElement bde : ScrumProcess.getBreakdownElements()) {
			if (bde.getName().equals("Preparation Phase")) {
				thePhase = (Phase) bde;
				break;
			}
		}
		
		for (BreakdownElement bde : thePhase.getBreakdownElements()) {
			if (bde.getName().equals("Product Owner")) {
				theRoleDesc = (RoleDescriptor) bde;
				break;
			}
		}
		
		theRoleDef = theRoleDesc.getRoleDefinition();

		assertEquals("C'est le représentant du \"métier\" dans le projet. ", theRoleDef.getDescription());
	}
	
	public void testOpenUpDetermineArchitecturalFeasibilityHasOneRoleDescriptor() {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity inceptionIterationAct = null;
		Activity determineArchitecturalFeasibility = null;
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		while (itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			if (tmpBde.getPresentationName().equals("Inception Iteration [1..n]")) {
				inceptionIterationAct = (Activity) tmpBde;
			}
		}
		
		assertTrue(inceptionIterationAct != null);
		
		// get the Activity Initiate Project
		Iterator<BreakdownElement> itSecondLevelAct = inceptionIterationAct.getBreakdownElements().iterator();
		while (itSecondLevelAct.hasNext()) {
			BreakdownElement tmpBde = itSecondLevelAct.next();
			if (tmpBde.getPresentationName().equals("Determine Architectural Feasibility")) {
				determineArchitecturalFeasibility = (Activity) tmpBde;
			}
		}
		
		assertTrue(determineArchitecturalFeasibility != null);
		
		int nbRoleDescriptors = 0;
		for (BreakdownElement bde : determineArchitecturalFeasibility.getBreakdownElements()) {
			if (bde instanceof RoleDescriptor) {
				nbRoleDescriptors++;
			}
		}
		assertTrue(nbRoleDescriptors == 2);
	}
	
	public void testOpenUpInceptionManageRequirementsAnalystHas2TaskDescriptors() {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity inceptionIterationAct = null;
		Activity manageRequirements = null;
		RoleDescriptor analyst = null;
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		while (itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			if (tmpBde.getPresentationName().equals("Inception Iteration [1..n]")) {
				inceptionIterationAct = (Activity) tmpBde;
			}
		}
		
		assertTrue(inceptionIterationAct != null);
		
		// get the Activity Initiate Project
		Iterator<BreakdownElement> itSecondLevelAct = inceptionIterationAct.getBreakdownElements().iterator();
		while (itSecondLevelAct.hasNext()) {
			BreakdownElement tmpBde = itSecondLevelAct.next();
			if (tmpBde.getPresentationName().equals("Manage Requirements")) {
				manageRequirements = (Activity) tmpBde;
			}
		}
		
		assertTrue(manageRequirements != null);
		
		for (BreakdownElement bde : manageRequirements.getBreakdownElements()) {
			if (bde instanceof RoleDescriptor && bde.getPresentationName().equals("Analyst")) {
				analyst = (RoleDescriptor) bde;
			}
		}
		
		assertTrue(analyst != null);
		
		assertEquals(analyst.getPrimaryTasks().size(), 2);
	}
	
	public void testOpenUpInceptionManageRequirementsHas3TaskDescriptors() {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity inceptionIterationAct = null;
		Activity manageRequirements = null;
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		while (itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			if (tmpBde.getPresentationName().equals("Inception Iteration [1..n]")) {
				inceptionIterationAct = (Activity) tmpBde;
			}
		}
		
		assertTrue(inceptionIterationAct != null);
		
		// get the Activity Initiate Project
		Iterator<BreakdownElement> itSecondLevelAct = inceptionIterationAct.getBreakdownElements().iterator();
		while (itSecondLevelAct.hasNext()) {
			BreakdownElement tmpBde = itSecondLevelAct.next();
			if (tmpBde.getPresentationName().equals("Manage Requirements")) {
				manageRequirements = (Activity) tmpBde;
			}
		}
		
		assertTrue(manageRequirements != null);
		
		int nbTaskDescriptors = 0;
		for (BreakdownElement bde : manageRequirements.getBreakdownElements()) {
			if (bde instanceof TaskDescriptor) {
				nbTaskDescriptors++;
			}
		}
		
		assertEquals(nbTaskDescriptors, 3);
	}
	
	
		// test to resolve we check the order of the 4 phases of openUP
	public void testOpenUPOrderisInceptionElabrorationConstructionTransition(){
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity IterationAct = null;
		
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		int num = 0 ;
		while (itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			IterationAct = (Activity) tmpBde;
			if (IterationAct.getPresentationName().equals("Inception Iteration [1..n]")){
				assertTrue("INCEPTION IS FIRST",num == 0);
			}
			else if (IterationAct.getPresentationName().equals("Elaboration Iteration [1..n]")){
				assertTrue("ELABORATION IS SECOND",num == 1);
			}
			else if (IterationAct.getPresentationName().equals("Construction Iteration [1..n]")){
				assertTrue("CONSTRUCTION IS THIRD",num == 2);
			}
			else if (IterationAct.getPresentationName().equals("Transition Iteration [1..n]")){
				assertTrue("TRANSITION IS FOURTH",num == 3);
			}
			num ++ ;
		}
	}
	
	// test to resolve we check the order of the activity in Elaboration phase
	public void testOpenUPElaborationOrder(){
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity IterationAct = null;
		boolean trouve = false ;
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		int num = 0 ;
		while (!trouve && itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			IterationAct = (Activity) tmpBde;
			trouve = (IterationAct.getPresentationName().equals("Elaboration Iteration [1..n]"));
		}
		assertTrue(trouve);
		
		//	get the Activity Initiate Project
		Iterator<BreakdownElement> itSecondLevelAct = IterationAct.getBreakdownElements().iterator();
		while (itSecondLevelAct.hasNext()) {
			BreakdownElement tmpBde = itSecondLevelAct.next();
			if (tmpBde.getPresentationName().equals("Manage Iteration")) {
				assertTrue(num == 0);
			}
			else if (tmpBde.getPresentationName().equals("Manage Requirements")){
				assertTrue(num == 1);
			}
			else if (tmpBde.getPresentationName().equals("Define the Architecture")){
				assertTrue(num == 2);
			}
			else if (tmpBde.getPresentationName().equals("Develop Solution (for requirement) (within context)")){
				assertTrue(num == 3);
			}
			else if (tmpBde.getPresentationName().equals("Validate Build")){
				assertTrue(num == 4);
			}
			else if (tmpBde.getPresentationName().equals("Ongoing Tasks")){
				assertTrue(num == 5);
			}
			if (tmpBde instanceof Activity){
				num ++ ;
			}
		}
		
	}
	
	//	 test to resolve we check the order of the tasks in the activity Manage iteration
	public void testOpenUPElaborationManageIterationOrder(){
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity IterationAct = null;
		boolean trouve = false ;
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		
		while (!trouve && itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			IterationAct = (Activity) tmpBde;
			trouve = (IterationAct.getPresentationName().equals("Elaboration Iteration [1..n]"));
		}
		assertTrue(trouve);
		trouve= false ;
		//	get the Activity Initiate Project
		Iterator<BreakdownElement> itSecondLevelAct = IterationAct.getBreakdownElements().iterator();
		BreakdownElement tmpBde = null ;
		while (!trouve && itSecondLevelAct.hasNext()) {
			tmpBde = itSecondLevelAct.next();
			trouve = (tmpBde.getPresentationName().equals("Manage Iteration"));
		}
		assertTrue(trouve);
		Activity manageRequirements = (Activity)tmpBde ;
		
		Iterator<BreakdownElement> itTask = manageRequirements.getBreakdownElements().iterator();
		int num = 0 ;
		while (itTask.hasNext()){
			BreakdownElement tmptask = itTask.next() ;
			if (tmptask.getPresentationName().equals("Plan Iteration")){
				assertTrue(num == 0);
			}
			else if (tmptask.getPresentationName().equals("Manage Iteration")){
				assertTrue(num == 1);
			}
			else if (tmptask.getPresentationName().equals("Assess Results")){
				assertTrue(num == 2);
			} 
			if (tmptask instanceof TaskDescriptor){
				num ++ ;
			}
		}
	}
			
	public void testScrumPreparationPhaseContainsRightMainDescriptionAndKeyConsiderations () {
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		Phase thePhase = null;
		String expectedMainDescription = "<p>\n" +
			"    Scrum ne prend pas en compte tous les aspects de préparation d'un projet. Seules sont présentées les taches spécifiques\n" +
			"    de Scrum plus une qui regroupe tous les travaux pouvant etre réalisés\n" +
			"</p>";

		for (BreakdownElement bde : ScrumProcess.getBreakdownElements()) {
			if (bde.getName().equals("Preparation Phase")) {
				thePhase = (Phase) bde;
				break;
			}
		}
		
		assertEquals(thePhase.getMainDescription(), expectedMainDescription);
		assertEquals(thePhase.getKeyConsiderations(), "");
	}
		
	public void testScrumRDContainsRightMainDescriptionAndKeyConsiderations () {
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		Activity theActivity = null;
		RoleDescriptor theRoleDesc = null;
		RoleDefinition theRoleDef;
		String expectedMainDescription = "<p>\n" +
				"    Le <b>Directeur de produit</b> (<i>Product Owner</i>) est le représentant des clients et utilisateurs. Il agit comme un\n" +
				"    \"proxy\" du \"métier\" au sein de l'équipe Scrum.\n" +
				"</p>\n" +
				"<p>\n" +
				"    Le terme <i>Directeur</i> n'est pas à prendre au sens hiérarchique du terme, mais dans le sens de l'<i>orientation</i>.\n" +
				"</p>\n" +
				"<p>\n" +
				"    Il détermine les caractéristiques du produit et définit le contenu en termes d'exigences. C'est lui qui définit\n" +
				"    l'<b>ordre</b> dans lequel les exigences seront développées et qui prend les décisions importantes concernant\n" +
				"    l'orientation du projet. Il veille à la rentabilité du produit (ROI).\n" +
				"</p>\n" +
				"<p>\n" +
				"    Il est souhaitable qu'il spécifie&nbsp;les tests fonctionnels (acceptance tests), voire qu'il les passe s'ils ne sont\n" +
				"    pas automatisés.\n" +
				"</p>";
		String expectedKeyConsiderations = "<p>\n" +
				"    L'implication dans le projet doit être importante pour assurer son succès\n" +
				"</p>";

		for (BreakdownElement bde : ScrumProcess.getBreakdownElements()) {
			if (bde.getName().equals("Preparation Phase")) {
				theActivity = (Activity) bde;
				break;
			}
		}
		
		for (BreakdownElement bde : theActivity.getBreakdownElements()) {
			if (bde.getName().equals("Product Owner")) {
				theRoleDesc = (RoleDescriptor) bde;
				break;
			}
		}
		
		theRoleDef = theRoleDesc.getRoleDefinition();
		
		assertEquals(theRoleDef.getMainDescription(), expectedMainDescription);
		assertEquals(theRoleDef.getKeyConsiderations(), expectedKeyConsiderations);
	}
	
	public void testScrumSprintPhasePlanSprintTaskContainsRightAlternativesAndPurpose () {
		if (ScrumProcess == null) {
			ScrumProcess = XMLParser.getProcess(pathScrum);
		}
		Phase thePhase = null;
		Iteration anIteration = null;
		TaskDescriptor taskDescriptor = null;
		String expectedAlternatives = "";
		String expectedPurpose = "Le but est de planifier&nbsp;le sprint&nbsp;qui va commencer";

		for (BreakdownElement bde : ScrumProcess.getBreakdownElements()) {
			if (bde.getName().equals("Sprint Phase")) {
				thePhase = (Phase) bde;
				break;
			}
		}
		
		for (BreakdownElement bde : thePhase.getBreakdownElements()) {
			if (bde.getName().equals("Sprint (n)")) {
				anIteration = (Iteration) bde;
				break;
			}
		}
		
		for (BreakdownElement bde : anIteration.getBreakdownElements()) {
			if (bde.getName().equals("Plan sprint")) {
				taskDescriptor = (TaskDescriptor) bde;
				break;
			}
		}
		
		assertEquals(taskDescriptor.getTaskDefinition().getAlternatives(), expectedAlternatives);
		assertEquals(taskDescriptor.getTaskDefinition().getPurpose(), expectedPurpose);
	}

	public void testSortieEPFInceptionPhaseIterationContainsRight_Alternatives_HowToStaff_Purpose_Descriptions () {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity anActivity = null;
		String expectedAlternatives = "";
		String expectedHowToStaff = "";
		String expectedPurpose = "";
		String expectedBriefDescription = "This iteration template defines the activities (and associated roles and work  products) performed in a typical iteration in the Inception phase. Each activity  and related goals are described.";
		String expectedMainDescription = "<h3>\n" +
    "    Introduction\n" +
    "</h3>\n" +
    "<p>\n" +
    "    The project starts with ";


		for (BreakdownElement bde : OpenUPProcess.getBreakdownElements()) {
			if (bde.getName().equals("inception_phase_iteration")) {
				anActivity = (Activity) bde;
				break;
			}
		}
		
		assertNotNull(anActivity);
		
		assertEquals(anActivity.getAlternatives(), expectedAlternatives);
		assertEquals(anActivity.getHowToStaff(), expectedHowToStaff);
		assertEquals(anActivity.getPurpose(), expectedPurpose);
		
		assertTrue(anActivity.getMainDescription().substring(0, 60).startsWith(expectedMainDescription));
		
		assertTrue(anActivity.getDescription().equals(expectedBriefDescription));
	}
	
	public void testScrumFillerTestProcessContainsRight_Alternatives_HowToStaff_Purpose () {
		Process theProcess;
		theProcess = XMLParser.getProcess(pathScrumFillerTest);
		String expectedAlternatives = "Et sinon ?";
		String expectedHowToStaff = "Une équipe Scrum est composée de 3 à 10 personnes.";
		String expectedPurpose = "Le but du jeux est de faire mieux que monsieur propre GO !";
		
		assertEquals(theProcess.getAlternatives(), expectedAlternatives);
		assertEquals(theProcess.getHowToStaff(), expectedHowToStaff);
		assertEquals(theProcess.getPurpose(), expectedPurpose);
	}
	
	public void testScrumFillerTestPreparationPhaseContainsProductOwnerWithRight_assignmentApproaches_skills_synonyms () {
		Process theProcess;
		theProcess = XMLParser.getProcess(pathScrumFillerTest);
		Phase thePhase = null;
		RoleDescriptor theRoleDesc = null;
		RoleDefinition theRoleDef;
		String expectedAssignmentApproaches = "<p>\r\n" +
				"    Il n'y a qu'une seule personne qui joue ce rôle. Cette personne doit être affectée au projet (dans l'idéal le Directeur\r\n"+
				"    de produit fait partie de l'équipe, mais ce n'est pas toujours possible dans la réalité). Le travail nécessite une\r\n"+
				"    affectation à plein temps ou presque.\r\n"+
				"</p>\r\n"+
				"<p>\r\n"+
				"    Il est&nbsp;important qu'il reste très disponible pour répondre aux questions de l'équipe, pour définir les tests\r\n"+
				"    fonctionnels et&nbsp;donner son avis sur divers aspects du logiciel (interface par exemple).\r\n"+
				"</p>";
		String expectedSkills = "Bonne connaissance du domaine métier.";
		String expectedSynonyms = "Propriétaire de produit (product owner en anglais), Analyste métier (business analyst), Client (dans XP)";

		for (BreakdownElement bde : theProcess.getBreakdownElements()) {
			if (bde.getName().equals("Preparation Phase")) {
				thePhase = (Phase) bde;
				break;
			}
		}
		
		for (BreakdownElement bde : thePhase.getBreakdownElements()) {
			if (bde.getName().equals("Product Owner")) {
				theRoleDesc = (RoleDescriptor) bde;
				break;
			}
		}
		
		theRoleDef = theRoleDesc.getRoleDefinition();
		
		assertEquals(expectedAssignmentApproaches, theRoleDef.getAssignmentApproaches());
		assertEquals(expectedSkills, theRoleDef.getSkills());
		assertEquals(expectedSynonyms, theRoleDef.getSynonyms());
	}
	
	public void testThatXpProcessIsNull() {
		Process theProcess;
		theProcess = XMLParser.getProcess(pathXP);
		
		assertNull(theProcess);
	}
	
	public void testThatProcessFromWrongFileIsNull() {
		Process theProcess;
		
		// Errors are Printed on the Console : IT'S NORMAL
		theProcess = XMLParser.getProcess(pathWrongFile);
		
		assertNull(theProcess);
		
		// IT'S DONE TO AVOID SEEING ERRORS ON THE CONSOLE
		for (int i=0; i<160; ++i) System.out.println();
	}
	
	/**
	 * Plan Iteration must not have additional Roles because its not used in Wilos, but in a normal
	 * case, they should be parsed, and this test case rewritten
	 */
	public void testOpenUpPlanIterationHasOneMainRoleAndNoAdditionalRole() {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		Activity IterationAct = null;
		boolean trouve = false ;
		// First Step, Stop on Inception Iteration
		Iterator<BreakdownElement> itTopLevelAct = OpenUPProcess.getBreakdownElements().iterator();
		
		while (!trouve && itTopLevelAct.hasNext()) {
			BreakdownElement tmpBde = itTopLevelAct.next();
			IterationAct = (Activity) tmpBde;
			trouve = (IterationAct.getPresentationName().equals("Elaboration Iteration [1..n]"));
		}
		assertTrue(trouve);
		trouve= false ;
		//	get the Activity Initiate Project
		Iterator<BreakdownElement> itSecondLevelAct = IterationAct.getBreakdownElements().iterator();
		BreakdownElement tmpBde = null ;
		while (!trouve && itSecondLevelAct.hasNext()) {
			tmpBde = itSecondLevelAct.next();
			trouve = (tmpBde.getPresentationName().equals("Manage Iteration"));
		}
		assertTrue(trouve);
		Activity manageRequirements = (Activity)tmpBde ;
		
		TaskDescriptor planIteration = null;
		for (BreakdownElement bde : manageRequirements.getBreakdownElements()) {
			if (bde.getPresentationName().equals("Plan Iteration")) {
				planIteration = (TaskDescriptor) bde; 
			}
		}
		assertNotNull(planIteration);
		
		assertNotNull(planIteration.getTaskDefinition());
		
		assertEquals(planIteration.getTaskDefinition().getName(), "plan_iteration");
		
		assertNotNull(planIteration.getMainRole());
		
		assertEquals(planIteration.getMainRole().getPresentationName(), "Project Manager");
		
		assertEquals(planIteration.getMainRole().getRoleDefinition().getName(), "project_manager");
		
		assertEquals(planIteration.getAdditionalRoles().size(), 0);
	}
	
	public void testOpenUPRoleDescriptorsMayHaveSameGUID() {
		if (OpenUPProcess == null) {
			OpenUPProcess = XMLParser.getProcess(pathOPenUP);
		}
		
		Vector<String> allRoleDescGuid = new Vector<String>();
		Vector<String> duplicatedDescGuid = new Vector<String>();
		
		final int nbExpected_topLevelActivities = 4;
		final int nbExpected_sndLevelActivities = 19;
		final int nbExpected_roleDescriptors = 61;
		
		// Counters for assertions
		int nb_topLevelActivities = 0;
		int nb_sndLevelActivities = 0;
		
		// First we get with the Top Level Activities : Inception, Elaboration, ...
		for (BreakdownElement bde : OpenUPProcess.getBreakdownElements()) {
			if (bde instanceof Activity) {
				Activity topLevelActivity = (Activity) bde;
				nb_topLevelActivities++;
				
				// For each TopLevelActivity, we get with its sndLevelActivity : Initite Project, ...
				for (BreakdownElement bde_2 : topLevelActivity.getBreakdownElements()) {
					if (bde_2 instanceof Activity) {
						Activity sndLevelActivity = (Activity) bde_2;
						nb_sndLevelActivities++;
						
						// We now can get all the RoleDescriptors of the process at this step
						for (BreakdownElement bde_3 : sndLevelActivity.getBreakdownElements()) {
							if (bde_3 instanceof RoleDescriptor) {
								RoleDescriptor aRD = (RoleDescriptor) bde_3;
								
								//System.out.print(sndLevelActivity.getPresentationName() + " " + sndLevelActivity.getGuid() + " - " + aRD.getPresentationName() + " " + aRD.getGuid());
								
								// If the Guid of this Role is already known
								if (allRoleDescGuid.contains(aRD.getGuid())) {
									duplicatedDescGuid.add(aRD.getGuid());
									//System.out.print("*** DUP ***");
								}
								//System.out.println();
								
								
								// In all cases
								allRoleDescGuid.add(aRD.getGuid());
							}
						}
					}
				}
			}
		}
		
		assertEquals("Top Activities Number", nbExpected_topLevelActivities, nb_topLevelActivities);
		assertEquals("Snd Activities Number", nbExpected_sndLevelActivities, nb_sndLevelActivities);
		assertEquals("Role Descriptors Number", nbExpected_roleDescriptors, allRoleDescGuid.size());
		
		assertNotSame("Duplicated Role Desc", 0, duplicatedDescGuid.size());
	}
	
	public void testOpenUpProcessCrashingIsNotNull() {
		if (OpenUPProcessCrashing == null) {
			OpenUPProcessCrashing = XMLParser.getProcess(pathOpenUpCrashing);
		}
		
		assertNotNull(OpenUPProcessCrashing);
	}
	
	public void testOpenUpProcessCrashingCointainsActivitiesAndRoleDescAndTaskDesk() {
		if (OpenUPProcessCrashing == null) {
			OpenUPProcessCrashing = XMLParser.getProcess(pathOpenUpCrashing);
		}
		
		assertNotNull(OpenUPProcessCrashing);
		
		final int nbExpected_topLevelActivities = 4;
		final int nbExpected_sndLevelActivities = 19;
		final int nbExpected_roleDescriptors = 61;		
		
//		 Counters for assertions
		int nb_topLevelActivities = 0;
		int nb_sndLevelActivities = 0;
		int nb_roleDescriptors = 0;
//		 First we get with the Top Level Activities : Inception, Elaboration, ...
		for (BreakdownElement bde : OpenUPProcess.getBreakdownElements()) {
			if (bde instanceof Activity) {
				Activity topLevelActivity = (Activity) bde;
				nb_topLevelActivities++;
				
				// For each TopLevelActivity, we get with its sndLevelActivity : Initite Project, ...
				for (BreakdownElement bde_2 : topLevelActivity.getBreakdownElements()) {
					if (bde_2 instanceof Activity) {
						Activity sndLevelActivity = (Activity) bde_2;
						nb_sndLevelActivities++;
						
						boolean isThereARoleDescriptor = false;
						boolean isThereATaskDescriptor = false;
						// We now can get all the RoleDescriptors of the process at this step
						for (BreakdownElement bde_3 : sndLevelActivity.getBreakdownElements()) {
							if (bde_3 instanceof RoleDescriptor) {
								RoleDescriptor aRD = (RoleDescriptor) bde_3;
								
								assertNotNull(aRD.getRoleDefinition());
								isThereARoleDescriptor = true;
								
								nb_roleDescriptors++;
							}
							
							if (bde_3 instanceof TaskDescriptor) {
								TaskDescriptor aTD = (TaskDescriptor) bde_3;
								
								assertNotNull(aTD.getTaskDefinition());
								isThereATaskDescriptor = true;
							}
						}
						assertTrue(isThereARoleDescriptor);
						assertTrue(isThereATaskDescriptor);
					}
				}
			}
		}
		
		assertEquals("Top Activities Number", nbExpected_topLevelActivities, nb_topLevelActivities);
		assertEquals("Snd Activities Number", nbExpected_sndLevelActivities, nb_sndLevelActivities);
		assertEquals("Role Descriptors Number", nbExpected_roleDescriptors, nb_roleDescriptors);
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

