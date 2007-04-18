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

package wilos.test.hibernate.misc.wilosuser;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.wilosuser.ProcessManagerDao;
import wilos.model.misc.wilosuser.ProcessManager;
import wilos.test.TestConfiguration;

public class ProcessManagerDaoTest {

	private ProcessManagerDao pmd;

	private ProcessManager pm;

	public ProcessManagerDaoTest() {
		this.pmd = (ProcessManagerDao) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProcessManagerDao");
	}

	@Before
	public void setUp() {

		this.pm = new ProcessManager();
		this.pm.setLogin("testPM");
		this.pm.setName("Lopes");
	}

	@After 
	public void tearDown() {
		this.pmd.deleteProcessManager(this.pm);
	}

	@Test
	public void testSaveOrUpdateProcessManager() {
		this.pmd.saveOrUpdateProcessManager(this.pm);

		ProcessManager pmTmp = this.pmd.getProcessManager("testPM");
		assertNotNull(pmTmp);
		assertTrue(this.pm.getLogin().equals(pmTmp.getLogin()));
		assertTrue(this.pm.getName().equals(pmTmp.getName()));
	}

	@Test
	public void testGetAllProcessManagers() {
		this.pmd.saveOrUpdateProcessManager(this.pm);

		Set<ProcessManager> setTmp = this.pmd.getAllProcessManagers();
		assertNotNull(setTmp);
		assertTrue(setTmp.size() >= 1);
	}

	@Test
	public void testGetProcessManager() {
		this.pmd.saveOrUpdateProcessManager(this.pm);
		ProcessManager pmTmp = this.pmd.getProcessManager("testPM");
		assertNotNull(pmTmp);
		assertEquals(pmTmp.getLogin(), "testPM");
		assertEquals(pmTmp.getName(), "Lopes");
	}

	/*@Test
	public void testDeleteProcessManager() {
		this.pmd.saveOrUpdateProcessManager(this.pm);
		this.pmd.deleteProcessManager(this.pm);

		ProcessManager pmTmp = this.pmd.getProcessManager("testPM");
		assertNull(pmTmp);
	}*/

}
