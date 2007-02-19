
package wilos.test.model.misc.concretebreakdownelement ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author nanawel
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.misc.concretebreakdownelement") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteBreakdownElementTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
