
package wilos.test.model.misc ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.model.misc") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.model.misc.concreteactivity.AllTests.suite()) ;
		suite.addTest(wilos.test.model.misc.concretebreakdownelement.AllTests.suite()) ;
		suite.addTest(wilos.test.model.misc.concreteiteration.AllTests.suite()) ;
		suite.addTest(wilos.test.model.misc.concretephase.AllTests.suite()) ;
		suite.addTest(wilos.test.model.misc.concretetaskdescriptor.AllTests.suite()) ;
		suite.addTest(wilos.test.model.misc.concreteroledescriptor.AllTests.suite()) ;
		suite.addTest(wilos.test.model.misc.concreteworkbreakdownelement.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
