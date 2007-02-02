package wilos.test.model.misc.concretebreakdownelement;


import junit.framework.TestCase;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.model.spem2.breakdownelement.BreakdownElement;

public class ConcreteBreakdownElementTest extends TestCase {

	private ConcreteBreakdownElement concreteBreakdownElement ;
	
	private static final String ID = "thisId" ;
	
	private static final String CONCRETE_NAME = "Concrete name" ;

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.concreteBreakdownElement = new ConcreteBreakdownElement() ;
		this.concreteBreakdownElement.setConcreteName(CONCRETE_NAME);
		this.concreteBreakdownElement.setId(ID);
		this.concreteBreakdownElement.setProject(new Project());
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
	 * Test method for {@link wilos.model.spem2.activity.Activity#clone()}.
	 */
	public void testClone() {
		try{
			assertEquals(this.concreteBreakdownElement, this.concreteBreakdownElement.clone()) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}
	}

	/**
	 * Test method for {@link wilos.model.spem2.activity.Activity#hashCode()}.
	 */
	public void testHashCode() {

		ConcreteBreakdownElement cbe = new ConcreteBreakdownElement() ;
		cbe.setId(ID) ;
		cbe.setConcreteName(CONCRETE_NAME) ;
		cbe.setBreakdownElement(new BreakdownElement()) ;

		assertNotNull(this.concreteBreakdownElement.hashCode()) ;
		assertNotNull(cbe.hashCode()) ;
		assertEquals(this.concreteBreakdownElement.hashCode(), cbe.hashCode()) ;
	}

	/**
	 * Test method for {@link wilos.model.spem2.activity.Activity#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {

		ConcreteBreakdownElement cbe = new ConcreteBreakdownElement() ;
		cbe.setConcreteName(CONCRETE_NAME) ;
		cbe.setConcreteSuperActivity(new SuperConcreteActivity()) ;
		cbe.setBreakdownElement(new BreakdownElement()) ;

		assertTrue(this.concreteBreakdownElement.equals(cbe)) ;

		ConcreteBreakdownElement cbe2 = new ConcreteBreakdownElement() ;
		cbe.setConcreteName("Another concrete name") ;
		cbe.setConcreteSuperActivity(new SuperConcreteActivity()) ;
		cbe.setBreakdownElement(new BreakdownElement()) ;

		assertFalse(this.concreteBreakdownElement.equals(cbe2)) ;
	}
	
	public void testAddSuperConcreteActivity() {
		// Rk: the setUp method is called here.

		SuperConcreteActivity superConcreteActivity = new SuperConcreteActivity();
		this.concreteBreakdownElement.addConcreteTaskDescriptor(superConcreteActivity);

		assertNotNull(this.concreteBreakdownElement.getSuperConcreteActivities());
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 1);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().contains(superConcreteActivity));

		assertEquals(superConcreteActivity.getConcreteBreakdownElement(), this.concreteBreakdownElement);

		// Rk: the tearDown method is called here.
	}
	
	public void testRemoveSuperConcreteActivity() {
		// Rk: the setUp method is called here.

		SuperConcreteActivity concreteSuperActivity = new SuperConcreteActivity();
		
		this.concreteBreakdownElement.addConcreteTaskDescriptor(concreteSuperActivity);
		assertNotNull(this.concreteBreakdownElement.getSuperConcreteActivities());
		assertEquals(concreteSuperActivity.getConcreteBreakdownElement(), this.concreteBreakdownElement);

		this.concreteBreakdownElement.removeSuperConcreteActivity(concreteSuperActivity);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 0);

		assertNull(concreteSuperActivity.getConcreteBreakdownElement());

		// Rk: the tearDown method is called here.
	}
}
