package wilos.test.model.spem2.workbreakdownelement;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.model.workbreakdownelement") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(WorkBreakdownElementTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
