package wilos.test.model.spem2.breakdownelement;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Mathieu BENOIT.
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.breakdownelement") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(BreakdownElementTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
