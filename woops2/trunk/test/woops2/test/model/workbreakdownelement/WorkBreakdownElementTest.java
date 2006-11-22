package woops2.test.model.workbreakdownelement;

import junit.framework.TestCase;
import woops2.model.workbreakdownelement.WorkBreakdownElement;

/**
 * @author deder
 * 
 */
public class WorkBreakdownElementTest extends TestCase {

	private WorkBreakdownElement workBreakdownElement;

	public static final String PREFIX = "prefix";

	public static final Boolean IS_OPTIONAL = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.workBreakdownElement = new WorkBreakdownElement();
		this.workBreakdownElement.setPrefix("prefix");
		this.workBreakdownElement.setIsOptional(IS_OPTIONAL);
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
	 * Test method for {@link woops2.model.workbreakdownelement.WorkBreakdownElement#clone()}.
	 */
	public void testClone() {
		try {
			assertEquals((WorkBreakdownElement)this.workBreakdownElement.clone(), this.workBreakdownElement);
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testClone method");
		}
	}
	
	/**
	 * Test method for
	 * {@link woops2.model.workbreakdownelement.WorkBreakdownElement#hashCode()}.
	 */
	public final void testHashCode() {
		// Rk: the setUp method is called here.

		assertNotNull(this.workBreakdownElement.hashCode());

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link woops2.model.workbreakdownelement.WorkBreakdownElement#equals(java.lang.Object)}.
	 */
	public final void testEqualsObject() {
		// Rk: the setUp method is called here.

		// Assert if it's equal by references.
		assertTrue("By references", this.workBreakdownElement
				.equals(this.workBreakdownElement));

		// Assert if it's equal field by field.
		WorkBreakdownElement bdeTmp1 = new WorkBreakdownElement();
		bdeTmp1.setPrefix(PREFIX);
		bdeTmp1.setIsOptional(IS_OPTIONAL);
		assertTrue("Field by field", this.workBreakdownElement.equals(bdeTmp1));

		// Assert if it's not equal.
		WorkBreakdownElement bdeTmp2 = new WorkBreakdownElement();
		bdeTmp2.setPrefix("prefixFalse");
		bdeTmp2.setIsOptional(true);
		assertFalse("Not equals", this.workBreakdownElement.equals(bdeTmp2));

		// Rk: the tearDown method is called here.
	}

}
