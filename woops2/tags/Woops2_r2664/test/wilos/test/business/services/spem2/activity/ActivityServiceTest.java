package wilos.test.business.services.spem2.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.model.spem2.activity.Activity;
import wilos.test.TestConfiguration;


public class ActivityServiceTest {

	private ActivityService activityService;

	private Activity activity;

	public static final String PREFIX = "prefix";

	public static final Boolean IS_OPTIONAL = true;

	public ActivityServiceTest() {
		// Get the ActivityService Singleton for managing Activity data
		this.activityService = (ActivityService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ActivityService");

	}

	@Before
	public void setUp() {

		// Create empty Activity
		this.activity = new Activity();
	}

	@After
	public void tearDown() {
		// Delete the tmp activity from the database.
		this.activityService.getActivityDao().deleteActivity(this.activity);
	}

	@Test
	public void testGetActivitiesList() {
		// Rk: the setUp method is called here.

		// Save the activity.
		this.activityService.saveActivity(this.activity);

		// Look if this activity is also into the database and look if the size
		// of the set is >= 1.
		List<Activity> activities = this.activityService.getActivitiesList();
		assertNotNull(activities);
		assertTrue(activities.size() >= 1);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testSaveActivity() {
		// Rk: the setUp method is called here.

		// Save the activity to test the method saveActivity.
		this.activity.setPrefix(PREFIX);
		this.activity.setIsOptional(IS_OPTIONAL);
		this.activityService.saveActivity(this.activity);
		String id = this.activity.getId();

		// Look if this activity is also into the database.
		Activity activityTmp = (Activity) this.activityService.getActivityDao()
				.getActivity(id);
		assertNotNull(activityTmp);
		assertEquals(activityTmp.getPrefix(), PREFIX);
		assertEquals(activityTmp.getIsOptional(), IS_OPTIONAL);

		// Rk: the tearDown method is called here.
	}

}
