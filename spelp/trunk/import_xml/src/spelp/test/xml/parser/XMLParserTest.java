package spelp.test.xml.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.junit.Test;

import spelp.xml.parser.XMLParser;
import spelp.xml.parser.XMLUtils;
import woops2.model.process.Process;
import woops2.model.role.RoleDefinition;
import woops2.model.role.RoleDescriptor;
import woops2.model.task.TaskDefinition;
import woops2.model.task.TaskDescriptor;

public class XMLParserTest extends XMLParser {
	public static File pathScrum = new File("ressources" + File.separator + "scrum.xml"); 
	public static File pathOPenUP =new File( "ressources" + File.separator + "sortieEPF.xml");
	
	
	public void testStart(){
		File[] f = new File[]{
				pathScrum,pathOPenUP
			};
			for (int p = 0 ; p < f.length ; p++){
				XMLUtils.setDocument(f[p]);
				XMLParser.start();
				assertTrue(XMLParser.RoleList.size() != 0 );
				assertTrue(XMLParser.TasksList.size() != 0 );
			}
	}
	
	
	public void testGetProcess(){
		File[] f = new File[]{
				pathScrum,pathOPenUP
		};
		for (int i = 0 ; i < f.length ; i++){
			Process p = XMLParser.getProcess(f[i]);
			assertTrue(p.getBreakDownElements().size() != 0);
		}
	}
	
	@Test
	public void testGetAllTaskDescriptors() {
		System.out.println("Test de getAllTaskDescriptors");
		File[] f = new File[]{
			pathScrum,pathOPenUP
		};
		for (int p = 0 ; p < f.length ; p++){
			System.out.println(">>>>>>>"+f[p].getName());
			XMLUtils.setDocument(f[p]);
			XMLParser.start();
			try {
				Set<RoleDescriptor> ensRole = XMLParser.getAllRoleDescriptors();
				Vector <TaskDefinition> v = new Vector<TaskDefinition>();
				Set<TaskDescriptor> aSet = XMLParser.getAllTaskDescriptors(ensRole);
				// test if the set of tasks is filled
				assertTrue(aSet.size() != 0);
				// test if all task are unique
				for (Iterator i = aSet.iterator() ; i.hasNext() ; ){
					TaskDescriptor td = (TaskDescriptor)i.next();		
					//assertTrue(td.getMainRole() != null);
					TaskDefinition t = td.getTaskDefinition();
					if (t!=null){
						if (td.getMainRole() != null){
							System.out.println(t.getName() +"\n MAIN ROLE : " + td.getMainRole().getName());
							System.out.print(" ADDITIONAL ROLES : ");
							for (Iterator j = td.getAdditionalRoles().iterator() ; j.hasNext() ; ){
								System.out.print(((RoleDescriptor)j.next()).getName() + " ");
								
							}
							System.out.print("\n");
						}
						System.out.println(" NB STEPS : " + t.getSteps().size());
						for (int j = 0 ; j < v.size() ; j++){
							assertTrue(
								t.getIdEPF().equals(((TaskDefinition)v.get(j)).getIdEPF()) && t == ((TaskDefinition)v.get(j))
								||
								!t.getIdEPF().equals(((TaskDefinition)v.get(j)).getIdEPF())
							);
						}
						if (!v.contains(t)){
							v.add(t);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Exception");
				e.printStackTrace();
				fail();
			}
		}
	}
	
	@Test
	public void testSetAllRoleDescriptors() {
		System.out.println("Test de getAllRoleDescriptors");
		File[] f = new File[]{
				pathScrum,pathOPenUP
			};
			for (int p = 0 ; p < f.length ; p++){
				System.out.println(">>>>>>>"+f[p].getName());
				XMLUtils.setDocument(f[p]);
				System.out.println(f[p].getAbsolutePath());
				try {
					Vector <RoleDefinition> v = new Vector<RoleDefinition>();
					Set<RoleDescriptor> aSet = XMLParser.getAllRoleDescriptors();
					// test if the set of role is filled
					assertTrue(aSet.size() != 0);
					// test if all role are unique
					for (Iterator i = aSet.iterator() ; i.hasNext() ; ){
						RoleDescriptor rd = (RoleDescriptor)i.next() ;
						RoleDefinition t = rd.getRoleDefinition();
						assertNotNull(t);
						System.out.println("ROLE DESCRIPTOR : " + rd.getName() + " ROLE DEF : " +  t.getName()+" :\n"+t.getDescription()+"\n");
						for (int j = 0 ; j < v.size() ; j++){
							//System.out.println("\nID : \n" + t.getIdEPF() + "\n" + ((RoleDefinition)v.get(j)).getId() + "\nMemes ref : " + (t == ((RoleDefinition)v.get(j))));
							assertTrue(
								t.getIdEPF().equals(((RoleDefinition)v.get(j)).getIdEPF()) && t == ((RoleDefinition)v.get(j))
								||
								!t.getIdEPF().equals(((RoleDefinition)v.get(j)).getIdEPF())
							);
						}
						if (!v.contains(t)){
							v.add(t);
						}
					}
				} catch (Exception e) {
				System.out.println("Exception");
				fail();
			}
		}
	}	

}
