
package wilos.test ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("JUnit Tests for Wilos project") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.business.services.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.spem2.AllTests.suite()) ;
		suite.addTest(wilos.test.model.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
