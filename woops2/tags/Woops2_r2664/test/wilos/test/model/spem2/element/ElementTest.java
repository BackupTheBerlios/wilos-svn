package wilos.test.model.spem2.element;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.model.spem2.element.Element;

public class ElementTest {

	private Element element;

	public static final String IDEPF = "idEPF";

	public static final String NAME = "name";

	public static final String DESCRIPTION = "description";

	@Before
	public void setUp() throws Exception {
		this.element = new Element();
		this.element.setDescription(DESCRIPTION);
		this.element.setName(NAME);
		this.element.setName(IDEPF);
	}

	@After
	public void tearDown() throws Exception {
		//None.
	}

	@Test
	public void testHashCode() {

		Element elt = new Element();
		elt.setDescription(DESCRIPTION);
		elt.setName(NAME);
		elt.setName(IDEPF);

		assertNotNull(this.element.hashCode());
		assertNotNull(elt.hashCode());
		assertEquals(this.element.hashCode(),elt.hashCode());
	}

	@Test
	public void testClone() {
		try {
			assertTrue(this.element.equals(this.element.clone()));
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testClone method");
		}
	}

	@Test
	public void testEquals() {
		Element elt = new Element();
		elt.setDescription(DESCRIPTION);
		elt.setName(NAME);
		elt.setName(IDEPF);

		assertTrue(this.element.equals(elt));
	}

}