package wilos.test.hibernate.spem2.role;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Soosuske
 *
 *
 */
public class AllTests {
	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.spem2.role") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(RoleDefinitionDaoTest.class) ;
		suite.addTestSuite(RoleDescriptorDaoTest.class) ;
		//$JUnit-END$
		return suite ;
	}
}
