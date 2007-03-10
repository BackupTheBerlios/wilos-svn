
package wilos.test.business ;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses( {
	wilos.test.business.services.misc.TestSuite.class,
	wilos.test.business.services.spem2.TestSuite.class
	})
public class TestSuite {
	// None.
}
