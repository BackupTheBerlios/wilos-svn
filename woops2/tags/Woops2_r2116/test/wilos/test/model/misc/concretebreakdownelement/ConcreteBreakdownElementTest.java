package wilos.test.model.misc.concretebreakdownelement;


import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.breakdownelement.BreakdownElement;

public class ConcreteBreakdownElementTest extends TestCase {

	private ConcreteBreakdownElement concreteBreakdownElement ;

	private ConcreteActivity concreteActivity ;

	private BreakdownElement breakdownElement;

	private static final String CONCRETE_NAME = "Concrete name" ;

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.concreteActivity = new ConcreteActivity() ;
		this.concreteActivity.setConcreteName("concreteactivityname");
		
		this.breakdownElement = new BreakdownElement();
		this.breakdownElement.setName("name");

		this.concreteBreakdownElement = new ConcreteBreakdownElement() ;
		this.concreteBreakdownElement.setConcreteName(CONCRETE_NAME);
		this.concreteBreakdownElement.addSuperConcreteActivity(this.concreteActivity) ;
		this.concreteBreakdownElement.addBreakdownElement(this.breakdownElement);
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

		ConcreteBreakdownElement cbe = new ConcreteBreakdownElement() ;
		cbe.setConcreteName(CONCRETE_NAME) ;
		cbe.addBreakdownElement(this.breakdownElement);
		cbe.addSuperConcreteActivity(this.concreteActivity) ;

		assertNotNull(this.concreteBreakdownElement.hashCode()) ;
		assertNotNull(cbe.hashCode()) ;
		assertEquals(this.concreteBreakdownElement.hashCode(), cbe.hashCode()) ;
	}

	/**
	 * Test method for {@link wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {

		ConcreteBreakdownElement cbe = new ConcreteBreakdownElement() ;
		cbe.setConcreteName(CONCRETE_NAME) ;
		cbe.addBreakdownElement(this.breakdownElement);
		cbe.addSuperConcreteActivity(this.concreteActivity) ;

		assertTrue(this.concreteBreakdownElement.equals(cbe)) ;

		ConcreteBreakdownElement cbe2 = new ConcreteBreakdownElement() ;
		cbe.setConcreteName("Another concrete name") ;
		cbe.addBreakdownElement(new BreakdownElement()) ;
		cbe.addSuperConcreteActivity(new ConcreteActivity()) ;

		assertFalse(this.concreteBreakdownElement.equals(cbe2)) ;
	}

	/**
	 * Test method for {@link wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement#addSuperConcreteActivity(ConcreteActivity)}.
	 */
	public void testAddSuperConcreteActivity() {
		// Rk: the setUp method is called here.
		
		assertNotNull(this.concreteBreakdownElement.getSuperConcreteActivities());
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 1);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().contains(this.concreteActivity));

		assertNotNull(this.concreteActivity.getConcreteBreakdownElements());
		assertTrue(this.concreteActivity.getConcreteBreakdownElements().size() == 1);
		assertTrue(this.concreteActivity.getConcreteBreakdownElements().contains(this.concreteBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement#removeSuperConcreteActivity()}.
	 */
	public void testRemoveSuperConcreteActivity() {
		// Rk: the setUp method is called here.

		this.concreteBreakdownElement.removeSuperConcreteActivity(this.concreteActivity);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 0);
		
		ConcreteActivity superConcreteActivity = new ConcreteActivity();
		superConcreteActivity.setConcreteName("Name");
		
		assertNotNull(this.concreteBreakdownElement.getSuperConcreteActivities());
		assertNotNull(superConcreteActivity.getConcreteBreakdownElements());

		this.concreteBreakdownElement.removeSuperConcreteActivity(superConcreteActivity);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 0);

		assertNotNull(superConcreteActivity.getConcreteBreakdownElements());

		// Rk: the tearDown method is called here.
	}
	
	public void testAddAllSuperActivities() {
		
		// Rk: the setUp method is called here.
		
		this.concreteBreakdownElement.removeSuperConcreteActivity(this.concreteActivity);
		assertEquals(0, this.concreteBreakdownElement.getSuperConcreteActivities().size());
		
		ConcreteActivity cAct = new ConcreteActivity() ;
		cAct.setConcreteName("a name");
		
		ConcreteActivity cAct2 = new ConcreteActivity() ;
		cAct2.setConcreteName("another name");
		
		Set<ConcreteActivity> list = new HashSet<ConcreteActivity>();
		list.add(cAct);
		list.add(cAct2);
		this.concreteBreakdownElement.addAllSuperActivities(list);
		
		this.concreteBreakdownElement.addBreakdownElement(this.breakdownElement);
		
		assertNotNull(this.concreteBreakdownElement.getSuperConcreteActivities());
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 2);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().contains(cAct));
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().contains(cAct2));
		
		assertTrue(this.concreteActivity.getConcreteBreakdownElements().size() == 1);
		assertTrue(cAct.getConcreteBreakdownElements().contains(this.concreteBreakdownElement));
		assertTrue(cAct2.getConcreteBreakdownElements().contains(this.concreteBreakdownElement));
		
		// Rk: the tearDown method is called here.
	}

	public void testRemoveAllSuperConcreteActivities() {
		// Rk: the setUp method is called here.

		this.concreteBreakdownElement.removeSuperConcreteActivity(this.concreteActivity);
		assertEquals(0, this.concreteBreakdownElement.getSuperConcreteActivities().size());
		
		ConcreteActivity cAct = new ConcreteActivity() ;
		cAct.setConcreteName("a name");
		
		ConcreteActivity cAct2 = new ConcreteActivity() ;
		cAct2.setConcreteName("another name");
		
		Set<ConcreteActivity> list = new HashSet<ConcreteActivity>();
		list.add(cAct);
		list.add(cAct2);
		this.concreteBreakdownElement.addAllSuperActivities(list);
		
		this.concreteBreakdownElement.addBreakdownElement(this.breakdownElement);
		
		assertNotNull(this.concreteBreakdownElement.getSuperConcreteActivities());
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 2);
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().contains(cAct));
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().contains(cAct2));
		
		assertTrue(this.concreteActivity.getConcreteBreakdownElements().size() == 1);
		assertTrue(cAct.getConcreteBreakdownElements().contains(this.concreteBreakdownElement));
		assertTrue(cAct2.getConcreteBreakdownElements().contains(this.concreteBreakdownElement));
		
		this.concreteBreakdownElement.removeAllSuperConcreteActivities();
		assertTrue(this.concreteBreakdownElement.getSuperConcreteActivities().size() == 0);
		assertFalse(this.concreteBreakdownElement.getSuperConcreteActivities().contains(cAct));
		assertFalse(this.concreteBreakdownElement.getSuperConcreteActivities().contains(cAct2));
		
		// Rk: the tearDown method is called here.
	}
}