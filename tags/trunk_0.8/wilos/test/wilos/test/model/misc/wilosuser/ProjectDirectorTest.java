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

package wilos.test.model.misc.wilosuser;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.ProjectDirector;

public class ProjectDirectorTest {

	private ProjectDirector pd1;
	private ProjectDirector pd2;
	
	private final static String LOGIN = "john" ;
	
	private final static String LOGIN2 = "cathy" ;

	private final static String NAME = "georges" ;
	
	private final static String NAME2 = "willis" ;
	
	private final static String FIRSTNAME = "johnny" ;
	
	private final static String FIRSTNAME2 = "catherine" ;

	private final static String PASS = "pass" ;
	
	private final static String PASS2 = "pass2" ;
	
	private final static String DESCRIPTION = "process1";
	private final static String DESCRIPTION2 = "process2";
	
	@Before
	public void setUp() {
		pd1 = new ProjectDirector();
		pd2 = new ProjectDirector();
	}

	@After
	public void tearDown() {
		//None.
	}

	@Test
	public void testEqualsObject() {
		pd1.setLogin(LOGIN);
		pd1.setFirstname(FIRSTNAME);
		pd1.setName(NAME);
		pd1.setPassword(PASS);
		pd2.setLogin(LOGIN);
		pd2.setFirstname(FIRSTNAME);
		pd2.setName(NAME);
		pd2.setPassword(PASS);
		assertTrue(pd1.equals(pd2));
		/*Login test*/
		pd2.setLogin(LOGIN2);
		assertFalse(pd1.equals(pd2));
		/*Name test*/
		pd2.setLogin(LOGIN);
		pd2.setName(NAME2);
		assertFalse(pd1.equals(pd2));
		/*FirstName test*/
		pd2.setName(NAME);
		pd2.setFirstname(FIRSTNAME2);
		assertFalse(pd1.equals(pd2));
		/*Password test*/
		pd2.setFirstname(FIRSTNAME);
		pd2.setPassword(PASS2);
		assertFalse(pd1.equals(pd2));
	}
	
	@Test
	public void testAddMonitoredProject() {
		Project project = new Project();
		project.setDescription(DESCRIPTION);
		pd1.addMonitoredProject(project);
		assertEquals(project,(Project)(pd1.getProjectMonitored().toArray())[0]);
		assertEquals(project.getProjectDirector(),pd1);
	}

	@Test
	public void testRemoveMonitoredProject() {
		Project project = new Project();
		project.setDescription(DESCRIPTION);
		pd1.addMonitoredProject(project);
		assertEquals(1,pd1.getProjectMonitored().size());
		pd1.removeMonitoredProject(project);		
		assertTrue(pd1.getProjectMonitored().isEmpty());
		assertEquals(project.getProjectDirector(),null);
	}

	@Test
	public void testRemoveAllMonitoredProjects() {
		Project project1 = new Project();
		project1.setDescription(DESCRIPTION);
		Project project2 = new Project();
		project2.setDescription(DESCRIPTION2);
		pd1.addMonitoredProject(project1);
		pd1.addMonitoredProject(project2);
		/*assertNotNull(pd1.getProjectMonitored());*/
		pd1.removeAllMonitoredProjects();
		assertTrue(pd1.getProjectMonitored().isEmpty());
		assertEquals(null,project1.getProjectDirector());
		assertEquals(null,project2.getProjectDirector());
	}

}
