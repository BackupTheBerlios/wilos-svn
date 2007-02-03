package wilos.test.model.misc.concreteworkbreakdownelement;

import junit.framework.TestCase;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

public class ConcreteWorkBreakdownElementTest extends TestCase {

	private ConcreteWorkBreakdownElement concreteWorkBreakdownElement ;

	private static final String ID = "thisId" ;

	private static final String CONCRETE_NAME = "Work Concrete name" ;

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.concreteWorkBreakdownElement = new ConcreteWorkBreakdownElement() ;
		this.concreteWorkBreakdownElement.setConcreteName(CONCRETE_NAME);
		this.concreteWorkBreakdownElement.setId(ID);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement#clone()}.
	 */
	public void testClone() {
		try{
			assertEquals(this.concreteWorkBreakdownElement, this.concreteWorkBreakdownElement.clone()) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}
	}

	/**
	 * Test method for {@link wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement#hashCode()}.
	 */
	public void testHashCode() {

		ConcreteWorkBreakdownElement cwbe = new ConcreteWorkBreakdownElement() ;
		cwbe.setId(ID) ;
		cwbe.setConcreteName(CONCRETE_NAME) ;
		cwbe.setWorkBreakdownElement(new WorkBreakdownElement()) ;

		assertNotNull(this.concreteWorkBreakdownElement.hashCode()) ;
		assertNotNull(cwbe.hashCode()) ;
		assertEquals(this.concreteWorkBreakdownElement.hashCode(), cwbe.hashCode()) ;
	}

	/**
	 * Test method for {@link wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {

		ConcreteWorkBreakdownElement cwbe = new ConcreteWorkBreakdownElement() ;
		cwbe.setConcreteName(CONCRETE_NAME) ;
		cwbe.setWorkBreakdownElement(new WorkBreakdownElement()) ;

		assertTrue(this.concreteWorkBreakdownElement.equals(cwbe)) ;

		ConcreteWorkBreakdownElement cwbe2 = new ConcreteWorkBreakdownElement() ;
		cwbe.setConcreteName("Another concrete name") ;
		cwbe.setWorkBreakdownElement(new WorkBreakdownElement()) ;

		assertFalse(this.concreteWorkBreakdownElement.equals(cwbe2)) ;
	}

	public void testAddWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement workBreakdownElement = new WorkBreakdownElement();
		this.concreteWorkBreakdownElement.addWorkBreakdownElement(workBreakdownElement);

		assertNotNull(this.concreteWorkBreakdownElement.getWorkbreakdownelement());
		assertTrue(this.concreteWorkBreakdownElement.getWorkbreakdownelement().equals(workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public void testRemoveWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement workBreakdownElement = new WorkBreakdownElement();
		this.concreteWorkBreakdownElement.addWorkBreakdownElement(workBreakdownElement);
		this.concreteWorkBreakdownElement.removeBreakdownElement(workBreakdownElement);

		assertFalse(this.concreteWorkBreakdownElement.getBreakdownElement().equals(workBreakdownElement));
		assertNull(this.concreteWorkBreakdownElement.getBreakdownElement());
		
		// Rk: the tearDown method is called here.
	}
}
