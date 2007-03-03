package wilos.test.business.services.util.xml.parser;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

import junit.framework.TestCase;
import wilos.business.services.util.xml.parser.XMLParser;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

public class XMLParserTestAnalyst extends TestCase {
	public static File pathOPenUP =new File("test"+ File.separator +"wilos"+ File.separator +"test"+File.separator+"business"+ File.separator+ "services" +File.separator +  "util" +File.separator  +  "xml" +File.separator  + "resources" +File.separator  + "sortieEPF.xml");
	
	public void testOpenUPInitiateProjectContainsExpectedRoleDescriptors() {
		Iterator<BreakdownElement> itTopLevelAct;
		Activity secondLevelActivity;
		Iterator<BreakdownElement> BdeIterator;
		BreakdownElement tmpBde;
		
		HashSet<String> expectedResults = new HashSet<String>();
		expectedResults.add("Stakeholder");
		expectedResults.add("Project Manager");
		expectedResults.add("Analyst");
		expectedResults.add("Tester");
		expectedResults.add("Architect");
		
		Process openUPProcess = XMLParser.getProcess(pathOPenUP);
		
		System.out.println("==> Procedure de test des task de analyst by SPELP\n");
			// Iterator on the set of the four Phases of OpenUP
			itTopLevelAct = openUPProcess.getBreakdownElements().iterator();
			
			// We want the third Phase : Construction Iteration
			while (itTopLevelAct.hasNext()) {
				secondLevelActivity = (Activity) itTopLevelAct.next();
				
				BdeIterator = secondLevelActivity.getBreakdownElements().iterator();
				while (BdeIterator.hasNext()) {
					tmpBde = BdeIterator.next();
					if (tmpBde instanceof Activity) {
						Activity a = (Activity)tmpBde ;
						Iterator<BreakdownElement> Actiterator = a.getBreakdownElements().iterator();
						while (Actiterator.hasNext()){
							BreakdownElement bdee = Actiterator.next();
							if (bdee instanceof RoleDescriptor && bdee.getPresentationName().equals("Analyst")){
								RoleDescriptor roro = (RoleDescriptor) bdee;
								System.out.println("Affichage du Role Descriptor");
								System.out.println("\tgetName : " + roro.getName());
								System.out.println("\tgetPresentationName : " + roro.getPresentationName());
								System.out.println("\tgetGuid : " + roro.getGuid());
								System.out.println("\t==> Affichage de ses tasks !!!");
								Iterator<TaskDescriptor> iter = roro.getPrimaryTasks().iterator();
								while (iter.hasNext()){
									TaskDescriptor td = iter.next();
									System.out.println("\t\t" + td.getPresentationName() + " " + td.getGuid());
								}
								System.out.println();
								
							}
						}
					}
				}
				
		}		
	}
}
