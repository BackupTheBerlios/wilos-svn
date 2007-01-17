
package wilos.test.business.services ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.business.services") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.business.services.activity.AllTests.suite()) ;
		suite.addTest(wilos.test.business.services.process.AllTests.suite()) ;
		suite.addTest(wilos.test.business.services.role.AllTests.suite()) ;
		suite.addTest(wilos.test.business.services.task.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
