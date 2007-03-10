package wilos.test.business.services.wilosuser;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * This class represents the test suite for the wilowUsers services
 * 
 * @author Michaël
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for business.services.wilosuser") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(LoginServiceTest.class) ;
		suite.addTestSuite(ParticipantServiceTest.class) ;
		suite.addTestSuite(ProcessManagerServiceTest.class) ;
		suite.addTestSuite(ProjectDirectorServiceTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
