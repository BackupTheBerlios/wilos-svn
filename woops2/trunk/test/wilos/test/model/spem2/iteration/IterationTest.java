package wilos.test.model.spem2.iteration;

import junit.framework.TestCase;
import wilos.model.spem2.iteration.Iteration;

/**
 * 
 * @author Soosuske
 *
 */
public class IterationTest extends TestCase{
	private Iteration iteration ;

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.iteration = new Iteration() ;
		this.iteration.setPrefix(PREFIX) ;
		this.iteration.setIsOptional(IS_OPTIONAL) ;
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

		Iteration iter = new Iteration() ;
		iter.setPrefix(PREFIX) ;
		iter.setIsOptional(IS_OPTIONAL) ;

		assertNotNull(this.iteration.hashCode()) ;
		assertNotNull(iter.hashCode()) ;
		assertEquals(this.iteration.hashCode(), iter.hashCode()) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.model.spem2.process.Process#equals(java.lang.Object)}.
	 */
	public final void testEquals() {
		// Rk: the setUp method is called here.

		// Assert if it's equal by references.
		assertTrue("By references", this.iteration.equals(this.iteration)) ;

		// Assert if it's equal field by field.
		Iteration iterationTmp1 = null ;
		try{
			iterationTmp1 = this.iteration.clone() ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testEquals method") ;
		}
		assertTrue("Field by field", this.iteration.equals(iterationTmp1)) ;

		// Assert if it's not equal.
		Iteration iterTmp2 = new Iteration() ;
		iterTmp2.setPrefix("prefixFalse") ;
		iterTmp2.setIsOptional(true) ;
		assertFalse("Not equals", this.iteration.equals(iterTmp2)) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.model.spem2.iteration.Iteration#clone()}.
	 */
	public final void testClone() {
		// Rk: the setUp method is called here.

		try{
			assertEquals(this.iteration.clone(), this.iteration) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}

		// Rk: the tearDown method is called here.
	}
}
