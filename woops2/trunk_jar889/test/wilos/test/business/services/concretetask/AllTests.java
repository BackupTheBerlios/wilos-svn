
package wilos.test.business.services.concretetask ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.business.services.concretetask") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteTaskDescriptorServiceTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
