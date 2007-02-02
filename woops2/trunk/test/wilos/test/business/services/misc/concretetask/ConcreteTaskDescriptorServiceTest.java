package wilos.test.business.services.misc.concretetask;

import java.util.List;

import junit.framework.TestCase;
import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.test.TestConfiguration;
import wilos.utils.Constantes.State;

public class ConcreteTaskDescriptorServiceTest extends TestCase {

	ConcreteTaskDescriptorService concreteTaskDescriptorService;

	ConcreteTaskDescriptor concreteTaskDescriptor;

	ProjectDao projectDao = (ProjectDao) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectDao");

	TaskDescriptor taskDescriptor;

	public static final String NAME = "name" ;

	public static final String DESCRIPTION = "description" ;

	public ConcreteTaskDescriptorServiceTest() {
		this.concreteTaskDescriptorService = (ConcreteTaskDescriptorService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteTaskDescriptorService");
		this.concreteTaskDescriptor = new ConcreteTaskDescriptor();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
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
		project.setName("project");

		this.projectDao.saveOrUpdateProject(project);

		this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorDao()
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		List<ConcreteTaskDescriptor> list = this.concreteTaskDescriptorService
		.getConcreteTaskDescriptorsForProject(project.getProject_id());

		assertNotNull(list);
		assertTrue(list.size() >= 1);
		assertTrue(list.contains(this.concreteTaskDescriptor));

		// Rk: the tearDown method is called here.
	}

	public void testStartConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		Project project = new Project();
		project.setName("project");

		this.projectDao.saveOrUpdateProject(project);
		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.startConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Get this concreteTaskDescriptor.
		List<ConcreteTaskDescriptor> list = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorsForProject(project.getProject_id());
		ConcreteTaskDescriptor tmp = list.get(0);

		assertNotNull(tmp);
		assertEquals(tmp.getState(), State.STARTED);

		// Rk: the tearDown method is called here.
	}

	public void testSuspendConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		Project project = new Project();
		project.setName("project");

		this.projectDao.saveOrUpdateProject(project);

		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.suspendConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Get this concreteTaskDescriptor.
		List<ConcreteTaskDescriptor> list = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorsForProject(project.getProject_id());
		ConcreteTaskDescriptor tmp = list.get(0);

		assertNotNull(tmp);
		assertEquals(tmp.getState(), State.SUSPENDED);

		// Rk: the tearDown method is called here.
	}

	public void testFinishConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		Project project = new Project();
		project.setName("project");

		this.projectDao.saveOrUpdateProject(project);
		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.finishConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Get this concreteTaskDescriptor.
		List<ConcreteTaskDescriptor> list = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorsForProject(project.getProject_id());
		ConcreteTaskDescriptor tmp = list.get(0);

		assertNotNull(tmp);
		assertEquals(tmp.getState(), State.FINISHED);

		// Rk: the tearDown method is called here.
	}
}
