package wilos.test.hibernate.spem2.phase;

import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.spem2.phase.PhaseDao;
import wilos.model.spem2.phase.Phase;
import wilos.test.TestConfiguration;

/**
 * 
 * @author Soosuske
 *
 */
public class PhaseDaoTest extends TestCase{
	private PhaseDao phaseDao = null ;

	private Phase phase = null ;
	
	public static final String NAME = "thisPhase" ;

	public static final String DESCRIPTION = "phase description" ;

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

		// Get the PhaseDao Singleton for managing Phase data
		this.phaseDao = (PhaseDao) TestConfiguration.getInstance().getApplicationContext().getBean("PhaseDao") ;

		// Create empty phase
		this.phase = new Phase() ;

		// Save the activity with the method to test.
		this.phaseDao.saveOrUpdatePhase(this.phase) ;
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
			this.phaseDao.deletePhase(this.phase) ;
		}
		catch(Exception exception){
			System.err.println("exception e ="+exception);
		}
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#saveOrUpdateProcess(woops2.model.process.Process)}.
	 */
	public final void testSaveOrUpdatePhase() {
		//Rk: the setUp method is called here.

		//Save the activity with the method to test.
		this.phaseDao.saveOrUpdatePhase(this.phase) ;
		
		// Check the saving.
		String id = this.phase.getId() ;
		Phase phaseTmp = (Phase) this.phaseDao.getPhase(id) ;
		assertNotNull(phaseTmp) ;
		
		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#getAllProcesses()}.
	 */
	public final void testGetAllPhases() {
		//Rk: the setUp method is called here.
		
		//Save the activity with the method to test.
		this.phaseDao.saveOrUpdatePhase(this.phase) ;
		
		// Look if this activity is also into the database and look if the size of the set is >= 1.
		List<Phase> phases = this.phaseDao.getAllPhases() ;
		assertNotNull(phases) ;
		assertTrue(phases.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#getProcess(java.lang.String)}.
	 */
	public final void testGetPhase() {
		//Rk: the setUp method is called here.

		// Add properties to the activity.
		this.phase.setName(NAME) ;
		this.phase.setDescription(DESCRIPTION) ;
		this.phase.setPrefix(PREFIX) ;
		this.phase.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES) ;
		this.phase.setIsEvenDriven(IS_EVEN_DRIVEN) ;
		this.phase.setIsOngoing(IS_ON_GOING) ;
		this.phase.setIsOptional(IS_OPTIONAL) ;
		this.phase.setIsPlanned(IS_PLANNED) ;
		this.phase.setIsRepeatable(IS_REPEATABLE) ;

		// Save the activity into the database.
		this.phaseDao.saveOrUpdatePhase(this.phase) ;
		String id = this.phase.getId() ;

		// Test the method getActivity with an existing activity.
		Phase phasesTmp = this.phaseDao.getPhase(id) ;
		assertNotNull(phasesTmp) ;
		assertEquals("Name", phasesTmp.getName(), NAME) ;
		assertEquals("Description", phasesTmp.getDescription(), DESCRIPTION) ;
		assertEquals("Prefix", phasesTmp.getPrefix(), PREFIX) ;
		assertEquals("HasMultipleOccurences", phasesTmp.getHasMultipleOccurrences(), HAS_MULTIPLE_OCCURENCES) ;
		assertEquals("IsEvenDriven", phasesTmp.getIsEvenDriven(), IS_EVEN_DRIVEN) ;
		assertEquals("IsOnGoing", phasesTmp.getIsOngoing(), IS_ON_GOING) ;
		assertEquals("IsOptional", phasesTmp.getIsOptional(), IS_OPTIONAL) ;
		assertEquals("IsPlanned", phasesTmp.getIsPlanned(), IS_PLANNED) ;
		assertEquals("IsRepeatale", phasesTmp.getIsRepeatable(), IS_REPEATABLE) ;

		// Test the method getActivity with an unexisting activity.
		this.phaseDao.deletePhase(this.phase) ;
		phasesTmp = this.phaseDao.getPhase(id) ;
		assertNull(phasesTmp) ;

		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#deleteProcess(woops2.model.process.Process)}.
	 */
	public final void testDeletePhase() {
		//Rk: the setUp method is called here.

		// Save the activity into the database.
		this.phaseDao.saveOrUpdatePhase(this.phase) ;
		String id = this.phase.getId() ;

		// Test the method deleteActivity with an activity existing into the db.
		this.phaseDao.deletePhase(this.phase) ;

		// See if this.activity is now absent in the db.
		Phase phaseTmp = (Phase) this.phaseDao.getPhase(id) ;
		assertNull(phaseTmp) ;

		// Test the method deleteActivity with an activity unexisting into the db.
		// Normally here there are no exception thrown.
		this.phaseDao.deletePhase(this.phase) ;

		// Rk: the tearDown method is called here.
	}
}
