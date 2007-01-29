
package wilos.test.business.services.process ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.business.services.process") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ProcessServiceTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
