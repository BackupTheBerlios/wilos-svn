
package wilos.test.hibernate.activity ;

import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.model.spem2.activity.Activity;
import wilos.test.TestConfiguration;

/*
 * Unit test for ActivityDao
 * 
 * @author deder
 * @author garwind
 */
public class ActivityDaoTest extends TestCase {
	
	private ActivityDao activityDao = null ;

	private Activity activity = null ;

	public static final String NAME = "thisActivity" ;

	public static final String DESCRIPTION = "activity description" ;

	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_OPTIONAL = true ;

	public static final Boolean HAS_MULTIPLE_OCCURENCES = true ;

	public static final Boolean IS_EVEN_DRIVEN = true ;

	public static final Boolean IS_ON_GOING = true ;

	public static final Boolean IS_PLANNED = true ;

	public static final Boolean IS_REPEATABLE = true ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the ActivityDao Singleton for managing Activity data
		this.activityDao = (ActivityDao) TestConfiguration.getInstance().getApplicationContext().getBean("ActivityDao") ;

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
		try{
			this.activityDao.deleteActivity(this.activity) ;
		}
		catch(Exception exception){
			System.err.println("exception e ="+exception);
		}
	}

	/*
	 * Test method for
	 * {@link woops2.hibernate.activity.ActivityDao#saveOrUpdateActivity(woops2.model.activity.Activity)}.
	 * 
	 */
	public void testSaveOrUpdateActivity() {
		// Rk: the setUp method is called here.

		//Save the activity with the method to test.
		this.activityDao.saveOrUpdateActivity(this.activity) ;
		
		// Check the saving.
		String id = this.activity.getId() ;
		Activity activityTmp = (Activity) this.activityDao.getActivity(id) ;
		assertNotNull(activityTmp) ;
		
		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.activity.ActivityDao#getAllActivities()}.
	 * 
	 */
	public void testGetAllActivities() {
		//Rk: the setUp method is called here.
		
		//Save the activity with the method to test.
		this.activityDao.saveOrUpdateActivity(this.activity) ;
		
		// Look if this activity is also into the database and look if the size of the set is >= 1.
		List<Activity> activities = this.activityDao.getAllActivities() ;
		assertNotNull(activities) ;
		assertTrue(activities.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.activity.ActivityDao#getActivity()}.
	 * 
	 */
	public void testGetActivity() {
		// Rk: the setUp method is called here.

		// Add properties to the activity.
		this.activity.setName(NAME) ;
		this.activity.setDescription(DESCRIPTION) ;
		this.activity.setPrefix(PREFIX) ;
		this.activity.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES) ;
		this.activity.setIsEvenDriven(IS_EVEN_DRIVEN) ;
		this.activity.setIsOngoing(IS_ON_GOING) ;
		this.activity.setIsOptional(IS_OPTIONAL) ;
		this.activity.setIsPlanned(IS_PLANNED) ;
		this.activity.setIsRepeatable(IS_REPEATABLE) ;

		// Save the activity into the database.
		this.activityDao.saveOrUpdateActivity(this.activity) ;
		String id = this.activity.getId() ;

		// Test the method getActivity with an existing activity.
		Activity activityTmp = this.activityDao.getActivity(id) ;
		assertNotNull(activityTmp) ;
		assertEquals("Name", activityTmp.getName(), NAME) ;
		assertEquals("Description", activityTmp.getDescription(), DESCRIPTION) ;
		assertEquals("Prefix", activityTmp.getPrefix(), PREFIX) ;
		assertEquals("HasMultipleOccurences", activityTmp.getHasMultipleOccurrences(), HAS_MULTIPLE_OCCURENCES) ;
		assertEquals("IsEvenDriven", activityTmp.getIsEvenDriven(), IS_EVEN_DRIVEN) ;
		assertEquals("IsOnGoing", activityTmp.getIsOngoing(), IS_ON_GOING) ;
		assertEquals("IsOptional", activityTmp.getIsOptional(), IS_OPTIONAL) ;
		assertEquals("IsPlanned", activityTmp.getIsPlanned(), IS_PLANNED) ;
		assertEquals("IsRepeatale", activityTmp.getIsRepeatable(), IS_REPEATABLE) ;

		// Test the method getActivity with an unexisting activity.
		this.activityDao.deleteActivity(this.activity) ;
		activityTmp = this.activityDao.getActivity(id) ;
		assertNull(activityTmp) ;

		// Rk: the tearDown method is called here.
	}

	/*
	 * Test method for {@link woops2.hibernate.activity.ActivityDao#deleteActivity()}.
	 * 
	 */
	public void testDeleteActivity() {
		// Rk: the setUp method is called here.

		// Save the activity into the database.
		this.activityDao.saveOrUpdateActivity(this.activity) ;
		String id = this.activity.getId() ;

		// Test the method deleteActivity with an activity existing into the db.
		this.activityDao.deleteActivity(this.activity) ;

		// See if this.activity is now absent in the db.
		Activity activityTmp = (Activity) this.activityDao.getActivity(id) ;
		assertNull(activityTmp) ;

		// Test the method deleteActivity with an activity unexisting into the db.
		// Normally here there are no exception thrown.
		this.activityDao.deleteActivity(this.activity) ;

		// Rk: the tearDown method is called here.
	}
}
