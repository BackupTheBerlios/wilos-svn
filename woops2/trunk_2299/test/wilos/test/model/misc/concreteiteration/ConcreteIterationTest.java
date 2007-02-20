/**
 * 
 */
package wilos.test.model.misc.concreteiteration;

import junit.framework.TestCase;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.spem2.iteration.Iteration;

/**
 * @author Sebastien
 * 
 */
public class ConcreteIterationTest extends TestCase {

	private ConcreteIteration concreteIteration;

	public static final String CONCRETENAME = "concreteName";
	
	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.concreteIteration = new ConcreteIteration();
		this.concreteIteration.setConcreteName(CONCRETENAME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link wilos.model.misc.concreteiteration.ConcreteIteration#hashCode()}.
	 */
	public void testHashCode() {
		
		// Rk: the setUp method is called here.

		ConcreteIteration tmp = new ConcreteIteration();
		tmp.setConcreteName(CONCRETENAME);
		
		Iteration iteration = new Iteration();
		iteration.setPrefix(PREFIX) ;
		iteration.setIsOptional(IS_OPTIONAL) ;
		
		tmp.setIteration(iteration);
		this.concreteIteration.setIteration(iteration);

		assertNotNull(this.concreteIteration.hashCode()) ;
		assertNotNull(tmp.hashCode()) ;
		assertEquals(this.concreteIteration.hashCode(), tmp.hashCode()) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.model.misc.concreteiteration.ConcreteIteration#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		
		// Rk: the setUp method is called here.

		ConcreteIteration tmp = new ConcreteIteration();
		tmp.setConcreteName(CONCRETENAME);
		
		Iteration iteration = new Iteration();
		iteration.setPrefix(PREFIX) ;
		iteration.setIsOptional(IS_OPTIONAL) ;
		
		tmp.setIteration(iteration);
		this.concreteIteration.setIteration(iteration);

		assertNotNull(this.concreteIteration) ;
		assertNotNull(tmp) ;
		assertTrue(this.concreteIteration.equals(tmp)) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.model.misc.concreteiteration.ConcreteIteration#clone()}.
	 */
	public void testClone() {
		
		// Rk: the setUp method is called here.
		
		ConcreteIteration tmp = null;
		
		Iteration iteration = new Iteration();
		iteration.setPrefix(PREFIX) ;
		iteration.setIsOptional(IS_OPTIONAL) ;
		
		this.concreteIteration.setIteration(iteration);
		
		try{
			tmp = this.concreteIteration.clone();
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}
		
		assertNotNull(tmp);
		assertEquals(tmp, this.concreteIteration) ;
		

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.model.misc.concreteiteration.ConcreteIteration#addIteration(wilos.model.spem2.iteration.Iteration)}.
	 */
	public void testAddIteration() {

		// Rk: the setUp method is called here.
		
		Iteration iteration = new Iteration();
		iteration.setPrefix(PREFIX) ;
		iteration.setIsOptional(IS_OPTIONAL) ;

		this.concreteIteration.addIteration(iteration);

		assertEquals(this.concreteIteration.getIteration(), iteration) ;
		assertTrue(iteration.getConcreteIterations().contains(this.concreteIteration)) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.model.misc.concreteiteration.ConcreteIteration#removeIteration(wilos.model.spem2.iteration.Iteration)}.
	 */
	public void testRemoveIteration() {

		// Rk: the setUp method is called here.
		
		Iteration iteration = new Iteration();
		iteration.setPrefix(PREFIX) ;
		iteration.setIsOptional(IS_OPTIONAL) ;

		this.concreteIteration.addIteration(iteration);
		this.concreteIteration.removeIteration(iteration);
		
		assertNull(this.concreteIteration.getIteration()) ;
		assertFalse(iteration.getConcreteIterations().contains(this.concreteIteration)) ;

		// Rk: the tearDown method is called here.
	}

}
