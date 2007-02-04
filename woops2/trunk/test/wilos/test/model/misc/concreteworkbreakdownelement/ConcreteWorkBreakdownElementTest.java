package wilos.test.model.misc.concreteworkbreakdownelement;

import junit.framework.TestCase;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
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
		cwbe.setConcreteName(CONCRETE_NAME) ;

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

		assertTrue(this.concreteWorkBreakdownElement.equals(cwbe)) ;

		ConcreteWorkBreakdownElement cwbe2 = new ConcreteWorkBreakdownElement() ;
		cwbe.setConcreteName("Another concrete name") ;
		
		assertFalse(this.concreteWorkBreakdownElement.equals(cwbe2)) ;
	}

	public void testAddWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement workBreakdownElement = new WorkBreakdownElement();
		this.concreteWorkBreakdownElement.addWorkBreakdownElement(workBreakdownElement);

		assertNotNull(this.concreteWorkBreakdownElement.getWorkBreakdownElement());
		assertTrue(this.concreteWorkBreakdownElement.getWorkBreakdownElement().equals(workBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}
	
	public void testRemoveWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		WorkBreakdownElement workBreakdownElement = new WorkBreakdownElement();
		this.concreteWorkBreakdownElement.addWorkBreakdownElement(workBreakdownElement);
		
		assertNotNull(this.concreteWorkBreakdownElement.getWorkBreakdownElement());
		assertTrue(this.concreteWorkBreakdownElement.getWorkBreakdownElement().equals(workBreakdownElement));
		
		this.concreteWorkBreakdownElement.removeWorkBreakdownElement(workBreakdownElement);

		assertFalse(workBreakdownElement.equals(this.concreteWorkBreakdownElement.getWorkBreakdownElement()));
		assertNull(this.concreteWorkBreakdownElement.getWorkBreakdownElement());
		
		// Rk: the tearDown method is called here.
	}
}
