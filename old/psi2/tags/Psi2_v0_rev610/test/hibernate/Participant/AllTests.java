package hibernate.Participant;

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
		TestSuite suite = new TestSuite("Test for hibernate") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ParticipantDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
