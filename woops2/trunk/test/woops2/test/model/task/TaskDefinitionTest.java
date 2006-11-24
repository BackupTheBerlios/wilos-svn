
package woops2.test.model.task ;

import java.util.HashSet ;
import java.util.Set ;

import junit.framework.TestCase ;
import woops2.model.task.Step ;
import woops2.model.task.TaskDefinition ;
import woops2.model.task.TaskDescriptor ;

/**
 * @author eperico
 * 
 * Unit test case of TaskDefinition model class
 * 
 */
public class TaskDefinitionTest extends TestCase {

	private TaskDefinition taskDefinition ;

	/**
	 * attribute from Element class
	 */
	public static final String NAME = "name" ;

	public static final String DESCRIPTION = "description" ;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.taskDefinition = new TaskDefinition() ;
		this.taskDefinition.setDescription(DESCRIPTION) ;
		this.taskDefinition.setName(NAME) ;
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link woops2.model.task.TaskDefinition#hashCode()}.
	 */
	public void testHashCode() {
		assertNotNull(this.taskDefinition.hashCode()) ;
	}

	/**
	 * Test method for {@link woops2.model.task.TaskDefinition#equals(Object obj)}.
	 */
	public void testEquals() {

		// Assert if it's equal by references.
		assertTrue("By references", this.taskDefinition.equals(this.taskDefinition)) ;

		// Assert if it's equal field by field.
		TaskDefinition td1 = null ;
		try{
			td1 = this.taskDefinition.clone() ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testEquals method") ;
		}
		assertTrue("Field by field", this.taskDefinition.equals(td1)) ;

		// Assert if it's not equal.
		TaskDescriptor td2 = new TaskDescriptor() ;
		td2.setIsRepeatable(false) ;
		td2.setPrefix("prefixFalse") ;
		assertFalse("Not equals", this.taskDefinition.equals(td2)) ;
	}

