
package wilos.test.hibernate.misc.concreteworkbreakdownelement ;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.misc.concreteworkbreakdownelement") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteWorkBreakdownElementDaoTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
