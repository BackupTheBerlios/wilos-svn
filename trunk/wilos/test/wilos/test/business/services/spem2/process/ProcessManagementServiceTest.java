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

package wilos.test.business.services.spem2.process;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.spem2.process.ProcessManagementService;
import wilos.model.misc.project.Project;
import wilos.model.spem2.process.Process;
import wilos.test.TestConfiguration;

public class ProcessManagementServiceTest {

	private ProjectService projectS;

	private ProcessManagementService pms;

	private Process process;

	private Project project;

	public ProcessManagementServiceTest() {
		this.projectS = (ProjectService) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProjectService");
		this.pms = (ProcessManagementService) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProcessManagementService");
	}

	@Before
	public void setUp() {
		this.process = new Process();
		this.process.setName("TestProcess");
		this.process.setDescription("description test");
	}

	@After
	public void tearDown() {
		//None.
	}

	@Test
	public void testHasBeenInstanciated() {
		this.project = new Project();
		this.projectS.getProjectDao().saveOrUpdateProject(this.project);
		this.process.addProject(this.project);
		this.pms.getProcessDao().saveOrUpdateProcess(this.process);
		assertTrue(this.pms.hasBeenInstanciated(this.process.getId()));
		this.projectS.getProjectDao().deleteProject(this.project);
	}

}
