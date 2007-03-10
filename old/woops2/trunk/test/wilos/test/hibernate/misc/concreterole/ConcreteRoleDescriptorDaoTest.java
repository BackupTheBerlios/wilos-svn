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
package wilos.test.hibernate.misc.concreterole;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.test.TestConfiguration;

/**
 * 
 * @author Almiriad
 * 
 */
public class ConcreteRoleDescriptorDaoTest {

	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao = null;

	private ConcreteRoleDescriptor concreteRoleDescriptor = null;

	/**
	 * attributes from ConcreteTaskDescriptor
	 */
	public static final String CONCRETE_NAME = "concreteName";

	/**
	 * attributes from Project
	 */

	public static final String PROJECT_NAME = "projectname";

	public static final Date CREATION_DATE = new Date();

	public static final Date LAUNCHING_DATE = new Date();

	public static final boolean IS_FINISHED = false;

	/**
	 * attributes from Participant
	 */

	public static final String PARTICIPANT_NAME = "participantname";

	public static final String PARTICIPANT_FIRSTNAME = "participantfirstname";

	public static final String EMAIL = "email";

	public static final String LOGIN = "login";

	public static final String PASSWORD = "password";

	public ConcreteRoleDescriptorDaoTest() {
		// Get the TaskDescriptorDao Singleton for managing TaskDescriptor data
		this.concreteRoleDescriptorDao = (ConcreteRoleDescriptorDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteRoleDescriptorDao");
	}

	@Before
	public void setUp() {

		// Create empty TaskDescriptor
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor();
		this.concreteRoleDescriptor.setConcreteName(CONCRETE_NAME);
	}

	@After
	public void tearDown() {

		this.concreteRoleDescriptorDao
				.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);
	}

	@Test
	public void testSaveOrUpdateConcreteRoleDescriptor() {
		// Rk: the setUp method is called here.

		// Save the roleDescriptor with the method to test.
		this.concreteRoleDescriptorDao
				.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);

		// Check the saving.
		String id = concreteRoleDescriptor.getId();
		ConcreteRoleDescriptor roleDescriptorTmp = (ConcreteRoleDescriptor) this.concreteRoleDescriptorDao
				.getHibernateTemplate().get(ConcreteRoleDescriptor.class, id);
		assertNotNull(roleDescriptorTmp);
		assertEquals(roleDescriptorTmp.getConcreteName(), CONCRETE_NAME);

		// Delete the data stored in the database
		this.concreteRoleDescriptorDao
				.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);
		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetAllConcreteRoleDescriptors() {
		// Rk: the setUp method is called here.

		// Save the roleDescriptor into the database.
		this.concreteRoleDescriptorDao.getHibernateTemplate().saveOrUpdate(
				this.concreteRoleDescriptor);

		// Look if this roleDescriptor is also into the database and look if the
		// size of the set is >= 1.
		List<ConcreteRoleDescriptor> concreteRoleDescriptors = this.concreteRoleDescriptorDao
				.getAllConcreteRoleDescriptors();
		assertNotNull(concreteRoleDescriptors);
		assertTrue(concreteRoleDescriptors.size() >= 1);

		// Delete the data stored in the database
		this.concreteRoleDescriptorDao
				.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);
		// Rk: the tearDown method is called here.
	}

	@Test
	public void testDeleteConcreteRoleDescriptor() {
		// Rk: the setUp method is called here.

		// Save the taskDescriptor into the database.
		this.concreteRoleDescriptorDao.getHibernateTemplate().saveOrUpdate(
				this.concreteRoleDescriptor);
		String id = this.concreteRoleDescriptor.getId();

		// Test the method deleteTaskDescriptor with an acitivity existing into
		// the db.
		this.concreteRoleDescriptorDao
				.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);

		// See if this.taskDescriptor is now absent in the db.
		ConcreteRoleDescriptor concreteRoleDescriptorTmp = (ConcreteRoleDescriptor) this.concreteRoleDescriptorDao
				.getHibernateTemplate().get(ConcreteRoleDescriptor.class, id);
		assertNull(concreteRoleDescriptorTmp);

		// Test the method deleteTaskDescriptor with a taskDescriptor unexisting
		// into the db.
		// Normally here there are no exception thrown.
		this.concreteRoleDescriptorDao
				.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetConcreteRoleDescriptor() {
		// Adds properties to the concreteBreakdownElement.
		this.concreteRoleDescriptor.setConcreteName(CONCRETE_NAME);

		// Saves the concreteBreakdownElement into the database.
		this.concreteRoleDescriptorDao
				.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);
		String id = this.concreteRoleDescriptor.getId();

		// Tests the method getBreakdownElement with an existing
		// concreteBreakdownElement.
		ConcreteBreakdownElement cbdeTmp = this.concreteRoleDescriptorDao
				.getConcreteRoleDescriptor(id);
		assertNotNull(cbdeTmp);
		assertEquals("concreteName", cbdeTmp.getConcreteName(), CONCRETE_NAME);

		// Tests the method getConcreteBreakdownElement with an unexisting
		// concreteBreakdownElement.
		this.concreteRoleDescriptorDao.getHibernateTemplate().delete(
				this.concreteRoleDescriptor);
		cbdeTmp = this.concreteRoleDescriptorDao.getConcreteRoleDescriptor(id);
		assertNull(cbdeTmp);

		// Delete the data stored in the database
		this.concreteRoleDescriptorDao
				.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);
		// Rk: the tearDown method is called here.
	}
}
