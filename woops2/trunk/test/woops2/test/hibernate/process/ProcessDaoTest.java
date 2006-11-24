package woops2.test.hibernate.process;

import junit.framework.TestCase;
import woops2.hibernate.process.ProcessDao;
import woops2.model.activity.Activity;
import woops2.model.process.Process;
import woops2.test.TestConfiguration;

/**
 * @author Administrateur
 *
 * This class represents ... TODO
 *
 */
public class ProcessDaoTest extends TestCase {
	
	private ProcessDao processDao = null ;

	private Process process = null ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the ActivityDao Singleton for managing Activity data
		this.processDao = (ProcessDao) TestConfiguration.getInstance().getApplicationContext().getBean("ProcessDao") ;

		// Create empty Activity
		this.process = new Process() ;

		// Save the activity with the method to test.
		this.processDao.saveOrUpdateProcess(this.process) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		// Delete the tmp activity from the database.
		try{
			this.processDao.deleteProcess(this.process) ;
		}
		catch(Exception exception){
			System.err.println("exception e ="+exception);
		}
	}

	/**
	 * Test method for {@link woops2.hibernate.process.ProcessDao#saveOrUpdateProcess(woops2.model.process.Process)}.
	 */
	public final void testSaveOrUpdateProcess() {
		//Rk: the setUp method is called here.

		//Save the activity with the method to test.
		this.processDao.saveOrUpdateProcess(this.process) ;
		
		// Check the saving.
		String id = this.process.getId() ;
		Activity activityTmp = (Activity) this.processDao.getProcess(id) ;
		assertNotNull(activityTmp) ;
		
		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link woops2.hibernate.process.ProcessDao#getAllProcesses()}.
	 */
	public final void testGetAllProcesses() {
		fail("Not yet implemented") ; // TODO
	}

	/**
	 * Test method for {@link woops2.hibernate.process.ProcessDao#getProcess(java.lang.String)}.
	 */
	public final void testGetProcess() {
		fail("Not yet implemented") ; // TODO
	}

	/**
	 * Test method for {@link woops2.hibernate.process.ProcessDao#deleteProcess(woops2.model.process.Process)}.
	 */
	public final void testDeleteProcess() {
		fail("Not yet implemented") ; // TODO
	}

}
