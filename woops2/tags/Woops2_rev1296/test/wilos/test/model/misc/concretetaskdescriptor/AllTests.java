
package wilos.test.model.misc.concretetaskdescriptor ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.misc.concretetaskdescriptor") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteTaskDescriptorTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
