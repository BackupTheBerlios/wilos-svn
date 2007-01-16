package wilos.test.model.misc.wilosuser;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * This class represents the test suite for the wilosUsers models.
 *
 * @author Martial
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for model") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ParticipantTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
