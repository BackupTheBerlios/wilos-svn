package wilos.test.model.misc.concreteroledescriptor;


import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
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
	
	public void testAddParticipant() {
		// Rk: the setUp method is called here.
		
		Participant participant = new Participant();
		this.concreteRoleDescriptor.addParticipant(participant);
		assertNotNull(this.concreteRoleDescriptor.getParticipant());
		assertEquals(this.concreteRoleDescriptor.getParticipant(), participant) ;
		
		// Rk: the tearDown method is called here.
	}
	
	public void testRemoveParticipant() {
		// Rk: the setUp method is called here.
		
		Participant participant = new Participant();
		
		this.concreteRoleDescriptor.addParticipant(participant);
		assertNotNull(this.concreteRoleDescriptor.getParticipant());
		assertEquals(this.concreteRoleDescriptor.getParticipant(), participant);
		
		this.concreteRoleDescriptor.removeParticipant(participant);
		assertNull(this.concreteRoleDescriptor.getParticipant());
		//assertTrue(roleDescriptor.getParticipant().size() == 0);
		
		this.concreteRoleDescriptor.removeParticipant(participant) ;
		assertNull(this.concreteRoleDescriptor.getParticipant());

		// Rk: the tearDown method is called here.
	}
	
	/**********/
	
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
	
	/**********/

	public void testAddConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.
		
		ConcreteTaskDescriptor concreteTaskDescriptor = new ConcreteTaskDescriptor();
		this.concreteRoleDescriptor.addConcreteTaskDescriptor(concreteTaskDescriptor);
		
		assertNotNull(this.concreteRoleDescriptor.getConcreteTaskDescriptors());
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().size() == 1);
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().contains(concreteTaskDescriptor));
		
		assertEquals(concreteTaskDescriptor.getConcreteRoleDescriptor(), this.concreteRoleDescriptor);
		
		// Rk: the tearDown method is called here.
	}

	public void testRemoveConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.
		
		ConcreteTaskDescriptor concreteTaskDescriptor = new ConcreteTaskDescriptor();
		
		this.concreteRoleDescriptor.addConcreteTaskDescriptor(concreteTaskDescriptor);
		assertNotNull(this.concreteRoleDescriptor.getConcreteTaskDescriptors());
		assertEquals(concreteTaskDescriptor.getConcreteRoleDescriptor(), this.concreteRoleDescriptor);
		
		this.concreteRoleDescriptor.removeConcreteTaskDescriptor(concreteTaskDescriptor);
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().size() == 0);
		
		assertNull(concreteTaskDescriptor.getConcreteRoleDescriptor());
		
		// Rk: the tearDown method is called here.
	}
	
	public void testAddAllConcreteTaskDescriptors() {
		// Rk: the setUp method is called here.
		
		Set<ConcreteTaskDescriptor> concreteTaskDescriptors = new HashSet<ConcreteTaskDescriptor>();
		ConcreteTaskDescriptor ctd1 = new ConcreteTaskDescriptor() ;
		ctd1.setConcreteName("ctd1") ;
		ConcreteTaskDescriptor ctd2 = new ConcreteTaskDescriptor() ;
		ctd2.setConcreteName("ctd2") ;	// only to specify that ctd1 and ctd2 are not equals
		concreteTaskDescriptors.add(ctd1) ;
		concreteTaskDescriptors.add(ctd2) ;
		for (ConcreteTaskDescriptor ctd : concreteTaskDescriptors) {
			ctd.addConcreteRoleDescriptor(this.concreteRoleDescriptor);
		}
		
		this.concreteRoleDescriptor.addAllConcreteTaskDescriptors(concreteTaskDescriptors) ;
		
		assertNotNull(this.concreteRoleDescriptor.getConcreteTaskDescriptors()) ;
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().size() == 2) ;
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().contains(ctd1)) ;
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().contains(ctd2)) ;
		
		assertNotNull(ctd1.getConcreteRoleDescriptor()) ;
		assertEquals(ctd1.getConcreteRoleDescriptor(), this.concreteRoleDescriptor) ;
		assertNotNull(ctd2.getConcreteRoleDescriptor()) ;
		assertEquals(ctd2.getConcreteRoleDescriptor(), this.concreteRoleDescriptor) ;
		
		// Rk: the tearDown method is called here.
	}

	public void testRemoveAllConcreteTaskDescriptors() {
		// Rk: the setUp method is called here.
		
		Set<ConcreteTaskDescriptor> concreteTaskDescriptors = new HashSet<ConcreteTaskDescriptor>();
		ConcreteTaskDescriptor ctd1 = new ConcreteTaskDescriptor() ;
		ctd1.setConcreteName("ctd1") ;
		ConcreteTaskDescriptor ctd2 = new ConcreteTaskDescriptor() ;
		ctd2.setConcreteName("ctd2") ;	// only to specify that ctd1 and ctd2 are not "equals()"
		concreteTaskDescriptors.add(ctd1) ;
		concreteTaskDescriptors.add(ctd2) ;
		for (ConcreteTaskDescriptor ctd : concreteTaskDescriptors) {
			ctd.addConcreteRoleDescriptor(this.concreteRoleDescriptor);
		}
		
		// see above for tests checking addAllConcreteTaskDescriptors
		this.concreteRoleDescriptor.addAllConcreteTaskDescriptors(concreteTaskDescriptors) ;
		
		this.concreteRoleDescriptor.removeAllConcreteTaskDescriptors() ;
		
		assertNull(ctd1.getConcreteRoleDescriptor()) ;
		assertNull(ctd2.getConcreteRoleDescriptor()) ;
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().size() == 0) ;
		
		// Rk: the tearDown method is called here.
	}
}
