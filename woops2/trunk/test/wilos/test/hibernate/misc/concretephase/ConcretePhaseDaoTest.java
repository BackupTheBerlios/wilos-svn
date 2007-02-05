package wilos.test.hibernate.misc.concretephase;



import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.misc.concretephase.ConcretePhaseDao;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.test.TestConfiguration;

/**
 * 
 * @author Soosuske
 *
 */
public class ConcretePhaseDaoTest extends TestCase{
	private ConcretePhaseDao concretePhaseDao = null ;

	private ConcretePhase concretePhase = null ;
	
	public static final String CONCRETENAME = "thisPhase" ;


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
		this.concretePhase.setConcreteName(CONCRETENAME) ;
		

		// Save the activity into the database.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase) ;
		String id = this.concretePhase.getId() ;

		// Test the method getActivity with an existing activity.
		ConcretePhase concretePhasesTmp = this.concretePhaseDao.getConcretePhase(id) ;
		assertNotNull(concretePhasesTmp) ;
		assertEquals("Name", concretePhasesTmp.getConcreteName(), CONCRETENAME) ;
		

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
		this.concretePhaseDao.deleteConcretePhase(this.concretePhase) ;

		// Rk: the tearDown method is called here.
	}
}
