
package wilos.test.business.services.task ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.business.services.task") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(StepServiceTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
