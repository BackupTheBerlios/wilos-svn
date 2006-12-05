
package wilos.test.business.activity ;

import java.util.List ;

import junit.framework.TestCase ;
import wilos.business.services.activity.ActivityService;
import wilos.model.spem2.activity.Activity;
import wilos.test.TestConfiguration ;

/**
 * @author deder
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

		// Get the ActivityService Singleton for managing Activity data
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

	/*
	 * (non-Javadoc) 
	 * Test method for
	 * {@link woops2.business.activity.ActivityManager#getActivitiesList()}.
	 * 
	 */
	public void testGetActivitiesList() {
		// Rk: the setUp method is called here.

		// Save the activity.
		this.activityService.saveActivity(this.activity) ;

		// Look if this activity is also into the database and look if the size of the set is >= 1.
		List<Activity> activities = this.activityService.getActivitiesList() ;
		assertNotNull(activities) ;
		assertTrue(activities.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	/* (non-Javadoc)
	 * Test method for
	 * {@link woops2.business.activity.ActivityManager#saveActivity(woops2.model.activity.Activity)}.
	 * 
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
