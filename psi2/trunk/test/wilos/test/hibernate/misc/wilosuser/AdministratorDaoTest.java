
package wilos.test.hibernate.misc.wilosuser ;

import junit.framework.TestCase ;
import wilos.hibernate.misc.wilosuser.AdministratorDao ;
import wilos.model.misc.wilosuser.Administrator ;
import wilos.test.configuration.TestConfiguration ;

/**
 * This class represents the test class for AdministratorDao class.
 * 
 * @author Marseyeah
 * 
 */
public class AdministratorDaoTest extends TestCase {

	private AdministratorDao ad ;

	private Administrator a ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.ad = (AdministratorDao) TestConfiguration.getInstance().getApplicationContext().getBean("AdministratorDao") ;
		this.a = new Administrator() ;
		this.a.setLogin("testAdmin") ;
		this.a.setPassword("pass") ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		this.ad.deleteAdministrator(this.a) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.AdministratorDao#saveOrUpdateAdministrator(wilos.model.misc.wilosuser.Administrator)}.
	 */
	public void testSaveOrUpdateAdministrator() {
		this.ad.saveOrUpdateAdministrator(this.a) ;

		Administrator admTmp = this.ad.getAdministrator("testAdmin") ;
		assertNotNull(admTmp) ;
		assertTrue(this.a.getLogin().equals(admTmp.getLogin())) ;
		assertTrue(this.a.getPassword().equals(admTmp.getPassword())) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.AdministratorDao#getAdministrator(java.lang.String)}.
	 */
	public void testGetAdministrator() {
		this.ad.saveOrUpdateAdministrator(this.a) ;
		Administrator admTmp = this.ad.getAdministrator("testAdmin") ;
		assertNotNull(admTmp) ;
		assertEquals(admTmp.getLogin(), "testAdmin") ;
		assertEquals(admTmp.getPassword(), "pass") ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.AdministratorDao#deleteAdministrator(wilos.model.misc.wilosuser.Administrator)}.
	 */
	public void testDeleteAdministrator() {
		this.ad.saveOrUpdateAdministrator(this.a) ;
		this.ad.deleteAdministrator(this.a) ;
		Administrator admTmp = this.ad.getAdministrator("testAdmin") ;
		assertNull(admTmp) ;
	}

}
