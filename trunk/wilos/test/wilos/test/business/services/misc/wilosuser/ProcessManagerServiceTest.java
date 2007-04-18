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

package wilos.test.business.services.misc.wilosuser;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.misc.wilosuser.ProcessManagerService;
import wilos.business.util.Security;
import wilos.model.misc.wilosuser.ProcessManager;
import wilos.model.spem2.process.Process;
import wilos.test.TestConfiguration;

public class ProcessManagerServiceTest {

	private ProcessManagerService processManagerService;

	private ProcessManager processManager1;

	private ProcessManager processManager2;

	public final Log logger = LogFactory.getLog(this.getClass());
	
	public ProcessManagerServiceTest(){
		this.processManagerService = (ProcessManagerService) TestConfiguration
		.getInstance().getApplicationContext().getBean(
				"ProcessManagerService");
	}

	@Before
	public void setUp(){
		this.processManager1 = new ProcessManager();
		this.processManager1.setLogin("john");
		this.processManager1.setName("georges");
		this.processManager1.setFirstname("georges");
		this.processManager1.setPassword("pass");
		// this.processManager1.setProcessesManaged(null);

		this.processManager2 = new ProcessManager();
		this.processManager2.setName("jose");
		this.processManager2.setFirstname("jose");
		this.processManager2.setLogin("ter");
		this.processManager2.setPassword("bouh");
		// this.processManager2.setProcessesManaged(null);

		this.processManagerService.saveProcessManager(processManager1);
		this.processManagerService.saveProcessManager(processManager2);
	}

	@After
	public void tearDown(){
		this.processManagerService.deleteProcessManager(this.processManager1);
		this.processManagerService.deleteProcessManager(this.processManager2);
	}

	@Test
	public void testSaveProcessManager() {
		ProcessManager ProcessManagerTmp = (ProcessManager) this.processManagerService
				.getProcessManagerDao().getProcessManager("john");
		assertNotNull(ProcessManagerTmp);
		assertEquals(ProcessManagerTmp.getName(), "georges");
		assertEquals(ProcessManagerTmp.getLogin(), "john");
		assertEquals(ProcessManagerTmp.getPassword(), Security.encode("pass"));
	}

	/*@Test
	public void testGetProcessManagers() {
		ProcessManager processManager3 = new ProcessManager();
		processManager3.setName("bert");
		processManager3.setFirstname("bert");
		processManager3.setLogin("rand");
		processManager3.setPassword("stephane");

		Set<ProcessManager> ensemble = this.processManagerService
				.getProcessManagers();

		assertNotNull(ensemble);
		assertFalse(ensemble.size() == 2);
		assertFalse(ensemble.isEmpty());

		this.processManager1 = this.processManagerService
				.getProcessManager(this.processManager1.getWilosuser_id());
		this.processManager1.setProcessesManaged(new HashSet<Process>());
		this.processManager2 = this.processManagerService
				.getProcessManager(this.processManager2.getWilosuser_id());
		this.processManager2.setProcessesManaged(new HashSet<Process>());
		assertTrue(ensemble.contains(this.processManager1));
		assertTrue(ensemble.contains(this.processManager2));
		assertFalse(ensemble.contains(processManager3));

		this.processManagerService.deleteProcessManager(processManager3);

	}*/

	@Test
	public void testDeleteProcessManager() {
		ProcessManager processManager3 = new ProcessManager();
		processManager3.setName("bert");
		processManager3.setLogin("rand");
		processManager3.setPassword("stephane");

		this.processManagerService.saveProcessManager(processManager3);
		processManager3 = this.processManagerService
				.getProcessManager(processManager3.getWilosuser_id());
		processManager3.setProcessesManaged(new HashSet<Process>());

		Set<ProcessManager> ensemble = this.processManagerService
				.getProcessManagers();
		assertTrue(ensemble.contains(processManager3));

		this.processManagerService.deleteProcessManager(processManager3);

		Set<ProcessManager> ensemble1 = this.processManagerService
				.getProcessManagers();
		// assertFalse(ensemble1.contains(processManager3));
	}
}
