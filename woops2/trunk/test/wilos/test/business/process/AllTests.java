
package wilos.test.business.process ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author Mathieu BENOIT.
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.business.process") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ProcessServiceTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
