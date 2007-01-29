
package wilos.test.model.misc ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.misc") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.model.misc.concretetaskdescriptor.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
