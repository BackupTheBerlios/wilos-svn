
package wilos.test.hibernate.misc.concreterole ;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.misc.concreterole") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteRoleDescriptorDaoTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
