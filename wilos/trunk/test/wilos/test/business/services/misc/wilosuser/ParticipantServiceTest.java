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

package wilos.test.business.services.misc.wilosuser;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.util.Security;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.test.TestConfiguration;

public class ParticipantServiceTest {

	private ParticipantService participantService;

	private ProjectService projectService;

	private Participant participant;

	private Project p1;

	private Project p2;

	private final static String LOGIN = "john";

	private final static String NAME = "georges";

	private final static String PASS = "pass";

	private final static String ROLE1 = "Testeur";

	private final static String ROLE2 = "Developpeur";

	private final static Boolean VROLE1 = true;

	private final static Boolean VROLE2 = false;

	public ParticipantServiceTest() {
		this.participantService = (ParticipantService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ParticipantService");
		// this.rs = (ConcreteRoleAffectationService)
		// TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteRoleAffectationService")
		// ;
		this.projectService = (ProjectService) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProjectService");
	}

	@Before
	public void setUp() {
		p1 = new Project();
		p1.setConcreteName("projectTestPS1");
		this.projectService.saveProject(p1);

		p2 = new Project();
		p2.setConcreteName("projectTestPS2");
		this.projectService.saveProject(p2);

		participant = new Participant();
		participant.setLogin(LOGIN);
		participant.setName(NAME);
		participant.setPassword(PASS);
		participantService.saveParticipant(participant);
	}

	@After
	public void tearDown() {
		// Delete the tmp participant from the database.
		this.participantService.getParticipantDao().deleteParticipant(
				this.participant);
		this.projectService.getProjectDao().deleteProject(this.p1);
		this.projectService.getProjectDao().deleteProject(this.p2);

	}

	@Test
	public void testGetRolesList() {
		// HashMap<String, Boolean> roles = new HashMap<String, Boolean>() ;
		// roles.put(ROLE1, VROLE1) ;
		// roles.put(ROLE2, VROLE2) ;
		// rs.saveParticipantRoles(roles, LOGIN) ;

		assertTrue(false);
	}

	@Test
	public void testSaveParticipant() {
		/*
		 * this.participant.setLogin(LOGIN); this.participant.setName(NAME);
		 * this.participant.setPassword(PASS);
		 * 
		 * this.participantService.saveParticipant(this.participant) ;
		 */
		Participant ParticipantTmp = (Participant) this.participantService
				.getParticipantDao().getParticipant(LOGIN);

		assertNotNull(ParticipantTmp);
		assertEquals(ParticipantTmp.getName(), NAME);
		assertEquals(ParticipantTmp.getLogin(), LOGIN);
		assertEquals(ParticipantTmp.getPassword(), Security.encode(PASS));
	}

	@Test
	public void testGetProjectsForAParticipant() {
		this.participant.addToProject(this.p1);
		participantService.saveParticipant(this.participant);
		Participant participant2 = new Participant();
		participant2.setLogin("test");
		participant2.setName("test");
		participant2.setPassword("test");

		participant2.addToProject(this.p2);
		participantService.saveParticipant(participant2);
		// this.projectService.saveProject(p1);
		p1 = this.projectService.getProject(p1.getId());
		// this.projectService.saveProject(p2);

		HashMap<Project, Boolean> temp = this.participantService
				.getProjectsForAParticipant(this.participant);

		for (Iterator iter = temp.keySet().iterator(); iter.hasNext();) {
			Project p = (Project) iter.next();
			if (p.getId().equals(p1.getId())) {
				assertTrue(temp.get(p));
			} else {
				assertFalse(temp.get(p));
			}

		}
		this.participantService.getParticipantDao().deleteParticipant(
				participant2);
	}

	@Test
	public void testGetAllAffectedProjectsForParticipant() {
		this.participant.addToProject(this.p1);
		participantService.saveParticipant(this.participant);
		Participant participant2 = new Participant();
		participant2.setLogin("test");
		participant2.setName("test");
		participant2.setPassword("test");

		participant2.addToProject(this.p2);
		participantService.saveParticipant(participant2);
		this.projectService.saveProject(p1);
		p1 = this.projectService.getProject(p1.getId());
		this.projectService.saveProject(p2);

		List<Project> temp = this.participantService
				.getAllAffectedProjectsForParticipant(this.participant);

		for (Iterator iter = temp.iterator(); iter.hasNext();) {
			Project p = (Project) iter.next();
			if (p.getConcreteName().equals("projectTestPS1"))
				assertTrue(p.getId().equals(p1.getId()));
			if (p.getConcreteName().equals("projectTestPS2"))
				assertTrue(p.getId().equals(p1.getId()));

		}
		this.participantService.getParticipantDao().deleteParticipant(
				participant2);
		for (Iterator iter = temp.iterator(); iter.hasNext();) {
			Project p = (Project) iter.next();
			this.projectService.getProjectDao().deleteProject(p);
		}

	}

	@Test
	public void testSaveProjectsForAParticipant() {
		HashMap<String, Boolean> affectedProjects = new HashMap<String, Boolean>();
		affectedProjects.put(this.p1.getId(), true);
		affectedProjects.put(this.p2.getId(), true);
		this.participantService.saveProjectsForAParticipant(this.participant,
				affectedProjects);
		HashMap<Project, Boolean> temp = this.participantService
				.getProjectsForAParticipant(this.participant);
		for (Iterator iter = temp.keySet().iterator(); iter.hasNext();) {
			Project p = (Project) iter.next();
			if (p.getId().equals(p1.getId())) {
				assertTrue(temp.get(p));
			}
			if (p.getId().equals(p2.getId())) {
				assertTrue(temp.get(p));
			}
		}
		HashMap<String, Boolean> affectedProjects2 = new HashMap<String, Boolean>();
		affectedProjects2.put(this.p1.getId(), true);
		affectedProjects2.put(this.p2.getId(), false);
		this.participantService.saveProjectsForAParticipant(this.participant,
				affectedProjects2);
		HashMap<Project, Boolean> temp2 = this.participantService
				.getProjectsForAParticipant(this.participant);
		for (Iterator iter = temp2.keySet().iterator(); iter.hasNext();) {
			Project p = (Project) iter.next();
			if (p.getId().equals(p1.getId())) {
				assertTrue(temp2.get(p));
			}
			if (p.getId().equals(p2.getId())) {
				assertFalse(temp2.get(p));
			}
		}
		// TODO a finir de tester pour la desaffectation du projectManager
	}

	@Test
	public void testGetManageableProjectsForAParticipant() {
		/*
		 * this.participant.addToProject(this.p1) ;
		 * this.ps.saveParticipant(this.p) ;
		 * 
		 * Participant ParticipantTmp = (Participant)
		 * this.ps.getParticipantDao().getParticipant(LOGIN) ;
		 * 
		 * HashMap<Project, Participant> plist =
		 * this.ps.getManageableProjectsForAParticipant(ParticipantTmp) ;
		 * for(Project p : plist.keySet()){
		 * if(participant.getName().equals(this.p1.getName()) &&
		 * participant.getProjectManager() == null){ assertTrue(plist.get(p) ==
		 * null) ; } else{ assertFalse(plist.get(p).getWilosuser_id() != null) ; } }
		 * this.participant.removeAllProject();
		 * this.participant.removeAllManagedProjects();
		 * this.ps.saveParticipant(this.p) ;
		 */
		assertTrue(false);
	}

	@Test
	public void testSaveManagedProjectsForAParticipant() {
		/*
		 * this.ps.saveParticipant(this.p) ; HashMap<String, Boolean>
		 * managedPjects = new HashMap<String, Boolean>();
		 * managedPjects.put(this.p1.getProject_id(), true);
		 * managedPjects.put(this.p2.getProject_id(), false);
		 * 
		 * this.ps.saveManagedProjectsForAParticipant(this.p, managedPjects) ;
		 * 
		 * Participant ParticipantTmp = (Participant)
		 * this.ps.getParticipantDao().getParticipant(LOGIN) ; HashMap<Project,
		 * Participant> plist =
		 * this.ps.getManageableProjectsForAParticipant(ParticipantTmp) ;
		 * for(Project p : plist.keySet()){
		 * if(participant.getName().equals(this.p1.getName()) &&
		 * participant.getProjectManager() == null){ assertTrue(plist.get(p) ==
		 * null) ; } else{ assertFalse(plist.get(p).getWilosuser_id() != null) ; } }
		 * this.participant.removeAllProject();
		 * this.participant.removeAllManagedProjects();
		 * this.ps.saveParticipant(this.p) ;
		 */
		assertTrue(false);
	}
}
