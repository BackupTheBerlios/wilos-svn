
package wilos.test ;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	wilos.test.business.TestSuite.class,
	wilos.test.model.TestSuite.class,
	wilos.test.hibernate.TestSuite.class
})
public class TestSuite {
	// None.
}