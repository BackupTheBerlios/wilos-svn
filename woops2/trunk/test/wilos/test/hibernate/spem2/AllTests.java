
package wilos.test.hibernate.spem2 ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.spem2") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.hibernate.spem2.process.AllTests.suite()) ;
		//suite.addTest(wilos.test.hibernate.spem2.activity.TestSuite.suite()) ;
		suite.addTest(wilos.test.hibernate.spem2.element.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.spem2.breakdownelement.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.spem2.workbreakdownelement.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.spem2.task.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.spem2.role.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.spem2.iteration.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.spem2.phase.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
