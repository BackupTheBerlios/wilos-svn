
package wilos.test.model ;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	wilos.test.model.misc.TestSuite.class,
	wilos.test.model.spem2.TestSuite.class
})
public class TestSuite {
	// None.
}
