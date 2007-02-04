
package wilos.test.hibernate.misc.concretebreakdownelement;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author nanawel
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.misc.concretebreakdownelement") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ConcreteBreakdownElementDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}