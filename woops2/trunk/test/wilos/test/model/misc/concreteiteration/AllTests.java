
package wilos.test.model.misc.concreteiteration ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author nanawel
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.misc.concreteiteration") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteIterationTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
