
package wilos.test ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("JUnit Tests for Woops2 project") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.business.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.AllTests.suite()) ;
		suite.addTest(wilos.test.model.spem2.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
