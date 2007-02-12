
package wilos.test.business.services.spem2 ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.business.services.spem2") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.business.services.spem2.activity.AllTests.suite()) ;
		suite.addTest(wilos.test.business.services.spem2.process.AllTests.suite()) ;
		suite.addTest(wilos.test.business.services.spem2.role.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
