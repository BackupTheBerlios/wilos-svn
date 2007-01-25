package wilos.test.hibernate.spem2.breakdownelement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import wilos.hibernate.spem2.breakdownelement.BreakdownElementDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.test.TestConfiguration;

/**
 * Unit test for BreakdownElementDao
 * 
 * @author deder
 */
public class BreakdownElementDaoTest extends TestCase {
	
	private BreakdownElementDao breakdownElementDao = null;

	private BreakdownElement breakdownElement = null;

	public static final String NAME = "thisBde";

	public static final String DESCRIPTION = "bde description";

	public static final String PREFIX = "prefix";

	public static final Boolean IS_OPTIONAL = true;

	public static final Boolean HAS_MULTIPLE_OCCURENCES = true;

	public static final Boolean IS_PLANNED = true;

	public Set<Activity> superActivities = new HashSet<Activity>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// Get the BreakdownElementDao Singleton for managing BreakdownElement
		// data
		this.breakdownElementDao = (BreakdownElementDao) TestConfiguration.getInstance().getApplicationContext().getBean("BreakdownElementDao");

		// Create empty BreakdownElement
		this.breakdownElement = new BreakdownElement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		this.breakdownElementDao.deleteBreakdownElement(this.breakdownElement);
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.breakdownElement.BreakdownElementDao#saveOrUpdateBreakdownElement(wilos.model.spem2.breakdownElement.breakdownElement)}.
	 * 
	 */
	public void testSaveOrUpdateBreakdownElement() {
		// Rk: the setUp method is called here.

		// Save the activity with the method to test.
		this.breakdownElementDao
				.saveOrUpdateBreakdownElement(this.breakdownElement);

		// Check the saving.
		String id = this.breakdownElement.getId();
		BreakdownElement bdeTmp = (BreakdownElement) this.breakdownElementDao
				.getHibernateTemplate().load(BreakdownElement.class, id);
		assertNotNull(bdeTmp);
		
		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.breakdownelement.BreakdownElementDao#getAllBreakdownElements()}.
	 * 
	 */
	public void testGetAllBreakdownElements() {
		// Rk: the setUp method is called here.

		// Save the activity into the database.
		this.breakdownElementDao.getHibernateTemplate().saveOrUpdate(
				this.breakdownElement);

		// Look if this bde is also into the database and look if the size of
		// the set is >= 1.
		List<BreakdownElement> bdes = this.breakdownElementDao
				.getAllBreakdownElements();
		assertNotNull(bdes);
		assertTrue(bdes.size() >= 1);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.breakdownelement.BreakdownElementDao#getBreakdownElement()}.
	 * 
	 */
	public void testGetBreakdownElement() {
		// Rk: the setUp method is called here.

		// Add properties to the breakdownElement.
		this.breakdownElement.setName(NAME);
		this.breakdownElement.setDescription(DESCRIPTION);
		this.breakdownElement.setPrefix(PREFIX);
		this.breakdownElement
				.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES);
		this.breakdownElement.setIsOptional(IS_OPTIONAL);
		this.breakdownElement.setIsPlanned(IS_PLANNED);
		
		// Save the breakdownElement into the database.
		this.breakdownElementDao.getHibernateTemplate().saveOrUpdate(
				this.breakdownElement);
		String id = this.breakdownElement.getId();

		// Test the method getBreakdownElement with an existing
		// breakdownElement.
		BreakdownElement bdeTmp = this.breakdownElementDao
				.getBreakdownElement(id);
		assertNotNull(bdeTmp);
		assertEquals("Name", bdeTmp.getName(), NAME);
		assertEquals("Description", bdeTmp.getDescription(), DESCRIPTION);
		assertEquals("Prefix", bdeTmp.getPrefix(), PREFIX);
		assertEquals("HasMultipleOccurences", bdeTmp
				.getHasMultipleOccurrences(), HAS_MULTIPLE_OCCURENCES);
		assertEquals("IsOptional", bdeTmp.getIsOptional(), IS_OPTIONAL);
		assertEquals("IsPlanned", bdeTmp.getIsPlanned(), IS_PLANNED);
		
		// Test the method getBreakdownElement with an unexisting
		// breakdownElement.
		this.breakdownElementDao.getHibernateTemplate().delete(
				this.breakdownElement);
		bdeTmp = this.breakdownElementDao.getBreakdownElement(id);
		assertNull(bdeTmp);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.breakdownelement.BreakdownElementDao#deleteBreakdownElement()}.
	 * 
	 */
	public void testDeleteBreakdownElement() {
		// Rk: the setUp method is called here.

		// Save the BreakdownElement into the database.
		this.breakdownElementDao.getHibernateTemplate().saveOrUpdate(
				this.breakdownElement);
		String id = this.breakdownElement.getId();

		// Test the method deleteBreakdownElement with an BreakdownElement
		// existing into the db.
		this.breakdownElementDao.deleteBreakdownElement(this.breakdownElement);

		// See if this.breakdownElement is now absent in the db.
		BreakdownElement bdeTmp = (BreakdownElement) this.breakdownElementDao
				.getHibernateTemplate().get(BreakdownElement.class, id);
		assertNull(bdeTmp);

		// Test the method deleteBreakdownElement with an BreakdownElement
		// unexisting into the db.
		this.breakdownElementDao.deleteBreakdownElement(this.breakdownElement);

		// Rk: the tearDown method is called here.
	}
}