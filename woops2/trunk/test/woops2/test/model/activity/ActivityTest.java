
package woops2.test.model.activity ;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import woops2.model.activity.Activity;
import woops2.model.breakdownelement.BreakdownElement;

/**
 * @author Sebastien
 * 
 * This class represents the class test of the Activity class.
 * 
 */
public class ActivityTest extends TestCase {

	private Activity activity ;

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.activity = new Activity() ;
		this.activity.setPrefix(PREFIX) ;
		this.activity.setName("name") ;
		this.activity.setIsOptional(IS_OPTIONAL) ;
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
	 * Test method for {@link woops2.model.activity.Activity#clone()}.
	 */
	public void testClone() {
		try {
			assertEquals((Activity)this.activity.clone(), this.activity);
		} catch (CloneNotSupportedException e) {
			fail("Error CloneNotSupportedException in the testClone method");
		}
	}

	/**
	 * Test method for {@link woops2.model.activity.Activity#hashCode()}.
	 */
	public void testHashCode() {
		assertNotNull(this.activity.hashCode()) ;
	}

	/**
	 * Test method for {@link woops2.model.activity.Activity#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		
		Activity tmp = new Activity() ;
		tmp.setPrefix(PREFIX) ;
		tmp.setName("name") ;
		tmp.setIsOptional(IS_OPTIONAL) ;
		
		assertTrue(this.activity.equals(tmp)) ;
		
		Activity act = new Activity() ;
		act.setPrefix(PREFIX) ;
		act.setName("name2") ;
		act.setIsOptional(IS_OPTIONAL) ;
		
		assertFalse(this.activity.equals(act)) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.activity.Activity#addToBreakdownElement(woops2.model.breakdownelement.BreakdownElement)}.
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
	 * Test method for
	 * {@link woops2.model.activity.Activity#addToAllBreakdownElements(java.util.Set)}.
	 */
	public void testAddToAllBreakdownElement() {
		
		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.setPrefix(PREFIX) ;
		breakdownElement.setName("name") ;
		breakdownElement.setIsOptional(IS_OPTIONAL) ;

		BreakdownElement tmp = new BreakdownElement() ;
		tmp.setPrefix(PREFIX) ;
		tmp.setName("name2") ;
		tmp.setIsOptional(IS_OPTIONAL) ;

		Set<BreakdownElement> set = new HashSet<BreakdownElement>() ;
		set.add(breakdownElement) ;
		set.add(tmp) ;

		this.activity.addToAllBreakdownElements(set) ;

		assertFalse(this.activity.getBreakDownElements().isEmpty()) ;
		assertTrue(this.activity.getBreakDownElements().size() == 2) ;
		assertFalse(breakdownElement.getSuperActivities().isEmpty()) ;
		assertTrue(breakdownElement.getSuperActivities().size() == 1) ;
		assertFalse(tmp.getSuperActivities().isEmpty()) ;
		assertTrue(tmp.getSuperActivities().size() == 1) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.activity.Activity#removeFromBreakdownElement(woops2.model.breakdownelement.BreakdownElement)}.
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
		breakdownElement.setName("name1");
		breakdownElement.setPrefix(PREFIX) ;
		breakdownElement.setIsOptional(IS_OPTIONAL) ;

		BreakdownElement tmp = new BreakdownElement() ;
		tmp.setName("name2");
		tmp.setPrefix(PREFIX) ;
		tmp.setIsOptional(IS_OPTIONAL) ;
		
		Set<BreakdownElement> set = new HashSet<BreakdownElement>() ;
		set.add(breakdownElement) ;
		set.add(tmp) ;

		this.activity.addToAllBreakdownElements(set);
		this.activity.removeFromAllBreakdownElements() ;

		assertTrue("bdes", this.activity.getBreakDownElements().isEmpty()) ;
		assertTrue("bde.acts", breakdownElement.getSuperActivities().isEmpty()) ;
		assertTrue("tmp.acts", tmp.getSuperActivities().isEmpty()) ;
	}
}
