package business.ProcessManager;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Martial
 *
 * This class represents ... TODO
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for business.ProcessManager") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ProcessManagerServiceTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
