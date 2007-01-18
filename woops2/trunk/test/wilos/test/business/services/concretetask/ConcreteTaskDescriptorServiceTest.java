package wilos.test.business.services.concretetask;

import java.util.List;

import junit.framework.TestCase;
import wilos.business.services.concretetask.ConcreteTaskDescriptorService;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.test.TestConfiguration;
import wilos.utils.Constantes.State;

public class ConcreteTaskDescriptorServiceTest extends TestCase {

	ConcreteTaskDescriptorService concreteTaskDescriptorService;

	ConcreteTaskDescriptor concreteTaskDescriptor;

	final String PROJECT_ID = "projectId";

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
		this.concreteTaskDescriptor.setProjectId(PROJECT_ID);
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

		List<ConcreteTaskDescriptor> list = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorsForProject(PROJECT_ID);
		assertNotNull(list);
		assertEquals(list.size(), 1);
		assertEquals(list.get(0), this.concreteTaskDescriptor);

		// Rk: the tearDown method is called here.
	}

	public void testStartConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.finishConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Get this concreteTaskDescriptor.
		List<ConcreteTaskDescriptor> list = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorsForProject(PROJECT_ID);
		ConcreteTaskDescriptor tmp = list.get(0);

		assertNotNull(tmp);
		assertEquals(tmp.getState(), State.STARTED);

		// Rk: the tearDown method is called here.
	}

	public void testSuspendConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.finishConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Get this concreteTaskDescriptor.
		List<ConcreteTaskDescriptor> list = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorsForProject(PROJECT_ID);
		ConcreteTaskDescriptor tmp = list.get(0);

		assertNotNull(tmp);
		assertEquals(tmp.getState(), State.SUSPENDED);

		// Rk: the tearDown method is called here.
	}

	public void testFinishConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.finishConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Get this concreteTaskDescriptor.
		List<ConcreteTaskDescriptor> list = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorsForProject(PROJECT_ID);
		ConcreteTaskDescriptor tmp = list.get(0);

		assertNotNull(tmp);
		assertEquals(tmp.getState(), State.FINISHED);

		// Rk: the tearDown method is called here.
	}

}
