package wilos.test.model.misc.concretephase;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.spem2.phase.Phase;

public class ConcretePhaseTest {
	private ConcretePhase concretephase;

	public static final String CONCRETENAME = "ConcreteName";

	public static final String NAME = "Name";

	@Before
	public void setUp() {
		this.concretephase = new ConcretePhase();
		this.concretephase.setConcreteName(CONCRETENAME);
	}

	@After
	public void tearDown() {
		// None
	}

	@Test
	public final void testHashCode() {
		// Rk: the setUp method is called here.

		ConcretePhase concretephas = new ConcretePhase();
		concretephas.setConcreteName(CONCRETENAME);
		assertNotNull(this.concretephase.hashCode());
		assertNotNull(concretephas.hashCode());
		assertEquals(this.concretephase.hashCode(), concretephas.hashCode());

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testEquals() {
		// Rk: the setUp method is called here.

		// Assert if it's equal by references.
		assertTrue("By references", this.concretephase
				.equals(this.concretephase));

		// Assert if it's equal field by field.
		ConcretePhase concretephaseTmp1 = null;
		try {
			concretephaseTmp1 = this.concretephase.clone();
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testEquals method");
		}
		assertTrue("Field by field", this.concretephase
				.equals(concretephaseTmp1));

		// Assert if it's not equal.
		ConcretePhase concretephasTmp2 = new ConcretePhase();
		concretephasTmp2.setConcreteName("concreteFalse");
		assertFalse("Not equals", this.concretephase.equals(concretephasTmp2));

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testClone() {
		// Rk: the setUp method is called here.

		try {
			assertEquals(this.concretephase.clone(), this.concretephase);
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testClone method");
		}

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testAddToPhase() {
		Phase phase = new Phase();
		phase.setName(NAME);

		this.concretephase.addPhase(phase);

		assertNotNull(this.concretephase.getPhase());
		assertTrue(phase.getConcretePhases().size() == 1);
	}

	@Test
	public void testRemoveFromPhase() {
		Phase phase = new Phase();
		phase.setName(NAME);

		this.concretephase.addPhase(phase);
		assertNotNull("null", this.concretephase.getPhase());
		assertTrue("empty", phase.getConcretePhases().size() == 1);

		this.concretephase.removePhase(phase);
		assertNull("null", this.concretephase.getPhase());
		assertTrue("empty", phase.getConcretePhases().isEmpty());
	}

}
