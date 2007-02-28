package wilos.test.hibernate.misc.concreteiteration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.concreteiteration.ConcreteIterationDao;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.test.TestConfiguration;

/**
 * 
 * @author Almiriad
 * 
 */
public class ConcreteIterationDaoTest {

	private ConcreteIterationDao concreteIterationDao = null;

	private ConcreteIteration concreteIteration;

	public static final String CONCRETE_NAME = "thisConcreteIteration";

	public ConcreteIterationDaoTest() {
		concreteIterationDao = (ConcreteIterationDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteIterationDao");
	}

	@Before
	public void setUp() {

		// Create empty ConcreteIteration
		this.concreteIteration = new ConcreteIteration();

		// Save the ConcreteIteration with the method to test.
		this.concreteIterationDao
				.saveOrUpdateConcreteIteration(this.concreteIteration);
	}

	@After
	public void tearDown() {

		this.concreteIterationDao
				.deleteConcreteIteration(this.concreteIteration);
	}

	@Test
	public final void testSaveOrUpdateConcreteIteration() {
		// Rk: the setUp method is called here.

		// Save the ConcreteIteration with the method to test.
		this.concreteIterationDao
				.saveOrUpdateConcreteIteration(this.concreteIteration);

		// Check the saving.
		String id = this.concreteIteration.getId();
		ConcreteIteration ConcreteIterationTmp = (ConcreteIteration) this.concreteIterationDao
				.getConcreteIteration(id);
		assertNotNull(ConcreteIterationTmp);

		// Delete the data stored in the database
		this.concreteIterationDao
				.deleteConcreteIteration(this.concreteIteration);
		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testGetAllConcreteIterations() {
		// Rk: the setUp method is called here.

		// Save the ConcreteIteration with the method to test.
		this.concreteIterationDao
				.saveOrUpdateConcreteIteration(this.concreteIteration);

		// Look if this ConcreteIteration is also into the database and look if
		// the size of the set is >= 1.
		List<ConcreteIteration> ConcreteIterations = this.concreteIterationDao
				.getAllConcreteIterations();
		assertNotNull(ConcreteIterations);
		assertTrue(ConcreteIterations.size() >= 1);

		// Delete the data stored in the database
		this.concreteIterationDao
				.deleteConcreteIteration(this.concreteIteration);
		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testGetConcreteIteration() {
		// Rk: the setUp method is called here.

		// Add properties to the ConcreteIteration.
		this.concreteIteration.setConcreteName(CONCRETE_NAME);

		// Save the ConcreteIteration into the database.
		this.concreteIterationDao
				.saveOrUpdateConcreteIteration(this.concreteIteration);
		String id = this.concreteIteration.getId();

		// Test the method getConcreteIteration with an existing
		// ConcreteIteration.
		ConcreteIteration concreteIterationTmp = this.concreteIterationDao
				.getConcreteIteration(id);
		assertNotNull(concreteIterationTmp);
		assertEquals("Name", concreteIterationTmp.getConcreteName(),
				CONCRETE_NAME);

		// Test the method getConcreteIteration with an unexisting
		// ConcreteIteration.
		this.concreteIterationDao
				.deleteConcreteIteration(this.concreteIteration);
		concreteIterationTmp = this.concreteIterationDao
				.getConcreteIteration(id);
		assertNull(concreteIterationTmp);

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testDeleteConcreteIteration() {
		// Rk: the setUp method is called here.

		// Save the ConcreteIteration into the database.
		this.concreteIterationDao
				.saveOrUpdateConcreteIteration(this.concreteIteration);
		String id = this.concreteIteration.getId();

		// Test the method deleteConcreteIteration with an ConcreteIteration
		// existing into the db.
		this.concreteIterationDao
				.deleteConcreteIteration(this.concreteIteration);

		// See if this.ConcreteIteration is now absent in the db.
		ConcreteIteration concreteIterationTmp = (ConcreteIteration) this.concreteIterationDao
				.getConcreteIteration(id);
		assertNull(concreteIterationTmp);

		// Test the method deleteConcreteIteration with an ConcreteIteration
		// unexisting into the db.
		// Normally here there are no exception thrown.
		this.concreteIterationDao
				.deleteConcreteIteration(this.concreteIteration);

		// Rk: the tearDown method is called here.
	}
}