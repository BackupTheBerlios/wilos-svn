package wilos.test.hibernate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { wilos.test.hibernate.spem2.TestSuite.class,
		wilos.test.hibernate.misc.TestSuite.class, })
public class TestSuite {
	// None.
}
