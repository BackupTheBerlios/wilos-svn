
package wilos.test.model.spem2.role ;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.model.misc.wilosuser.Participant;

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
	 * Test method for {@link wilos.model.role.RoleDescriptor #hashCode()}.
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
	 * Test method for {@link wilos.model.role.RoleDescriptor#clone()}.
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
	 * Test method for {@link wilos.model.role.RoleDefinition#equals(java.lang.Object)}.
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
	 * Test method for {@link wilos.model.role.RoleDescriptor#AddRoleDefinition()}.
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
	 * Test method for {@link wilos.model.role.RoleDescriptor#RemoveRoleDefinition()}.
	 * 
	 */
	public void testRemoveRoleDefinition() {
		RoleDefinition role = new RoleDefinition() ;
		role.setName(NAME) ;
		role.setDescription(DESCRIPTION) ;

		this.roleDescriptor.addRoleDefinition(role) ;
		assertNotNull(this.roleDescriptor.getRoleDefinition()) ;
		assertTrue(role.getRoleDescriptors().size() == 1) ;

		this.roleDescriptor.removeRoleDefinition(role) ;
		assertNull(this.roleDescriptor.getRoleDefinition()) ;
		assertTrue(role.getRoleDescriptors().isEmpty()) ;
	}

	/**
	 * Test method for {@link wilos.model.role.RoleDescriptor#addPrimaryTask()}.
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
	 * Test method for {@link wilos.model.role.RoleDescriptor#RemovePrimaryTask()}.
	 * 
	 */
	public void testRemovePrimaryTask() {
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
	 * Test method for {@link wilos.model.role.RoleDescriptor#RemoveAllPrimaryTasks()}.
	 * 
	 */
	public void testRemoveAllPrimaryTasks() {
		TaskDescriptor task = new TaskDescriptor() ;
		task.setName(NAME) ;
		task.setDescription(DESCRIPTION) ;

		TaskDescriptor tmp = new TaskDescriptor() ;
		tmp.setName("nom") ;
		tmp.setDescription(DESCRIPTION) ;
		
		Set<TaskDescriptor> set = new HashSet<TaskDescriptor>() ;
		set.add(task) ;
		set.add(tmp) ;

		this.roleDescriptor.addAllPrimaryTasks(set);
		assertTrue(this.roleDescriptor.getPrimaryTasks().size() == 2) ;
		assertNotNull(task.getMainRole()) ;
		assertNotNull(tmp.getMainRole()) ;

		this.roleDescriptor.removeAllPrimaryTasks() ;
		assertTrue(this.roleDescriptor.getPrimaryTasks().isEmpty()) ;
		assertNull(task.getMainRole()) ;
		assertNull(tmp.getMainRole()) ;
	}

	/**
	 * Test method for {@link wilos.model.role.RoleDescriptor#AddAllPrimaryTasks()}.
	 * 
	 */
	public void testAddAllPrimaryTasks() {
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
	 * Test method for {@link wilos.model.role.RoleDescriptor#addAdditionalTask()}.
	 * 
	 */
	public void testAddAdditionnalTask() {
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
	 * Test method for {@link wilos.model.role.RoleDescriptor#removeAdditionalTask()}.
	 * 
	 */
	public void testRemoveAdditionalTask() {
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
	 * Test method for {@link wilos.model.role.RoleDescriptor#RemoveAllAdditionalTasks()}.
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

		this.roleDescriptor.addAllAdditionalTasks(set) ;
		assertTrue(this.roleDescriptor.getAdditionalTasks().size() == 2) ;
		assertTrue(task.getAdditionalRoles().size() == 1);
		assertTrue(tmp.getAdditionalRoles().size() == 1);
		
		this.roleDescriptor.removeAllAdditionalTasks() ;
		assertTrue(this.roleDescriptor.getAdditionalTasks().isEmpty()) ;
		assertTrue(task.getAdditionalRoles().isEmpty());
		assertTrue(tmp.getAdditionalRoles().isEmpty());
	}
	
	/**
	 * Test method for {@link wilos.model.role.RoleDescriptor#addParticipant()}.
	 * 
	 */
	public void testAddParticipant() {
		Participant participant = new Participant() ;
		
		this.roleDescriptor.addParticipant(participant) ;

		assertFalse(this.roleDescriptor.getParticipants().isEmpty()) ;
		//TODO assertFalse(participant.getRolesListForAProject().isEmpty()) ;
		assertTrue(this.roleDescriptor.getParticipants().size() == 1) ;
		//TODO assertTrue(participant.getRolesListForAProject().size() == 1) ;
	}
	
	/**
	 * Test method for {@link wilos.model.role.RoleDescriptor#AddAllParticipants()}.
	 * 
	 */
	public void testAddAllParticipants() {
		Participant task = new Participant() ;
		task.setName(NAME) ;
		
		Participant tmp = new Participant() ;
		
		Set<Participant> set = new HashSet<Participant>() ;
		set.add(task) ;
		set.add(tmp) ;

		this.roleDescriptor.addAllParticipants(set) ;

		assertFalse(this.roleDescriptor.getParticipants().isEmpty()) ;
		assertTrue(this.roleDescriptor.getParticipants().size() == 2) ;
	}
	
	/**
	 * Test method for {@link wilos.model.role.RoleDescriptor#removeParticipant()}.
	 * 
	 */
	public void testRemoveParticipant() {
		Participant task = new Participant() ;
		task.setName(NAME) ;
		
		this.roleDescriptor.addParticipant(task) ;
		assertFalse(this.roleDescriptor.getParticipants().isEmpty()) ;
		//TODO assertFalse(task.getRolesListForAProject().isEmpty());
		
		this.roleDescriptor.removeParticipant(task) ;
		assertTrue(this.roleDescriptor.getParticipants().isEmpty()) ;
		//TODO assertTrue(task.getRolesListForAProject().isEmpty());
	}
	
	/**
	 * Test method for {@link wilos.model.role.RoleDescriptor#RemoveAllParticipants()}.
	 * 
	 */
	public void testRemoveAllParticipants() {

		Participant task = new Participant() ;
		task.setName(NAME) ;
		
		Participant tmp = new Participant() ;
		tmp.setName("autreNom") ;
		
		Set<Participant> set = new HashSet<Participant>() ;
		set.add(task) ;
		set.add(tmp) ;

		this.roleDescriptor.addAllParticipants(set) ;
		assertTrue(this.roleDescriptor.getParticipants().size() == 2) ;
		//TODO assertTrue(task.getRolesListForAProject().size() == 1);
		//TODO assertTrue(tmp.getRolesListForAProject().size() == 1);
		
		this.roleDescriptor.removeAllParticipants() ;
		assertTrue(this.roleDescriptor.getParticipants().isEmpty()) ;
		//TODO assertTrue(task.getRolesListForAProject().isEmpty());
		//TODO assertTrue(tmp.getRolesListForAProject().isEmpty());
	}
}
