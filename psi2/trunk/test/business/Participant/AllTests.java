package business.Participant;

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
		TestSuite suite = new TestSuite("Test for business") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ParticipantServiceTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
