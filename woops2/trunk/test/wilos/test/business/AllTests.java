
package wilos.test.business ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.business") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.business.services.spem2.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
