package wilos.test.business.services.project;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * This class represents the test suite for the project services
 * 
 * @author Alex
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for business.services.project") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ProjectServiceTest.class) ;		
		//$JUnit-END$
		return suite ;
	}

}