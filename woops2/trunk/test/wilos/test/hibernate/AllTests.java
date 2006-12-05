
package wilos.test.hibernate ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 * 
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for woops2.test.hibernate") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.hibernate.process.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.activity.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.element.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.breakdownelement.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.workbreakdownelement.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.task.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.role.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
