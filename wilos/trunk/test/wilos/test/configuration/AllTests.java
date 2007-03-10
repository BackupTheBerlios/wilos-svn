package wilos.test.configuration;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * This class represents the test suite that launch all tests.
 * 
 * @author martial
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("JUnit Tests for PSI2 project") ;
		
		//begin of the model classes tests
		suite.addTest(wilos.test.model.misc.wilosuser.AllTests.suite()) ;
		
		//begin of the business classes tests
		suite.addTest(wilos.test.business.services.wilosuser.AllTests.suite()) ;
		
		//begin of the hibernate classes tests
		suite.addTest(wilos.test.hibernate.misc.wilosuser.AllTests.suite()) ;
		//end of the hibernate classes tests
		
		//begin of the hibernate classes tests
		suite.addTest(wilos.test.business.util.AllTests.suite()) ;
		//end of the hibernate classes tests
		
//		begin of the hibernate classes tests
		suite.addTest(wilos.test.business.services.project.AllTests.suite()) ;
		//end of the hibernate classes tests

		// $JUnit-END$
		return suite ;
	}
}
