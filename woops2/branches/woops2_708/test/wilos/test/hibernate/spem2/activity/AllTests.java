package wilos.test.hibernate.spem2.activity;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.activity") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ActivityDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
