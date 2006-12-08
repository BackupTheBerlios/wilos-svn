
package wilos.test.business.util ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author Marseyeah
 * 
 * This class represents all tests conecerning util package's classes.
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
