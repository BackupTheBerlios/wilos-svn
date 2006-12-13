package wilos.test.model.spem2.workbreakdownelement;

import junit.framework.TestCase;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

/**
 * @author deder
 * 
 */
public class WorkBreakdownElementTest extends TestCase {

	private WorkBreakdownElement workBreakdownElement;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.workBreakdownElement = new WorkBreakdownElement() ;
		this.workBreakdownElement.setGuid("idEPF") ;
		this.workBreakdownElement.setName("name1") ;
		this.workBreakdownElement.setDescription("description1") ;
		this.workBreakdownElement.setPrefix("prefix1") ;
		this.workBreakdownElement.setIsOptional(true) ;
		this.workBreakdownElement.setIsPlanned(false) ;
		this.workBreakdownElement.setHasMultipleOccurrences(false) ;
		this.workBreakdownElement.setIsOngoing(true);
		this.workBreakdownElement.setIsPlanned(true);
		this.workBreakdownElement.setIsRepeatable(true);
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
	 * Test method for {@link wilos.model.spem2.workbreakdownelement.WorkBreakdownElement#clone()}.
	 */
	public void testClone() {
		try {
			assertEquals(this.workBreakdownElement, this.workBreakdownElement.clone());
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testClone method");
		}
	}
	
	/**
	 * Test method for
	 * {@link wilos.model.spem2.workbreakdownelement.WorkBreakdownElement#hashCode()}.
	 */
	public final void testHashCode() {
		// Rk: the setUp method is called here.

		workBreakdownElement = new WorkBreakdownElement() ;
		workBreakdownElement.setGuid("idEPF") ;
		workBreakdownElement.setName("name1") ;
		workBreakdownElement.setDescription("description1") ;
		workBreakdownElement.setPrefix("prefix1") ;
		workBreakdownElement.setIsOptional(true) ;
		workBreakdownElement.setIsPlanned(false) ;
		workBreakdownElement.setHasMultipleOccurrences(false) ;
		workBreakdownElement.setIsOngoing(true);
		workBreakdownElement.setIsPlanned(true);
		workBreakdownElement.setIsRepeatable(true);
		
		assertNotNull(this.workBreakdownElement.hashCode());
		assertNotNull(workBreakdownElement.hashCode());
		assertEquals(this.workBreakdownElement.hashCode(),workBreakdownElement.hashCode());

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.model.spem2.workbreakdownelement.WorkBreakdownElement#equals(java.lang.Object)}.
	 */
	public final void testEquals() {
		// Rk: the setUp method is called here.

		// Assert if it's equal by references.
		assertTrue("By references", this.workBreakdownElement
				.equals(this.workBreakdownElement));

		// Assert if it's equal field by field.
		WorkBreakdownElement bdeTmp1 = null;
		try {
			bdeTmp1 = this.workBreakdownElement.clone();
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testEquals method");
		}
		assertTrue("Field by field", this.workBreakdownElement.equals(bdeTmp1));

		// Assert if it's not equal.
		WorkBreakdownElement bdeTmp2 = new WorkBreakdownElement();
		workBreakdownElement.setGuid("idEPF2") ;
		bdeTmp2.setName("name2") ;
		bdeTmp2.setDescription("description2") ;
		bdeTmp2.setPrefix("prefix2") ;
		bdeTmp2.setIsOptional(true) ;
		bdeTmp2.setIsPlanned(false) ;
		bdeTmp2.setHasMultipleOccurrences(false) ;
		bdeTmp2.setIsOngoing(true);
		bdeTmp2.setIsPlanned(true);
		bdeTmp2.setIsRepeatable(true);
		assertFalse("Not equals", this.workBreakdownElement.equals(bdeTmp2));

		// Rk: the tearDown method is called here.
	}

}
