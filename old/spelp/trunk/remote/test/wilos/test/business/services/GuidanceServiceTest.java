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
import wilos.business.services.guide.GuidanceService;
import wilos.model.spem2.guide.Guidance;
import wilos.test.TestConfiguration;

public class GuidanceServiceTest extends TestCase {

	private GuidanceService guidanceService ;

	private Guidance guidance ;

	//public static final String id = "";
	public static final String guid = "guident" ;
	public static final String name = "le_nom" ;
	public static final String description = "la_description" ;

	
	
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the GuidelineService Singleton for managing Guideline data
		this.guidanceService = (GuidanceService) TestConfiguration.getInstance().getApplicationContext().getBean("GuidanceService") ;

		// Create empty Guideline
		this.guidance = new Guidance() ;
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		// Delete the tmp guidance from the database.
		this.guidanceService.getGuidanceDao().deleteGuidance(this.guidance) ;
	}

	
	
	
	
	
	// TODO JF : mettre la javadoc
	public void testSaveGuidance() {
		// Rk: the setUp method is called here.

		// Save the guideline to test the method saveGuideline.
		this.guidance.setGuid(guid);
		this.guidance.setName(name);
		this.guidance.setDescription(description);
		
		// TODO modifier pour la nouvelle conception de guidance
		/* this.guidance.setTaskdefinition(null);
		this.guidance.setRoledefinition(null);
		this.guidance.setActivity(null);*/
		
		

		
		
		this.guidanceService.saveGuidance(this.guidance) ;
		String id = this.guidance.getId() ;

		// Look if this guideline is also into the database.
		Guidance guidanceTmp = (Guidance) this.guidanceService.getGuidanceDao().getGuidance(id) ;
		assertNotNull(guidanceTmp) ;
		assertEquals(guidanceTmp.getGuid(), guid) ;
		assertEquals(guidanceTmp.getName(), name) ;
		assertEquals(guidanceTmp.getDescription(), description) ;

		// Rk: the tearDown method is called here.
	}

}
