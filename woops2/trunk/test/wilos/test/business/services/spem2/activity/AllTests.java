
package wilos.test.business.services.spem2.activity ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder.
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
