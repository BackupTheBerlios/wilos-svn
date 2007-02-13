package wilos.test.model.misc.concretephase;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author soosuske
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.misc.concretephase") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcretePhaseTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}

