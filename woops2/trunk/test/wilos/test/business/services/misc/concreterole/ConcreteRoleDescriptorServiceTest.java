package wilos.test.business.services.misc.concreterole;

import java.util.List;

import junit.framework.TestCase;
import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
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

	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao;

	private ProjectDao projectDao = null;

	private ConcreteActivityDao concreteActivityDao;

	public static final String NAME = "name";

	public ConcreteRoleDescriptorServiceTest() {
		this.concreteRoleDescriptorService = (ConcreteRoleDescriptorService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteRoleDescriptorService");
		this.projectDao = (ProjectDao) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProjectDao");
		this.concreteTaskDescriptorDao = (ConcreteTaskDescriptorDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteTaskDescriptorDao");
		this.concreteActivityDao = (ConcreteActivityDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteActivityDao");

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

	public void testGetSuperConcreteActivities() {
		// Rk: the setUp method is called here.

		//init.
		ConcreteActivity ca = new ConcreteActivity();
		ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();
		crd.setConcreteName(this.NAME);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao().saveOrUpdateConcreteRoleDescriptor(crd);
		ca.addConcreteBreakdownElement(crd);
		this.concreteActivityDao.saveOrUpdateConcreteActivity(ca);

		//test itself.
		List<ConcreteActivity> list = this.concreteRoleDescriptorService.getSuperConcreteActivities(crd.getId());
		assertTrue(list.size() == 1);

		//clean.
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao().deleteConcreteRoleDescriptor(crd);
		this.concreteActivityDao.deleteConcreteActivity(ca);

		// Rk: the tearDown method is called here.
	}

	public void testGetAllConcreteRoleDescriptorsForProject() {
		// Rk: the setUp method is called here.

		Project project = new Project();
		project.setConcreteName("project");
		this.projectDao.saveOrUpdateProject(project);

		Project project2 = new Project();
		project2.setConcreteName("project2");
		this.projectDao.saveOrUpdateProject(project2);

		this.concreteRoleDescriptor.setProject(project);

		ConcreteRoleDescriptor ctdTmp = new ConcreteRoleDescriptor();
		ctdTmp.setConcreteName("pouet");
		ctdTmp.setProject(project);

		ConcreteRoleDescriptor ctdTmp2 = new ConcreteRoleDescriptor();
		ctdTmp2.setConcreteName("pouet2");
		ctdTmp2.setProject(project2);

		this.concreteRoleDescriptorService
				.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(ctdTmp);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(ctdTmp2);

		List<ConcreteRoleDescriptor> list = this.concreteRoleDescriptorService
				.getAllConcreteRoleDescriptorsForProject(project.getId());

		assertNotNull(list);
		assertTrue(list.size() == 2);

		// clean.
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.deleteConcreteRoleDescriptor(ctdTmp);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.deleteConcreteRoleDescriptor(ctdTmp2);

		this.projectDao.deleteProject(project);
		this.projectDao.deleteProject(project2);
		// Rk: the tearDown method is called here.
	}

	public void testGetAllConcreteTaskDescriptorsForConcreteRoleDescriptor() {
		// Rk: the setUp method is called here.

		ConcreteTaskDescriptor ctdTmp = new ConcreteTaskDescriptor();
		ctdTmp.setConcreteName("pouet");
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(ctdTmp);

		ConcreteTaskDescriptor ctdTmp2 = new ConcreteTaskDescriptor();
		ctdTmp2.setConcreteName("pouet2");
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(ctdTmp2);

		ConcreteTaskDescriptor ctdTmp3 = new ConcreteTaskDescriptor();
		ctdTmp3.setConcreteName("pouet3");
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(ctdTmp3);

		this.concreteRoleDescriptor.addConcreteTaskDescriptor(ctdTmp);
		this.concreteRoleDescriptor.addConcreteTaskDescriptor(ctdTmp2);

		this.concreteRoleDescriptorService
				.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);

		List<ConcreteTaskDescriptor> list = this.concreteRoleDescriptorService
				.getAllConcreteTaskDescriptorsForConcreteRoleDescriptor(this.concreteRoleDescriptor);

		assertNotNull(list);
		assertTrue(list.size() == 2);

		// clean.
		this.concreteTaskDescriptorDao.deleteConcreteTaskDescriptor(ctdTmp);
		this.concreteTaskDescriptorDao.deleteConcreteTaskDescriptor(ctdTmp2);
		this.concreteTaskDescriptorDao.deleteConcreteTaskDescriptor(ctdTmp3);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);
		// Rk: the tearDown method is called here.
	}
}