	/**
	 * Test method for {@link woops2.model.task.TaskDefinition#addStep(woops2.model.task.Step)}.
	 */
	public void testAddStep() {
		Step step = new Step() ;
		step.setDescription(DESCRIPTION) ;
		step.setName(NAME) ;

		this.taskDefinition.addStep(step) ;

		assertTrue(this.taskDefinition.getSteps().size() == 1) ;
		assertNotNull(step.getTaskDefinition()) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDefinition#addTaskDescriptor(woops2.model.task.TaskDescriptor)}.
	 */
	public void testAddTaskDescriptor() {
		TaskDescriptor taskDescriptor = new TaskDescriptor() ;
		taskDescriptor.setDescription(DESCRIPTION) ;
		taskDescriptor.setName(NAME) ;

		this.taskDefinition.addTaskDescriptor(taskDescriptor) ;

		assertTrue(this.taskDefinition.getTaskDescriptors().size() == 1) ;
		assertNotNull(taskDescriptor.getTaskDefinition()) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDefinition#addToAllSteps(woops2.model.task.Step)}.
	 */
	public void testAddToAllSteps() {
		Step step1 = new Step() ;
		step1.setDescription("description1") ;
		step1.setName("name1") ;

		Step step2 = new Step() ;
		step2.setDescription("description2") ;
		step2.setName("name2") ;

		Set<Step> set = new HashSet<Step>() ;
		set.add(step1) ;
		set.add(step2) ;

		this.taskDefinition.addAllSteps(set) ;

		assertFalse(this.taskDefinition.getSteps().isEmpty()) ;
		assertEquals(2, this.taskDefinition.getSteps().size()) ;
		assertNotNull(step1.getTaskDefinition()) ;
		assertNotNull(step2.getTaskDefinition()) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDefinition#addToAllTaskDesciptors(woops2.model.task.TaskDesciptor)}.
	 */
	public void testAddToAllTaskDesciptors() {
		TaskDescriptor td1 = new TaskDescriptor() ;
		td1.setDescription("description1") ;
		td1.setName("name1") ;

		TaskDescriptor td2 = new TaskDescriptor() ;
		td2.setDescription("description2") ;
		td2.setName("name2") ;

		Set<TaskDescriptor> set = new HashSet<TaskDescriptor>() ;
		set.add(td1) ;
		set.add(td2) ;

		this.taskDefinition.addAllTaskDesciptors(set) ;

		assertFalse(this.taskDefinition.getTaskDescriptors().isEmpty()) ;
		assertEquals(2, this.taskDefinition.getTaskDescriptors().size()) ;
		assertNotNull(td1.getTaskDefinition()) ;
		assertNotNull(td2.getTaskDefinition()) ;
	}

	/**
	 * Test method for {@link woops2.model.task.TaskDefinition#removeStep(woops2.model.task.Step)}.
	 */
	public void testRemoveStep() {
		Step step = new Step() ;
		step.setDescription(DESCRIPTION) ;
		step.setName(NAME) ;

		this.taskDefinition.removeStep(step) ;

		assertTrue(this.taskDefinition.getSteps().isEmpty()) ;
		assertNull(step.getTaskDefinition()) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDefinition#removeTaskDescriptor(woops2.model.task.TaskDescriptor)}.
	 */
	public void testRemoveTaskDescriptor() {
		TaskDescriptor taskDescriptor = new TaskDescriptor() ;
		taskDescriptor.setDescription(DESCRIPTION) ;
		taskDescriptor.setName(NAME) ;

		this.taskDefinition.removeTaskDescriptor(taskDescriptor) ;

		assertTrue(this.taskDefinition.getTaskDescriptors().isEmpty()) ;
		assertNull(taskDescriptor.getTaskDefinition()) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDefinition#removeAllSteps(woops2.model.task.Step)}.
	 */
	public void testRemoveAllSteps() {
		Step step1 = new Step() ;
		step1.setDescription(DESCRIPTION) ;
		step1.setName("otherName") ;

		Step step2 = new Step() ;
		step2.setDescription(DESCRIPTION) ;
		step2.setName(NAME) ;

		Set<Step> set = new HashSet<Step>() ;
		set.add(step1) ;
		set.add(step2) ;

		this.taskDefinition.addAllSteps(set) ;
		assertTrue(this.taskDefinition.getSteps().size() == 2) ;
		assertNotNull(step1.getTaskDefinition()) ;
		assertNotNull(step2.getTaskDefinition()) ;

		this.taskDefinition.removeAllSteps() ;
		assertTrue(this.taskDefinition.getSteps().isEmpty()) ;
		assertNull(step1.getTaskDefinition()) ;
		assertNull(step2.getTaskDefinition()) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDefinition#removeAllTaskDescriptors(woops2.model.task.TaskDescriptor)}.
	 */
	public void testRemoveAllTaskDescriptors() {
		TaskDescriptor td1 = new TaskDescriptor() ;
		td1.setDescription(DESCRIPTION) ;
		td1.setName(NAME) ;

		TaskDescriptor td2 = new TaskDescriptor() ;
		td2.setDescription(DESCRIPTION) ;
		td2.setName("otherName") ;

		Set<TaskDescriptor> set = new HashSet<TaskDescriptor>() ;
		set.add(td1) ;
		set.add(td2) ;

		this.taskDefinition.addAllTaskDesciptors(set) ;
		assertTrue(this.taskDefinition.getTaskDescriptors().size() == 2) ;
		assertNotNull(td1.getTaskDefinition()) ;
		assertNotNull(td2.getTaskDefinition()) ;

		this.taskDefinition.removeAllTaskDescriptors() ;
		assertTrue(this.taskDefinition.getTaskDescriptors().isEmpty()) ;
		assertNull(td1.getTaskDefinition()) ;
		assertNull(td2.getTaskDefinition()) ;
	}
}
