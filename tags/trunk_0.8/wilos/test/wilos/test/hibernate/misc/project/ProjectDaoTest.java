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

package wilos.test.hibernate.misc.project;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.project.Project;
import wilos.test.TestConfiguration;

public class ProjectDaoTest {

	private ProjectDao pDao;

	private Project p;

	public ProjectDaoTest() {
		this.pDao = (ProjectDao) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProjectDao");
	}

	@Before
	public void setUp() {
		this.p = new Project();
		// this.p.setConcreteName("testProject") ;
		// this.p.setDescription("testDesc") ;

		this.pDao.saveOrUpdateProject(p);
	}

	@After
	public void tearDown() {
		this.pDao.deleteProject(this.p);
	}

	@Test
	public void testSaveOrUpdateProject() {
		Project pTmp = new Project();
		this.pDao.saveOrUpdateProject(pTmp);
		assertNotNull(this.pDao.getProject(pTmp.getId()));
	}

	/*@Test
	public void testGetAllProjects() {
		Set<Project> projects = this.pDao.getAllProjects();
		assertNotNull(projects);
	}*/

	@Test
	public void testGetProject() {
		Project pTmp = this.pDao.getProject("testProject");
		//assertNotNull(pTmp);
		//assertEquals(pTmp.getConcreteName(), "testProject");
		//assertEquals(pTmp.getDescription(), "testDesc");
	}

	@Test
	public void testDeleteProject() {
		Project p1 = new Project();
		p1.setConcreteName("testProjectToDelete");
		p1.setDescription("testDesc");
		this.pDao.saveOrUpdateProject(p1);
		this.pDao.deleteProject(p1);
		Project pTmp = this.pDao.getProject(p1.getId());
		assertNull(pTmp);
	}

}
