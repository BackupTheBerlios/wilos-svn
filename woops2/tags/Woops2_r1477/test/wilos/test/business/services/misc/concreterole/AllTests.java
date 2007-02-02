package wilos.test.business.services.misc.concreterole;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author eperico
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.business.services.concreterole") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteRoleDescriptorServiceTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
