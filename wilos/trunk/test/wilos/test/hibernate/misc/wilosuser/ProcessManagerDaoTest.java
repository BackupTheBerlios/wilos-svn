
package wilos.test.hibernate.misc.wilosuser ;

import java.util.Set;

import junit.framework.TestCase;
import wilos.hibernate.misc.wilosuser.ProcessManagerDao;
import wilos.model.misc.wilosuser.ProcessManager;
import wilos.test.TestConfiguration;

/**
 * This class represents the test class for ProcessManagerDao class.
 * 
 * @author Marseyeah
 * 
 */
public class ProcessManagerDaoTest extends TestCase {

	private ProcessManagerDao pmd ;

	private ProcessManager pm ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.pmd = (ProcessManagerDao) TestConfiguration.getInstance().getApplicationContext().getBean("ProcessManagerDao") ;
		this.pm = new ProcessManager() ;
		this.pm.setLogin("testPM") ;
		this.pm.setName("Lopes") ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		this.pmd.deleteProcessManager(this.pm) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.ProcessManagerDao#saveOrUpdateProcessManager(wilos.model.misc.wilosuser.ProcessManager)}.
	 */
	public void testSaveOrUpdateProcessManager() {
		this.pmd.saveOrUpdateProcessManager(this.pm) ;

		ProcessManager pmTmp = this.pmd.getProcessManager("testPM") ;
		assertNotNull(pmTmp) ;
		assertTrue(this.pm.getLogin().equals(pmTmp.getLogin())) ;
		assertTrue(this.pm.getName().equals(pmTmp.getName())) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.ProcessManagerDao#getAllProcessManagers()}.
	 */
	public void testGetAllProcessManagers() {
		this.pmd.saveOrUpdateProcessManager(this.pm) ;

		Set<ProcessManager> setTmp = this.pmd.getAllProcessManagers() ;
		assertNotNull(setTmp) ;
		assertTrue(setTmp.size() >= 1) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.ProcessManagerDao#getProcessManager(java.lang.String)}.
	 */
	public void testGetProcessManager() {
		this.pmd.saveOrUpdateProcessManager(this.pm) ;
		ProcessManager pmTmp = this.pmd.getProcessManager("testPM") ;
		assertNotNull(pmTmp) ;
		assertEquals(pmTmp.getLogin(), "testPM") ;
		assertEquals(pmTmp.getName(), "Lopes") ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.ProcessManagerDao#deleteProcessManager(wilos.model.misc.wilosuser.ProcessManager)}.
	 */
	public void testDeleteProcessManager() {
		this.pmd.saveOrUpdateProcessManager(this.pm) ;
		this.pmd.deleteProcessManager(this.pm) ;

		ProcessManager pmTmp = this.pmd.getProcessManager("testPM") ;
		assertNull(pmTmp) ;
	}

}
