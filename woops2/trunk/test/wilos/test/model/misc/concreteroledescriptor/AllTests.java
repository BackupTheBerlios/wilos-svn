
package wilos.test.model.misc.concreteroledescriptor ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author nanawel
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.misc.concreteroledescriptor") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteRoleDescriptorTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
