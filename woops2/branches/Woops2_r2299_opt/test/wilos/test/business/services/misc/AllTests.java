package wilos.test.business.services.misc;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author eperico
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.business.services.misc") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.business.services.misc.concreterole.AllTests.suite()) ;
		suite.addTest(wilos.test.business.services.misc.concretetask.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
