package woops2.test.business.process;

import java.util.List;

import junit.framework.TestCase;
import woops2.business.process.ProcessService;
import woops2.model.process.Process;
import woops2.test.TestConfiguration;

/**
 * @author Administrateur
 *
 * This class represents ... TODO
 *
 */
public class ProcessServiceTest extends TestCase {
	
	private ProcessService processService ;

	private Process process ;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		
//		 Get the ActivityDao Singleton for managing Activity data
		this.processService = (ProcessService) TestConfiguration.getInstance().getApplicationContext().getBean("ProcessService") ;

		// Create empty Activity
		this.process = new Process() ;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link woops2.business.process.ProcessService#getProcessesList()}.
	 */
	public final void testGetProcessesList() {
		fail("Not yet implemented") ; // TODO
	}

	/**
	 * Test method for {@link woops2.business.process.ProcessService#saveActivity(woops2.model.process.Process)}.
	 */
	public final void testSaveProcess() {
//		 Rk: the setUp method is called here.

		// Save the activity.
		this.processService.saveProcess(this.process) ;

		// Look if this activity is also into the database and look if the size of the set is >= 1.
		List<Process> processes = this.processService.getProcessesList() ;
		assertNotNull(processes) ;
		assertTrue(processes.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link woops2.business.process.ProcessService#getprocessDao()}.
	 */
	public final void testGetprocessDao() {
		fail("Not yet implemented") ; // TODO
	}

	/**
	 * Test method for {@link woops2.business.process.ProcessService#setProcessDao(woops2.hibernate.process.ProcessDao)}.
	 */
	public final void testSetProcessDao() {
		fail("Not yet implemented") ; // TODO
	}

}
