package woops2.test.model.breakdownelement;

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

	private BreakdownElement breakdownElement;

	public static final String PREFIX = "prefix";

	public static final Boolean IS_OPTIONAL = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.breakdownElement = new BreakdownElement();
		this.breakdownElement.setPrefix(PREFIX);
		this.breakdownElement.setIsOptional(IS_OPTIONAL);
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
	 * Test method for
	 * {@link woops2.model.breakdownelement.BreakdownElement#hashCode()}.
	 */
	public void testHashCode() {
		// Rk: the setUp method is called here.

		assertNotNull(this.breakdownElement.hashCode());

		// Rk: the tearDown method is called here.

	}

	/**
	 * Test method for
	 * {@link woops2.model.breakdownelement.BreakdownElement#equals(Object obj)}.
	 */
	public void testEquals() {
		// Rk: the setUp method is called here.

		// Assert if it's equal by references.
		assertTrue("By references", this.breakdownElement.equals(this.breakdownElement));

		// Assert if it's equal field by field.
		BreakdownElement bdeTmp1 = new BreakdownElement();
		bdeTmp1.setPrefix(PREFIX);
		bdeTmp1.setIsOptional(IS_OPTIONAL);
		assertTrue("Field by field", this.breakdownElement.equals(bdeTmp1));

		// Assert if it's not equal.
		BreakdownElement bdeTmp2 = new BreakdownElement();
		bdeTmp2.setPrefix("prefixFalse");
		bdeTmp2.setIsOptional(true);
		assertFalse("Not equals", this.breakdownElement.equals(bdeTmp2));

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link woops2.model.breakdownelement.BreakdownElement#addToActivity(woops2.model.activity.Activity)}.
	 */
	public void testAddToActivity() {
		// Rk: the setUp method is called here.

		assertTrue("Empty (begin)", this.breakdownElement.getSuperActivities().isEmpty());

		Activity activity1 = new Activity();
		this.breakdownElement.addToActivity(activity1);
		assertTrue("acts.size == 1", this.breakdownElement.getSuperActivities().size() == 1);
		assertTrue("bdes.size1 == 1", activity1.getBreakDownElements().size() == 1);

		Activity activity2 = new Activity();
		this.breakdownElement.addToActivity(activity2);
		assertTrue("acts.size ==  2", this.breakdownElement.getSuperActivities().size() == 2);
		assertTrue("bdes.size2 ==  1", activity2.getBreakDownElements().size() == 1);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link woops2.model.breakdownelement.BreakdownElement#addToAllActivities(java.util.Set<Activity>)}.
	 */
	public void testAddToAllActivities() {
		// Rk: the setUp method is called here.

		Activity activity1 = new Activity();
		Activity activity2 = new Activity();
		activity2.setName("name");
		Set<Activity> activities = new HashSet<Activity>() ;
		activities.add(activity1);
		activities.add(activity2);
		
		this.breakdownElement.addToAllActivities(activities);
		
		assertTrue("acts.size ==  2", this.breakdownElement.getSuperActivities().size() == 2);
		assertTrue("bdes1.size == 1", activity1.getBreakDownElements().size() == 1);
		assertTrue("bdes2.size == 1", activity2.getBreakDownElements().size() == 1);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link woops2.model.breakdownelement.BreakdownElement#removeFromActivity(woops2.model.activity.Activity)}.
	 */
	public void testRemoveFromActivity() {
		// Rk: the setUp method is called here.

		Activity activity = new Activity();
		this.breakdownElement.addToActivity(activity);
		this.breakdownElement.removeFromActivity(activity);

		assertTrue(this.breakdownElement.getSuperActivities().isEmpty());
		assertTrue(activity.getBreakDownElements().isEmpty());

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link woops2.model.breakdownelement.BreakdownElement#removeFromAllActivity()}.
	 */
	public void testRemoveFromAllActivities() {
		// Rk: the setUp method is called here.

		Activity activity1 = new Activity();
		Activity activity2 = new Activity();
		activity2.setName("name");
		Set<Activity> activities = new HashSet<Activity>() ;
		activities.add(activity1);
		activities.add(activity2);
		this.breakdownElement.addToAllActivities(activities);
		
		this.breakdownElement.removeFromAllActivities();
		
		assertTrue("acts.size ==  0", this.breakdownElement.getSuperActivities().size() == 0);
		assertTrue("bdes1.size == 1", activity1.getBreakDownElements().size() == 1);
		assertTrue("bdes2.size == 1", activity2.getBreakDownElements().size() == 1);


		// Rk: the tearDown method is called here.
	}
}
