
package wilos.test.business.services.activity ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author Mathieu BENOIT.
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.business.services.activity") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ActivityServiceTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
