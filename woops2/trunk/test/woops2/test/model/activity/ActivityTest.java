package woops2.test.model.activity;

import junit.framework.TestCase;
import woops2.model.activity.Activity;
import woops2.model.breakdownelement.BreakdownElement;

/**
 * @author morpheus
 * 
 * This class represents the class test of the Activity class.
 * 
 */
public class ActivityTest extends TestCase {

	public static final String PREFIX = "prefix";

	public static final Boolean IS_OPTIONAL = true;

	public void testAddToBreakdownElement() {

		Activity activity = new Activity();
		activity.setPrefix(PREFIX);
		activity.setIsOptional(IS_OPTIONAL);

		BreakdownElement breakdownElement = new BreakdownElement();
		breakdownElement.setPrefix(PREFIX);
		breakdownElement.setIsOptional(IS_OPTIONAL);

		activity.addToBreakdownElement(breakdownElement);

		assertTrue(activity.getBreakDownElements().size() != 0);
		assertTrue(breakdownElement.getSuperActivities().size() != 0);
	}
}
