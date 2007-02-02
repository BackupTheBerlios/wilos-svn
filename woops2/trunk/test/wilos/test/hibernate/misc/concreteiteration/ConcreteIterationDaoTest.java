package wilos.test.hibernate.misc.concreteiteration;

import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.misc.concreteiteration.ConcreteIterationDao;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.test.TestConfiguration;

/**
 * 
 * @author Almiriad
 *
 */
public class ConcreteIterationDaoTest extends TestCase {
	private ConcreteIterationDao concreteIterationDao = (ConcreteIterationDao) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteIterationDao") ;

	private ConcreteIteration concreteIteration = (ConcreteIteration) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteIteration");
	
	public static final String CONCRETE_NAME = "thisConcreteIteration" ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the ConcreteIterationDao Singleton for managing ConcreteIteration data
		this.concreteIterationDao = (ConcreteIterationDao) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteIterationDao") ;

		// Create empty ConcreteIteration
		this.concreteIteration = new ConcreteIteration() ;

		// Save the ConcreteIteration with the method to test.
		this.concreteIterationDao.saveOrUpdateConcreteIteration(this.concreteIteration) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		this.concreteIterationDao.deleteConcreteIteration(this.concreteIteration) ;
	}

	/*
	 * Test method for {@link woops2.hibernate.ConcreteIteration.ConcreteIterationDao#saveOrUpdateProcess(woops2.model.process.Process)}.
	 */
	public final void testSaveOrUpdateConcreteIteration() {
		//Rk: the setUp method is called here.

		//Save the ConcreteIteration with the method to test.
		this.concreteIterationDao.saveOrUpdateConcreteIteration(this.concreteIteration) ;
		
		// Check the saving.
		String id = this.concreteIteration.getId() ;
		ConcreteIteration ConcreteIterationTmp = (ConcreteIteration) this.concreteIterationDao.getConcreteIteration(id) ;
		assertNotNull(ConcreteIterationTmp) ;
		
		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#getAllProcesses()}.
	 */
	public final void testGetAllConcreteIterations() {
		//Rk: the setUp method is called here.
		
		//Save the ConcreteIteration with the method to test.
		this.concreteIterationDao.saveOrUpdateConcreteIteration(this.concreteIteration) ;
		
		// Look if this ConcreteIteration is also into the database and look if the size of the set is >= 1.
		List<ConcreteIteration> ConcreteIterations = this.concreteIterationDao.getAllConcreteIterations() ;
		assertNotNull(ConcreteIterations) ;
		assertTrue(ConcreteIterations.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#getProcess(java.lang.String)}.
	 */
	public final void testGetConcreteIteration() {
		//Rk: the setUp method is called here.

		// Add properties to the ConcreteIteration.
		this.concreteIteration.setName(CONCRETE_NAME) ;
		
		// Save the ConcreteIteration into the database.
		this.concreteIterationDao.saveOrUpdateConcreteIteration(this.concreteIteration) ;
		String id = this.concreteIteration.getId() ;

		// Test the method getConcreteIteration with an existing ConcreteIteration.
		ConcreteIteration ConcreteIterationTmp = this.concreteIterationDao.getConcreteIteration(id) ;
		assertNotNull(ConcreteIterationTmp) ;
		assertEquals("Name", ConcreteIterationTmp.getName(), CONCRETE_NAME) ;
		
		// Test the method getConcreteIteration with an unexisting ConcreteIteration.
		this.concreteIterationDao.deleteConcreteIteration(this.concreteIteration) ;
		ConcreteIterationTmp = this.concreteIterationDao.getConcreteIteration(id) ;
		assertNull(ConcreteIterationTmp) ;

		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.process.ProcessDao#deleteProcess(woops2.model.process.Process)}.
	 */
	public final void testDeleteConcreteIteration() {
		//Rk: the setUp method is called here.

		// Save the ConcreteIteration into the database.
		this.concreteIterationDao.saveOrUpdateConcreteIteration(this.concreteIteration) ;
		String id = this.concreteIteration.getId() ;

		// Test the method deleteConcreteIteration with an ConcreteIteration existing into the db.
		this.concreteIterationDao.deleteConcreteIteration(this.concreteIteration) ;

		// See if this.ConcreteIteration is now absent in the db.
		ConcreteIteration concreteIterationTmp = (ConcreteIteration) this.concreteIterationDao.getConcreteIteration(id) ;
		assertNull(concreteIterationTmp) ;

		// Test the method deleteConcreteIteration with an ConcreteIteration unexisting into the db.
		// Normally here there are no exception thrown.
		this.concreteIterationDao.deleteConcreteIteration(this.concreteIteration) ;

		// Rk: the tearDown method is called here.
	}
}
