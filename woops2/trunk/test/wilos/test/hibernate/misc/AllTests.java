
package wilos.test.hibernate.misc ;

import junit.framework.Test ;
import junit.framework.TestSuite ;

/**
 * @author deder
 *
 */
public class AllTests {

	public static Test suite () {
		TestSuite suite = new TestSuite("Test for wilos.test.hibernate.misc") ;
		// $JUnit-BEGIN$
		suite.addTest(wilos.test.hibernate.misc.concretetaskdescriptor.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.misc.concretebreakdownelement.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.misc.concreterole.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.misc.concreteiteration.AllTests.suite()) ;
		suite.addTest(wilos.test.hibernate.misc.concretetaskdescriptor.AllTests.suite()) ;
		// $JUnit-END$
		return suite ;
	}

}
