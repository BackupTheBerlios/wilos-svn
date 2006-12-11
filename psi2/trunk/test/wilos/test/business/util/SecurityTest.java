
package wilos.test.business.util ;

import junit.framework.TestCase ;
import wilos.business.util.Security ;

/**
 * This class represents the class test of the Security class.
 * 
 * @author Marseyeah
 * 
 */
public class SecurityTest extends TestCase {

	/**
	 * Test method for {@link woops2.business.util.Security#encode(String)}.
	 */
	public void testEncode() {
		// Test for a normal string
		String s = "test" ;
		assertEquals(Security.encode(s), "098f6bcd4621d373cade4e832627b4f6") ;

	}

}
