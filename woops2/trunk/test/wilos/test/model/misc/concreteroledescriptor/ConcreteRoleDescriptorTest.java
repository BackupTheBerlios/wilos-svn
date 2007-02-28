package wilos.test.model.misc.concreteroledescriptor;


import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcreteRoleDescriptorTest {

	private ConcreteRoleDescriptor concreteRoleDescriptor ;

public static final String CONCRETENAME = "concreteName";

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	@Before
	public void setUp() {
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor() ;
		this.concreteRoleDescriptor.setConcreteName(CONCRETENAME);
	}

	@After
	public void tearDown() {
		//None
	}

	@Test
	public void testHashCode() {

		// Rk: the setUp method is called here.

		ConcreteRoleDescriptor tmp = new ConcreteRoleDescriptor();
		tmp.setConcreteName(CONCRETENAME);

		RoleDescriptor roleDescriptor = new RoleDescriptor();
		roleDescriptor.setPrefix(PREFIX) ;
		roleDescriptor.setIsOptional(IS_OPTIONAL) ;

		tmp.setRoleDescriptor(roleDescriptor);
		this.concreteRoleDescriptor.setRoleDescriptor(roleDescriptor);

		assertNotNull(this.concreteRoleDescriptor.hashCode()) ;
		assertNotNull(tmp.hashCode()) ;
		assertEquals(this.concreteRoleDescriptor.hashCode(), tmp.hashCode()) ;

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testEqualsObject() {

		// Rk: the setUp method is called here.

		ConcreteRoleDescriptor tmp = new ConcreteRoleDescriptor();
		tmp.setConcreteName(CONCRETENAME);

		RoleDescriptor roleDescriptor = new RoleDescriptor();
		roleDescriptor.setPrefix(PREFIX) ;
		roleDescriptor.setIsOptional(IS_OPTIONAL) ;

		tmp.setRoleDescriptor(roleDescriptor);
		this.concreteRoleDescriptor.setRoleDescriptor(roleDescriptor);

		assertNotNull(this.concreteRoleDescriptor) ;
		assertNotNull(tmp) ;
		assertTrue(this.concreteRoleDescriptor.equals(tmp)) ;

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testClone() {

		// Rk: the setUp method is called here.

		ConcreteRoleDescriptor tmp = null;

		RoleDescriptor roleDescriptor = new RoleDescriptor();
		roleDescriptor.setPrefix(PREFIX) ;
		roleDescriptor.setIsOptional(IS_OPTIONAL) ;

		this.concreteRoleDescriptor.setRoleDescriptor(roleDescriptor);

		try{
			tmp = this.concreteRoleDescriptor.clone();
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}

		assertNotNull(tmp);
		assertEquals(tmp, this.concreteRoleDescriptor) ;


		// Rk: the tearDown method is called here.
	}

	@Test
	public void testAddParticipant() {
		// Rk: the setUp method is called here.

		Participant participant = new Participant();
		this.concreteRoleDescriptor.addParticipant(participant);
		assertNotNull(this.concreteRoleDescriptor.getParticipant());
		assertEquals(this.concreteRoleDescriptor.getParticipant(), participant) ;

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testRemoveParticipant() {
		// Rk: the setUp method is called here.

		Participant participant = new Participant();
		participant.setFirstname("cocolapin");

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

	
	@Test
	public void testAddRoleDescriptor() {
		// Rk: the setUp method is called here.

		RoleDescriptor roleDescriptor = new RoleDescriptor();
		this.concreteRoleDescriptor.addRoleDescriptor(roleDescriptor);
		assertNotNull(this.concreteRoleDescriptor.getRoleDescriptor());
		assertEquals(this.concreteRoleDescriptor.getRoleDescriptor(), roleDescriptor) ;

		// Rk: the tearDown method is called here.
	}

	@Test
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

	@Test
	public void testAddConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		ConcreteTaskDescriptor concreteTaskDescriptor = new ConcreteTaskDescriptor();
		this.concreteRoleDescriptor.addConcreteTaskDescriptor(concreteTaskDescriptor);

		assertNotNull(this.concreteRoleDescriptor.getConcreteTaskDescriptors());
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().size() == 1);
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().contains(concreteTaskDescriptor));

		assertEquals(concreteTaskDescriptor.getMainConcreteRoleDescriptor(), this.concreteRoleDescriptor);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testRemoveConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		ConcreteTaskDescriptor concreteTaskDescriptor = new ConcreteTaskDescriptor();

		this.concreteRoleDescriptor.addConcreteTaskDescriptor(concreteTaskDescriptor);
		assertNotNull(this.concreteRoleDescriptor.getConcreteTaskDescriptors());
		assertNotNull(this.concreteRoleDescriptor.getConcreteTaskDescriptors().contains(concreteTaskDescriptor));
		assertEquals(concreteTaskDescriptor.getMainConcreteRoleDescriptor(), this.concreteRoleDescriptor);

		this.concreteRoleDescriptor.removeConcreteTaskDescriptor(concreteTaskDescriptor);
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().size() == 0);

		assertNull(concreteTaskDescriptor.getMainConcreteRoleDescriptor());

		// Rk: the tearDown method is called here.
	}

	@Test
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

		assertNotNull(ctd1.getMainConcreteRoleDescriptor()) ;
		assertEquals(ctd1.getMainConcreteRoleDescriptor(), this.concreteRoleDescriptor) ;
		assertNotNull(ctd2.getMainConcreteRoleDescriptor()) ;
		assertEquals(ctd2.getMainConcreteRoleDescriptor(), this.concreteRoleDescriptor) ;

		// Rk: the tearDown method is called here.
	}

	@Test
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

		assertNull(ctd1.getMainConcreteRoleDescriptor()) ;
		assertNull(ctd2.getMainConcreteRoleDescriptor()) ;
		assertTrue(this.concreteRoleDescriptor.getConcreteTaskDescriptors().size() == 0) ;

		// Rk: the tearDown method is called here.
	}
}
