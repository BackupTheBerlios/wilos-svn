
package wilos.test.hibernate ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.hibernate.spem2.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
