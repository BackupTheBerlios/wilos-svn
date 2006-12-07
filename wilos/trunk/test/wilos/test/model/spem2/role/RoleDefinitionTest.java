
package wilos.test.model.spem2.role ;

import java.util.HashSet ;
import java.util.Set ;

import junit.framework.TestCase ;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;

public class RoleDefinitionTest extends TestCase {

	private RoleDefinition roleDefinition ;

	public static final String NAME = "name" ;

	public static final String NAME2 = "name1" ;

	public static final String DESCRIPTION = "roleDescriptor description" ;

	public static final String DESCRIPTION2 = "description" ;

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
		this.roleDefinition = new RoleDefinition() ;
		this.roleDefinition.setDescription(DESCRIPTION) ;
		this.roleDefinition.setName(NAME) ;
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
	 * Test method for {@link wilos.model.spem2.role.RoleDefinition#clone()}.
	 */
	public final void testClone() {
		// Rk: the setUp method is called here.

		try{
			assertEquals(this.roleDefinition.clone(), this.roleDefinition) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.model.spem2.role.RoleDefinition#hashCode()}.
	 */
	public void testHashCode() {

		RoleDefinition rd = new RoleDefinition() ;
		rd.setDescription(DESCRIPTION) ;
		rd.setName(NAME) ;

		assertNotNull(this.roleDefinition.hashCode()) ;
		assertNotNull(rd.hashCode()) ;
		assertEquals(this.roleDefinition.hashCode(), rd.hashCode()) ;
	}

	/**
	 * Test method for {@link wilos.model.spem2.role.RoleDefinition#equals(java.lang.Object)}.
	 */
	public void testEquals() {
		RoleDefinition tmp = null ;
		try{
			tmp = this.roleDefinition.clone() ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testEquals method") ;
		}
		assertTrue(this.roleDefinition.equals(tmp)) ;

		RoleDefinition role = new RoleDefinition() ;
		role.setName("name2") ;
		role.setDescription(DESCRIPTION) ;

		assertFalse(this.roleDefinition.equals(role)) ;
	}

	/**
	 * Test method for {@link wilos.model.spem2.role.RoleDefinition#addRoleDescriptor()}.
	 */
	public void testAddRoleDescriptor() {
		RoleDescriptor role = new RoleDescriptor() ;
		role.setName(NAME) ;
		role.setDescription(DESCRIPTION) ;

		this.roleDefinition.addRoleDescriptor(role) ;
		assertFalse(this.roleDefinition.getRoleDescriptors().isEmpty()) ;
		assertTrue(this.roleDefinition.getRoleDescriptors().size() == 1) ;
	}

	/**
	 * Test method for {@link wilos.model.spem2.role.RoleDefinition#RemoveRoleDescriptor()}.
	 */
	public void testRemoveRoleDescriptor() {
		RoleDescriptor role = new RoleDescriptor() ;
		role.setName(NAME) ;
		role.setDescription(DESCRIPTION) ;

		this.roleDefinition.addRoleDescriptor(role) ;
		assertFalse(this.roleDefinition.getRoleDescriptors().isEmpty()) ;
		
		this.roleDefinition.removeRoleDescriptor(role) ;
		assertTrue(this.roleDefinition.getRoleDescriptors().isEmpty()) ;
	}

	/**
	 * Test method for {@link wilos.model.spem2.role.RoleDefinition#RemoveAllRoleDescriptor()}.
	 * 
	 */
	public void testRemoveAllRoleDescriptor() {
		RoleDescriptor role = new RoleDescriptor() ;
		role.setName(NAME) ;
		role.setDescription(DESCRIPTION) ;

		RoleDescriptor tmp = new RoleDescriptor() ;
		tmp.setName(NAME2) ;
		tmp.setDescription(DESCRIPTION) ;

		Set<RoleDescriptor> set = new HashSet<RoleDescriptor>() ;
		set.add(role) ;
		set.add(tmp) ;

		this.roleDefinition.addAllRoleDescriptors(set) ;
		assertNotNull(role.getRoleDefinition()) ;
		assertNotNull(tmp.getRoleDefinition()) ;
		assertTrue(this.roleDefinition.getRoleDescriptors().size() == set.size()) ;
		
		this.roleDefinition.removeAllRoleDescriptors() ;
		assertNull(role.getRoleDefinition()) ;
		assertNull(tmp.getRoleDefinition()) ;
		assertTrue(this.roleDefinition.getRoleDescriptors().isEmpty()) ;
	}

	/**
	 * Test method for
	 * {@link wilos.model.spem2.role.RoleDefinitionTest#addToAllRoleDescriptors(java.util.Set)}.
	 */
	public void testAddToAllRoleDescriptors() {
		RoleDescriptor role = new RoleDescriptor() ;
		role.setName(NAME2) ;
		role.setDescription(DESCRIPTION2) ;

		RoleDescriptor tmp = new RoleDescriptor() ;
		tmp.setName(NAME) ;
		tmp.setDescription(DESCRIPTION) ;

		Set<RoleDescriptor> set = new HashSet<RoleDescriptor>() ;
		set.add(role) ;
		set.add(tmp) ;

		this.roleDefinition.addAllRoleDescriptors(set) ;

		assertFalse(this.roleDefinition.getRoleDescriptors().isEmpty()) ;
		assertTrue(this.roleDefinition.getRoleDescriptors().size() == 2) ;
		assertNotNull(role.getRoleDefinition()) ;
		assertNotNull(tmp.getRoleDefinition()) ;
	}
}
