package woops2.test.model.role;

import java.util.HashSet;
import java.util.Set;

import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.role.RoleDefinition;
import woops2.model.role.RoleDescriptor;
import woops2.model.task.Step;
import junit.framework.TestCase;

public class RoleDefinitionTest extends TestCase{

	private RoleDefinition roleDefinition;
	
	public static final String NAME = "name" ;

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
		this.roleDefinition = new RoleDefinition() ;
		this.roleDefinition.setDescription(DESCRIPTION);
		this.roleDefinition.setName(NAME) ;
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}
	
	/**
	 * Test method for {@link woops2.model.role.RoleDefinition#hashCode()}.
	 */
	public void testHashCode() {
		assertNotNull(this.roleDefinition.hashCode());
	}
	
	/**
	 * Test method for {@link woops2.model.role.RoleDefinition#addRoleDescriptor()}.
	 */
	public void testAddRoleDescriptor() {
		RoleDescriptor role = new RoleDescriptor() ;
		role.setName(NAME);
		role.setDescription(DESCRIPTION);
		
		this.roleDefinition.addRoleDescriptor(role) ;
		assertFalse(this.roleDefinition.getRoleDescriptors().isEmpty()) ;
		assertTrue(this.roleDefinition.getRoleDescriptors().size() == 1) ;
		
	}
	
	/**
	 * Test method for {@link woops2.model.role.RoleDefinition#RemoveRoleDescriptor()}.
	 */
	public void testRemoveRoleDescriptor() {
		RoleDescriptor role = new RoleDescriptor() ;
		role.setName(NAME);
		role.setDescription(DESCRIPTION);
		
		this.roleDefinition.addRoleDescriptor(role) ;
		this.roleDefinition.removeRoleDescriptor(role);
		
		assertTrue(this.roleDefinition.getRoleDescriptors().isEmpty()) ;
		

	}
	/**
	 * Test method for {@link woops2.model.role.RoleDefinition#RemoveAllRoleDescriptor()}.
	 *
	 */
	public void testRemoveAllRoleDescriptor() {
		RoleDescriptor role = new RoleDescriptor() ;
		role.setName(NAME);
		role.setDescription(DESCRIPTION);
		
		RoleDescriptor tmp = new RoleDescriptor() ;
		tmp.setName(NAME);
		tmp.setDescription(DESCRIPTION);
		
		this.roleDefinition.addRoleDescriptor(role) ;
		this.roleDefinition.addRoleDescriptor(tmp) ;
		this.roleDefinition.removeAllRoleDescriptor();

		assertTrue(this.roleDefinition.getRoleDescriptors().isEmpty()) ;
	}
	
	/**
	 * Test method for {@link woops2.model.role.RoleDefinitionTest#addToAllRoleDescriptors(java.util.Set)}.
	 */
	public void testAddToAllRoleDescriptors() {
		RoleDescriptor role = new RoleDescriptor() ;
		role.setName(NAME);
		role.setDescription(DESCRIPTION);
		

		RoleDescriptor tmp = new RoleDescriptor() ;
		tmp.setName(NAME);
		tmp.setDescription(DESCRIPTION);

		Set<RoleDescriptor> set = new HashSet<RoleDescriptor>() ;
		set.add(role) ;
		set.add(tmp) ;

		this.roleDefinition.addToAllRoleDescriptors(set) ;

		assertFalse(this.roleDefinition.getRoleDescriptors().isEmpty()) ;
		assertTrue(this.roleDefinition.getRoleDescriptors().size() == 2);
	}
}
