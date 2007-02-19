
package wilos.test.hibernate.spem2.workbreakdownelement ;

import java.util.List ;

import junit.framework.TestCase ;
import wilos.hibernate.spem2.workbreakdownelement.WorkBreakdownElementDao ;
import wilos.model.spem2.breakdownelement.BreakdownElement ;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement ;
import wilos.model.spem2.workbreakdownelement.WorkOrder;
import wilos.test.TestConfiguration ;

/**
 * @author Sebastien
 * 
 * Unit test for WorkBreakdownElementDao
 * 
 */
public class WorkBreakdownElementDaoTest extends TestCase {

	private WorkBreakdownElementDao workBreakdownElementDao = null ;

	private WorkBreakdownElement workBreakdownElement = null ;

	public static final String NAME = "thisWBdE" ;

	public static final String DESCRIPTION = "wbde description" ;

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	public static final Boolean HAS_MULTIPLE_OCCURENCES = true ;

	public static final Boolean IS_PLANNED = true ;

	public static final Boolean IS_REPEATABLE = true ;

	public static final Boolean IS_ONGOING = true ;

	public static final Boolean IS_EVEN_DRIVEN = true ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the BreakdownElementDao Singleton for managing BreakdownElement
		// data
		this.workBreakdownElementDao = (WorkBreakdownElementDao) TestConfiguration.getInstance().getApplicationContext().getBean("WorkBreakdownElementDao") ;

