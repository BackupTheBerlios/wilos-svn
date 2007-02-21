package wilos.test.model.spem2.phase;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.spem2.phase.Phase;

public class PhaseTest extends TestCase{
	private Phase phase ;

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;
	
	public static final String CONCRETENAME = "ConcreteName" ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.phase = new Phase() ;
		this.phase.setPrefix(PREFIX) ;
		this.phase.setIsOptional(IS_OPTIONAL) ;
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
	 * Test method for {@link wilos.model.spem2.iteration.Iteration#hashCode()}.
	 */
	public final void testHashCode() {
		// Rk: the setUp method is called here.

		Phase phas = new Phase() ;
		phas.setPrefix(PREFIX) ;
		phas.setIsOptional(IS_OPTIONAL) ;

		assertNotNull(this.phase.hashCode()) ;
		assertNotNull(phas.hashCode()) ;
		assertEquals(this.phase.hashCode(), phas.hashCode()) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.model.spem2.phase.Phase#equals(java.lang.Object)}.
	 */
	public final void testEquals() {
		// Rk: the setUp method is called here.

		// Assert if it's equal by references.
		assertTrue("By references", this.phase.equals(this.phase)) ;

		// Assert if it's equal field by field.
		Phase phaseTmp1 = null ;
		try{
			phaseTmp1 = this.phase.clone() ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testEquals method") ;
		}
		assertTrue("Field by field", this.phase.equals(phaseTmp1)) ;

		// Assert if it's not equal.
		Phase phasTmp2 = new Phase() ;
		phasTmp2.setPrefix("prefixFalse") ;
		phasTmp2.setIsOptional(true) ;
		assertFalse("Not equals", this.phase.equals(phasTmp2)) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.model.spem2.phase.Phase#clone()}.
	 */
	public final void testClone() {
		// Rk: the setUp method is called here.

		try{
			assertEquals(this.phase.clone(), this.phase) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}

		// Rk: the tearDown method is called here.
	}
	
	public void testAddConcretePhase() {
		ConcretePhase concretePhase = new ConcretePhase() ;
		concretePhase.setConcreteName(CONCRETENAME) ;

		this.phase.addConcretePhase(concretePhase) ;

		assertTrue(this.phase.getConcretePhases().size() == 1) ;
		assertNotNull(concretePhase.getPhase()) ;
	}
	
	public void testAddToAllConcretePhase() {
		ConcretePhase cp1 = new ConcretePhase() ;
		cp1.setConcreteName("name1") ;

		ConcretePhase cp2 = new ConcretePhase() ;
		cp2.setConcreteName("name2")  ;

		Set<ConcretePhase> set = new HashSet<ConcretePhase>() ;
		set.add(cp1) ;
		set.add(cp2) ;

		this.phase.addAllConcretePhases(set) ;

		assertFalse(this.phase.getConcretePhases().isEmpty()) ;
		assertEquals(2, this.phase.getConcretePhases().size()) ;
		assertNotNull(cp1.getPhase()) ;
		assertNotNull(cp2.getPhase()) ;
	}
	
	public void testRemoveConcretePhase() {
		ConcretePhase concretePhase = new ConcretePhase() ;
		concretePhase.setConcreteName(CONCRETENAME);

		this.phase.removeConcretePhase(concretePhase) ;

		assertTrue(this.phase.getConcretePhases().isEmpty()) ;
		assertNull(concretePhase.getPhase()) ;
	}
	
	public void testRemoveAllConcretePhases() {
		ConcretePhase cp1 = new ConcretePhase() ;
		cp1.setConcreteName("name1") ;

		ConcretePhase cp2 = new ConcretePhase() ;
		cp2.setConcreteName("name2")  ;

		Set<ConcretePhase> set = new HashSet<ConcretePhase>() ;
		set.add(cp1) ;
		set.add(cp2) ;

		this.phase.addAllConcretePhases(set) ;
		assertTrue(this.phase.getConcretePhases().size() == 2) ;
		assertNotNull(cp1.getPhase()) ;
		assertNotNull(cp2.getPhase()) ;

		this.phase.removeAllConcretePhase() ;
		assertTrue(this.phase.getConcretePhases().isEmpty()) ;
		assertNull(cp1.getPhase()) ;
		assertNull(cp2.getPhase()) ;
	}
}