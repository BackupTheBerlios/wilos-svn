
package wilos.test.hibernate.misc.wilosuser ;

import java.util.Set;

import junit.framework.TestCase;
import wilos.hibernate.misc.wilosuser.WilosUserDao;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.test.TestConfiguration;

/**
 * 
 * This class represents the test class for WilosUserDao class.
 * 
 * @author Marseyeah
 * 
 */
public class WilosUserDaoTest extends TestCase {

	private WilosUserDao wud ;

	private WilosUser wu ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.wud = (WilosUserDao) TestConfiguration.getInstance().getApplicationContext().getBean("WilosUserDao") ;
		this.wu = new Participant() ;
		this.wu.setLogin("loginWU") ;
		this.wu.setName("nameWU") ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		this.wud.deleteWilosUser(this.wu) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.WilosUserDao#saveOrUpdateWilosUser(wilos.model.misc.wilosuser.WilosUser)}.
	 */
	public void testSaveOrUpdateWilosUser() {
		this.wud.saveOrUpdateWilosUser(this.wu) ;

		WilosUser wuTmp = this.wud.getWilosUser("loginWU") ;
		assertNotNull(wuTmp) ;
		assertTrue(this.wu.getLogin().equals(wuTmp.getLogin())) ;
		assertTrue(this.wu.getName().equals(wuTmp.getName())) ;
	}

	/**
	 * Test method for 
	 * {@link wilos.hibernate.misc.wilosuser.WilosUserDao#getAllWilosUsers()}.
	 */
	public void testGetAllWilosUsers() {
		this.wud.saveOrUpdateWilosUser(this.wu) ;

		Set<WilosUser> setTmp = this.wud.getAllWilosUsers() ;
		assertNotNull(setTmp) ;
		assertTrue(setTmp.size() >= 1) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.WilosUserDao#getWilosUser(java.lang.String)}.
	 */
	public void testGetWilosUser() {
		this.wud.saveOrUpdateWilosUser(this.wu) ;

		WilosUser wuTmp = this.wud.getWilosUser("loginWU") ;
		assertNotNull(wuTmp) ;
		assertEquals(wuTmp.getLogin(), "loginWU") ;
		assertEquals(wuTmp.getName(), "nameWU") ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.WilosUserDao#deleteWilosUser(wilos.model.misc.wilosuser.WilosUser)}.
	 */
	public void testDeleteWilosUser() {
		this.wud.saveOrUpdateWilosUser(this.wu) ;
		this.wud.deleteWilosUser(this.wu) ;

		WilosUser wuTmp = this.wud.getWilosUser("loginWU") ;
		assertNull(wuTmp) ;
	}

}
