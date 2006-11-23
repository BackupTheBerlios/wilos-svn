package woops2.test.model.element;

import junit.framework.TestCase;
import woops2.model.element.Element;

/**
 * @author Sebastien
 * 
 * This class represents the class test of the Element class.
 * 
 */
public class ElementTest extends TestCase {

	private Element element;

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
	 * Test method for {@link woops2.model.element.Element#hashCode()}.
	 */
	public void testHashCode() {
		
		element = new Element();
		element.setDescription(DESCRIPTION);
		element.setName(NAME);
		
		assertNotNull(this.element.hashCode());
		assertNotNull(element.hashCode());
		assertEquals(this.element.hashCode(),element.hashCode());
	}
	
	/**
	 * Test method for {@link woops2.model.element.Element#clone()}.
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
	 * {@link woops2.model.element.Element#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		Element element = new Element();
		element.setDescription(DESCRIPTION);
		element.setName(NAME);

		assertTrue(this.element.equals(element));
	}

}