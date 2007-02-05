package wilos.test.model.misc.wilosuser;

import wilos.model.misc.wilosuser.Administrator;
import junit.framework.TestCase;

/**
 * @author BlackMilk
 *
 * This class represents the class test of the Administrator class
 * 
 *
 */
public class AdministratorTest extends TestCase {
	
	private Administrator admin1;
	private Administrator admin2;
	
	private final static String LOGIN = "john" ;
	
	private final static String LOGIN2 = "cathy" ;

	private final static String NAME = "georges" ;
	
	private final static String NAME2 = "willis" ;
	
	private final static String FIRSTNAME = "johnny" ;
	
	private final static String FIRSTNAME2 = "catherine" ;

	private final static String PASS = "pass" ;
	
	private final static String PASS2 = "pass2" ;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		admin1 = new Administrator();
		admin2 = new Administrator();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Administrator#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		admin1.setLogin(LOGIN);
		admin1.setFirstname(FIRSTNAME);
		admin1.setName(NAME);
		admin1.setPassword(PASS);
		admin2.setLogin(LOGIN);
		admin2.setFirstname(FIRSTNAME);
		admin2.setName(NAME);
		admin2.setPassword(PASS);
		assertTrue(admin1.equals(admin2));
		/*Login test*/
		admin2.setLogin(LOGIN2);
		assertFalse(admin1.equals(admin2));
		/*Name test*/
		admin2.setLogin(LOGIN);
		admin2.setName(NAME2);
		assertFalse(admin1.equals(admin2));
		/*FirstName test*/
		admin2.setName(NAME);
		admin2.setFirstname(FIRSTNAME2);
		assertFalse(admin1.equals(admin2));
		/*Password test*/
		admin2.setFirstname(FIRSTNAME);
		admin2.setPassword(PASS2);
		assertFalse(admin1.equals(admin2));
	}

}
