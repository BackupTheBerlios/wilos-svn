/**
 * 
 */
package woops2.test.model.breakdownelement;

import woops2.model.activity.Activity;
import woops2.model.breakdownelement.BreakdownElement;
import junit.framework.TestCase;

/**
 * This class represents the class test of the Activity class.
 * 
 * @author deder
 *
 */
public class BreakdownElementTest extends TestCase {
	
	private BreakdownElement breakdownElement;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.breakdownElement = new BreakdownElement();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link woops2.model.breakdownelement.BreakdownElement#addToActivity(woops2.model.activity.Activity)}.
	 */
	public void testAddToActivity() {
		assertTrue(this.breakdownElement.getSuperActivities().size() == 0);
		Activity activity1 = new Activity();
		this.breakdownElement.addToActivity(activity1);
		assertTrue(this.breakdownElement.getSuperActivities().size() == 1);
		Activity activity2 = new Activity();
		this.breakdownElement.addToActivity(activity2);
		assertTrue(this.breakdownElement.getSuperActivities().size() == 2);
	}

	/**
	 * Test method for {@link woops2.model.breakdownelement.BreakdownElement#removeFromActivity(woops2.model.activity.Activity)}.
	 */
	public void testRemoveFromActivity() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link woops2.model.breakdownelement.BreakdownElement#removeAllActivities()}.
	 */
	public void testRemoveAllActivities() {
		Activity activity1 = new Activity();
		this.breakdownElement.addToActivity(activity1);
		Activity activity2 = new Activity();
		this.breakdownElement.addToActivity(activity2);
		assertTrue(this.breakdownElement.getSuperActivities().size() == 2);
		this.breakdownElement.removeAllActivities();
		assertTrue(this.breakdownElement.getSuperActivities().size() == 0);
	}

	/**
	 * Test method for {@link woops2.model.breakdownelement.BreakdownElement#removeFromAllActivity()}.
	 */
	public void testRemoveFromAllActivity() {
		fail("Not yet implemented"); // TODO
	}
}
