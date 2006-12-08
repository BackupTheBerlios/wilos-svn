package wilos.test.model.misc.wilosuser;

import java.util.HashSet;
import java.util.Set;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.misc.wilosuser.Participant;
import junit.framework.TestCase;

/**
 * @author Martial
 *
 * This class represents  the class test of the Participant class.
 *
 */
public class ParticipantTest extends TestCase {

		private Participant p;
		
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		p=new Participant();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link woops2.model.wilosuser.Participant#getRolesListForAProject()}.
	 */
	public void testGetRolesListForAProject() {
		Set<RoleDescriptor> roles= new HashSet<RoleDescriptor>();
		roles.add(new RoleDescriptor());
		p.setRolesListForAProject(roles);
		assertTrue(p.getRolesListForAProject().equals(roles));

	}

	/**
	 * Test method for {@link woops2.model.wilosuser.Participant#setRolesListForAProject(java.util.Set)}.
	 */
	public void testSetRolesListForAProject() {
		Set<RoleDescriptor> roles= new HashSet<RoleDescriptor>();
		roles.add(new RoleDescriptor());
		p.setRolesListForAProject(roles);
		assertTrue(p.getRolesListForAProject().equals(roles));

	}

	/**
	 * Test method for {@link woops2.model.wilosuser.Participant#addToRoleDescriptor(woops2.model.role.RoleDescriptor)}.
	 */
	public void testAddToRoleDescriptor() {
		RoleDescriptor r =new RoleDescriptor();
		p.addToRoleDescriptor(r);
		assertTrue(p.getRolesListForAProject().contains(r));
	}

	/**
	 * Test method for {@link woops2.model.wilosuser.Participant#removeFromRoleDescriptor(woops2.model.role.RoleDescriptor)}.
	 */
	public void testRemoveFromRoleDescriptor() {
		RoleDescriptor r =new RoleDescriptor();
		RoleDescriptor r1 =new RoleDescriptor();
		p.addToRoleDescriptor(r);
		p.addToRoleDescriptor(r1);
		p.removeFromRoleDescriptor(r1);
		assertFalse(p.getRolesListForAProject().contains(r1));
	}

	/**
	 * Test method for {@link woops2.model.wilosuser.Participant#removeAllRoleDescriptors()}.
	 */
	public void testRemoveAllRoleDescriptors() {
		RoleDescriptor r =new RoleDescriptor();
		RoleDescriptor r1 =new RoleDescriptor();
		p.addToRoleDescriptor(r);
		p.addToRoleDescriptor(r1);
		p.removeAllRoleDescriptors();
		assertTrue(p.getRolesListForAProject().isEmpty());
	}

	/**
	 * Test method for {@link woops2.model.wilosuser.Participant#removeFromAllRoleDescriptor()}.
	 */
	public void testRemoveFromAllRoleDescriptor() {
		RoleDescriptor r =new RoleDescriptor();
		RoleDescriptor r1 =new RoleDescriptor();
		p.addToRoleDescriptor(r);
		p.addToRoleDescriptor(r1);
		p.removeAllRoleDescriptors();
		assertTrue(p.getRolesListForAProject().isEmpty());
	}

}
