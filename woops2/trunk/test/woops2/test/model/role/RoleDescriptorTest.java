package woops2.test.model.role;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import woops2.model.role.RoleDefinition;
import woops2.model.role.RoleDescriptor;
import woops2.model.task.TaskDescriptor;

public class RoleDescriptorTest extends TestCase {

	private RoleDescriptor roleDescriptor = null;

	public static final String NAME = "thisRoleDescriptor";

	public static final String DESCRIPTION = "roleDescriptor description";

	public static final String PREFIX = "prefix";

	public static final Boolean IS_OPTIONAL = true;

	public static final Boolean HAS_MULTIPLE_OCCURENCES = true;

	public static final Boolean IS_PLANNED = true;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.roleDescriptor = new RoleDescriptor() ;
		this.roleDescriptor.setDescription(DESCRIPTION);
		this.roleDescriptor.setName(NAME) ;
		this.roleDescriptor.setPrefix(PREFIX);
		this.roleDescriptor.setIsOptional(IS_OPTIONAL);
		this.roleDescriptor.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES);
		this.roleDescriptor.setIsPlanned(IS_PLANNED);
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}
	
	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor
	 * #hashCode()}.
	 */
	public void testHashCode() {
		assertNotNull(this.roleDescriptor.hashCode());
	}
	
	
	/**
	 * Test method for
	 * {@link woops2.model.role.RoleDefinition#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		RoleDescriptor role = new RoleDescriptor();
		role.setDescription(DESCRIPTION);
		role.setName(NAME);

		assertTrue(this.roleDescriptor.equals(role));
	}
	
	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#AddToRoleDefinition()}.
	 *
	 */
	public void testAddToRoleDefinition() {
		RoleDefinition role = new RoleDefinition();
		role.setName(NAME);
		role.setDescription(DESCRIPTION);
		
		this.roleDescriptor.addToRoleDefinition(role) ;
		assertNotNull(this.roleDescriptor.getRoleDefinition());
		assertNotNull(role.getRoleDescriptors());
	}
	
	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveFromRoleDefinition()}.
	 *
	 */
	public void testRemoveFromRoleDefinition() {
		RoleDefinition role = new RoleDefinition();
		role.setName(NAME);
		role.setDescription(DESCRIPTION);
		
		this.roleDescriptor.addToRoleDefinition(role) ;
		this.roleDescriptor.removeFromRoleDefinition(role);
		assertNull(this.roleDescriptor.getRoleDefinition());
		
	}
	
	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#AddTaskDescriptor()}.
	 *
	 */
	public void testAddTaskDescriptor() {
		TaskDescriptor task = new TaskDescriptor();
		task.setName(NAME);
		task.setDescription(DESCRIPTION);
		
		this.roleDescriptor.addTaskDescriptor(task) ;
		assertFalse(this.roleDescriptor.getPrimaryTasks().isEmpty());
		assertNull(task.getMainRole());
	}
	
	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveTaskDescriptor()}.
	 *
	 */
	public void testRemoveTaskDescriptor() {
		TaskDescriptor task = new TaskDescriptor();
		task.setName(NAME);
		task.setDescription(DESCRIPTION);
		
		this.roleDescriptor.addTaskDescriptor(task) ;
		this.roleDescriptor.removeTaskDescriptor(task);
		
		assertTrue(this.roleDescriptor.getPrimaryTasks().isEmpty());
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveAllPrimaryTasks()}.
	 *
	 */
	public void testRemoveAllPrimaryTasks() {
		TaskDescriptor task = new TaskDescriptor();
		task.setName(NAME);
		task.setDescription(DESCRIPTION);
		
		TaskDescriptor tmp = new TaskDescriptor();
		tmp.setName(NAME);
		tmp.setDescription(DESCRIPTION);
		
		this.roleDescriptor.addTaskDescriptor(task) ;
		this.roleDescriptor.addTaskDescriptor(tmp) ;
		this.roleDescriptor.removeAllPrimaryTasks();
		
		assertTrue(this.roleDescriptor.getPrimaryTasks().isEmpty());
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#AddToAllTaskDescriptors()}.
	 *
	 */
	public void testAddToAllTaskDescriptors() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME);
		task.setDescription(DESCRIPTION);
		

		TaskDescriptor tmp = new TaskDescriptor() ;
		tmp.setName(NAME);
		tmp.setDescription(DESCRIPTION);

		Set<TaskDescriptor> set = new HashSet<TaskDescriptor>() ;
		set.add(task) ;
		set.add(tmp) ;

		this.roleDescriptor.addToAllTaskDescriptors(set) ;

		assertFalse(this.roleDescriptor.getPrimaryTasks().isEmpty()) ;
		assertTrue(this.roleDescriptor.getPrimaryTasks().size() == 2);
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#AddToTaskDescriptor()}.
	 *
	 */
	public void testAddToTaskDescriptor() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME);
		task.setDescription(DESCRIPTION);
		
		this.roleDescriptor.addToTaskDescriptor(task);
		
		assertFalse(this.roleDescriptor.getAdditionalTasks().isEmpty()) ;
		assertFalse(task.getAdditionalRoles().isEmpty()) ;
		assertTrue(this.roleDescriptor.getAdditionalTasks().size() == 1) ;
		assertTrue(task.getAdditionalRoles().size() == 1) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveFromTaskDescriptor()}.
	 *
	 */
	public void testRemoveFromTaskDescriptor() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME);
		task.setDescription(DESCRIPTION);
		
		this.roleDescriptor.addToTaskDescriptor(task);
		this.roleDescriptor.removeFromTaskDescriptor(task);
		
		assertTrue(this.roleDescriptor.getAdditionalTasks().isEmpty()) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveAllAdditionalTasks()}.
	 *
	 */
	public void testRemoveAllAdditionalTasks() {
		
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME);
		task.setDescription(DESCRIPTION);
		

		TaskDescriptor tmp = new TaskDescriptor() ;
		tmp.setName(NAME);
		tmp.setDescription(DESCRIPTION);
		
		this.roleDescriptor.addToTaskDescriptor(task);
		this.roleDescriptor.addToTaskDescriptor(tmp);
		this.roleDescriptor.removeAllAdditionalTasks();
		
		assertTrue(this.roleDescriptor.getAdditionalTasks().isEmpty()) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveFromAllTaskDescriptor()}.
	 *
	 */
	public void testRemoveFromAllTaskDescriptor() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME);
		task.setDescription(DESCRIPTION);
		

		TaskDescriptor tmp = new TaskDescriptor() ;
		tmp.setName(NAME);
		tmp.setDescription(DESCRIPTION);
		
		this.roleDescriptor.addToTaskDescriptor(task);
		this.roleDescriptor.addToTaskDescriptor(tmp);
		this.roleDescriptor.removeFromAllTaskDescriptor();
		
		assertTrue(this.roleDescriptor.getAdditionalTasks().isEmpty()) ;
	}
	
}
