package wilos.test.hibernate.spem2.phase;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.spem2.phase") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(PhaseDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
