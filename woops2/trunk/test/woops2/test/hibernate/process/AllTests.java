package woops2.test.hibernate.process;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.hibernate.process") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ProcessDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
