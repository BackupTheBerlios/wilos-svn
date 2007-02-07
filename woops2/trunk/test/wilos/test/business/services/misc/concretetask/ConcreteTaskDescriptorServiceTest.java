package wilos.test.business.services.misc.concretetask;

import java.util.List;

import junit.framework.TestCase;
import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.test.TestConfiguration;
import wilos.utils.Constantes.State;

public class ConcreteTaskDescriptorServiceTest extends TestCase {

	ConcreteTaskDescriptorService concreteTaskDescriptorService;

	ConcreteTaskDescriptor concreteTaskDescriptor;

	ConcreteTaskDescriptorDao concreteTaskDescriptorDao = null;

	ProjectDao projectDao = null;

	TaskDescriptor taskDescriptor;

	public static final String NAME = "name";

	public static final String DESCRIPTION = "description";

	public ConcreteTaskDescriptorServiceTest() {
		this.concreteTaskDescriptorService = (ConcreteTaskDescriptorService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteTaskDescriptorService");
		this.concreteTaskDescriptorDao = (ConcreteTaskDescriptorDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteTaskDescriptorDao");
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

		this.concreteTaskDescriptor = new ConcreteTaskDescriptor();

		this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorDao()
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		this.concreteTaskDescriptorService.getConcreteTaskDescriptorDao()
				.deleteConcreteTaskDescriptor(this.concreteTaskDescriptor);
	}

	public void testGetConcreteTaskDescriptorsForProject() {
		// Rk: the setUp method is called here.

		Project project = new Project();
		project.setConcreteName("project");
		this.projectDao.saveOrUpdateProject(project);

		Project project2 = new Project();
		project2.setConcreteName("project");
		this.projectDao.saveOrUpdateProject(project2);

		this.concreteTaskDescriptor.setProject(project);

		ConcreteTaskDescriptor ctdTmp = new ConcreteTaskDescriptor();
		ctdTmp.setConcreteName("pouet");
		ctdTmp.setProject(project2);

		this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorDao()
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		this.concreteTaskDescriptorService.getConcreteTaskDescriptorDao()
				.saveOrUpdateConcreteTaskDescriptor(ctdTmp);

		List<ConcreteTaskDescriptor> list = this.concreteTaskDescriptorService
				.getAllConcreteTaskDescriptorsForProject(project.getId());

		assertNotNull(list);
		assertTrue(list.size() == 1);

		// Rk: the tearDown method is called here.
	}

	public void testStartConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.startConcreteTaskDescriptor(this.concreteTaskDescriptor);

		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		String id = this.concreteTaskDescriptor.getId();

		// Get this concreteTaskDescriptor.
		ConcreteTaskDescriptor tmpConcreteTaskDescriptor = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptor(id);

		assertNotNull(tmpConcreteTaskDescriptor);
		assertEquals(tmpConcreteTaskDescriptor.getState(), State.STARTED);

		// Rk: the tearDown method is called here.
	}

	public void testSuspendConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.suspendConcreteTaskDescriptor(this.concreteTaskDescriptor);

		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		String id = this.concreteTaskDescriptor.getId();

		// Get this concreteTaskDescriptor.
		ConcreteTaskDescriptor tmpConcreteTaskDescriptor = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptor(id);

		assertNotNull(tmpConcreteTaskDescriptor);
		assertEquals(tmpConcreteTaskDescriptor.getState(), State.SUSPENDED);

		// Rk: the tearDown method is called here.
	}

	public void testFinishConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.finishConcreteTaskDescriptor(this.concreteTaskDescriptor);

		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		String id = this.concreteTaskDescriptor.getId();

		// Get this concreteTaskDescriptor.
		ConcreteTaskDescriptor tmpConcreteTaskDescriptor = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptor(id);

		assertNotNull(tmpConcreteTaskDescriptor);
		assertEquals(tmpConcreteTaskDescriptor.getState(), State.FINISHED);

		// Rk: the tearDown method is called here.
	}
}
