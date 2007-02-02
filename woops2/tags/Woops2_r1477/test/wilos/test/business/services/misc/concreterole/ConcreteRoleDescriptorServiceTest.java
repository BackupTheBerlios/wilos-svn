package wilos.test.business.services.misc.concreterole;

import java.util.List;

import junit.framework.TestCase;
import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
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

	ProjectDao projectDao = (ProjectDao) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectDao");

	TaskDescriptor taskDescriptor;

	public static final String NAME = "name" ;

	public static final String DESCRIPTION = "description" ;

	final String PROJECT_ID = "projectId";

	public ConcreteRoleDescriptorServiceTest() {
		this.concreteRoleDescriptorService = (ConcreteRoleDescriptorService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteRoleDescriptorService");
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor();
		this.concreteRoleDescriptor.setConcreteName("pouet");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
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

		Project project = new Project();
		project.setName("project");

		this.projectDao.saveOrUpdateProject(project);

		this.concreteRoleDescriptor.setProject(project);
		this.concreteRoleDescriptorService
				.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);

		List<ConcreteRoleDescriptor> list = this.concreteRoleDescriptorService
		.getConcreteRoleDescriptorsForProject(project.getProject_id());

		assertNotNull("pouet",list);
		assertTrue("taille",list.size() >= 1);
		assertEquals("pouet",((ConcreteRoleDescriptor) list.get(0)).getConcreteName());

		// Rk: the tearDown method is called here.
	}
}
