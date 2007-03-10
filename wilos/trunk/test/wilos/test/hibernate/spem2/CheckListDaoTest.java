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

package wilos.test.hibernate.spem2;

import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.spem2.checklist.CheckListDao;
import wilos.model.spem2.checklist.CheckList;
import wilos.test.TestConfiguration;

public class CheckListDaoTest extends TestCase {
	public static final String guid = "le_guid" ;
	public static final String name = "le_nom" ;
	public static final String description = "la_description" ;

	private CheckListDao checklistDAO;
	private CheckList checklist;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;

		this.checklistDAO = (CheckListDao) TestConfiguration.getInstance().getApplicationContext().getBean("CheckListDao") ;

		this.checklist = new CheckList() ;		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		this.checklistDAO.deleteCheckList(this.checklist) ;
	}

	public void testSaveOrUpdateCheckList() {
		
		//Save the checklist with the method to test.
		this.checklistDAO.saveOrUpdateCheckList(this.checklist) ;
		
		// Check the saving.
		String id = this.checklist.getId() ;
		CheckList checklistTmp = (CheckList) this.checklistDAO.getCheckList(id) ;
		assertNotNull(checklistTmp) ;
	}
	
	public void testGetAllGuidances() {	
		//Save the checklist with the method to test.
		this.checklistDAO.saveOrUpdateCheckList(this.checklist) ;
		
		// Look if this checklist is also into the database and look if the size of the set is >= 1.
		List<CheckList> checklists = this.checklistDAO.getAllCheckList();
		assertNotNull(checklists) ;
		assertTrue(checklists.size() >= 1) ;
	}
	
	public void testGetCheckList() {
		// Add properties to the checklist.
		this.checklist.setGuid(guid);
		this.checklist.setName(name);
		this.checklist.setDescription(description);
		
		// Save the checklist into the database.
		this.checklistDAO.saveOrUpdateCheckList(this.checklist);
		String id = checklist.getId();
		
		// Test the method getCheckList with an existing checklist.
		CheckList checklistTmp = this.checklistDAO.getCheckList(id);
		assertNotNull(checklistTmp);
		assertEquals("GUID", checklistTmp.getGuid(), guid) ;
		assertEquals("Name", checklistTmp.getName(), name) ;
		assertEquals("Name", checklistTmp.getDescription(), description) ;
		
		// Test the method getCheckList with an unexisting checklist.
		this.checklistDAO.deleteCheckList(this.checklist) ;
		checklistTmp = this.checklistDAO.getCheckList(id) ;
		assertNull(checklistTmp) ;
	}

	public void testDeleteCheckList() {
		// Save the checklist into the database.
		this.checklistDAO.saveOrUpdateCheckList(this.checklist) ;
		String id = this.checklist.getId() ;

		// Test the method deleteCheckList with an checklist existing into the db.
		this.checklistDAO.deleteCheckList(this.checklist) ;

		// See if this.checklist is now absent in the db.
		CheckList checklistTmp = (CheckList) this.checklistDAO.getCheckList(id) ;
		assertNull(checklistTmp) ;

		// Test the method deleteCheckList with an checklist unexisting into the db.
		// Normally here there are no exception thrown.
		this.checklistDAO.deleteCheckList(this.checklist) ;
	}	
}
