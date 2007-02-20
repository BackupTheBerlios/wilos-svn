package wilos.test.hibernate.spem2.process;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.spem2.process") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ProcessDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
