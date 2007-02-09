package wilos.test.hibernate.misc.concretephase;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.misc.concretephase") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcretePhaseDaoTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}

