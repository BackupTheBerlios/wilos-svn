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

package wilos.test.business.services.misc.project ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.misc.project.ProjectService;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.test.TestConfiguration;

public class ProjectServiceTest {

	private ProjectService ps ;

	private Project p ;

	private Project p2 ;
	private Participant parti ;
	
	public ProjectServiceTest(){
		this.ps = (ProjectService) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectService") ;
	}

	@Before
	public void setUp() {
		
		p = new Project() ;
		p.setConcreteName("Wilos") ;
		p.setDescription("projet de test") ;
		p.setIsFinished(true) ;
		this.ps.getProjectDao().saveOrUpdateProject(p);

		p2 = new Project() ;
		p2.setConcreteName("Wilos2") ;
		p2.setDescription("projet de test2") ;
		p2.setIsFinished(false) ;
		this.ps.getProjectDao().saveOrUpdateProject(p2);
	}

	@After
	public void tearDown(){
		// Delete the tmp Project from the database.
		this.ps.getProjectDao().deleteProject(this.p) ;
		this.ps.getProjectDao().deleteProject(this.p2) ;
	}

	@Test
	public void testSaveProject() {
		this.ps.saveProject(this.p) ;
		Project ProjectTmp = (Project) this.ps.getProjectDao().getProject(this.p.getId()) ;
		assertNotNull(ProjectTmp) ;
		assertEquals(ProjectTmp.getConcreteName(), "Wilos") ;
		assertEquals(ProjectTmp.getDescription(), "projet de test") ;
		// assertEquals(ProjectTmp.getIsFinished(), true) ;
	}

	@Test
	public void testProjectExists() {
		// Test for an existing project
		this.ps.saveProject(this.p) ;
		assertTrue(this.ps.projectExist("Wilos")) ;

		// Test for a non-existing project
		assertFalse(this.ps.projectExist("poulou")) ;
	}

	@Test
	public void testGetUnfinishedProjects() {
		this.ps.saveProject(this.p) ;
		this.ps.saveProject(this.p2) ;

		Set<Project> unfProjects = this.ps.getUnfinishedProjects() ;
		
		for(Iterator iter = unfProjects.iterator(); iter.hasNext();){
			Project project = (Project) iter.next() ;
			assertFalse(project.getIsFinished());
		}
		
	}
	
	@Test
	public void testAddParticipant() {
		parti = new Participant() ;		
		Set<Participant> participants = new HashSet<Participant>() ;
		
		assertFalse(participants.contains(parti)) ;		
		this.ps.addParticipant(parti, p) ;
		participants = this.p.getParticipants() ;
		assertTrue(participants.contains(parti)) ;		
	}
}
