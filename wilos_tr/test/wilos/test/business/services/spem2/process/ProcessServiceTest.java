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
package wilos.test.business.services.spem2.process;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.spem2.process.ProcessService;
import wilos.model.spem2.process.Process;
import wilos.test.TestConfiguration;


public class ProcessServiceTest {

	private ProcessService processService;

	private Process process;

	public ProcessServiceTest() {
		// Get the ActivityDao Singleton for managing Activity data
		this.processService = (ProcessService) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProcessService");
	}

	@Before
	public void setUp() {

		// Create empty Activity
		this.process = new Process();
	}

	@After
	public void tearDown() {

		// Delete the tmp activity from the database.
		this.processService.getProcessDao().deleteProcess(this.process);
	}

	@Test
	public final void testGetProcessesList() {
		// Rk: the setUp method is called here.

		// Save the activity.
		//FIXME rajouter le wilosuserid en param de saveProcess
		//this.processService.saveProcess(this.process);

		// Look if this activity is also into the database and look if the size
		// of the set is >= 1.
		List<Process> processes = this.processService.getProcessesList();
		assertNotNull(processes);
		assertTrue(processes.size() >= 1);

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testSaveProcess() {
		// Rk: the setUp method is called here.

		// Save the activity.
		//FIXME rajouter le wilosuserid en param de saveProcess
		//this.processService.saveProcess(this.process);

		// Rk: the tearDown method is called here.
	}
}
