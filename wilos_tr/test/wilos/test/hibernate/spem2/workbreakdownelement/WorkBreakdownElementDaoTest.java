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

package wilos.test.hibernate.spem2.workbreakdownelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.spem2.workbreakdownelement.WorkBreakdownElementDao;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkOrder;
import wilos.test.TestConfiguration;

/**
 * @author Sebastien
 * 
 * Unit test for WorkBreakdownElementDao
 * 
 */
public class WorkBreakdownElementDaoTest {

	private WorkBreakdownElementDao workBreakdownElementDao = null;

	private WorkBreakdownElement workBreakdownElement = null;

	public static final String NAME = "thisWBdE";

	public static final String DESCRIPTION = "wbde description";

	public static final String PREFIX = "prefix";

	public static final Boolean IS_OPTIONAL = true;

	public static final Boolean HAS_MULTIPLE_OCCURENCES = true;

	public static final Boolean IS_PLANNED = true;

	public static final Boolean IS_REPEATABLE = true;

	public static final Boolean IS_ONGOING = true;

	public static final Boolean IS_EVEN_DRIVEN = true;

	@Before
	public void setUp() {

		// Get the BreakdownElementDao Singleton for managing BreakdownElement
		// data
		this.workBreakdownElementDao = (WorkBreakdownElementDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"WorkBreakdownElementDao");

		// Create empty WorkBreakdownElement
		this.workBreakdownElement = new WorkBreakdownElement();
	}

	@After
	public void tearDown() {

		this.workBreakdownElementDao
				.deleteWorkBreakdownElement(this.workBreakdownElement);
	}

	@Test
	public void testSaveOrUpdateWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		WorkOrder workOrder = new WorkOrder();
		workOrder.setLinkType("link type");
		this.workBreakdownElement.addPredecessor(workOrder);

		WorkBreakdownElement wbde = new WorkBreakdownElement();
		wbde.setName(NAME);
		wbde.addSuccessor(workOrder);

		// Save the workbreakdownElement with the method to test.
		this.workBreakdownElementDao
				.saveOrUpdateWorkBreakdownElement(this.workBreakdownElement);
		this.workBreakdownElementDao.saveOrUpdateWorkBreakdownElement(wbde);

		// Check the saving.
		String id = this.workBreakdownElement.getId();
		String id2 = wbde.getId();

		WorkBreakdownElement wbdeTmp = (WorkBreakdownElement) this.workBreakdownElementDao
				.getHibernateTemplate().load(WorkBreakdownElement.class, id);
		WorkBreakdownElement wbdeTmp2 = (WorkBreakdownElement) this.workBreakdownElementDao
				.getHibernateTemplate().load(WorkBreakdownElement.class, id2);

		assertNotNull(wbdeTmp);
		assertNotNull(wbdeTmp2);

		assertTrue(this.workBreakdownElement.getPredecessors().size() == 1);
		assertTrue(wbde.getSuccessors().size() == 1);
		assertTrue(this.workBreakdownElement.getPredecessors().contains(
				workOrder));
		assertTrue(wbde.getSuccessors().contains(workOrder));

		this.workBreakdownElementDao.deleteWorkBreakdownElement(wbde);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetAllWorkBreakdownElements() {
		// Rk: the setUp method is called here.

		// Save the workBreakdownElement into the database.
		this.workBreakdownElementDao
				.saveOrUpdateWorkBreakdownElement(this.workBreakdownElement);

		// Look if this bde is also into the database and look if the size of
		// the set is >= 1.
		List<WorkBreakdownElement> wbdes = this.workBreakdownElementDao
				.getAllWorkBreakdownElements();
		assertNotNull(wbdes);
		assertTrue(wbdes.size() >= 1);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		// Add properties to the workBreakdownElement.
		this.workBreakdownElement.setName(NAME);
		this.workBreakdownElement.setDescription(DESCRIPTION);
		this.workBreakdownElement.setPrefix(PREFIX);
		this.workBreakdownElement
				.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES);
		this.workBreakdownElement.setIsOptional(IS_OPTIONAL);
		this.workBreakdownElement.setIsPlanned(IS_PLANNED);
		this.workBreakdownElement.setIsRepeatable(IS_REPEATABLE);
		this.workBreakdownElement.setIsOngoing(IS_ONGOING);
		this.workBreakdownElement.setIsEvenDriven(IS_EVEN_DRIVEN);

		// Save the workBreakdownElement into the database.
		this.workBreakdownElementDao
				.saveOrUpdateWorkBreakdownElement(this.workBreakdownElement);
		String id = this.workBreakdownElement.getId();

		// Test the method getWorkBreakdownElement with an existing
		// workBreakdownElement.
		WorkBreakdownElement wbdeTmp = this.workBreakdownElementDao
				.getWorkBreakdownElement(id);
		assertNotNull(wbdeTmp);
		assertEquals("Name", wbdeTmp.getName(), NAME);
		assertEquals("Description", wbdeTmp.getDescription(), DESCRIPTION);
		assertEquals("Prefix", wbdeTmp.getPrefix(), PREFIX);
		assertEquals("HasMultipleOccurences", wbdeTmp
				.getHasMultipleOccurrences(), HAS_MULTIPLE_OCCURENCES);
		assertEquals("IsOptional", wbdeTmp.getIsOptional(), IS_OPTIONAL);
		assertEquals("IsPlanned", wbdeTmp.getIsPlanned(), IS_PLANNED);
		assertEquals("IsRepeatable", wbdeTmp.getIsRepeatable(), IS_REPEATABLE);
		assertEquals("IsOngoing", wbdeTmp.getIsOngoing(), IS_ONGOING);
		assertEquals("IsEvenDriven", wbdeTmp.getIsEvenDriven(), IS_EVEN_DRIVEN);

		// Test the method getWorkBreakdownElement with an unexisting
		// workBreakdownElement.
		this.workBreakdownElementDao
				.deleteWorkBreakdownElement(this.workBreakdownElement);
		wbdeTmp = this.workBreakdownElementDao.getWorkBreakdownElement(id);
		assertNull(wbdeTmp);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testDeleteWorkBreakdownElement() {
		// Rk: the setUp method is called here.

		// Save the BreakdownElement into the database.
		this.workBreakdownElementDao
				.saveOrUpdateWorkBreakdownElement(this.workBreakdownElement);
		String id = this.workBreakdownElement.getId();

		// Test the method deleteBreakdownElement with an BreakdownElement
		// existing into the db.
		this.workBreakdownElementDao
				.deleteWorkBreakdownElement(this.workBreakdownElement);

		// See if this.breakdownElement is now absent in the db.
		BreakdownElement wbdeTmp = (BreakdownElement) this.workBreakdownElementDao
				.getHibernateTemplate().get(WorkBreakdownElement.class, id);
		assertNull(wbdeTmp);

		// Test the method deleteBreakdownElement with an BreakdownElement
		// unexisting into the db.
		// Normally here there are no exception thrown.
		this.workBreakdownElementDao
				.deleteWorkBreakdownElement(this.workBreakdownElement);

		// Rk: the tearDown method is called here.
	}

}
