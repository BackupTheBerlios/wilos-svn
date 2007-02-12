package wilos.test.model.misc.concreteworkbreakdownelement;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author eperico
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.misc.concreteworkbreakdownelement") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteWorkBreakdownElementTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
