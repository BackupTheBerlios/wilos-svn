
package wilos.test.hibernate.misc.concrecteactivity;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.misc.concrecteactivity") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ConcreteActivityDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}