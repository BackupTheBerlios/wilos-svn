
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.activity = new Activity() ;
		activity.setName("name1") ;
		activity.setDescription("description1") ;
		activity.setPrefix("prefix1") ;
		activity.setIsOptional(true) ;
		activity.setIsPlanned(false) ;
		activity.setHasMultipleOccurrences(false) ;
		activity.setIsEvenDriven(true) ;
		activity.setIsOngoing(false) ;
		activity.setIsRepeatable(true) ;
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
			assertEquals(this.activity, (Activity)this.activity.clone());
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
		tmp.setName("name1") ;
		tmp.setDescription("description1") ;
		tmp.setPrefix("prefix1") ;
		tmp.setIsOptional(true) ;
		tmp.setIsPlanned(false) ;
		tmp.setHasMultipleOccurrences(false) ;
		tmp.setIsEvenDriven(true) ;
		tmp.setIsOngoing(false) ;
		tmp.setIsRepeatable(true) ;
		
		assertTrue(this.activity.equals(tmp)) ;
		
		Activity act = new Activity() ;
		act.setName("name2") ;
		act.setDescription("description2") ;
		act.setPrefix("prefix2") ;
		act.setIsOptional(true) ;
		act.setIsPlanned(false) ;
		act.setHasMultipleOccurrences(false) ;
		act.setIsEvenDriven(true) ;
		act.setIsOngoing(false) ;
		act.setIsRepeatable(true) ;
		
		assertFalse(this.activity.equals(act)) ;
	}

	/**
	 * Test method for
	 * {@link woops2.model.activity.Activity#addToBreakdownElement(woops2.model.breakdownelement.BreakdownElement)}.
	 */
	public void testAddToBreakdownElement() {
		
		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.setName("name1") ;
		breakdownElement.setDescription("description1") ;
		breakdownElement.setPrefix("prefix1") ;
		breakdownElement.setIsOptional(true) ;
		breakdownElement.setIsPlanned(false) ;
		breakdownElement.setHasMultipleOccurrences(false) ;

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
		breakdownElement.setName("name1") ;
		breakdownElement.setDescription("description1") ;
		breakdownElement.setPrefix("prefix1") ;
		breakdownElement.setIsOptional(true) ;
		breakdownElement.setIsPlanned(false) ;
		breakdownElement.setHasMultipleOccurrences(false) ;

		BreakdownElement tmp = new BreakdownElement() ;
		tmp.setName("name1") ;
		tmp.setDescription("description1") ;
		tmp.setPrefix("prefix1") ;
		tmp.setIsOptional(true) ;
		tmp.setIsPlanned(false) ;
		tmp.setHasMultipleOccurrences(false) ;

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
		breakdownElement.setPrefix("prefix1) ;
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
