
package wilos.test.model.spem2 ;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	wilos.test.model.spem2.element.TestSuite.class,
	wilos.test.model.spem2.activity.TestSuite.class,
	wilos.test.model.spem2.breakdownelement.TestSuite.class,
	wilos.test.model.spem2.process.TestSuite.class,
	wilos.test.model.spem2.role.TestSuite.class,
	wilos.test.model.spem2.task.TestSuite.class,
	wilos.test.model.spem2.workbreakdownelement.TestSuite.class,
	wilos.test.model.spem2.phase.TestSuite.class,
	wilos.test.model.spem2.iteration.TestSuite.class
	})
public class TestSuite {
	// None.
}
