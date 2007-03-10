package wilos.test.hibernate.spem2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { wilos.test.hibernate.spem2.process.TestSuite.class,
		wilos.test.hibernate.spem2.phase.TestSuite.class,
		wilos.test.hibernate.spem2.iteration.TestSuite.class,
		wilos.test.hibernate.spem2.element.TestSuite.class,
		wilos.test.hibernate.spem2.breakdownelement.TestSuite.class,
		wilos.test.hibernate.spem2.activity.TestSuite.class,
		wilos.test.hibernate.spem2.role.TestSuite.class,
		wilos.test.hibernate.spem2.task.TestSuite.class,
		wilos.test.hibernate.spem2.workbreakdownelement.TestSuite.class, })
public class TestSuite {
	// None.
}
