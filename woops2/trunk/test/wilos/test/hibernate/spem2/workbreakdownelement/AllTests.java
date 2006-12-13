package wilos.test.hibernate.spem2.workbreakdownelement;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Sebastien
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.workbreakdownelement") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(WorkBreakdownElementDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}