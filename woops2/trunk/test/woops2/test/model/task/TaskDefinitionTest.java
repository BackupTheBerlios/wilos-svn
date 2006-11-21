package woops2.test.model.task;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import woops2.model.task.Step;
import woops2.model.task.TaskDefinition;
import woops2.model.task.TaskDescriptor;

/**
 * @author eperico
 * 
 * Unit test case of TaskDefinition model class
 * 
 */
public class TaskDefinitionTest extends TestCase {

	private TaskDefinition taskDefinition;

	/**
	 * attribute from Element class
	 */
	public static final String NAME = "name";

	public static final String DESCRIPTION = "description";

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.taskDefinition = new TaskDefinition();
		this.taskDefinition.setDescription(DESCRIPTION);
		this.taskDefinition.setName(NAME);
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link woops2.model.task.TaskDefinition#hashCode()}.
	 */
	public void testHashCode() {
		assertNotNull(this.taskDefinition.hashCode());
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDefinition#addStep(woops2.model.task.Step)}.
	 */
	public void testAddStep() {
		Step step = new Step();
		step.setDescription(DESCRIPTION);
		step.setName(NAME);

		this.taskDefinition.addStep(step);

		assertNotNull(this.taskDefinition.getSteps());
		assertNotNull(step.getTaskDefinition());
	}
	
	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDefinition#addTaskDescriptor(woops2.model.task.TaskDescriptor)}.
	 */
	public void testAddTaskDescriptor() {
		TaskDescriptor taskDescriptor = new TaskDescriptor();
		taskDescriptor.setDescription(DESCRIPTION);
		taskDescriptor.setName(NAME);
		
		this.taskDefinition.addTaskDescriptor(taskDescriptor);

		assertNotNull(this.taskDefinition.getTaskDescriptors());
		assertNotNull(taskDescriptor.getTaskDefinition());
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDefinition#addToAllSteps(woops2.model.task.Step)}.
	 */
	public void testAddToAllSteps() {
		Step step1 = new Step();
		step1.setDescription(DESCRIPTION);
		step1.setName(NAME);

		Step step2 = new Step();
		step2.setDescription(DESCRIPTION);
		step2.setName(NAME);

		Set<Step> set = new HashSet<Step>();
		set.add(step1);
		set.add(step2);

		this.taskDefinition.addToAllSteps(set);

		assertFalse(this.taskDefinition.getSteps().isEmpty());
		assertEquals(2, this.taskDefinition.getSteps().size());
		// assertTrue(this.taskDefinition.getSteps().size() == 2);
	}
	
	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDefinition#addToAllTaskDesciptors(woops2.model.task.TaskDesciptor)}.
	 */
	public void testAddToAllTaskDesciptors() {
		TaskDescriptor td1 = new TaskDescriptor();
		td1.setDescription(DESCRIPTION);
		td1.setName(NAME);
		
		TaskDescriptor td2 = new TaskDescriptor();
		td2.setDescription(DESCRIPTION);
		td2.setName(NAME);
		
		Set<TaskDescriptor> set = new HashSet<TaskDescriptor>();
		set.add(td1);
		set.add(td2);

		this.taskDefinition.addToAllTaskDesciptors(set);

		assertFalse(this.taskDefinition.getTaskDescriptors().isEmpty());
		assertEquals(2, this.taskDefinition.getTaskDescriptors().size());
	}
}
