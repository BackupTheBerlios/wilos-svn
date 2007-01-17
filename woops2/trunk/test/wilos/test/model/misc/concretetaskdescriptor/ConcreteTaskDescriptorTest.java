package wilos.test.model.misc.concretetaskdescriptor;

import junit.framework.TestCase;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

public class ConcreteTaskDescriptorTest extends TestCase {

	private ConcreteTaskDescriptor concreteTaskDescriptor ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.concreteTaskDescriptor = new ConcreteTaskDescriptor() ;
		this.concreteTaskDescriptor.setConcreteName("Concrete Name");
		this.concreteTaskDescriptor.setAccomplishedTime(15);
		this.concreteTaskDescriptor.setPlannedTime(24);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}
	
	public void testAddTaskDescriptor() {
		TaskDescriptor taskDescriptor = new TaskDescriptor();
		this.concreteTaskDescriptor.addTaskDescriptor(taskDescriptor);
		assertNotNull(this.concreteTaskDescriptor.getTaskDescriptor());
		assertEquals(this.concreteTaskDescriptor.getTaskDescriptor(), taskDescriptor);
		assertTrue(taskDescriptor.getConcreteTaskDescriptors().size() == 1);
	}

	public void testRemoveTaskDescriptor() {
		TaskDescriptor taskDescriptor = new TaskDescriptor();
		
		this.concreteTaskDescriptor.addTaskDescriptor(taskDescriptor);
		assertNotNull(this.concreteTaskDescriptor.getTaskDescriptor());
		assertEquals(this.concreteTaskDescriptor.getTaskDescriptor(), taskDescriptor);
		
		this.concreteTaskDescriptor.removeTaskDescriptor(taskDescriptor);
		assertNull(this.concreteTaskDescriptor.getTaskDescriptor());
		assertTrue(taskDescriptor.getConcreteTaskDescriptors().size() == 0);
	}

}
