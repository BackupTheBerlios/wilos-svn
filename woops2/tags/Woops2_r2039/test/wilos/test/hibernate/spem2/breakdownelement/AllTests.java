package wilos.test.hibernate.spem2.breakdownelement;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.spem2.breakdownelement") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(BreakdownElementDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}