/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
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
package wilos.test.hibernate.misc.concretephase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.concretephase.ConcretePhaseDao;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.test.TestConfiguration;

/**
 * 
 * @author Soosuske
 * 
 */
public class ConcretePhaseDaoTest {

	private ConcretePhaseDao concretePhaseDao = null;

	private ConcretePhase concretePhase = null;

	public static final String CONCRETENAME = "thisPhase";

	public ConcretePhaseDaoTest() {
		// Get the ConcretePhaseDao Singleton for managing ConcretePhase data
		this.concretePhaseDao = (ConcretePhaseDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcretePhaseDao");
	}

	@Before
	public void setUp() {

		// Create empty Concretephase
		this.concretePhase = new ConcretePhase();

		// Save the activity with the method to test.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase);
	}

	@After
	public void tearDown() {

		this.concretePhaseDao.deleteConcretePhase(this.concretePhase);
	}

	@Test
	public final void testSaveOrUpdateConcretePhase() {
		// Rk: the setUp method is called here.

		// Save the activity with the method to test.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase);

		// Check the saving.
		String id = this.concretePhase.getId();
		ConcretePhase concretePhaseTmp = (ConcretePhase) this.concretePhaseDao
				.getConcretePhase(id);
		assertNotNull(concretePhaseTmp);

		// Delete the data stored in the database
		this.concretePhaseDao.deleteConcretePhase(this.concretePhase);
		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testGetAllConcretePhases() {
		// Rk: the setUp method is called here.

		// Save the activity with the method to test.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase);

		// Look if this activity is also into the database and look if the size
		// of the set is >= 1.
		List<ConcretePhase> concretePhases = this.concretePhaseDao
				.getAllConcretePhases();
		assertNotNull(concretePhases);
		assertTrue(concretePhases.size() >= 1);

		// Delete the data stored in the database
		this.concretePhaseDao.deleteConcretePhase(this.concretePhase);
		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testGetConcretePhase() {
		// Rk: the setUp method is called here.

		// Add properties to the activity.
		this.concretePhase.setConcreteName(CONCRETENAME);

		// Save the activity into the database.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase);
		String id = this.concretePhase.getId();

		// Test the method getActivity with an existing activity.
		ConcretePhase concretePhasesTmp = this.concretePhaseDao
				.getConcretePhase(id);
		assertNotNull(concretePhasesTmp);
		assertEquals("Name", concretePhasesTmp.getConcreteName(), CONCRETENAME);

		// Test the method getActivity with an unexisting activity.
		this.concretePhaseDao.deleteConcretePhase(this.concretePhase);
		concretePhasesTmp = this.concretePhaseDao.getConcretePhase(id);
		assertNull(concretePhasesTmp);

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testDeleteConcretePhase() {
		// Rk: the setUp method is called here.

		// Save the activity into the database.
		this.concretePhaseDao.saveOrUpdateConcretePhase(this.concretePhase);
		String id = this.concretePhase.getId();

		// Test the method deleteActivity with an activity existing into the db.
		this.concretePhaseDao.deleteConcretePhase(this.concretePhase);

		// See if this.activity is now absent in the db.
		ConcretePhase concretePhaseTmp = (ConcretePhase) this.concretePhaseDao
				.getConcretePhase(id);
		assertNull(concretePhaseTmp);

		// Test the method deleteActivity with an activity unexisting into the
		// db.
		// Normally here there are no exception thrown.
		this.concretePhaseDao.deleteConcretePhase(this.concretePhase);

		// Rk: the tearDown method is called here.
	}
}
