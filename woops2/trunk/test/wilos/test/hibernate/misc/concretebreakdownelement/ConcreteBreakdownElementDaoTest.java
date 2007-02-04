package wilos.test.hibernate.misc.concretebreakdownelement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.activity.Activity;
import wilos.test.TestConfiguration;

/**
 * Unit test for ConcreteConcreteBreakdownElementDao
 *
 * @author nanawel
 */
public class ConcreteBreakdownElementDaoTest extends TestCase {

	private ConcreteBreakdownElementDao concreteBreakdownElementDao = null;

	private ConcreteBreakdownElement concreteBreakdownElement = null;

	public static final String CONCRETE_NAME = "thisCbde";

	public Set<Activity> superActivities = new HashSet<Activity>();

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// Gets the ConcreteBreakdownElementDao Singleton for managing ConcreteBreakdownElement
		// data
		this.concreteBreakdownElementDao = (ConcreteBreakdownElementDao) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteBreakdownElementDao");

		// Creates empty BreakdownElement
		this.concreteBreakdownElement = new ConcreteBreakdownElement();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		this.concreteBreakdownElementDao.deleteConcreteBreakdownElement(this.concreteBreakdownElement);
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.concretebreakdownElement.ConcreteBreakdownElementDao#saveOrUpdateConcreteBreakdownElement(wilos.model.misc.concretebreakdownElement.concreteBreakdownElement)}.
	 *
	 */
	public void testSaveOrUpdateConcreteBreakdownElement() {
		// Rk: the setUp method is called here.

		// Saves the activity with the method to test.
		this.concreteBreakdownElementDao
				.saveOrUpdateConcreteBreakdownElement(this.concreteBreakdownElement);

		// Checks the saving.
		String id = this.concreteBreakdownElement.getId();
		ConcreteBreakdownElement cbdeTmp = (ConcreteBreakdownElement) this.concreteBreakdownElementDao
				.getHibernateTemplate().load(ConcreteBreakdownElement.class, id);
		assertNotNull(cbdeTmp);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao#getAllConcreteBreakdownElements()}.
	 *
	 */
	public void testGetAllConcreteBreakdownElements() {
		// Rk: the setUp method is called here.

		// Saves the activity into the database.
		this.concreteBreakdownElementDao.getHibernateTemplate().saveOrUpdate(
				this.concreteBreakdownElement);

		// Looks if this bde is also into the database and looks if the size of
		// the set is >= 1.
		List<ConcreteBreakdownElement> cbdes = this.concreteBreakdownElementDao
				.getAllConcreteBreakdownElements();
		assertNotNull(cbdes);
		assertTrue(cbdes.size() >= 1);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.breakdownelement.ConcreteBreakdownElementDao#getBreakdownElement()}.
	 *
	 */
	public void testGetBreakdownElement() {
		// Rk: the setUp method is called here.

		// Adds properties to the concreteBreakdownElement.
		this.concreteBreakdownElement.setConcreteName(CONCRETE_NAME);

		// Saves the concreteBreakdownElement into the database.
		this.concreteBreakdownElementDao.saveOrUpdateConcreteBreakdownElement(
				this.concreteBreakdownElement);
		String id = this.concreteBreakdownElement.getId();

		// Tests the method getBreakdownElement with an existing
		// concreteBreakdownElement.
		ConcreteBreakdownElement cbdeTmp = this.concreteBreakdownElementDao
				.getConcreteBreakdownElement(id);
		assertNotNull(cbdeTmp);
		assertEquals("Name", cbdeTmp.getConcreteName(), CONCRETE_NAME);

		// Tests the method getConcreteBreakdownElement with an unexisting
		// concreteBreakdownElement.
		this.concreteBreakdownElementDao.getHibernateTemplate().delete(
				this.concreteBreakdownElement);
		cbdeTmp = this.concreteBreakdownElementDao.getConcreteBreakdownElement(id);
		assertNull(cbdeTmp);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao#deleteConcreteBreakdownElement()}.
	 *
	 */
	public void testDeleteConcreteBreakdownElement() {
		// Rk: the setUp method is called here.

		// Saves the ConcreteBreakdownElement into the database.
		this.concreteBreakdownElementDao.getHibernateTemplate().saveOrUpdate(
				this.concreteBreakdownElement);
		String id = this.concreteBreakdownElement.getId();

		// Tests the method deleteConcreteBreakdownElement with an ConcreteBreakdownElement
		// existing into the db.
		this.concreteBreakdownElementDao.deleteConcreteBreakdownElement(this.concreteBreakdownElement);

		// Looks if this.concreteBreakdownElement is now absent in the db.
		ConcreteBreakdownElement cbdeTmp = (ConcreteBreakdownElement) this.concreteBreakdownElementDao
				.getHibernateTemplate().get(ConcreteBreakdownElement.class, id);
		assertNull(cbdeTmp);

		// Tests the method deleteConcreteBreakdownElement with a nonexistent
		// ConcreteBreakdownElement into the db.
		this.concreteBreakdownElementDao.deleteConcreteBreakdownElement(this.concreteBreakdownElement);

		// Rk: the tearDown method is called here.
	}
}
