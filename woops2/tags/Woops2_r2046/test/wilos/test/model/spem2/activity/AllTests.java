package wilos.test.model.spem2.activity;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Mathieu BENOIT.
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.spem2.activity") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ActivityTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}