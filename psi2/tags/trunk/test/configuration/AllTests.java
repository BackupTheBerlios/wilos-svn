package configuration;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author martial
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("JUnit Tests for PSI2 project") ;
		
		//begin of the business classes tests
		suite.addTest(business.Participant.AllTests.suite()) ;
		suite.addTest(business.Login.AllTests.suite()) ;
		suite.addTest(business.ProcessManager.AllTests.suite()) ;
		suite.addTest(business.ProjectDirector.AllTests.suite()) ;
		//end of the business classes tests
		
		//begin of the hibernate classes tests
		suite.addTest(hibernate.Participant.AllTests.suite()) ;
		//end of the hibernate classes tests
		
		
//		begin of the hibernate classes tests
		suite.addTest(model.Participant.AllTests.suite()) ;
		//end of the hibernate classes tests
		
		
		
		
		
		
		
		// $JUnit-END$
		return suite ;
	}

}
