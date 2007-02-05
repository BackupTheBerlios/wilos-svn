package wilos.test.hibernate.misc.concretephase;



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
public class ConcretePhaseDaoTest extends TestCase{
	private ConcretePhaseDao concretePhaseDao = null ;

	private ConcretePhase concretePhase = null ;
	
	public static final String NAME = "thisPhase" ;

	public static final String DESCRIPTION = "Concretephase description" ;

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

		// Get the ConcretePhaseDao Singleton for managing ConcretePhase data
		this.concretePhaseDao = (ConcretePhaseDao) TestConfiguration.getInstance().getApplicationContext().getBean("ConcretePhaseDao") ;

		// Create empty Concretephase
		this.concretePhase = new ConcretePhase() ;

		// Save the activity with the method to test.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		this.concretePhaseDao.deleteConcretePhase(this.concretePhase) ;
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#saveOrUpdateProcess(woops2.model.process.Process)}.
	 */
	public final void testSaveOrUpdateConcretePhase() {
		//Rk: the setUp method is called here.

		//Save the activity with the method to test.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase) ;
		
		// Check the saving.
		String id = this.concretePhase.getId() ;
		ConcretePhase concretePhaseTmp = (ConcretePhase) this.concretePhaseDao.getConcretePhase(id) ;
		assertNotNull(concretePhaseTmp) ;
		
		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#getAllProcesses()}.
	 */
	public final void testGetAllConcretePhases() {
		//Rk: the setUp method is called here.
		
		//Save the activity with the method to test.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase) ;
		
		// Look if this activity is also into the database and look if the size of the set is >= 1.
		List<ConcretePhase> concretePhases = this.concretePhaseDao.getAllConcretePhases() ;
		assertNotNull(concretePhases) ;
		assertTrue(concretePhases.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#getProcess(java.lang.String)}.
	 */
	public final void testGetConcretePhase() {
		//Rk: the setUp method is called here.

		// Add properties to the activity.
		this.concretePhase.setName(NAME) ;
		this.concretePhase.setDescription(DESCRIPTION) ;
		this.concretePhase.setPrefix(PREFIX) ;
		this.concretePhase.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES) ;
		this.concretePhase.setIsEvenDriven(IS_EVEN_DRIVEN) ;
		this.concretePhase.setIsOngoing(IS_ON_GOING) ;
		this.concretePhase.setIsOptional(IS_OPTIONAL) ;
		this.concretePhase.setIsPlanned(IS_PLANNED) ;
		this.concretePhase.setIsRepeatable(IS_REPEATABLE) ;

		// Save the activity into the database.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase) ;
		String id = this.concretePhase.getId() ;

		// Test the method getActivity with an existing activity.
		ConcretePhase concretePhasesTmp = this.concretePhaseDao.getConcretePhase(id) ;
		assertNotNull(concretePhasesTmp) ;
		assertEquals("Name", concretePhasesTmp.getName(), NAME) ;
		assertEquals("Description", concretePhasesTmp.getDescription(), DESCRIPTION) ;
		assertEquals("Prefix", concretePhasesTmp.getPrefix(), PREFIX) ;
		assertEquals("HasMultipleOccurences", concretePhasesTmp.getHasMultipleOccurrences(), HAS_MULTIPLE_OCCURENCES) ;
		assertEquals("IsEvenDriven", concretePhasesTmp.getIsEvenDriven(), IS_EVEN_DRIVEN) ;
		assertEquals("IsOnGoing", concretePhasesTmp.getIsOngoing(), IS_ON_GOING) ;
		assertEquals("IsOptional", concretePhasesTmp.getIsOptional(), IS_OPTIONAL) ;
		assertEquals("IsPlanned", concretePhasesTmp.getIsPlanned(), IS_PLANNED) ;
		assertEquals("IsRepeatale", concretePhasesTmp.getIsRepeatable(), IS_REPEATABLE) ;

		// Test the method getActivity with an unexisting activity.
		this.concretePhaseDao.deleteConcretePhase(this.concretePhase) ;
		concretePhasesTmp = this.concretePhaseDao.getConcretePhase(id) ;
		assertNull(concretePhasesTmp) ;

		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#deleteProcess(woops2.model.process.Process)}.
	 */
	public final void testDeleteConcretePhase() {
		//Rk: the setUp method is called here.

		// Save the activity into the database.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase) ;
		String id = this.concretePhase.getId() ;

		// Test the method deleteActivity with an activity existing into the db.
		this.concretePhaseDao.deleteConcretePhase(this.concretePhase) ;

		// See if this.activity is now absent in the db.
		ConcretePhase concretePhaseTmp = (ConcretePhase) this.concretePhaseDao.getConcretePhase(id) ;
		assertNull(concretePhaseTmp) ;

		// Test the method deleteActivity with an activity unexisting into the db.
		// Normally here there are no exception thrown.
		this.concretPhaseDao.deleteConcretePhase(this.concretePhase) ;

		// Rk: the tearDown method is called here.
	}
}
