
package wilos.test.business.services.spem2.role ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.business.services.role") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(RoleDescriptorServiceTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
