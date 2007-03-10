package model.Participant;

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
		TestSuite suite = new TestSuite("Test for model") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ParticipantTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
