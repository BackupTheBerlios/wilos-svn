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
		try {
			processes = XMLParser.getAllProcesses(fileError);
			assertTrue(processes.size() == 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testFindsProcessInRegularFiles() {
		Set<Process> processes;
		
		try {
			processes= XMLParser.getAllProcesses(pathScrum);
			assertTrue(processes.size() == 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		try {
			processes = XMLParser.getAllProcesses(pathOPenUP);
			assertTrue(processes.size() == 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			processes = XMLParser.getAllProcesses(pathMonTest);
			assertTrue(processes.size() == 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testProcessFromScrumFileContainsPhases() {
		HashSet<Process> processes;
		Iterator<Process> it;
		
		try {
			processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
			it = processes.iterator();
			while (it.hasNext()) {
				assertTrue(it.next().getActivities().size() > 0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * passe dans tous les cas a cause de l'implementation faite par Woops
	 */
	public void testPhasesFromScrumContainBreakDownElements() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<Activity> itAct;
		
		try {
			processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
			itProc = processes.iterator();
			while (itProc.hasNext()) {
				itAct = itProc.next().getActivities().iterator();
				
				while (itAct.hasNext()) {
					assertTrue(itAct.next().getBreakDownElements().size() > 0);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testPhase1FromScrumContainsRoleDescriptors() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<Activity> itAct;
		Activity at;
		HashSet<BreakdownElement> bdeSet;
		Iterator<BreakdownElement> itBde;
		boolean isThereARoleDesc;
		
		try {
			processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
			itProc = processes.iterator();
			while (itProc.hasNext()) {
				itAct = itProc.next().getActivities().iterator();
				
					// Only the first Phase has role Descriptors !!!
					Activity tmpAct = itAct.next();
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
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testPhase1FromScrumContainsTaskDescriptors() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<Activity> itAct;
		Activity at;
		HashSet<BreakdownElement> bdeSet;
		Iterator<BreakdownElement> itBde;
		boolean isThereATaskDesc;
		
		try {
			processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
			itProc = processes.iterator();
			while (itProc.hasNext()) {
				itAct = itProc.next().getActivities().iterator();
				
					// Only the first Phase has role Descriptors !!!
					Activity tmpAct = itAct.next();
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testPhase2FromScrumContains1Iteration() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<Activity> itAct;
		HashSet<Activity> actSet;
		Iterator<Activity> itAct2;
		
		try {
			processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
			itProc = processes.iterator();
			while (itProc.hasNext()) {
				// Iterator on the set of the two Phases of Scrum
				itAct = itProc.next().getActivities().iterator();
				
				// We skip the first phase !!!
				itAct.next();
				
				// We work on the second Phase
				Phase secondPhase = (Phase) itAct.next();
				// We get the set of activities of the second Phase
				actSet = (HashSet<Activity>) secondPhase.getActivities();
				// And an iterator on it
				itAct2 = actSet.iterator();
				
				// There is only one Iteration in the second Phase
				assertTrue(itAct2.hasNext());
				assertTrue(itAct2.next() instanceof Iteration);
				assertTrue(! itAct2.hasNext());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testPhase2IterationFromScrumContainsAllExpected() {
		HashSet<Process> processes;
		Iterator<Process> itProc;
		Iterator<Activity> itAct;
		HashSet<Activity> secondPhaseActivities;
		Iterator<Activity> secondPhaseActivitiesIterator;
		
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
		expectedResults.add("Sprint Phase");
		
		int expectedNumber = 12;
		
		
		try {
			processes = (HashSet<Process>) XMLParser.getAllProcesses(pathScrum);
			itProc = processes.iterator();
			while (itProc.hasNext()) {
				// Iterator on the set of the two Phases of Scrum
				itAct = itProc.next().getActivities().iterator();
				
				// We skip the first phase !!!
				itAct.next();
				
				// We work on the second Phase
				Phase secondPhase = (Phase) itAct.next();
				// We get the set of activities of the second Phase
				secondPhaseActivities = (HashSet<Activity>) secondPhase.getActivities();
				// And an iterator on it
				secondPhaseActivitiesIterator = secondPhaseActivities.iterator();
				
				assertTrue(secondPhaseActivitiesIterator.hasNext());
				
				Iteration secondPhaseIteration = (Iteration) secondPhaseActivitiesIterator.next();
				
				Iterator<BreakdownElement> it;
				
				it = secondPhaseIteration.getBreakDownElements().iterator();
				
				int i = 0;
				while (it.hasNext()) {
					assertTrue(expectedResults.contains( ((BreakdownElement) it.next()).getName() ) );
					i += 1;
				}
				
				assertTrue(i == expectedNumber);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	/*// OLDER TEST FUNCTIONS DEPRECATED
	public void testGetDeliveryProcess() {
		try {
			Set<Process> p2 = XMLParser.getAllProcesses(pathScrum);
			Set<Process> p3 = XMLParser.getAllProcesses(pathOPenUP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
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
	}*/
	
	
}
