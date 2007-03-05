package wilos.test.business.services.spem2.process;

import wilos.test.business.services.spem2.process.ProcessManagementServiceTest;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author BlackMilk
 *
 * This class represents the test suite for the process management service
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for wilos.test.business.services.spem2.process") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ProcessManagementServiceTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
