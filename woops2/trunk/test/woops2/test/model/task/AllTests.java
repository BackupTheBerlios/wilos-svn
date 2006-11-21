package woops2.test.model.task;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Sebastien.
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.model.task") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(StepTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
