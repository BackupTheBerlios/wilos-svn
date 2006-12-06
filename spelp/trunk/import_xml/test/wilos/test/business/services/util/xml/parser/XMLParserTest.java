package spelp.test.xml.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Vector;

import org.junit.Test;

import spelp.xml.parser.XMLParser;
import sun.net.smtp.SmtpClient;
import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.process.Process;
import woops2.model.role.RoleDefinition;
import woops2.model.role.RoleDescriptor;
import woops2.model.task.Step;
import woops2.model.task.TaskDefinition;
import woops2.model.task.TaskDescriptor;

public class XMLParserTest extends XMLParser {
	public static File pathScrum = new File("ressources" + File.separator + "scrum.xml"); 
	public static File pathOPenUP =new File("ressources" + File.separator + "sortieEPF.xml");
	public static File pathMonTest =new File("ressources" + File.separator + "monTest.xml");
	public static File fileError =new File("prout");
		
	@Test
	public void testGetProcess(){
		Process p;		
		System.out.println("Test de getProcess");
//		 test avec un fichier XML a nous
		try {
			p = XMLParser.getProcess(pathMonTest );
			System.out.println("Test avec mon test");
			RoleDescriptor rdXML ;
			RoleDefinition rdfXML ;
			TaskDescriptor tdXML ;
			TaskDefinition tdfXML ;
			
			
			RoleDefinition notreRdef = new RoleDefinition() ;
			notreRdef.setIdEPF("1");
			notreRdef.setName("Product Owner");
			notreRdef.setDescription("petite description");
			
			RoleDescriptor notreRdesc = new RoleDescriptor() ;
			notreRdesc.setName("ScrumMaster");
			notreRdesc.setIdEPF("5");
			notreRdesc.setIsOptional(false);
			notreRdesc.setIsPlanned(true);
			notreRdesc.addRoleDefinition(notreRdef);
			notreRdesc.setPrefix("");
			
			Step s1 = new Step();
			s1.setIdEPF("3");
			s1.setName("Definir le but du sprint");
			s1.setDescription("coucou");
			
			TaskDefinition notreTdef = new TaskDefinition() ;
			notreTdef.setIdEPF("2");
			notreTdef.setName("Plan sprint");
			notreTdef.setDescription("Planification du court terme");
			notreTdef.addStep(s1);
			
			TaskDescriptor notreTdesc = new TaskDescriptor () ;
			notreTdesc.setIdEPF("4");
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
					assertTrue(td.getIdEPF().equals(notreTdesc.getIdEPF() )) ;
					assertTrue(td.getPrefix().equals(notreTdesc.getPrefix() )) ;
					assertTrue(td.getDescription().equals(notreTdesc.getDescription() )) ;
					assertTrue(td.getIsEvenDriven().equals(notreTdesc.getIsEvenDriven() )) ;
					assertTrue(td.getIsOngoing().equals(notreTdesc.getIsOngoing()) ) ;
					assertTrue(td.getIsOptional().equals(notreTdesc.getIsOptional()) ) ;
					assertTrue(td.getIsPlanned().equals(notreTdesc.getIsPlanned()) ) ;
					assertTrue(td.getIsRepeatable().equals(notreTdesc.getIsRepeatable()) ) ;
					assertTrue(td.getTaskDefinition().getIdEPF().equals(notreTdesc.getTaskDefinition().getIdEPF()));
					assertTrue(td.getTaskDefinition().getName().equals(notreTdesc.getTaskDefinition().getName()));
					assertTrue(td.getHasMultipleOccurrences().equals(notreTdesc.getHasMultipleOccurrences()));
					assertTrue(td.getMainRole().getIdEPF().equals(notreTdesc.getMainRole().getIdEPF()));
					for (RoleDescriptor stmp : td.getAdditionalRoles()){
						assertTrue(stmp.getIdEPF().equals(notreRdesc.getIdEPF()));
					}
					for (TaskDescriptor stmp : td.getTaskDefinition().getTaskDescriptors()){
						assertTrue(stmp.equals(tdXML));
						assertTrue(stmp.getIdEPF().equals(notreTdesc.getIdEPF()));
						assertTrue(stmp.getDescription().equals(notreTdesc.getDescription()));
						assertTrue(stmp.getName().equals(notreTdesc.getName()));
						assertTrue(stmp.getTaskDefinition().getIdEPF().equals(notreTdesc.getTaskDefinition().getIdEPF()));
					}
					for (Step stmp : td.getTaskDefinition().getSteps()){
						assertTrue(stmp.getIdEPF().equals(s1.getIdEPF()));
						assertTrue(stmp.getDescription().equals(s1.getDescription()));
						assertTrue(stmp.getName().equals(s1.getName()));
						assertTrue(stmp.getTaskDefinition().getIdEPF().equals(s1.getTaskDefinition().getIdEPF()));
					}
				}
				else if (t instanceof RoleDescriptor) {
					RoleDescriptor td = (RoleDescriptor) t ;
					rdXML = td ;
					rdfXML = td.getRoleDefinition();
					assertTrue(td.getIdEPF().equals(notreRdesc.getIdEPF() )) ;
					assertTrue(td.getDescription().equals(notreRdesc.getDescription() )) ;
					assertTrue(td.getIsOptional().equals(notreRdesc.getIsOptional() )) ;
					assertTrue(td.getIsPlanned().equals(notreRdesc.getIsPlanned() )) ;
					assertTrue(td.getName().equals(notreRdesc.getName() )) ;
					assertTrue(td.getPrefix().equals(notreRdesc.getPrefix() )) ;
					assertTrue(td.getRoleDefinition().getIdEPF().equals(notreRdesc.getRoleDefinition().getIdEPF()));
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
				pathScrum,pathOPenUP
		};
		for (int i = 0 ; i < f.length ; i++){
			System.out.println(">>>>>>>"+f[i].getName());;
			try {
				p = XMLParser.getProcess(f[i]);
				assertTrue(p.getBreakDownElements().size() != 0);
				if (f[i].getName().equals("scrum.xml")){
					// nombre de role dans scrum.xml
					assertTrue(RoleList.size() == 4) ;
					// nombre de task dans scrum.xml
					assertTrue(TasksList.size() == 8) ;
					// nombre de role descriptor dans scrum.xml
					assertTrue(p.getBreakDownElements().size() == 35); 
				}
				else if(f[i].getName().equals("sortieEPF.xml")){
					// nombre de role dans sortieEPF.xml
					assertTrue(RoleList.size() == 13) ;
					// nombre de task dans sortieEPF.xml
					assertTrue(TasksList.size() == 23) ;
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
