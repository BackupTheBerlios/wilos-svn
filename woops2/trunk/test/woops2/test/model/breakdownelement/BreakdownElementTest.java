
package woops2.test.model.breakdownelement ;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import woops2.model.activity.Activity;
import woops2.model.breakdownelement.BreakdownElement;

/**
 * This class represents the class test of the BreakdownElement class.
 * 
 * @author deder
 * 
 */
public class BreakdownElementTest extends TestCase {

	private BreakdownElement breakdownElement ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.breakdownElement = new BreakdownElement() ;
		this.breakdownElement.setIdEPF("idEPF1");
		this.breakdownElement.setName("name1");
		this.breakdownElement.setDescription("description1");
		this.breakdownElement.setPrefix("prefix1") ;
		this.breakdownElement.setIsOptional(true) ;
		this.breakdownElement.setIsPlanned(false) ;
		this.breakdownElement.setHasMultipleOccurrences(false) ;
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
	 * Test method for {@link woops2.model.element.BreakdownElement#clone()}.
	 */
	public void testClone() {
		try{
			assertEquals(this.breakdownElement, this.breakdownElement.clone()) ;
		}
		catch(CloneNotSupportedException e){
			fail("Error CloneNotSupportedException in the testClone method") ;
		}
	}

	/**
	 * Test method for {@link woops2.model.breakdownelement.BreakdownElement#hashCode()}.
	 */
	public void testHashCode() {
		// Rk: the setUp method is called here.

		BreakdownElement bdeTmp1 = new BreakdownElement() ;
		bdeTmp1.setIdEPF("idEPF1");
		bdeTmp1.setName("name1");
		bdeTmp1.setDescription("description1");
		bdeTmp1.setPrefix("prefix1") ;
		bdeTmp1.setIsOptional(true) ;
		bdeTmp1.setIsPlanned(false) ;
		bdeTmp1.setHasMultipleOccurrences(false) ;

		assertNotNull(this.breakdownElement.hashCode()) ;
		assertNotNull(bdeTmp1.hashCode()) ;
		assertEquals(this.breakdownElement.hashCode(), bdeTmp1.hashCode()) ;

		// Rk: the tearDown method is called here.

	}

	/**
	 * Test method for {@link woops2.model.breakdownelement.BreakdownElement#equals(Object obj)}.
	 */
	public void testEquals() {
		// Rk: the setUp method is called here.

		// Assert if it's equal by references.
		assertTrue("By references", this.breakdownElement.equals(this.breakdownElement)) ;

		// Assert if it's equal field by field.
		BreakdownElement bdeTmp1 = new BreakdownElement() ;
		bdeTmp1.setIdEPF("idEPF1");
		bdeTmp1.setName("name1");
		bdeTmp1.setDescription("description1");
		bdeTmp1.setPrefix("prefix1") ;
		bdeTmp1.setIsOptional(true) ;
		bdeTmp1.setIsPlanned(false) ;
		bdeTmp1.setHasMultipleOccurrences(false) ;
		assertTrue("Field by field", this.breakdownElement.equals(bdeTmp1)) ;

		// Assert if it's not equal.
		BreakdownElement bdeTmp2 = new BreakdownElement() ;
		bdeTmp2.setIdEPF("idEPF2");
		bdeTmp2.setName("name2");
		bdeTmp2.setDescription("description2");
		bdeTmp2.setPrefix("prefix2") ;
		bdeTmp2.setIsOptional(true) ;
		bdeTmp2.setIsPlanned(false) ;
		bdeTmp2.setHasMultipleOccurrences(false) ;
		assertFalse("Not equals", this.breakdownElement.equals(bdeTmp2)) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link woops2.model.breakdownelement.BreakdownElement#addToActivity(woops2.model.activity.Activity)}.
	 */
	public void testAddToActivity() {
		// Rk: the setUp method is called here.

		assertTrue("Empty (begin)", this.breakdownElement.getSuperActivities().isEmpty()) ;

		Activity activity1 = new Activity() ;
		activity1.setIdEPF("idEPF");
		activity1.setName("name1") ;
		activity1.setDescription("description1") ;
		activity1.setPrefix("prefix1") ;
		activity1.setIsOptional(true) ;
		activity1.setIsPlanned(false) ;
		activity1.setHasMultipleOccurrences(false) ;
		activity1.setIsEvenDriven(true) ;
		activity1.setIsOngoing(false) ;
		activity1.setIsRepeatable(true) ;
		this.breakdownElement.addToActivity(activity1) ;
		assertTrue("acts.size == 1", this.breakdownElement.getSuperActivities().size() == 1) ;
		assertTrue("bdes.size1 == 1", activity1.getBreakDownElements().size() == 1) ;

		Activity activity2 = new Activity() ;
		activity2.setIdEPF("idEPF");
		activity2.setName("name2") ;
		activity2.setDescription("description2") ;
		activity2.setPrefix("prefix2") ;
		activity2.setIsOptional(true) ;
		activity2.setIsPlanned(false) ;
		activity2.setHasMultipleOccurrences(false) ;
		activity2.setIsEvenDriven(true) ;
		activity2.setIsOngoing(false) ;
		activity2.setIsRepeatable(true) ;
		this.breakdownElement.addToActivity(activity2) ;
		assertTrue("acts.size ==  2", this.breakdownElement.getSuperActivities().size() == 2) ;
		assertTrue("bdes.size2 ==  1", activity2.getBreakDownElements().size() == 1) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link woops2.model.breakdownelement.BreakdownElement#addToAllActivities(java.util.Set<Activity>)}.
	 */
	public void testAddToAllActivities() {
		// Rk: the setUp method is called here.

		Activity activity1 = new Activity() ;
		activity1.setIdEPF("idEPF");
		activity1.setName("name1") ;
		activity1.setDescription("description1") ;
		activity1.setPrefix("prefix1") ;
		activity1.setIsOptional(true) ;
		activity1.setIsPlanned(false) ;
		activity1.setHasMultipleOccurrences(false) ;
		activity1.setIsEvenDriven(true) ;
		activity1.setIsOngoing(false) ;
		activity1.setIsRepeatable(true) ;

		Activity activity2 = new Activity() ;
		activity2.setIdEPF("idEPF");
		activity2.setName("name2") ;
		activity2.setDescription("description2") ;
		activity2.setPrefix("prefix2") ;
		activity2.setIsOptional(true) ;
		activity2.setIsPlanned(false) ;
		activity2.setHasMultipleOccurrences(false) ;
		activity2.setIsEvenDriven(true) ;
		activity2.setIsOngoing(false) ;
		activity2.setIsRepeatable(true) ;

		Set<Activity> activities = new HashSet<Activity>() ;
		activities.add(activity1) ;
		activities.add(activity2) ;

		this.breakdownElement.addToAllActivities(activities) ;

		assertTrue("acts.size ==  2", this.breakdownElement.getSuperActivities().size() == 2) ;
		assertTrue("bdes1.size == 1", activity1.getBreakDownElements().size() == 1) ;
		assertTrue("bdes2.size == 1", activity2.getBreakDownElements().size() == 1) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link woops2.model.breakdownelement.BreakdownElement#removeFromActivity(woops2.model.activity.Activity)}.
	 */
	public void testRemoveFromActivity() {
		// Rk: the setUp method is called here.

		Activity activity = new Activity() ;
		activity.setIdEPF("idEPF");
		activity.setName("name1") ;
		activity.setDescription("description1") ;
		activity.setPrefix("prefix1") ;
		activity.setIsOptional(true) ;
		activity.setIsPlanned(false) ;
		activity.setHasMultipleOccurrences(false) ;
		activity.setIsEvenDriven(true) ;
		activity.setIsOngoing(false) ;
		activity.setIsRepeatable(true) ;
		this.breakdownElement.addToActivity(activity) ;
		this.breakdownElement.removeFromActivity(activity) ;

		assertTrue(this.breakdownElement.getSuperActivities().isEmpty()) ;
		assertTrue(activity.getBreakDownElements().isEmpty()) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link woops2.model.breakdownelement.BreakdownElement#removeFromAllActivity()}.
	 */
	public void testRemoveFromAllActivities() {
		// Rk: the setUp method is called here.

		Activity activity = new Activity() ;
		activity.setIdEPF("idEPF");
		activity.setName("name1") ;
		activity.setDescription("description1") ;
		activity.setPrefix("prefix1") ;
		activity.setIsOptional(true) ;
		activity.setIsPlanned(false) ;
		activity.setHasMultipleOccurrences(false) ;
		activity.setIsEvenDriven(true) ;
		activity.setIsOngoing(false) ;
		activity.setIsRepeatable(true) ;

		Activity activity2 = new Activity() ;
		activity2.setIdEPF("idEPF");
		activity2.setName("name2") ;
		activity2.setDescription("description2") ;
		activity2.setPrefix("prefix2") ;
		activity2.setIsOptional(true) ;
		activity2.setIsPlanned(false) ;
		activity2.setHasMultipleOccurrences(false) ;
		activity2.setIsEvenDriven(true) ;
		activity2.setIsOngoing(false) ;
		activity2.setIsRepeatable(true) ;

		assertTrue(this.breakdownElement.getSuperActivities().isEmpty()) ;
		assertTrue(activity.getBreakDownElements().isEmpty()) ;

		// Rk: the tearDown method is called here.
	}
}
