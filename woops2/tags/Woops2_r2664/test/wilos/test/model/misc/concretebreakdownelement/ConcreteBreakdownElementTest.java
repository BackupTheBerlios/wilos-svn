package wilos.test.model.misc.concretebreakdownelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.breakdownelement.BreakdownElement;

public class ConcreteBreakdownElementTest {

	private ConcreteBreakdownElement concreteBreakdownElement;

	private ConcreteActivity concreteActivity;

	private BreakdownElement breakdownElement;

	private static final String CONCRETE_NAME = "Concrete name";

	@Before
	public void setUp() {
		this.concreteActivity = new ConcreteActivity();
		this.concreteActivity.setConcreteName("concreteactivityname");

		this.breakdownElement = new BreakdownElement();
		this.breakdownElement.setName("name");

		this.concreteBreakdownElement = new ConcreteBreakdownElement();
		this.concreteBreakdownElement.setConcreteName(CONCRETE_NAME);
		this.concreteBreakdownElement
				.addSuperConcreteActivity(this.concreteActivity);
		this.concreteBreakdownElement
				.addBreakdownElement(this.breakdownElement);
	}

	@After
	public void tearDown() {
		// None
	}

	@Test
	public void testClone() {
		try {
			assertEquals(this.concreteBreakdownElement,
					this.concreteBreakdownElement.clone());
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testClone method");
		}
	}

	@Test
	public void testHashCode() {

		ConcreteBreakdownElement cbe = new ConcreteBreakdownElement();
		cbe.setConcreteName(CONCRETE_NAME);
		cbe.addBreakdownElement(this.breakdownElement);
		cbe.addSuperConcreteActivity(this.concreteActivity);

		assertNotNull(this.concreteBreakdownElement.hashCode());
		assertNotNull(cbe.hashCode());
		assertEquals(this.concreteBreakdownElement.hashCode(), cbe.hashCode());
	}

	@Test
	public void testEqualsObject() {

		ConcreteBreakdownElement cbe = new ConcreteBreakdownElement();
		cbe.setConcreteName(CONCRETE_NAME);
		cbe.addBreakdownElement(this.breakdownElement);
		cbe.addSuperConcreteActivity(this.concreteActivity);

		assertTrue(this.concreteBreakdownElement.equals(cbe));

		ConcreteBreakdownElement cbe2 = new ConcreteBreakdownElement();
		cbe.setConcreteName("Another concrete name");
		cbe.addBreakdownElement(new BreakdownElement());
		cbe.addSuperConcreteActivity(new ConcreteActivity());

		assertFalse(this.concreteBreakdownElement.equals(cbe2));
	}

	@Test
	public void testAddSuperConcreteActivity() {
		// Rk: the setUp method is called here.
		this.concreteBreakdownElement
				.removeSuperConcreteActivity(this.concreteActivity);
		this.concreteActivity.removeAllConcreteBreakdownElements();
		this.concreteBreakdownElement
				.addSuperConcreteActivity(this.concreteActivity);
		assertNotNull(this.concreteBreakdownElement
				.getSuperConcreteActivities());
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.size() == 1);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.contains(this.concreteActivity));

		assertNotNull(this.concreteActivity.getConcreteBreakdownElements());
		assertTrue(this.concreteActivity.getConcreteBreakdownElements().size() == 1);
		assertTrue(this.concreteActivity.getConcreteBreakdownElements()
				.contains(this.concreteBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testRemoveSuperConcreteActivity() {
		// Rk: the setUp method is called here.

		this.concreteBreakdownElement
				.removeSuperConcreteActivity(this.concreteActivity);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.size() == 0);
		assertFalse(this.concreteActivity.getConcreteBreakdownElements()
				.contains(this.concreteBreakdownElement));
		assertTrue(this.concreteActivity.getConcreteBreakdownElements().size() == 1);

		ConcreteActivity superConcreteActivity = new ConcreteActivity();
		superConcreteActivity.setConcreteName("Name");

		assertNotNull(this.concreteBreakdownElement
				.getSuperConcreteActivities());
		assertNotNull(superConcreteActivity.getConcreteBreakdownElements());

		this.concreteBreakdownElement
				.addSuperConcreteActivity(superConcreteActivity);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.contains(superConcreteActivity));
		this.concreteBreakdownElement
				.removeSuperConcreteActivity(superConcreteActivity);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.size() == 0);

		assertNotNull(superConcreteActivity.getConcreteBreakdownElements());

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testAddAllSuperActivities() {

		// Rk: the setUp method is called here.

		this.concreteBreakdownElement
				.removeSuperConcreteActivity(this.concreteActivity);
		assertEquals(0, this.concreteBreakdownElement
				.getSuperConcreteActivities().size());

		ConcreteActivity cAct = new ConcreteActivity();
		cAct.setConcreteName("a name");

		ConcreteActivity cAct2 = new ConcreteActivity();
		cAct2.setConcreteName("another name");

		Set<ConcreteActivity> list = new HashSet<ConcreteActivity>();
		list.add(cAct);
		list.add(cAct2);
		this.concreteBreakdownElement.addAllSuperActivities(list);

		this.concreteBreakdownElement
				.addBreakdownElement(this.breakdownElement);

		assertNotNull(this.concreteBreakdownElement
				.getSuperConcreteActivities());
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.size() == 2);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.contains(cAct));
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.contains(cAct2));

		assertTrue(this.concreteActivity.getConcreteBreakdownElements().size() == 1);
		assertTrue(cAct.getConcreteBreakdownElements().contains(
				this.concreteBreakdownElement));
		assertTrue(cAct2.getConcreteBreakdownElements().contains(
				this.concreteBreakdownElement));

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testRemoveAllSuperConcreteActivities() {
		// Rk: the setUp method is called here.

		this.concreteBreakdownElement
				.removeSuperConcreteActivity(this.concreteActivity);
		assertEquals(0, this.concreteBreakdownElement
				.getSuperConcreteActivities().size());
		assertTrue(this.concreteActivity.getConcreteBreakdownElements().size() == 1);
		assertFalse(this.concreteActivity.getConcreteBreakdownElements()
				.contains(this.concreteBreakdownElement));

		ConcreteActivity cAct = new ConcreteActivity();
		cAct.setConcreteName("a name");

		ConcreteActivity cAct2 = new ConcreteActivity();
		cAct2.setConcreteName("another name");

		Set<ConcreteActivity> list = new HashSet<ConcreteActivity>();
		list.add(cAct);
		list.add(cAct2);
		this.concreteBreakdownElement.addAllSuperActivities(list);

		this.concreteBreakdownElement
				.addBreakdownElement(this.breakdownElement);

		assertNotNull(this.concreteBreakdownElement
				.getSuperConcreteActivities());
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.size() == 2);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.contains(cAct));
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.contains(cAct2));

		assertTrue(this.concreteActivity.getConcreteBreakdownElements().size() == 1);
		assertTrue(cAct.getConcreteBreakdownElements().contains(
				this.concreteBreakdownElement));
		assertTrue(cAct2.getConcreteBreakdownElements().contains(
				this.concreteBreakdownElement));

		this.concreteBreakdownElement.removeAllSuperConcreteActivities();
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities()
				.size() == 0);
		assertFalse(this.concreteBreakdownElement.getSuperConcreteActivities()
				.contains(cAct));
		assertFalse(this.concreteBreakdownElement.getSuperConcreteActivities()
				.contains(cAct2));

		// Rk: the tearDown method is called here.
	}
}
