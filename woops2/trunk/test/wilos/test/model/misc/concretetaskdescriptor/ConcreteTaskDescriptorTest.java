package wilos.test.model.misc.concretetaskdescriptor;

import java.text.ParseException;
import java.util.Date;

import junit.framework.TestCase;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.utils.Constantes;
import wilos.utils.Constantes.State;

public class ConcreteTaskDescriptorTest extends TestCase {

	private ConcreteTaskDescriptor concreteTaskDescriptor ;

	private Date date;

	public static final String CONCRETE_NAME = "Concrete name" ;

	public static final int ACCOMPLISHED_TIME = 15 ;

	public static final int PLANNED_TIME = 24 ;

	public static final String PLANNED_FINISHING_DATE_STRING = "18/01/2007 10:00" ;


	public ConcreteTaskDescriptorTest(){
		try {
			date = Constantes.DATE_FORMAT.parse(PLANNED_FINISHING_DATE_STRING);
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
		this.concreteTaskDescriptor.setConcreteName(CONCRETE_NAME);
		this.concreteTaskDescriptor.setAccomplishedTime(ACCOMPLISHED_TIME);
		this.concreteTaskDescriptor.setPlannedTime(PLANNED_TIME);
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

	public void testClone () {
		try{
			assertEquals(this.concreteTaskDescriptor, this.concreteTaskDescriptor.clone()) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}
	}

	public void testHashCode() {
		ConcreteTaskDescriptor ctd = new ConcreteTaskDescriptor();
		ctd.setConcreteName(CONCRETE_NAME);
		ctd.setAccomplishedTime(ACCOMPLISHED_TIME);
		ctd.setPlannedTime(PLANNED_TIME);
		ctd.setPlannedFinishingDate(this.date);

		assertNotNull(this.concreteTaskDescriptor.hashCode());
		assertNotNull(ctd.hashCode());
		assertEquals(this.concreteTaskDescriptor.hashCode(),ctd.hashCode());
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
