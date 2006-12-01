package business.ProjectDirector;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Michaël
 *
 * This class represents ... TODO
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for business.ProjectDirector") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ProjectDirectorServiceTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
