package wilos.test.model.misc.concreteactivity;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Mathieu BENOIT.
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.misc.concreteactivity") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ConcreteActivityTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
