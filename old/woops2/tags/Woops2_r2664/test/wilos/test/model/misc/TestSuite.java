
package wilos.test.model.misc ;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	wilos.test.model.misc.concreteactivity.TestSuite.class,
	wilos.test.model.misc.concretebreakdownelement.TestSuite.class,
	wilos.test.model.misc.concreteiteration.TestSuite.class,
	wilos.test.model.misc.concretephase.TestSuite.class,
	wilos.test.model.misc.concreteroledescriptor.TestSuite.class,
	wilos.test.model.misc.concretetaskdescriptor.TestSuite.class,
	wilos.test.model.misc.concreteworkbreakdownelement.TestSuite.class,
	})
public class TestSuite {
	// None.
}