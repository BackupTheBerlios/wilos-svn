
package wilos.test.hibernate.misc ;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	wilos.test.hibernate.misc.concreteactivity.TestSuite.class,
	wilos.test.hibernate.misc.concretebreakdownelement.TestSuite.class,
	wilos.test.hibernate.misc.concreteiteration.TestSuite.class,
	wilos.test.hibernate.misc.concretephase.TestSuite.class,
	wilos.test.hibernate.misc.concreterole.TestSuite.class,
	wilos.test.hibernate.misc.concretetaskdescriptor.TestSuite.class,
	wilos.test.hibernate.misc.concreteworkbreakdownelement.TestSuite.class
	})
public class TestSuite {
	// None.
}
