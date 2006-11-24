
package woops2.test.business ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author Mathieu BENOIT.
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.business") ;
		// $JUnit-BEGIN$
		suite.addTest(woops2.test.business.activity.AllTests.suite()) ;
		suite.addTest(woops2.test.business.process.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