		// Create empty WorkBreakdownElement
		this.workBreakdownElement = new WorkBreakdownElement() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		this.workBreakdownElementDao.deleteWorkBreakdownElement(this.workBreakdownElement) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.workbreakdownElement.WorkBreakdownElementDao#saveOrUpdateWorkBreakdownElement(wilos.model.spem2.workbreakdownElement.workBreakdownElement)}.
	 * 
	 */
	public void testSaveOrUpdateWorkBreakdownElement() {
		// Rk: the setUp method is called here.
		
		WorkOrder workOrder = new WorkOrder();
		workOrder.setLinkType("link type");		
		this.workBreakdownElement.addPredecessor(workOrder);
		
		WorkBreakdownElement wbde = new WorkBreakdownElement();
		wbde.setName(NAME);		
		wbde.addSuccessor(workOrder);

		// Save the workbreakdownElement with the method to test.
		this.workBreakdownElementDao.saveOrUpdateWorkBreakdownElement(this.workBreakdownElement) ;
		this.workBreakdownElementDao.saveOrUpdateWorkBreakdownElement(wbde);
		
		// Check the saving.
		String id = this.workBreakdownElement.getId() ;
		String id2 = wbde.getId();
		
		WorkBreakdownElement wbdeTmp = (WorkBreakdownElement) this.workBreakdownElementDao.getHibernateTemplate().load(WorkBreakdownElement.class, id) ;
		WorkBreakdownElement wbdeTmp2 = (WorkBreakdownElement) this.workBreakdownElementDao.getHibernateTemplate().load(WorkBreakdownElement.class, id2) ;
		
		assertNotNull(wbdeTmp) ;
		assertNotNull(wbdeTmp2);
		
		assertTrue(this.workBreakdownElement.getPredecessors().size() == 1);
		assertTrue(wbde.getSuccessors().size() == 1);
		assertTrue(this.workBreakdownElement.getPredecessors().contains(workOrder));
		assertTrue(wbde.getSuccessors().contains(workOrder));
		
		this.workBreakdownElementDao.deleteWorkBreakdownElement(wbde);
		
		
		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.workbreakdownelement.WorkBreakdownElementDao#getAllWorkBreakdownElements()}.
	 * 
	 */
	public void testGetAllWorkBreakdownElements() {
		// Rk: the setUp method is called here.

		// Save the workBreakdownElement into the database.
		this.workBreakdownElementDao.saveOrUpdateWorkBreakdownElement(this.workBreakdownElement) ;

		// Look if this bde is also into the database and look if the size of
		// the set is >= 1.
		List<WorkBreakdownElement> wbdes = this.workBreakdownElementDao.getAllWorkBreakdownElements() ;
		assertNotNull(wbdes) ;
		assertTrue(wbdes.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.workbreakdownelement.WorkBreakdownElementDao#getWorkBreakdownElement()}.
	 * 
	 */
	public void testGetWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		// Add properties to the workBreakdownElement.
		this.workBreakdownElement.setName(NAME) ;
		this.workBreakdownElement.setDescription(DESCRIPTION) ;
		this.workBreakdownElement.setPrefix(PREFIX) ;
		this.workBreakdownElement.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES) ;
		this.workBreakdownElement.setIsOptional(IS_OPTIONAL) ;
		this.workBreakdownElement.setIsPlanned(IS_PLANNED) ;
		this.workBreakdownElement.setIsRepeatable(IS_REPEATABLE) ;
		this.workBreakdownElement.setIsOngoing(IS_ONGOING) ;
		this.workBreakdownElement.setIsEvenDriven(IS_EVEN_DRIVEN) ;

		// Save the workBreakdownElement into the database.
		this.workBreakdownElementDao.saveOrUpdateWorkBreakdownElement(this.workBreakdownElement) ;
		String id = this.workBreakdownElement.getId() ;

		// Test the method getWorkBreakdownElement with an existing
		// workBreakdownElement.
		WorkBreakdownElement wbdeTmp = this.workBreakdownElementDao.getWorkBreakdownElement(id) ;
		assertNotNull(wbdeTmp) ;
		assertEquals("Name", wbdeTmp.getName(), NAME) ;
		assertEquals("Description", wbdeTmp.getDescription(), DESCRIPTION) ;
		assertEquals("Prefix", wbdeTmp.getPrefix(), PREFIX) ;
		assertEquals("HasMultipleOccurences", wbdeTmp.getHasMultipleOccurrences(), HAS_MULTIPLE_OCCURENCES) ;
		assertEquals("IsOptional", wbdeTmp.getIsOptional(), IS_OPTIONAL) ;
		assertEquals("IsPlanned", wbdeTmp.getIsPlanned(), IS_PLANNED) ;
		assertEquals("IsRepeatable", wbdeTmp.getIsRepeatable(), IS_REPEATABLE) ;
		assertEquals("IsOngoing", wbdeTmp.getIsOngoing(), IS_ONGOING) ;
		assertEquals("IsEvenDriven", wbdeTmp.getIsEvenDriven(), IS_EVEN_DRIVEN) ;

		// Test the method getWorkBreakdownElement with an unexisting
		// workBreakdownElement.
		this.workBreakdownElementDao.deleteWorkBreakdownElement(this.workBreakdownElement);
		wbdeTmp = this.workBreakdownElementDao.getWorkBreakdownElement(id) ;
		assertNull(wbdeTmp) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.workbreakdownelement.WorkBreakdownElementDao#deleteWorkBreakdownElement()}.
	 * 
	 */
	public void testDeleteWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		// Save the BreakdownElement into the database.
		this.workBreakdownElementDao.saveOrUpdateWorkBreakdownElement(this.workBreakdownElement) ;
		String id = this.workBreakdownElement.getId() ;

		// Test the method deleteBreakdownElement with an BreakdownElement
		// existing into the db.
		this.workBreakdownElementDao.deleteWorkBreakdownElement(this.workBreakdownElement) ;

		// See if this.breakdownElement is now absent in the db.
		BreakdownElement wbdeTmp = (BreakdownElement) this.workBreakdownElementDao.getHibernateTemplate().get(WorkBreakdownElement.class, id) ;
		assertNull(wbdeTmp) ;

		// Test the method deleteBreakdownElement with an BreakdownElement
		// unexisting into the db.
		// Normally here there are no exception thrown.
		this.workBreakdownElementDao.deleteWorkBreakdownElement(this.workBreakdownElement) ;

		// Rk: the tearDown method is called here.
	}

}
