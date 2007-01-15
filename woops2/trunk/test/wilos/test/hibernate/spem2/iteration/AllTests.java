package wilos.test.hibernate.spem2.iteration;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.spem2.iteration") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(IterationDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
