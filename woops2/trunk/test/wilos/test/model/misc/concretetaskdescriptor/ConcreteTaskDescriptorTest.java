package wilos.test.model.misc.concretetaskdescriptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.utils.Constantes.State;

public class ConcreteTaskDescriptorTest extends TestCase {

	private ConcreteTaskDescriptor concreteTaskDescriptor ;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
	Date date = null;
	
	public ConcreteTaskDescriptorTest(){
		try {
			date = sdf.parse("18-01-2007 10:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

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
		this.concreteTaskDescriptor.setPlannedFinishingDate(this.date);
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
		assertEquals(this.concreteTaskDescriptor.getState(), State.CREATED);
		assertEquals(this.concreteTaskDescriptor.getPlannedFinishingDate(), this.date);
		assertTrue(taskDescriptor.getConcreteTaskDescriptors().size() == 1);
		System.out.println(this.concreteTaskDescriptor.getState());
		System.out.println(this.concreteTaskDescriptor.getPlannedFinishingDate());
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
