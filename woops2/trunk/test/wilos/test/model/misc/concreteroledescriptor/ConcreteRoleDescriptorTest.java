package wilos.test.model.misc.concreteroledescriptor;


import junit.framework.TestCase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcreteRoleDescriptorTest extends TestCase {

	private ConcreteRoleDescriptor concreteRoleDescriptor ;

	/*
	public ConcreteRoleDescriptorTest(){
		
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor() ;
		this.concreteRoleDescriptor.setConcreteName("Concrete Name");
		this.concreteRoleDescriptor.setId("465");
		this.concreteRoleDescriptor.setProjectId("8979");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}
	
	public void testAddRoleDescriptor() {
		// Rk: the setUp method is called here.
		
		RoleDescriptor roleDescriptor = new RoleDescriptor();
		this.concreteRoleDescriptor.addRoleDescriptor(roleDescriptor);
		assertNotNull(this.concreteRoleDescriptor.getRoleDescriptor());
		assertEquals(this.concreteRoleDescriptor.getRoleDescriptor(), roleDescriptor) ;
		
		// Rk: the tearDown method is called here.
	}

	public void testRemoveRoleDescriptor() {
		// Rk: the setUp method is called here.
		
		RoleDescriptor roleDescriptor = new RoleDescriptor();
		
		this.concreteRoleDescriptor.addRoleDescriptor(roleDescriptor);
		assertNotNull(this.concreteRoleDescriptor.getRoleDescriptor());
		assertEquals(this.concreteRoleDescriptor.getRoleDescriptor(), roleDescriptor);
		
		this.concreteRoleDescriptor.removeRoleDescriptor(roleDescriptor);
		assertNull(this.concreteRoleDescriptor.getRoleDescriptor());
		assertTrue(roleDescriptor.getConcreteRoleDescriptors().size() == 0);
		
		this.concreteRoleDescriptor.removeRoleDescriptor(roleDescriptor) ;
		assertNull(this.concreteRoleDescriptor.getRoleDescriptor());

		// Rk: the tearDown method is called here.
	}

}
