
package wilos.test.hibernate.misc.concretetaskdescriptor ;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.misc.concretetaskdescriptor") ;
		// $JUnit-BEGIN$
		suite.addTestSuite(ConcreteTaskDescriptorDaoTest.class) ;
		// $JUnit-END$
		return suite ;
	}

}
