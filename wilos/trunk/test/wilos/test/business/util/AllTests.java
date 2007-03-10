
package wilos.test.business.util ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * This class represents all tests concerning util package's classes.
 * 
 * @author Marseyeah
 * 
 */
public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for business.util") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(SecurityTest.class) ;
		// $JUnit-END$
		return suite ;
	}
}
