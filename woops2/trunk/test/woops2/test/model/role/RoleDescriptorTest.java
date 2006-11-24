
package woops2.test.model.role ;

import java.util.HashSet ;
import java.util.Set ;

import junit.framework.TestCase ;
import woops2.model.role.RoleDefinition ;
import woops2.model.role.RoleDescriptor ;
import woops2.model.task.TaskDescriptor ;

public class RoleDescriptorTest extends TestCase {

	private RoleDescriptor roleDescriptor = null ;

	public static final String NAME = "thisRoleDescriptor" ;

	public static final String DESCRIPTION = "roleDescriptor description" ;

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	public static final Boolean HAS_MULTIPLE_OCCURENCES = true ;

	public static final Boolean IS_PLANNED = true ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.roleDescriptor = new RoleDescriptor() ;
		this.roleDescriptor.setDescription(DESCRIPTION) ;
		this.roleDescriptor.setName(NAME) ;
		this.roleDescriptor.setPrefix(PREFIX) ;
		this.roleDescriptor.setIsOptional(IS_OPTIONAL) ;
		this.roleDescriptor.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES) ;
		this.roleDescriptor.setIsPlanned(IS_PLANNED) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor #hashCode()}.
	 */
	public void testHashCode() {

		RoleDescriptor rd = new RoleDescriptor() ;
		rd.setDescription(DESCRIPTION) ;
		rd.setName(NAME) ;
		rd.setPrefix(PREFIX) ;
		rd.setIsOptional(IS_OPTIONAL) ;
		rd.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES) ;
		rd.setIsPlanned(IS_PLANNED) ;

		assertNotNull(this.roleDescriptor.hashCode()) ;
		assertNotNull(rd.hashCode()) ;
		assertEquals(this.roleDescriptor.hashCode(), rd.hashCode()) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#clone()}.
	 */
	public final void testClone() {
		// Rk: the setUp method is called here.

		try{
			assertEquals(this.roleDescriptor.clone(), this.roleDescriptor) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDefinition#equals(java.lang.Object)}.
	 */
	public void testEquals() {
		RoleDescriptor role = new RoleDescriptor() ;
		role.setDescription(DESCRIPTION) ;
		role.setName(NAME) ;
		role.setPrefix(PREFIX) ;
		role.setIsOptional(IS_OPTIONAL) ;
		role.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES) ;
		role.setIsPlanned(IS_PLANNED) ;

		assertTrue(this.roleDescriptor.equals(role)) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#AddRoleDefinition()}.
	 * 
	 */
	public void testAddRoleDefinition() {
		RoleDefinition role = new RoleDefinition() ;
		role.setName(NAME) ;
		role.setDescription(DESCRIPTION) ;

		this.roleDescriptor.addRoleDefinition(role) ;
		assertNotNull(this.roleDescriptor.getRoleDefinition()) ;
		assertNotNull(role.getRoleDescriptors()) ;
		assertTrue(role.getRoleDescriptors().size() == 1) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveRoleDefinition()}.
	 * 
	 */
	public void testRemoveFromRoleDefinition() {
		RoleDefinition role = new RoleDefinition() ;
		role.setName(NAME) ;
		role.setDescription(DESCRIPTION) ;

		this.roleDescriptor.addRoleDefinition(role) ;
		assertNotNull(this.roleDescriptor.getRoleDefinition()) ;
		assertTrue(role.getRoleDescriptors().size() == 1) ;

		this.roleDescriptor.removeRoleDefinition(role) ;
		assertNull(this.roleDescriptor.getRoleDefinition()) ;
		assertTrue(role.getRoleDescriptors().size() == 0) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#AddTaskDescriptor()}.
	 * 
	 */
	public void testAddPrimaryTask() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME) ;
		task.setDescription(DESCRIPTION) ;

		this.roleDescriptor.addPrimaryTask(task);
		assertFalse(this.roleDescriptor.getPrimaryTasks().isEmpty()) ;
		assertTrue(this.roleDescriptor.getPrimaryTasks().size() == 1) ;
		assertNotNull(task.getMainRole()) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveTaskDescriptor()}.
	 * 
	 */
	public void testRemoveTaskDescriptor() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME) ;
		task.setDescription(DESCRIPTION) ;

		this.roleDescriptor.addPrimaryTask(task) ;
		assertFalse(this.roleDescriptor.getPrimaryTasks().isEmpty()) ;
		assertNotNull(task.getMainRole()) ;

		this.roleDescriptor.removePrimaryTask(task) ;
		assertTrue(this.roleDescriptor.getPrimaryTasks().isEmpty()) ;
		assertNull(task.getMainRole()) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveAllPrimaryTasks()}.
	 * 
	 */
	public void testRemoveAllPrimaryTasks() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME) ;
		task.setDescription(DESCRIPTION) ;

		TaskDescriptor tmp = new TaskDescriptor() ;
		tmp.setName("nom") ;
		tmp.setDescription(DESCRIPTION) ;

		this.roleDescriptor.addPrimaryTask(task) ;
		this.roleDescriptor.addPrimaryTask(tmp) ;
		assertTrue(this.roleDescriptor.getPrimaryTasks().size() == 2) ;
		assertNotNull(task.getMainRole()) ;
		assertNotNull(tmp.getMainRole()) ;

		this.roleDescriptor.removeAllPrimaryTasks() ;
		assertTrue(this.roleDescriptor.getPrimaryTasks().isEmpty()) ;
		assertNull(task.getMainRole()) ;
		assertNull(tmp.getMainRole()) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#AddAllTaskDescriptors()}.
	 * 
	 */
	public void testAddAllTaskDescriptors() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME) ;
		task.setDescription(DESCRIPTION) ;

		TaskDescriptor tmp = new TaskDescriptor() ;
		tmp.setName("nom") ;
		tmp.setDescription(DESCRIPTION) ;

		Set<TaskDescriptor> set = new HashSet<TaskDescriptor>() ;
		set.add(task) ;
		set.add(tmp) ;

		this.roleDescriptor.addAllPrimaryTasks(set) ;

		assertFalse(this.roleDescriptor.getPrimaryTasks().isEmpty()) ;
		assertTrue(this.roleDescriptor.getPrimaryTasks().size() == 2) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#AddTaskDescriptor()}.
	 * 
	 */
	public void testAddToTaskDescriptor() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME) ;
		task.setDescription(DESCRIPTION) ;

		this.roleDescriptor.addAdditionalTask(task) ;

		assertFalse(this.roleDescriptor.getAdditionalTasks().isEmpty()) ;
		assertFalse(task.getAdditionalRoles().isEmpty()) ;
		assertTrue(this.roleDescriptor.getAdditionalTasks().size() == 1) ;
		assertTrue(task.getAdditionalRoles().size() == 1) ;
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveTaskDescriptor()}.
	 * 
	 */
	public void testRemoveFromTaskDescriptor() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME) ;
		task.setDescription(DESCRIPTION) ;

		this.roleDescriptor.addAdditionalTask(task) ;
		assertFalse(this.roleDescriptor.getAdditionalTasks().isEmpty()) ;
		assertFalse(task.getAdditionalRoles().isEmpty());
		
		this.roleDescriptor.removeAdditionalTask(task) ;
		assertTrue(this.roleDescriptor.getAdditionalTasks().isEmpty()) ;
		assertTrue(task.getAdditionalRoles().isEmpty());
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveAllAdditionalTasks()}.
	 * 
	 */
	public void testRemoveAllAdditionalTasks() {

		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME) ;
		task.setDescription(DESCRIPTION) ;

		TaskDescriptor tmp = new TaskDescriptor() ;
		tmp.setName("autreNom") ;
		tmp.setDescription(DESCRIPTION) ;

		Set<TaskDescriptor> set = new HashSet<TaskDescriptor>() ;
		set.add(task) ;
		set.add(tmp) ;

		this.roleDescriptor.addAllPrimaryTasks(set) ;
		assertTrue(this.roleDescriptor.getAdditionalTasks().size() == 2) ;
		assertTrue(task.getAdditionalRoles().size() == 1);
		assertTrue(tmp.getAdditionalRoles().size() == 1);
		
		this.roleDescriptor.removeAllAdditionalTasks() ;
		assertTrue(this.roleDescriptor.getAdditionalTasks().isEmpty()) ;
		assertTrue(task.getAdditionalRoles().isEmpty());
		assertTrue(tmp.getAdditionalRoles().isEmpty());
	}

	/**
	 * Test method for {@link woops2.model.role.RoleDescriptor#RemoveFromAllTaskDescriptor()}.
	 * 
	 */
	public void testRemoveFromAllTaskDescriptor() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME) ;
		task.setDescription(DESCRIPTION) ;

		TaskDescriptor tmp = new TaskDescriptor() ;
		tmp.setName("nom") ;
		tmp.setDescription(DESCRIPTION) ;

		Set<TaskDescriptor> set = new HashSet<TaskDescriptor>() ;
		set.add(task) ;
		set.add(tmp) ;

		this.roleDescriptor.addAllPrimaryTasks(set) ;
		assertTrue(this.roleDescriptor.getPrimaryTasks().size() == 2) ;
		assertTrue(task.getAdditionalRoles().size() == 1);
		assertTrue(tmp.getAdditionalRoles().size() == 1);
		
		this.roleDescriptor.removeAllAdditionalTasks() ;
		assertTrue(this.roleDescriptor.getPrimaryTasks().isEmpty()) ;
		assertTrue(task.getAdditionalRoles().isEmpty());
		assertTrue(tmp.getAdditionalRoles().isEmpty());
	}

}
