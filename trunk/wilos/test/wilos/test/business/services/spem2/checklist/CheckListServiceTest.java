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

package wilos.test.business.services.spem2.checklist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.spem2.checklist.CheckListService;
import wilos.model.spem2.checklist.CheckList;
import wilos.test.TestConfiguration;

public class CheckListServiceTest {

	private CheckListService checklistService;

	private CheckList checklist;

	public static final String guid = "guident";

	public static final String name = "le_nom";

	public static final String description = "la_description";

	public CheckListServiceTest() {
		// Get the CheckListService Singleton for managing Guideline data
		this.checklistService = (CheckListService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"CheckListService");
	}

	@Before
	public void setUp() {
		// Create empty Checklist
		this.checklist = new CheckList();
	}

	@After
	public void tearDown() {
		// Delete the tmp checklist from the database.
		this.checklistService.getCheckListDao().deleteCheckList(this.checklist);
	}

	@Test
	public void testSaveCheckList() {
		// Rk: the setUp method is called here.

		// Save the checklist to test the method saveGuideline.
		this.checklist.setGuid(guid);
		this.checklist.setName(name);
		this.checklist.setDescription(description);

		/*
		 * Section tmpSection = new Section(); tmpSection.setName("name");
		 * 
		 * this.sections.add(tmpSection);
		 */

		// this.checklist.setSections(this.sections);
		// TODO modifier pour la nouvelle conception de guidance
		/*
		 * this.guidance.setTaskdefinition(null);
		 * this.guidance.setRoledefinition(null);
		 * this.guidance.setActivity(null);
		 */

		this.checklistService.saveCheckList(this.checklist);
		String id = this.checklist.getId();

		// Look if this checklist is also into the database.
		CheckList checklistTmp = (CheckList) this.checklistService
				.getCheckListDao().getCheckList(id);
		assertNotNull(checklistTmp);
		assertEquals(checklistTmp.getGuid(), guid);
		assertEquals(checklistTmp.getName(), name);
		assertEquals(checklistTmp.getDescription(), description);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetSection() {

	}
}
