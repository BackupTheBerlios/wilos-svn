package woops2.test.model.activity;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import woops2.model.activity.Activity;
import woops2.model.breakdownelement.BreakdownElement;

/**
 * @author Sebastien
 *
 * This class represents ... TODO
 *
 */
public class ActivityTest extends TestCase {
	
	private Activity activity ;

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.activity = new Activity() ;
		this.activity.setPrefix(PREFIX) ;
		this.activity.setIsOptional(IS_OPTIONAL) ;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link woops2.model.activity.Activity#hashCode()}.
	 */
	public void testHashCode() {
		fail("Not yet implemented") ;
	}

	/**
	 * Test method for {@link woops2.model.activity.Activity#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		fail("Not yet implemented") ;
	}

	/**
	 * Test method for {@link woops2.model.activity.Activity#addToBreakdownElement(woops2.model.breakdownelement.BreakdownElement)}.
	 */
	public void testAddToBreakdownElement() {
		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.setPrefix(PREFIX) ;
		breakdownElement.setIsOptional(IS_OPTIONAL) ;

		this.activity.addToBreakdownElement(breakdownElement) ;

		assertFalse(this.activity.getBreakDownElements().isEmpty()) ;
		assertFalse(breakdownElement.getSuperActivities().isEmpty()) ;
		assertTrue(this.activity.getBreakDownElements().size() == 1) ;
		assertTrue(breakdownElement.getSuperActivities().size() == 1) ;
	}

	/**
	 * Test method for {@link woops2.model.activity.Activity#addToAllBreakdownElement(java.util.Set)}.
	 */
	public void testAddToAllBreakdownElement() {
		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.setPrefix(PREFIX) ;
		breakdownElement.setIsOptional(IS_OPTIONAL) ;

		BreakdownElement tmp = new BreakdownElement() ;
		tmp.setPrefix(PREFIX) ;
		tmp.setIsOptional(IS_OPTIONAL) ;

		Set<BreakdownElement> list = new HashSet<BreakdownElement>() ;
		list.add(breakdownElement) ;
		list.add(tmp) ;

		this.activity.addToAllBreakdownElement(list) ;

		assertFalse(this.activity.getBreakDownElements().isEmpty()) ;
		assertTrue(this.activity.getBreakDownElements().size() == 2);
		assertFalse(breakdownElement.getSuperActivities().isEmpty()) ;
		assertTrue(breakdownElement.getSuperActivities().size() == 1);
		assertFalse(tmp.getSuperActivities().isEmpty()) ;
		assertTrue(tmp.getSuperActivities().size() == 1);
	}

	/**
	 * Test method for {@link woops2.model.activity.Activity#removeFromBreakdownElement(woops2.model.breakdownelement.BreakdownElement)}.
	 */
	public void testRemoveFromBreakdownElement() {
		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.setPrefix(PREFIX) ;
		breakdownElement.setIsOptional(IS_OPTIONAL) ;

		this.activity.addToBreakdownElement(breakdownElement) ;
		this.activity.removeFromBreakdownElement(breakdownElement) ;

		assertTrue(this.activity.getBreakDownElements().isEmpty()) ;
		assertTrue(breakdownElement.getSuperActivities().isEmpty()) ;
	}

	/**
	 * Test method for {@link woops2.model.activity.Activity#removeFromAllBreakdownElements()}.
	 */
	public void testRemoveFromAllBreakdownElements() {
		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.setPrefix(PREFIX) ;
		breakdownElement.setIsOptional(IS_OPTIONAL) ;

		BreakdownElement tmp = new BreakdownElement() ;
		tmp.setPrefix(PREFIX) ;
		tmp.setIsOptional(IS_OPTIONAL) ;

		this.activity.addToBreakdownElement(breakdownElement) ;
		this.activity.addToBreakdownElement(tmp) ;
		this.activity.removeFromAllBreakdownElements();

		assertTrue(this.activity.getBreakDownElements().isEmpty()) ;
		assertTrue(breakdownElement.getSuperActivities().isEmpty()) ;
		assertTrue(tmp.getSuperActivities().isEmpty()) ;
	}
}
