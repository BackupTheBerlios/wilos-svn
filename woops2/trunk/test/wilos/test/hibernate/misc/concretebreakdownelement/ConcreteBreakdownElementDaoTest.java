package wilos.test.hibernate.misc.concretebreakdownelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.activity.Activity;
import wilos.test.TestConfiguration;

/**
 * Unit test for ConcreteConcreteBreakdownElementDao
 * 
 * @author nanawel
 */
public class ConcreteBreakdownElementDaoTest {

	private ConcreteBreakdownElementDao concreteBreakdownElementDao = null;

	private ConcreteBreakdownElement concreteBreakdownElement = null;

	public static final String CONCRETE_NAME = "thisCbde";

	public Set<Activity> superActivities = new HashSet<Activity>();

	public ConcreteBreakdownElementDaoTest() {
		// Gets the ConcreteBreakdownElementDao Singleton for managing
		// ConcreteBreakdownElement
		// data
		this.concreteBreakdownElementDao = (ConcreteBreakdownElementDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteBreakdownElementDao");
	}

	@Before
	public void setUp() {

		// Creates empty BreakdownElement
		this.concreteBreakdownElement = new ConcreteBreakdownElement();
	}

	@After
	public void tearDown() {

		this.concreteBreakdownElementDao
				.deleteConcreteBreakdownElement(this.concreteBreakdownElement);
	}

	@Test
	public void testSaveOrUpdateConcreteBreakdownElement() {
		// Rk: the setUp method is called here.

		// Saves the activity with the method to test.
		this.concreteBreakdownElementDao
				.saveOrUpdateConcreteBreakdownElement(this.concreteBreakdownElement);

		// Checks the saving.
		String id = this.concreteBreakdownElement.getId();
		ConcreteBreakdownElement cbdeTmp = (ConcreteBreakdownElement) this.concreteBreakdownElementDao
				.getHibernateTemplate()
				.load(ConcreteBreakdownElement.class, id);
		assertNotNull(cbdeTmp);

		// Delete the data stored in the database
		this.concreteBreakdownElementDao
				.deleteConcreteBreakdownElement(this.concreteBreakdownElement);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetAllConcreteBreakdownElements() {
		// Rk: the setUp method is called here.

		// Saves the activity into the database.
		this.concreteBreakdownElementDao
				.saveOrUpdateConcreteBreakdownElement(this.concreteBreakdownElement);

		// Looks if this bde is also into the database and looks if the size of
		// the set is >= 1.
		List<ConcreteBreakdownElement> cbdes = this.concreteBreakdownElementDao
				.getAllConcreteBreakdownElements();
		assertNotNull(cbdes);
		assertTrue(cbdes.size() >= 1);

		// Delete the data stored in the database
		this.concreteBreakdownElementDao
				.deleteConcreteBreakdownElement(this.concreteBreakdownElement);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetBreakdownElement() {
		// Rk: the setUp method is called here.

		// Adds properties to the concreteBreakdownElement.
		this.concreteBreakdownElement.setConcreteName(CONCRETE_NAME);

		// Saves the concreteBreakdownElement into the database.
		this.concreteBreakdownElementDao
				.saveOrUpdateConcreteBreakdownElement(this.concreteBreakdownElement);
		String id = this.concreteBreakdownElement.getId();

		// Tests the method getBreakdownElement with an existing
		// concreteBreakdownElement.
		ConcreteBreakdownElement cbdeTmp = this.concreteBreakdownElementDao
				.getConcreteBreakdownElement(id);
		assertNotNull(cbdeTmp);
		assertEquals("Name", cbdeTmp.getConcreteName(), CONCRETE_NAME);

		// Tests the method getConcreteBreakdownElement with an unexisting
		// concreteBreakdownElement.
		this.concreteBreakdownElementDao
				.deleteConcreteBreakdownElement(this.concreteBreakdownElement);
		cbdeTmp = this.concreteBreakdownElementDao
				.getConcreteBreakdownElement(id);
		assertNull(cbdeTmp);

		// Delete the data stored in the database
		this.concreteBreakdownElementDao
				.deleteConcreteBreakdownElement(this.concreteBreakdownElement);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testDeleteConcreteBreakdownElement() {
		// Rk: the setUp method is called here.

		// Saves the ConcreteBreakdownElement into the database.
		this.concreteBreakdownElementDao
				.saveOrUpdateConcreteBreakdownElement(this.concreteBreakdownElement);
		String id = this.concreteBreakdownElement.getId();

		// Tests the method deleteConcreteBreakdownElement with an
		// ConcreteBreakdownElement
		// existing into the db.
		this.concreteBreakdownElementDao
				.deleteConcreteBreakdownElement(this.concreteBreakdownElement);

		// Looks if this.concreteBreakdownElement is now absent in the db.
		ConcreteBreakdownElement cbdeTmp = (ConcreteBreakdownElement) this.concreteBreakdownElementDao
				.getConcreteBreakdownElement(id);
		assertNull(cbdeTmp);

		// Tests the method deleteConcreteBreakdownElement with a nonexistent
		// ConcreteBreakdownElement into the db.
		this.concreteBreakdownElementDao
				.deleteConcreteBreakdownElement(this.concreteBreakdownElement);

		// Rk: the tearDown method is called here.
	}
}
