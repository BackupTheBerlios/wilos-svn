package wilos.test.business.services.spem2.role;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.test.TestConfiguration;

public class RoleDescriptorServiceTest {

	private RoleDescriptor roleDescriptor;
	
	private RoleDescriptorDao roleDescriptorDao;
	
	private RoleDescriptorService roleDescriptorService;
	
	public RoleDescriptorServiceTest() {
		// Get the ActivityDao Singleton for managing Activity data
		this.roleDescriptorService = (RoleDescriptorService) TestConfiguration.getInstance()
				.getApplicationContext().getBean("RoleDescriptorService");
		this.roleDescriptorDao = (RoleDescriptorDao) TestConfiguration.getInstance()
		.getApplicationContext().getBean("RoleDescriptorDao");
	}
	
	@Before
	public void setUp() {
		// None
		this.roleDescriptor = new RoleDescriptor();
	}

	@After
	public void tearDown() {
		// None
		this.roleDescriptorService.getRoleDescriptorDao().deleteRoleDescriptor(this.roleDescriptor);
	}
	
	@Test
	public void getAdditionalTasksTest() {
		TaskDescriptor matache = new TaskDescriptor();
		matache.setName("nom1");
		this.roleDescriptor.addAdditionalTask(matache);
		
		Set<TaskDescriptor> list = this.roleDescriptorService.getAdditionalTasks(this.roleDescriptor);
		
		assertTrue(list.size() == 1);
		assertTrue(list.contains(matache));
	}
	
	@Test
	public void getPrimaryTasksTest() {
		TaskDescriptor matache = new TaskDescriptor();
		matache.setName("nom1");
		this.roleDescriptor.addPrimaryTask(matache);
		
		Set<TaskDescriptor> list = this.roleDescriptorService.getPrimaryTasks(this.roleDescriptor);
		
		assertTrue(list.size() == 1);
		assertTrue(list.contains(matache));
		
	}
	
	@Test
	public void getRoleDescriptorById() {
		//FIXME
		/*this.roleDescriptorDao.saveOrUpdateRoleDescriptor(this.roleDescriptor);
		System.out.println("IDDDDDDDDD"+this.roleDescriptor.getId());
		RoleDescriptor rd = this.roleDescriptorService.getRoleDescriptorById(this.roleDescriptor.getId());
		assertTrue(this.roleDescriptor.equals(rd));
		this.roleDescriptorDao.deleteRoleDescriptor(this.roleDescriptor);*/
	}
}
