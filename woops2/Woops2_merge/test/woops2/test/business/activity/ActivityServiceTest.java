
package woops2.test.business.activity ;

import java.util.List;

import junit.framework.TestCase;
import woops2.business.activity.ActivityService;
import woops2.model.activity.Activity;
import woops2.test.TestConfiguration;

/**
 * @author Mathieu BENOIT.
 * 
 */
public class ActivityServiceTest extends TestCase {

	private ActivityService activityService ;

	private Activity activity ;

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the ActivityDao Singleton for managing Activity data
		this.activityService = (ActivityService) TestConfiguration.getInstance().getApplicationContext().getBean("ActivityService") ;

		// Create empty Activity
		this.activity = new Activity() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		// Delete the tmp activity from the database.
		this.activityService.getActivityDao().deleteActivity(this.activity) ;
	}

	/**
	 * Test method for {@link woops2.business.activity.ActivityManager#getActivitiesList()}.
	 * 
	 * PRINCIPLE Create a tmp activity, save it into the database. Then get all activities from the
	 * database with the method to test, and look if the size of the activities set got is >= 1. To
	 * finish delete this tmp activity from the database.
	 */
	public void testGetActivitiesList() {
		// Rk: the setUp method is called here.

		// Save the activity.
		this.activityService.getActivityDao().saveOrUpdateActivity(this.activity) ;

		// Look if this activity is also into the database and look if the size of the set is >= 1.
		List<Activity> activities = this.activityService.getActivitiesList() ;
		assertNotNull(activities) ;
		assertTrue(activities.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link woops2.business.activity.ActivityManager#saveActivity(woops2.model.activity.Activity)}.
	 * 
	 * PRINCIPLE Create a tmp activity, save it into the database with the method to test. Then look
	 * for the database to check if this tmp activity exists. To finish delete this tmp activity
	 * from the database.
	 */
	public void testSaveActivity() {
		// Rk: the setUp method is called here.

		// Save the activity to test the method saveActivity.
		this.activity.setPrefix(PREFIX) ;
		this.activity.setIsOptional(IS_OPTIONAL) ;
		this.activityService.saveActivity(this.activity) ;
		String id = this.activity.getId() ;

		// Look if this activity is also into the database.
		Activity activityTmp = (Activity) this.activityService.getActivityDao().getActivity(id) ;
		assertNotNull(activityTmp) ;
		assertEquals(activityTmp.getPrefix(), PREFIX) ;
		assertEquals(activityTmp.getIsOptional(), IS_OPTIONAL) ;

		// Rk: the tearDown method is called here.
	}

}
