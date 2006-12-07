package wilos.test.hibernate.spem2.process;

import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.spem2.process.ProcessDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.process.Process;
import wilos.test.TestConfiguration;

/**
 * @author deder
 *
 */
public class ProcessDaoTest extends TestCase {
	
	private ProcessDao processDao = null ;

	private Process process = null ;
	
	public static final String NAME = "thisProcess" ;

	public static final String DESCRIPTION = "process description" ;

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	public static final Boolean HAS_MULTIPLE_OCCURENCES = true ;

	public static final Boolean IS_EVEN_DRIVEN = true ;

	public static final Boolean IS_ON_GOING = true ;

	public static final Boolean IS_PLANNED = true ;

	public static final Boolean IS_REPEATABLE = true ;

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

	/*
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

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#getAllProcesses()}.
	 */
	public final void testGetAllProcesses() {
		//Rk: the setUp method is called here.
		
		//Save the activity with the method to test.
		this.processDao.saveOrUpdateProcess(this.process) ;
		
		// Look if this activity is also into the database and look if the size of the set is >= 1.
		List<Process> processes = this.processDao.getAllProcesses() ;
		assertNotNull(processes) ;
		assertTrue(processes.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#getProcess(java.lang.String)}.
	 */
	public final void testGetProcess() {
		//Rk: the setUp method is called here.

		// Add properties to the activity.
		this.process.setName(NAME) ;
		this.process.setDescription(DESCRIPTION) ;
		this.process.setPrefix(PREFIX) ;
		this.process.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES) ;
		this.process.setIsEvenDriven(IS_EVEN_DRIVEN) ;
		this.process.setIsOngoing(IS_ON_GOING) ;
		this.process.setIsOptional(IS_OPTIONAL) ;
		this.process.setIsPlanned(IS_PLANNED) ;
		this.process.setIsRepeatable(IS_REPEATABLE) ;

		// Save the activity into the database.
		this.processDao.saveOrUpdateProcess(this.process) ;
		String id = this.process.getId() ;

		// Test the method getActivity with an existing activity.
		Process processTmp = this.processDao.getProcess(id) ;
		assertNotNull(processTmp) ;
		assertEquals("Name", processTmp.getName(), NAME) ;
		assertEquals("Description", processTmp.getDescription(), DESCRIPTION) ;
		assertEquals("Prefix", processTmp.getPrefix(), PREFIX) ;
		assertEquals("HasMultipleOccurences", processTmp.getHasMultipleOccurrences(), HAS_MULTIPLE_OCCURENCES) ;
		assertEquals("IsEvenDriven", processTmp.getIsEvenDriven(), IS_EVEN_DRIVEN) ;
		assertEquals("IsOnGoing", processTmp.getIsOngoing(), IS_ON_GOING) ;
		assertEquals("IsOptional", processTmp.getIsOptional(), IS_OPTIONAL) ;
		assertEquals("IsPlanned", processTmp.getIsPlanned(), IS_PLANNED) ;
		assertEquals("IsRepeatale", processTmp.getIsRepeatable(), IS_REPEATABLE) ;

		// Test the method getActivity with an unexisting activity.
		this.processDao.deleteProcess(this.process) ;
		processTmp = this.processDao.getProcess(id) ;
		assertNull(processTmp) ;

		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#deleteProcess(woops2.model.process.Process)}.
	 */
	public final void testDeleteProcess() {
		//Rk: the setUp method is called here.

		// Save the activity into the database.
		this.processDao.saveOrUpdateProcess(this.process) ;
		String id = this.process.getId() ;

		// Test the method deleteActivity with an activity existing into the db.
		this.processDao.deleteProcess(this.process) ;

		// See if this.activity is now absent in the db.
		Activity activityTmp = (Activity) this.processDao.getProcess(id) ;
		assertNull(activityTmp) ;

		// Test the method deleteActivity with an activity unexisting into the db.
		// Normally here there are no exception thrown.
		this.processDao.deleteProcess(this.process) ;

		// Rk: the tearDown method is called here.
	}

}
