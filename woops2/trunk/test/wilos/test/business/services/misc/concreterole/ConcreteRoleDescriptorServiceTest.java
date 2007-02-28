package wilos.test.business.services.misc.concreterole;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.test.TestConfiguration;

public class ConcreteRoleDescriptorServiceTest {

	private ConcreteRoleDescriptor mainConcreteRoleDescriptor;

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

	@Before
	public void setUp() {
		this.mainConcreteRoleDescriptor = new ConcreteRoleDescriptor();
		this.mainConcreteRoleDescriptor.setConcreteName(NAME);
	}

	@After
	public void tearDown() {
		//None
	}

	@Test
	public void testGetSuperConcreteActivities() {
		// Rk: the setUp method is called here.

		//init.
		ConcreteActivity ca = new ConcreteActivity();
		ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();
		crd.setConcreteName(NAME);
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

	@Test
	public void testGetAllConcreteRoleDescriptorsForProject() {
		// Rk: the setUp method is called here.

		Project project = new Project();
		project.setConcreteName("project");
		this.projectDao.saveOrUpdateProject(project);

		Project project2 = new Project();
		project2.setConcreteName("project2");
		this.projectDao.saveOrUpdateProject(project2);

		this.mainConcreteRoleDescriptor.setProject(project);

		ConcreteRoleDescriptor ctdTmp = new ConcreteRoleDescriptor();
		ctdTmp.setConcreteName("pouet");
		ctdTmp.setProject(project);

		ConcreteRoleDescriptor ctdTmp2 = new ConcreteRoleDescriptor();
		ctdTmp2.setConcreteName("pouet2");
		ctdTmp2.setProject(project2);

		this.concreteRoleDescriptorService
				.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(this.mainConcreteRoleDescriptor);
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
				.deleteConcreteRoleDescriptor(this.mainConcreteRoleDescriptor);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.deleteConcreteRoleDescriptor(ctdTmp);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.deleteConcreteRoleDescriptor(ctdTmp2);

		this.projectDao.deleteProject(project);
		this.projectDao.deleteProject(project2);
		// Rk: the tearDown method is called here.
	}

	@Test
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

		this.mainConcreteRoleDescriptor.addConcreteTaskDescriptor(ctdTmp);
		this.mainConcreteRoleDescriptor.addConcreteTaskDescriptor(ctdTmp2);

		this.concreteRoleDescriptorService
				.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(this.mainConcreteRoleDescriptor);

		List<ConcreteTaskDescriptor> list = this.concreteRoleDescriptorService
				.getAllConcreteTaskDescriptorsForConcreteRoleDescriptor(this.mainConcreteRoleDescriptor);

		assertNotNull(list);
		assertTrue(list.size() == 2);

		// clean.
		this.concreteTaskDescriptorDao.deleteConcreteTaskDescriptor(ctdTmp);
		this.concreteTaskDescriptorDao.deleteConcreteTaskDescriptor(ctdTmp2);
		this.concreteTaskDescriptorDao.deleteConcreteTaskDescriptor(ctdTmp3);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.deleteConcreteRoleDescriptor(this.mainConcreteRoleDescriptor);
		// Rk: the tearDown method is called here.
	}
}
