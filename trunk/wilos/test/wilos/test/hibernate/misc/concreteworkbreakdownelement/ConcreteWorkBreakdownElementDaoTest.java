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
package wilos.test.hibernate.misc.concreteworkbreakdownelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElementDao;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkOrder;
import wilos.test.TestConfiguration;
import wilos.utils.Constantes;

/**
 * @author Sebastien
 *
 * Unit test for WorkBreakdownElementDao
 *
 */
public class ConcreteWorkBreakdownElementDaoTest {

	private ConcreteWorkBreakdownElementDao concreteWorkBreakdownElementDao = null;

	private ConcreteWorkBreakdownElement concreteWorkBreakdownElement = null;

	public static final String CONCRETE_NAME = "this CWBdE";

	public static Date PLANNED_STARTING_DATE = new Date();

	public static Date PLANNED_FINISHING_DATE = new Date();

	public static final float PLANNED_TIME = 2.0F;

	@Before
	public void setUp() {

		// Get the BreakdownElementDao Singleton for managing BreakdownElement
		// data
		this.concreteWorkBreakdownElementDao = (ConcreteWorkBreakdownElementDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteWorkBreakdownElementDao");

		// Create empty ConcreteWorkBreakdownElement
		this.concreteWorkBreakdownElement = new ConcreteWorkBreakdownElement();

		try {
			PLANNED_STARTING_DATE = Constantes.DATE_FORMAT
					.parse("09/02/2007 10:00");
			PLANNED_FINISHING_DATE = Constantes.DATE_FORMAT
			.parse("09/02/2007 10:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() {

		this.concreteWorkBreakdownElementDao
				.deleteConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);
	}

	@Test
	public void testSaveOrUpdateConcreteWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		ConcreteWorkOrder concreteWorkOrder = new ConcreteWorkOrder();
		concreteWorkOrder.setConcreteLinkType("link type");
		this.concreteWorkBreakdownElement
				.addConcretePredecessor(concreteWorkOrder);

		ConcreteWorkBreakdownElement cwbde = new ConcreteWorkBreakdownElement();
		cwbde.setConcreteName(CONCRETE_NAME);
		cwbde.addConcreteSuccessor(concreteWorkOrder);

		// Save the workbreakdownElement with the method to test.
		this.concreteWorkBreakdownElementDao
				.saveOrUpdateConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);
		this.concreteWorkBreakdownElementDao
				.saveOrUpdateConcreteWorkBreakdownElement(cwbde);

		// Check the saving.
		String id = this.concreteWorkBreakdownElement.getId();
		String id2 = cwbde.getId();

		ConcreteWorkBreakdownElement wbdeTmp = (ConcreteWorkBreakdownElement) this.concreteWorkBreakdownElementDao
				.getHibernateTemplate().load(
						ConcreteWorkBreakdownElement.class, id);
		ConcreteWorkBreakdownElement wbdeTmp2 = (ConcreteWorkBreakdownElement) this.concreteWorkBreakdownElementDao
				.getHibernateTemplate().load(
						ConcreteWorkBreakdownElement.class, id2);

		assertNotNull(wbdeTmp);
		assertNotNull(wbdeTmp2);

		assertTrue(this.concreteWorkBreakdownElement.getConcretePredecessors()
				.size() == 1);
		assertTrue(cwbde.getConcreteSuccessors().size() == 1);
		assertTrue(this.concreteWorkBreakdownElement.getConcretePredecessors()
				.contains(concreteWorkOrder));
		assertTrue(cwbde.getConcreteSuccessors().contains(concreteWorkOrder));

		// Delete the date stored in the database
		this.concreteWorkBreakdownElementDao
				.deleteConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);
		this.concreteWorkBreakdownElementDao
				.deleteConcreteWorkBreakdownElement(cwbde);
		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetAllConcreteWorkBreakdownElements() {
		// Rk: the setUp method is called here.

		// Save the workBreakdownElement into the database.
		this.concreteWorkBreakdownElementDao
				.saveOrUpdateConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);

		// Look if this bde is also into the database and look if the size of
		// the set is >= 1.
		List<ConcreteWorkBreakdownElement> wbdes = this.concreteWorkBreakdownElementDao
				.getAllConcreteWorkBreakdownElements();
		assertNotNull(wbdes);
		assertTrue(wbdes.size() >= 1);

		// Delete the date stored in the database
		this.concreteWorkBreakdownElementDao
				.deleteConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);
		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetConcreteWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		// Add properties to the workBreakdownElement.
		this.concreteWorkBreakdownElement.setConcreteName(CONCRETE_NAME);
		this.concreteWorkBreakdownElement
				.setPlannedFinishingDate(PLANNED_FINISHING_DATE);
		this.concreteWorkBreakdownElement
				.setPlannedStartingDate(PLANNED_STARTING_DATE);
		this.concreteWorkBreakdownElement.setPlannedTime(PLANNED_TIME);

		// Save the workBreakdownElement into the database.
		this.concreteWorkBreakdownElementDao
				.saveOrUpdateConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);
		String id = this.concreteWorkBreakdownElement.getId();

		// Test the method getConcreteWorkBreakdownElement with an existing
		// workBreakdownElement.
		ConcreteWorkBreakdownElement cwbdeTmp = this.concreteWorkBreakdownElementDao
				.getConcreteWorkBreakdownElement(id);
		assertNotNull(cwbdeTmp);
		assertEquals("ConcreteName", cwbdeTmp.getConcreteName(), CONCRETE_NAME);
		assertEquals("PlannedTime", cwbdeTmp.getPlannedTime(), PLANNED_TIME);

		// Test the method getConcreteWorkBreakdownElement with an unexisting
		// workBreakdownElement.
		this.concreteWorkBreakdownElementDao
				.deleteConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);
		cwbdeTmp = this.concreteWorkBreakdownElementDao
				.getConcreteWorkBreakdownElement(id);
		assertNull(cwbdeTmp);

		// Delete the date stored in the database
		this.concreteWorkBreakdownElementDao
				.deleteConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);
		// Rk: the tearDown method is called here.
	}

	@Test
	public void testDeleteConcreteWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		// Save the BreakdownElement into the database.
		this.concreteWorkBreakdownElementDao
				.saveOrUpdateConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);
		String id = this.concreteWorkBreakdownElement.getId();

		// Test the method deleteBreakdownElement with an BreakdownElement
		// existing into the db.
		this.concreteWorkBreakdownElementDao
				.deleteConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);

		// See if this.breakdownElement is now absent in the db.
		ConcreteWorkBreakdownElement cwbdeTmp = (ConcreteWorkBreakdownElement) this.concreteWorkBreakdownElementDao
				.getConcreteWorkBreakdownElement(id);
		assertNull(cwbdeTmp);

		// Test the method deleteBreakdownElement with an BreakdownElement
		// unexisting into the db.
		// Normally here there are no exception thrown.
		this.concreteWorkBreakdownElementDao
				.deleteConcreteWorkBreakdownElement(this.concreteWorkBreakdownElement);

		// Rk: the tearDown method is called here.
	}

}
