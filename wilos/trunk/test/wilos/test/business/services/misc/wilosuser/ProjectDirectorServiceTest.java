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

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.misc.wilosuser.ProjectDirectorService;
import wilos.business.util.Security;
import wilos.model.misc.wilosuser.ProjectDirector;
import wilos.test.TestConfiguration;

public class ProjectDirectorServiceTest {

	private ProjectDirectorService projectDirectorService;

	private ProjectDirector projectDirector1;

	private ProjectDirector projectDirector2;
	
	public ProjectDirectorServiceTest(){
		this.projectDirectorService = (ProjectDirectorService) TestConfiguration
		.getInstance().getApplicationContext().getBean(
				"ProjectDirectorService");
	}

	@Before
	public void setUp() {
			this.projectDirector1 = new ProjectDirector();
		this.projectDirector1.setLogin("john");
		this.projectDirector1.setName("georges");
		this.projectDirector1.setPassword("pass");

		this.projectDirector2 = new ProjectDirector();
		this.projectDirector2.setLogin("bert");
		this.projectDirector2.setName("rand");
		this.projectDirector2.setPassword("stephane");

		this.projectDirectorService.saveProjectDirector(this.projectDirector1);
		this.projectDirectorService.saveProjectDirector(this.projectDirector2);
	}

	@After
	public void tearDown() {
		this.projectDirectorService
				.deleteProjectDirector(this.projectDirector1);
		this.projectDirectorService
				.deleteProjectDirector(this.projectDirector2);
	}

	@Test
	public void testSaveProjectDirector() {
		// this.projectDirectorService.saveProjectDirector(this.projectDirector);
		ProjectDirector ProjectDirectorTmp = (ProjectDirector) this.projectDirectorService
				.getProjectDirectorDao().getProjectDirector("bert");
		assertNotNull(ProjectDirectorTmp);
		assertEquals(ProjectDirectorTmp.getName(), "rand");
		assertEquals(ProjectDirectorTmp.getLogin(), "bert");
		assertEquals(ProjectDirectorTmp.getPassword(), Security
				.encode("stephane"));
	}

	@Test
	public void testGetProjectDirectors() {
		// this.projectDirectorService.saveProjectDirector(this.projectDirector);
		// this.projectDirectorService.saveProjectDirector(this.test);

		ProjectDirector test2 = new ProjectDirector();
		test2.setLogin("test");
		test2.setName("test");
		test2.setPassword("test");

		/*Set<ProjectDirector> ensemble = this.projectDirectorService.getProjectDirectors();

		assertNotNull(ensemble);
		assertTrue(ensemble.contains(this.projectDirector1));
		assertTrue(ensemble.contains(this.projectDirector2));
		assertFalse(ensemble.contains(test2));*/

	}

	@Test
	public void testDeleteProjectDirector() {
		// this.projectDirectorService.saveProjectDirector(this.projectDirector);
		ProjectDirector projectDirector3 = new ProjectDirector();
		projectDirector3.setLogin("test");
		projectDirector3.setName("test");
		projectDirector3.setPassword("test");
		this.projectDirectorService.saveProjectDirector(projectDirector3);

		/*Set<ProjectDirector> ensemble = this.projectDirectorService
				.getProjectDirectors();
		assertTrue(ensemble.contains(projectDirector3));
		this.projectDirectorService.deleteProjectDirector(projectDirector3);
		Set<ProjectDirector> ensemble1 = this.projectDirectorService
				.getProjectDirectors();
		assertFalse(ensemble1.contains(projectDirector3));*/

		// this.projectDirectorService.saveProjectDirector(this.projectDirector);
	}
}
