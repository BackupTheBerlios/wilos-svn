package woops2.test.model.role;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Julien SIEGA.
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.model.role") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(RoleDefinitionTest.class) ;
		suite.addTestSuite(RoleDescriptorTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
