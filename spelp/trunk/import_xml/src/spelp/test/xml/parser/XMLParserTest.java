package spelp.test.xml.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.junit.Test;

import spelp.xml.parser.XMLParser;
import woops2.model.process.Process;
import woops2.model.role.RoleDefinition;
import woops2.model.role.RoleDescriptor;
import woops2.model.task.TaskDefinition;
import woops2.model.task.TaskDescriptor;

public class XMLParserTest {
	public static File pathScrum = new File("ressources" + File.separator + "scrum.xml"); 
	public static File pathOPenUP =new File( "ressources" + File.separator + "sortieEPF.xml");
	@Test
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
		File[] f = new File[]{
			pathScrum,pathOPenUP
		};
		for (int p = 0 ; p < f.length ; p++){
			XMLParser.setFile(f[p]);
			System.out.println(f[p].getAbsolutePath());
			try {
				Vector <TaskDefinition> v = new Vector<TaskDefinition>();
				Set<TaskDescriptor> aSet = XMLParser.getAllTaskDescriptors();
				// test if the set of tasks is filled
				assertTrue(aSet.size() != 0);
				// test if all task are unique
				for (Iterator i = aSet.iterator() ; i.hasNext() ; ){
					TaskDefinition t = ((TaskDescriptor)i.next()).getTaskDefinition();
					if (t!=null){
						for (int j = 0 ; j < v.size() ; j++){
							System.out.println("\nID : \n" + t.getId() + "\n" + ((TaskDefinition)v.get(j)).getId() + "\nMemes ref : " + (t == ((TaskDefinition)v.get(j))));
							assertTrue(
								t.getId().equals(((TaskDefinition)v.get(j)).getId()) && t == ((TaskDefinition)v.get(j))
								||
								!t.getId().equals(((TaskDefinition)v.get(j)).getId())
							);
						}
						if (!v.contains(t)){
							v.add(t);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Exception");
				fail();
			}
		}
	}
	@Test
	public void testSetAllRoleDescriptors() {
		File[] f = new File[]{
				pathScrum,pathOPenUP
			};
			for (int p = 0 ; p < f.length ; p++){
				XMLParser.setFile(f[p]);
				System.out.println(f[p].getAbsolutePath());
				try {
					Vector <RoleDefinition> v = new Vector<RoleDefinition>();
					Set<RoleDescriptor> aSet = XMLParser.getAllRoleDescriptors();
					// test if the set of role is filled
					assertTrue(aSet.size() != 0);
					// test if all role are unique
					for (Iterator i = aSet.iterator() ; i.hasNext() ; ){
						RoleDefinition t = ((RoleDescriptor)i.next()).getRoleDefinition();
						if (t!=null){
							for (int j = 0 ; j < v.size() ; j++){
								System.out.println("\nID : \n" + t.getId() + "\n" + ((RoleDefinition)v.get(j)).getId() + "\nMemes ref : " + (t == ((RoleDefinition)v.get(j))));
								assertTrue(
									t.getId().equals(((RoleDefinition)v.get(j)).getId()) && t == ((RoleDefinition)v.get(j))
									||
									!t.getId().equals(((RoleDefinition)v.get(j)).getId())
								);
							}
							if (!v.contains(t)){
								v.add(t);
							}
						}
					}
				

					for(int i=0;i<v.size();i++)
						System.out.println(v.get(i).getName()+" :\n"+v.get(i).getDescription()+"\n");
					
				} catch (Exception e) {
				System.out.println("Exception");
				fail();
			}
		}
	}	

}
