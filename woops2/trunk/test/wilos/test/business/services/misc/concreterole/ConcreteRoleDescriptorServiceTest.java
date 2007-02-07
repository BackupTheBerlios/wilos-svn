package wilos.test.business.services.misc.concreterole;

import java.util.List;

import junit.framework.TestCase;
import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.test.TestConfiguration;

/**
 *
 * @author eperico
 *
 */
public class ConcreteRoleDescriptorServiceTest extends TestCase {

	private ConcreteRoleDescriptor concreteRoleDescriptor;

	private ConcreteRoleDescriptorService concreteRoleDescriptorService = null;

	private ProjectDao projectDao = null;

	public static final String NAME = "name";

	public ConcreteRoleDescriptorServiceTest() {
		this.concreteRoleDescriptorService = (ConcreteRoleDescriptorService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteRoleDescriptorService");
		this.projectDao = (ProjectDao) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProjectDao");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		this.concreteRoleDescriptor = new ConcreteRoleDescriptor();
		this.concreteRoleDescriptor.setConcreteName(NAME);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetAllConcreteRoleDescriptorsForProject() {
		// Rk: the setUp method is called here.

		Project project = new Project();
		project.setConcreteName("project");
		this.projectDao.saveOrUpdateProject(project);

		Project project2 = new Project();
		project2.setConcreteName("project");
		this.projectDao.saveOrUpdateProject(project2);

		this.concreteRoleDescriptor.setProject(project);

		ConcreteRoleDescriptor ctdTmp = new ConcreteRoleDescriptor();
		ctdTmp.setConcreteName("pouet");
		ctdTmp.setProject(project2);

		this.concreteRoleDescriptorService
				.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(ctdTmp);

		List<ConcreteRoleDescriptor> list = this.concreteRoleDescriptorService
				.getAllConcreteRoleDescriptorsForProject(project.getId());

		assertNotNull(list);
		assertTrue(list.size() == 1);

		//clean.
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
		.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
		.deleteConcreteRoleDescriptor(ctdTmp);

		// Rk: the tearDown method is called here.
	}
}
