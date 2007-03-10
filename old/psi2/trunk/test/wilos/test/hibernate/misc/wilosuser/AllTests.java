package wilos.test.hibernate.misc.wilosuser;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 * This class represents all tests concerning Wilos users DAO classes.
 *
 * @author Martial
 * @author Marseyeah
 * 
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for hibernate") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ParticipantDaoTest.class) ;
		suite.addTestSuite(AdministratorDaoTest.class) ;
		suite.addTestSuite(ProcessManagerDaoTest.class) ;
		suite.addTestSuite(ProjectDirectorDaoTest.class) ;
		suite.addTestSuite(WilosUserDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
