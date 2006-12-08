package business.Login;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Michaël
 *
 * This class represents ... TODO
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for business.Login") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(LoginServiceTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
