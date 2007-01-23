package wilos.test.model.spem2.element;

import junit.framework.TestCase;
import wilos.model.spem2.element.Element;

/**
 * @author Sebastien
 * 
 * This class represents the class test of the Element class.
 * 
 */
public class ElementTest extends TestCase {

	private Element element;

	public static final String IDEPF = "idEPF";
	
	public static final String NAME = "name";

	public static final String DESCRIPTION = "description";

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.element = new Element();
		this.element.setDescription(DESCRIPTION);
		this.element.setName(NAME);
		this.element.setName(IDEPF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link wilos.model.spem2.element.Element#hashCode()}.
	 */
	public void testHashCode() {
		
		Element elt = new Element();
		elt.setDescription(DESCRIPTION);
		elt.setName(NAME);
		elt.setName(IDEPF);
		
		assertNotNull(this.element.hashCode());
		assertNotNull(elt.hashCode());
		assertEquals(this.element.hashCode(),elt.hashCode());
	}
	
	/**
	 * Test method for {@link wilos.model.spem2.element.Element#clone()}.
	 */
	public void testClone() {
		try {
			assertTrue(this.element.equals(this.element.clone()));
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testClone method");
		}
	}

	/**
	 * Test method for
	 * {@link wilos.model.spem2.element.Element#equals(java.lang.Object)}.
	 */
	public void testEquals() {
		Element elt = new Element();
		elt.setDescription(DESCRIPTION);
		elt.setName(NAME);
		elt.setName(IDEPF);

		assertTrue(this.element.equals(elt));
	}

}