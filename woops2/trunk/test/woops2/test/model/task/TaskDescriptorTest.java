package woops2.test.model.task;

import junit.framework.TestCase;
import woops2.model.role.RoleDescriptor;
import woops2.model.task.TaskDefinition;
import woops2.model.task.TaskDescriptor;

/**
 * @author eperico
 * 
 * Unit test case of TaskDescriptor model class
 * 
 */
public class TaskDescriptorTest extends TestCase {

	private TaskDescriptor taskDescriptor;

	/**
	 * attributes from Element
	 */
	public static final String ID = "thisId";

	public static final String NAME = "thisTaskDescriptor";

	public static final String DESCRIPTION = "taskDescriptor description";

	/**
	 * attributes from BreakdownElement
	 */
	public static final String PREFIX = "prefix";

	public static final Boolean IS_PLANNED = true;

	public static final Boolean HAS_MULTIPLE_OCCURENCES = true;

	public static final Boolean IS_OPTIONAL = true;

	/**
	 * attributes from WorkBreakdownElement
	 */
	public static final Boolean IS_REPEATABLE = true;

	public static final Boolean IS_ON_GOING = true;

	public static final Boolean IS_EVEN_DRIVEN = true;

	protected void setUp() throws Exception {
		super.setUp();
		this.taskDescriptor = new TaskDescriptor();
		this.taskDescriptor.setDescription(DESCRIPTION);
		this.taskDescriptor.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES);
		this.taskDescriptor.setId(ID);
		this.taskDescriptor.setIsEvenDriven(IS_EVEN_DRIVEN);
		this.taskDescriptor.setIsOngoing(IS_ON_GOING);
		this.taskDescriptor.setIsOptional(IS_OPTIONAL);
		this.taskDescriptor.setIsPlanned(IS_PLANNED);
		this.taskDescriptor.setIsRepeatable(IS_REPEATABLE);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link woops2.model.task.TaskDescriptor#hashCode()}.
	 */
	public void testHashCode() {
		assertNotNull(this.taskDescriptor.hashCode());
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDescriptor#addToTaskDefinition(woops2.model.task.TaskDefinition)}.
	 */
	public void testAddToTaskDefinition() {
		TaskDefinition taskDefinition = new TaskDefinition();
		taskDefinition.setDescription(DESCRIPTION);
		taskDefinition.setName(NAME);

		this.taskDescriptor.addToTaskDefinition(taskDefinition);

		assertNotNull(this.taskDescriptor.getTaskDefinition());
		assertFalse(taskDefinition.getTaskDescriptors().isEmpty());
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDescriptor#removeFromTaskDefinition(woops2.model.task.TaskDefinition)}.
	 */
	public void testRemoveFromTaskDefinition() {
		TaskDefinition taskDefinition = new TaskDefinition();
		taskDefinition.setDescription(DESCRIPTION);
		taskDefinition.setName(NAME);

		this.taskDescriptor.addToTaskDefinition(taskDefinition);
		this.taskDescriptor.removeFromTaskDefinition(taskDefinition);

		assertNull(this.taskDescriptor.getTaskDefinition());
		assertTrue(taskDefinition.getTaskDescriptors().isEmpty());
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDescriptor#addToMainRole(woops2.model.task.RoleDescriptor)}.
	 */
	public void testAddToMainRole() {
		RoleDescriptor roleDescriptor = new RoleDescriptor();
		roleDescriptor.setName(NAME);
		roleDescriptor.setDescription(DESCRIPTION);
		roleDescriptor.setPrefix(PREFIX);
		roleDescriptor.setIsPlanned(IS_PLANNED);
		roleDescriptor.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES);
		roleDescriptor.setIsOptional(IS_OPTIONAL);

		this.taskDescriptor.addToMainRole(roleDescriptor);

		assertNotNull(this.taskDescriptor.getMainRole());
		// assertFalse(roleDescriptor.getAdditionalTasks().isEmpty());
		assertFalse(roleDescriptor.getPrimaryTasks().isEmpty());
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDescriptor#removeFromMainRole(woops2.model.task.RoleDescriptor)}.
	 */
	public void testRemoveFromMainRole() {
		RoleDescriptor roleDescriptor = new RoleDescriptor();
		roleDescriptor.setName(NAME);
		roleDescriptor.setDescription(DESCRIPTION);
		roleDescriptor.setPrefix(PREFIX);
		roleDescriptor.setIsPlanned(IS_PLANNED);
		roleDescriptor.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES);
		roleDescriptor.setIsOptional(IS_OPTIONAL);

		this.taskDescriptor.addToMainRole(roleDescriptor);
		this.taskDescriptor.removeFromMainRole(roleDescriptor);

		assertNull(this.taskDescriptor.getMainRole());
		assertTrue(roleDescriptor.getAdditionalTasks().isEmpty());
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDescriptor#addToRoleDescriptor(woops2.model.task.RoleDescriptor)}.
	 */
	public void testAddToRoleDescriptor() {
		RoleDescriptor roleDescriptor = new RoleDescriptor();
		roleDescriptor.setName(NAME);
		roleDescriptor.setDescription(DESCRIPTION);
		roleDescriptor.setPrefix(PREFIX);
		roleDescriptor.setIsPlanned(IS_PLANNED);
		roleDescriptor.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES);
		roleDescriptor.setIsOptional(IS_OPTIONAL);

		this.taskDescriptor.addToRoleDescriptor(roleDescriptor);

		assertNotNull(this.taskDescriptor.getAdditionalRoles());
		assertFalse(roleDescriptor.getAdditionalTasks().isEmpty());
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDescriptor#removeFromRoleDescriptor(woops2.model.task.RoleDescriptor)}.
	 */
	public void testRemoveFromRoleDescriptor() {
		RoleDescriptor roleDescriptor = new RoleDescriptor();
		roleDescriptor.setName(NAME);
		roleDescriptor.setDescription(DESCRIPTION);
		roleDescriptor.setPrefix(PREFIX);
		roleDescriptor.setIsPlanned(IS_PLANNED);
		roleDescriptor.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES);
		roleDescriptor.setIsOptional(IS_OPTIONAL);

		this.taskDescriptor.addToRoleDescriptor(roleDescriptor);
		this.taskDescriptor.removeFromRoleDescriptor(roleDescriptor);

		assertNull(this.taskDescriptor.getAdditionalRoles());
		assertTrue(roleDescriptor.getAdditionalTasks().isEmpty());
	}

	/**
	 * Test method for
	 * {@link woops2.model.task.TaskDescriptor#removeFromAllRoleDescriptor(woops2.model.task.RoleDescriptor)}.
	 */
	public void testRemoveFromRoleAllDescriptor() {
		RoleDescriptor rd1 = new RoleDescriptor();
		RoleDescriptor rd2 = new RoleDescriptor();

		rd1.setName(NAME);
		rd1.setDescription(DESCRIPTION);
		rd1.setPrefix(PREFIX);
		rd1.setIsPlanned(IS_PLANNED);
		rd1.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES);
		rd1.setIsOptional(IS_OPTIONAL);

		rd2.setName(NAME);
		rd2.setDescription(DESCRIPTION);
		rd2.setPrefix(PREFIX);
		rd2.setIsPlanned(IS_PLANNED);
		rd2.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES);
		rd2.setIsOptional(IS_OPTIONAL);

		this.taskDescriptor.addToRoleDescriptor(rd1);
		this.taskDescriptor.addToRoleDescriptor(rd2);
		this.taskDescriptor.removeFromAllRoleDescriptor();

		assertTrue(this.taskDescriptor.getAdditionalRoles().isEmpty());
		assertTrue(rd1.getSuperActivities().isEmpty());
		assertTrue(rd2.getSuperActivities().isEmpty());
	}

}
