package woops2.test.model.element;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Sebastien
 *
 * This class represents ... TODO
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for woops2.test.model.element") ;
		//$JUnit-BEGIN$
		suite.addTestSuite(ElementTest.class) ;
		//$JUnit-END$
		return suite ;
	}

}
