package woops2.test.model.breakdownelement;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Mathieu BENOIT.
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.model.breakdownelement") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(BreakdownElementTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
