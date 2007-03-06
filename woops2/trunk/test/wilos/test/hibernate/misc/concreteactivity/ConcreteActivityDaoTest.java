package wilos.test.hibernate.misc.concreteactivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.test.TestConfiguration;

/*
 * Unit test for ConcreteActivityDao
 *
 * @author deder
 * @author garwind
 */
public class ConcreteActivityDaoTest {

	private ConcreteActivityDao concreteactivityDao = null;
	
	private ConcreteActivity concreteactivity = null;

	public static final String NAME = "thisConcreteActivity";

	public static final String DESCRIPTION = "concrete activity description";

	public static final String PREFIX = "prefix";

	public static final Boolean IS_OPTIONAL = true;

	public static final Boolean HAS_MULTIPLE_OCCURENCES = true;

	public static final Boolean IS_EVEN_DRIVEN = true;

	public static final Boolean IS_ON_GOING = true;

	public static final Boolean IS_PLANNED = true;

	public static final Boolean IS_REPEATABLE = true;

	public ConcreteActivityDaoTest() {
		// Get the ConcreteActivityDao Singleton for managing Activity data
		this.concreteactivityDao = (ConcreteActivityDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteActivityDao");
	}

	@Before
	public void setUp() {

		// Create empty ConcreteActivity.
		this.concreteactivity = new ConcreteActivity();
	}

	@After
	public void tearDown() {

		this.concreteactivityDao.deleteConcreteActivity(this.concreteactivity);
	}

	@Test
	public void testSaveOrUpdateConcreteActivity() {
		// Rk: the setUp method is called here.

		// Save the concrete activity with the method to test.
		this.concreteactivityDao
				.saveOrUpdateConcreteActivity(this.concreteactivity);

		// Check the saving.
		String id = this.concreteactivity.getId();
		ConcreteActivity concreteactivityTmp = (ConcreteActivity) this.concreteactivityDao
				.getConcreteActivity(id);
		assertNotNull(concreteactivityTmp);

		// Delete the data stored in the database
		this.concreteactivityDao.deleteConcreteActivity(this.concreteactivity);
		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetAllConcreteActivities() {
		// Rk: the setUp method is called here.

		// Save the concrete activity with the method to test.
		this.concreteactivityDao
				.saveOrUpdateConcreteActivity(this.concreteactivity);

		// Look if this concrete activity is also into the database and look if
		// the size of the set is >= 1.
		List<ConcreteActivity> concreteactivities = this.concreteactivityDao
				.getAllConcreteActivities();
		assertNotNull(concreteactivities);
		assertTrue(concreteactivities.size() >= 1);

		// Delete the data stored in the database
		this.concreteactivityDao.deleteConcreteActivity(this.concreteactivity);
		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetConcreteActivity() {
		// Rk: the setUp method is called here.

		// Add properties to the concrete activity.
		this.concreteactivity.setConcreteName(NAME);

		// Save the concrete activity into the database.
		this.concreteactivityDao
				.saveOrUpdateConcreteActivity(this.concreteactivity);
		String id = this.concreteactivity.getId();

		// Test the method getConcreteActivity with an existing activity.
		ConcreteActivity concreteactivityTmp = this.concreteactivityDao
				.getConcreteActivity(id);
		assertNotNull(concreteactivityTmp);
		assertEquals("Name", concreteactivityTmp.getConcreteName(), NAME);

		// Test the method getConcreteActivity with an unexisting activity.
		this.concreteactivityDao.deleteConcreteActivity(this.concreteactivity);
		concreteactivityTmp = this.concreteactivityDao.getConcreteActivity(id);
		assertNull(concreteactivityTmp);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testDeleteConcreteActivity() {
		// Rk: the setUp method is called here.

		// Save the concreteactivity into the database.
		this.concreteactivityDao
				.saveOrUpdateConcreteActivity(this.concreteactivity);
		String id = this.concreteactivity.getId();

		// Test the method deleteConcreteActivity with an activity existing into
		// the db.
		this.concreteactivityDao.deleteConcreteActivity(this.concreteactivity);

		// See if this.concreteactivity is now absent in the db.
		ConcreteActivity concreteactivityTmp = (ConcreteActivity) this.concreteactivityDao
				.getConcreteActivity(id);
		assertNull(concreteactivityTmp);

		// Test the method deleteConcreteActivity with an activity unexisting
		// into the db.
		// Normally here there are no exception thrown.
		this.concreteactivityDao.deleteConcreteActivity(this.concreteactivity);

		// Rk: the tearDown method is called here.
	}
}
