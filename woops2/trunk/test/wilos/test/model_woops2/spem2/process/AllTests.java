package wilos.test.model.spem2.process;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.model.process") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ProcessTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
