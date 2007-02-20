
package wilos.test.hibernate.misc.concreteiteration ;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.misc.concreteiteration") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteIterationDaoTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
