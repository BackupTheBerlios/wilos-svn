/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/

package wilos.test.business.services;

import junit.framework.TestCase;
import wilos.business.services.checklist.CheckListService;
import wilos.business.services.section.SectionService;
import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.section.Section;
import wilos.test.TestConfiguration;

public class SectionServiceTest extends TestCase {
	private SectionService sectionService ;

	private Section section ;
	
	public static final String guid = "guident" ;
	public static final String name = "le_nom" ;
	public static final String description = "la_description" ;
	
	
	protected void setUp() throws Exception {
		super.setUp();
		
		//		 Get the CheckListService Singleton for managing Guideline data
		this.sectionService = (SectionService) TestConfiguration.getInstance().getApplicationContext().getBean("SectionService") ;

		// Create empty Checklist
		this.section = new Section();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		// Delete the tmp checklist from the database.
		this.sectionService.getSectionDao().deleteSection(this.section) ;
	}

	public void testSaveSection() {
		//		 Rk: the setUp method is called here.

		// Save the checklist to test the method saveGuideline.
		this.section.setGuid(guid);
		this.section.setName(name);
		this.section.setDescription(description);
		
		/*Section tmpSection = new Section();
		tmpSection.setName("name");
		
		this.sections.add(tmpSection);*/
		
		//this.checklist.setSections(this.sections);
		
		
		// TODO modifier pour la nouvelle conception de guidance
		/* this.guidance.setTaskdefinition(null);
		this.guidance.setRoledefinition(null);
		this.guidance.setActivity(null);*/
		
		this.sectionService.saveSection(this.section) ;
		String id = this.section.getId() ;

		// Look if this checklist is also into the database.
		Section sectionTmp = (Section) this.sectionService.getSectionDao().getSection(id) ;
		assertNotNull(sectionTmp) ;
		assertEquals(sectionTmp.getGuid(), guid) ;
		assertEquals(sectionTmp.getName(), name) ;
		assertEquals(sectionTmp.getDescription(), description) ;

		// Rk: the tearDown method is called here.
	}

}
