
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
	
	private Administrator a2 ;

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
		this.ad.saveOrUpdateAdministrator(this.a);
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
		this.a2 = new Administrator() ;
		this.a2.setLogin("testAdmin2") ;
		this.a2.setPassword("pass") ;
		this.ad.deleteAdministrator(this.a2) ;
		
		Administrator admTmp2 = this.ad.getAdministrator("testAdmin2") ;
		assertNull(admTmp2) ;
	}

}
