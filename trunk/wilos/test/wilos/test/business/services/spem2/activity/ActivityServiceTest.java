/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package wilos.test.business.services.spem2.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.spem2.activity.Activity;
import wilos.test.TestConfiguration;

public class ActivityServiceTest {

	private ActivityService activityService;

	private ConcreteActivityDao concreteActivityDao;

	private Activity activity;

	public static final String PREFIX = "prefix";

	public static final Boolean IS_OPTIONAL = true;

	public ActivityServiceTest() {
		// Get the ActivityService Singleton for managing Activity data
		this.activityService = (ActivityService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ActivityService");
		// Get the ActivityService Singleton for managing Activity data
		this.concreteActivityDao = (ConcreteActivityDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteActivityDao");

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

	@Test
	public void testGetAllConcreteActivities() {
		// Rk: the setUp method is called here.

		ConcreteActivity cact = new ConcreteActivity();
		cact.setConcreteName("cname");
		this.concreteActivityDao.saveOrUpdateConcreteActivity(cact);

		// Save the activity to test the method saveActivity.
		this.activity.addConcreteActivity(cact);
		this.activityService.saveActivity(this.activity);
		
		// Look if this activity is also into the database.
		Set<ConcreteActivity> activityTmp = this.activityService.getAllConcreteActivities(this.activity);
		assertNotNull(activityTmp);
		assertEquals(activityTmp.size(), 1);
		
		// Rk: the tearDown method is called here.
	}

}
