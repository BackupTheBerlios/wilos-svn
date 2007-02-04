package wilos.test.model.misc.concretebreakdownelement;


import junit.framework.TestCase;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.breakdownelement.BreakdownElement;

public class ConcreteBreakdownElementTest extends TestCase {

	private ConcreteBreakdownElement concreteBreakdownElement ;
	
	private ConcreteActivity concreteActivity ; 

	private static final String ID = "thisId" ;

	private static final String CONCRETE_NAME = "Concrete name" ;

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.concreteActivity = new ConcreteActivity() ;
		
		this.concreteBreakdownElement = new ConcreteBreakdownElement() ;
		this.concreteBreakdownElement.setConcreteName(CONCRETE_NAME);
		this.concreteBreakdownElement.setId(ID);
		this.concreteBreakdownElement.setBreakdownElement(new BreakdownElement()) ;
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
	 * Test method for {@link wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement#clone()}.
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
	 * Test method for {@link wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement#hashCode()}.
	 */
	public void testHashCode() {

		this.concreteBreakdownElement.addSuperConcreteActivity(this.concreteActivity) ;
		
		ConcreteBreakdownElement cbe = new ConcreteBreakdownElement() ;
		cbe.setId(ID) ;
		cbe.setConcreteName(CONCRETE_NAME) ;
		cbe.setBreakdownElement(new BreakdownElement()) ;
		cbe.addSuperConcreteActivity(new ConcreteActivity()) ;

		assertNotNull(this.concreteBreakdownElement.hashCode()) ;
		assertNotNull(cbe.hashCode()) ;
		assertEquals(this.concreteBreakdownElement.hashCode(), cbe.hashCode()) ;
	}

	/**
	 * Test method for {@link wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {

		this.concreteBreakdownElement.addSuperConcreteActivity(this.concreteActivity) ;
		
		ConcreteBreakdownElement cbe = new ConcreteBreakdownElement() ;
		cbe.setConcreteName(CONCRETE_NAME) ;
		cbe.setBreakdownElement(new BreakdownElement()) ;
		cbe.addSuperConcreteActivity(new ConcreteActivity()) ;

		assertTrue(this.concreteBreakdownElement.equals(cbe)) ;

		ConcreteBreakdownElement cbe2 = new ConcreteBreakdownElement() ;
		cbe.setConcreteName("Another concrete name") ;
		cbe.setBreakdownElement(new BreakdownElement()) ;
		cbe.addSuperConcreteActivity(new ConcreteActivity()) ;
			
		assertFalse(this.concreteBreakdownElement.equals(cbe2)) ;
	}

	/**
	 * Test method for {@link wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement#addSuperConcreteActivity(ConcreteActivity)}.
	 */
	public void testAddSuperConcreteActivity() {
		// Rk: the setUp method is called here.

		this.concreteBreakdownElement.addSuperConcreteActivity(this.concreteActivity) ;

		assertNotNull(this.concreteBreakdownElement.getSuperConcreteActivities());
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 1);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().contains(this.concreteActivity));

		assertTrue(this.concreteActivity.getConcreteBreakdownElements().size() >= 1);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement#removeSuperConcreteActivity()}.
	 */
	public void testRemoveSuperConcreteActivity() {
		// Rk: the setUp method is called here.

		ConcreteActivity concreteSuperActivity = new ConcreteActivity();

		this.concreteBreakdownElement.addSuperConcreteActivity(concreteSuperActivity);
		assertNotNull(this.concreteBreakdownElement.getSuperConcreteActivities());
		assertNotNull(concreteSuperActivity.getConcreteBreakdownElements());

		this.concreteBreakdownElement.removeSuperConcreteActivity(concreteSuperActivity);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 0);

		assertNotNull(concreteSuperActivity.getConcreteBreakdownElements());
		
		this.concreteBreakdownElement.removeSuperConcreteActivity(this.concreteActivity);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 0);

		// Rk: the tearDown method is called here.
	}
}
