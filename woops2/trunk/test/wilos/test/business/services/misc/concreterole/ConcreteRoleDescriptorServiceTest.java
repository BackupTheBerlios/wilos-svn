package wilos.test.business.services.misc.concreterole;

import java.util.List;

import junit.framework.TestCase;
import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.test.TestConfiguration;

/**
 * 
 * @author eperico
 *
 */
public class ConcreteRoleDescriptorServiceTest extends TestCase {
	
	ConcreteRoleDescriptorService concreteRoleDescriptorService;

	ConcreteRoleDescriptor concreteRoleDescriptor;
	
	TaskDescriptor taskDescriptor;
	
	public static final String NAME = "name" ;

	public static final String DESCRIPTION = "description" ;

	final String PROJECT_ID = "projectId";

	public ConcreteRoleDescriptorServiceTest() {
		this.concreteRoleDescriptorService = (ConcreteRoleDescriptorService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteRoleDescriptorService");
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.concreteRoleDescriptor.setProjectId(PROJECT_ID);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);
	}

	public void testGetConcreteRoleDescriptorsForProject() {
		// Rk: the setUp method is called here.

		this.concreteRoleDescriptor.setProjectId(PROJECT_ID);
		this.concreteRoleDescriptorService
				.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);
		List<ConcreteRoleDescriptor> list = this.concreteRoleDescriptorService
		.getConcreteRoleDescriptorsForProject(PROJECT_ID);
		
		assertNotNull(list);
		assertTrue(list.size() >= 1);
		assertTrue(list.contains(this.concreteRoleDescriptor));

		// Rk: the tearDown method is called here.
	}
}
