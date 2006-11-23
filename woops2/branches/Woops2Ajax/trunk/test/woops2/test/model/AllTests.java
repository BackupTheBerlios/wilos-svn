
package woops2.test.model ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.model") ;
		// $JUnit-BEGIN$
		suite.addTest(woops2.test.model.process.AllTests.suite()) ;
		suite.addTest(woops2.test.model.activity.AllTests.suite()) ;
		suite.addTest(woops2.test.model.breakdownelement.AllTests.suite()) ;
		suite.addTest(woops2.test.model.workbreakdownelement.AllTests.suite()) ;
		suite.addTest(woops2.test.model.task.AllTests.suite()) ;
		suite.addTest(woops2.test.model.role.AllTests.suite()) ;
		suite.addTest(woops2.test.model.element.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
