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
package wilos.test.hibernate.spem2.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.spem2.element.ElementDao;
import wilos.model.spem2.element.Element;
import wilos.test.TestConfiguration;

/**
 * Unit test for ElementDao
 *
 * @author deder
 */
public class ElementDaoTest {

	protected final Log logger = LogFactory.getLog(this.getClass());

	private ElementDao elementDao = null;

	private Element element = null;

	public static final String NAME = "thisElt";

	public static final String DESCRIPTION = "elt description";

	@Before
	public void setUp() {
		// Get the ElementDao Singleton for managing Element data
		this.elementDao = (ElementDao) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ElementDao");

		// Create empty Activity
		this.element = new Element();
	}

	@After
	public void tearDown() {
		this.elementDao.deleteElement(this.element);
	}

	@Test
	public void testSaveOrUpdateElement() {
		// Rk: the setUp method is called here.

		// Save the element with the method to test.
		this.elementDao.saveOrUpdateElement(this.element);

		// Check the saving.
		String id = this.element.getId();
		Element elementTmp = (Element) this.elementDao.getHibernateTemplate()
				.load(Element.class, id);
		assertNotNull(elementTmp);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testgetAllElements() {
		// Rk: the setUp method is called here.

		// Save the element into the database.
		this.elementDao.getHibernateTemplate().saveOrUpdate(this.element);

		// Look if this element is also into the database and look if the size
		// of the set is >= 1.
		List<Element> elements = this.elementDao.getAllElements();
		assertNotNull(elements);
		logger
				.error("####### ElementDaoTest.testgetAllElements ####### --> elements.sze() == "
						+ elements.size());
		assertTrue(elements.size() >= 1);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetElement() {
		// Rk: the setUp method is called here.

		// Add properties to the element.
		this.element.setName(NAME);
		this.element.setDescription(DESCRIPTION);

		// Save the element into the database.
		this.elementDao.saveOrUpdateElement(this.element);
		String id = this.element.getId();

		// Test the method getElement with an existing element.
		Element elementTmp = this.elementDao.getElement(id);
		assertNotNull(elementTmp);
		assertEquals("Name", elementTmp.getName(), NAME);
		assertEquals("Description", elementTmp.getDescription(), DESCRIPTION);

		// Test the method getElement with an unexisting element.
		this.elementDao.deleteElement(this.element);
		elementTmp = this.elementDao.getElement(id);
		assertNull(elementTmp);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testDeleteElement() {
		// Rk: the setUp method is called here.

		// Save the element into the database.
		this.elementDao.saveOrUpdateElement(this.element);
		String id = this.element.getId();

		// Test the method deleteElement with an element existing into the db.
		this.elementDao.deleteElement(this.element);

		// See if this.element is now absent in the db.
		Element elementTmp = (Element) this.elementDao.getElement(id);
		assertNull(elementTmp);

		// Test the method deleteElement with an element unexisting into the db.
		// Normally here there are no exception thrown.
		this.elementDao.deleteElement(this.element);

		// Rk: the tearDown method is called here.
	}
}
