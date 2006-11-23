
package woops2.test.model.process ;

import junit.framework.TestCase ;
import woops2.model.process.Process ;

/**
 * @author deder
 * 
 */
public class ProcessTest extends TestCase {

	private Process process ;

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.process = new Process() ;
		this.process.setPrefix(PREFIX) ;
		this.process.setIsOptional(IS_OPTIONAL) ;
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
	 * Test method for {@link woops2.model.process.Process#hashCode()}.
	 */
	public final void testHashCode() {
		// Rk: the setUp method is called here.

		Process proc = new Process() ;
		proc.setPrefix(PREFIX) ;
		proc.setIsOptional(IS_OPTIONAL) ;

		assertNotNull(this.process.hashCode()) ;
		assertNotNull(proc.hashCode()) ;
		assertEquals(this.process.hashCode(), proc.hashCode()) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link woops2.model.process.Process#equals(java.lang.Object)}.
	 */
	public final void testEquals() {
		// Rk: the setUp method is called here.

		// Assert if it's equal by references.
		assertTrue("By references", this.process.equals(this.process)) ;

		// Assert if it's equal field by field.
		Process processTmp1 = null ;
		try{
			processTmp1 = this.process.clone() ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testEquals method") ;
		}
		assertTrue("Field by field", this.process.equals(processTmp1)) ;

		// Assert if it's not equal.
		Process procTmp2 = new Process() ;
		procTmp2.setPrefix("prefixFalse") ;
		procTmp2.setIsOptional(true) ;
		assertFalse("Not equals", this.process.equals(procTmp2)) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link woops2.model.process.Process#clone()}.
	 */
	public final void testClone() {
		// Rk: the setUp method is called here.

		try{
			assertEquals(this.process.clone(), this.process) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}

		// Rk: the tearDown method is called here.
	}
}
