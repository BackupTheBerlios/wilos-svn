package wilos.test.model.spem2.element;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Sebastien
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for wilos.test.model.spem2.element") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ElementTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
