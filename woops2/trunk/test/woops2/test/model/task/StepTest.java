package woops2.test.model.task;

import junit.framework.TestCase;
import woops2.model.task.Step;
import woops2.model.task.TaskDefinition;

/**
 * @author Sebastien
 * 
 * This class represents the class test of the Step class.
 * 
 */
public class StepTest extends TestCase {

	private Step step;

	public static final String NAME = "name";

	public static final String DESCRIPTION = "description";

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.step = new Step();
		this.step.setDescription(DESCRIPTION);
		this.step.setName(NAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link woops2.model.task.Step#hashCode()}.
	 */
	public void testHashCode() {
		assertNotNull(this.step.hashCode());
	}

	/**
	 * Test method for {@link woops2.model.task.Step#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.Step#addToTaskDefinition(woops2.model.task.TaskDefinition)}.
	 */
	public void testAddToTaskDefinition() {
		TaskDefinition taskDefinition = new TaskDefinition();
		taskDefinition.setDescription(DESCRIPTION);
		taskDefinition.setName(NAME);

		this.step.addToTaskDefinition(taskDefinition);

		assertNotNull(this.step.getTaskDefinition());
		assertFalse(taskDefinition.getSteps().isEmpty());
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.Step#removeFromTaskDefinition(woops2.model.task.TaskDefinition)}.
	 */
	public void testRemoveFromTaskDefinition() {
		TaskDefinition taskDefinition = new TaskDefinition();
		taskDefinition.setDescription(DESCRIPTION);
		taskDefinition.setName(NAME);

		this.step.addToTaskDefinition(taskDefinition);
		this.step.removeFromTaskDefinition(taskDefinition);

		assertNull(this.step.getTaskDefinition());
		assertTrue(taskDefinition.getSteps().isEmpty());
	}

}
