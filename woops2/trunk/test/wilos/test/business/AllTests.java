
package wilos.test.business ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.business") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.business.activity.AllTests.suite()) ;
		suite.addTest(wilos.test.business.process.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
