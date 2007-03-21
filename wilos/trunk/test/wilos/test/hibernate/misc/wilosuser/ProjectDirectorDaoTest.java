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

package wilos.test.hibernate.misc.wilosuser;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.wilosuser.ProjectDirectorDao;
import wilos.model.misc.wilosuser.ProjectDirector;
import wilos.test.TestConfiguration;

public class ProjectDirectorDaoTest {

	private ProjectDirectorDao pdd;

	private ProjectDirector pd;

	public ProjectDirectorDaoTest() {
		this.pdd = (ProjectDirectorDao) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProjectDirectorDao");
	}

	@Before
	public void setUp() {
		this.pd = new ProjectDirector();
		this.pd.setLogin("testLogin");
		this.pd.setName("testName");
	}

	@After
	public void tearDown() {
		this.pdd.deleteProjectDirector(this.pd);
	}

	@Test
	public void testSaveOrUpdateProjectDirector() {
		this.pdd.saveOrUpdateProjectDirector(this.pd);

		ProjectDirector pdTmp = this.pdd.getProjectDirector("testLogin");
		assertNotNull(pdTmp);
		assertTrue(this.pd.getLogin().equals(pdTmp.getLogin()));
		assertTrue(this.pd.getName().equals(pdTmp.getName()));
	}

	/*@Test
	public void testGetAllProjectDirectors() {
		this.pdd.saveOrUpdateProjectDirector(this.pd);

		Set<ProjectDirector> setTmp = this.pdd.getAllProjectDirectors();
		assertNotNull(setTmp);
		assertTrue(setTmp.size() >= 1);
	}*/

	@Test
	public void testGetProjectDirector() {
		this.pdd.saveOrUpdateProjectDirector(this.pd);

		ProjectDirector pdTmp = this.pdd.getProjectDirector("testLogin");
		assertNotNull(pdTmp);
		assertEquals(pdTmp.getLogin(), "testLogin");
		assertEquals(pdTmp.getName(), "testName");
	}

	/*@Test
	public void testDeleteProjectDirector() {
		this.pdd.saveOrUpdateProjectDirector(this.pd);
		this.pdd.deleteProjectDirector(this.pd);

		ProjectDirector pdTmp = this.pdd.getProjectDirector("testLogin");
		assertNull(pdTmp);
	}*/

}
