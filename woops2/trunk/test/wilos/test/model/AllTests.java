
package wilos.test.model ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.model.spem2.AllTests.suite()) ;
		suite.addTest(wilos.test.model.misc.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
