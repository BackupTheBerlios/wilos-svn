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

package wilos.test.model.misc.wilosuser;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.model.misc.wilosuser.ProcessManager;
import wilos.model.spem2.process.Process;

public class ProcessManagerTest {
	
	private ProcessManager pm1;
	private ProcessManager pm2;
	
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
	public void setUp()  {
		pm1 = new ProcessManager();
		pm2 = new ProcessManager();
	}

	@After
	public void tearDown() {
		//None.
	}

	@Test
	public void testEqualsObject() {
		pm1.setLogin(LOGIN);
		pm1.setFirstname(FIRSTNAME);
		pm1.setName(NAME);
		pm1.setPassword(PASS);
		pm2.setLogin(LOGIN);
		pm2.setFirstname(FIRSTNAME);
		pm2.setName(NAME);
		pm2.setPassword(PASS);
		assertTrue(pm1.equals(pm2));
		/*Login test*/
		pm2.setLogin(LOGIN2);
		assertFalse(pm1.equals(pm2));
		/*Name test*/
		pm2.setLogin(LOGIN);
		pm2.setName(NAME2);
		assertFalse(pm1.equals(pm2));
		/*FirstName test*/
		pm2.setName(NAME);
		pm2.setFirstname(FIRSTNAME2);
		assertFalse(pm1.equals(pm2));
		/*Password test*/
		pm2.setFirstname(FIRSTNAME);
		pm2.setPassword(PASS2);
		assertFalse(pm1.equals(pm2));
	}

	@Test
	public void testAddManagedProcess() {
		Process process = new Process();
		process.setDescription(DESCRIPTION);
		pm1.addManagedProcess(process);
		assertEquals(process,(Process)(pm1.getProcessesManaged().toArray())[0]);
		assertEquals(process.getProcessManager(),pm1);
	}

	@Test
	public void testRemoveManagedProject() {		
		Process process = new Process();
		process.setDescription(DESCRIPTION);
		pm1.addManagedProcess(process);
		assertEquals(1,pm1.getProcessesManaged().size());
		pm1.removeManagedProcess(process);		
		assertTrue(pm1.getProcessesManaged().isEmpty());
		assertEquals(process.getProcessManager(),null);
	}

	@Test
	public void testRemoveAllManagedProjects() {
		Process process1 = new Process();
		process1.setDescription(DESCRIPTION);
		Process process2 = new Process();
		process2.setDescription(DESCRIPTION2);
		pm1.addManagedProcess(process1);
		pm1.addManagedProcess(process2);
		assertNotNull(pm1.getProcessesManaged());
		pm1.removeAllManagedProcess();
		assertTrue(pm1.getProcessesManaged().isEmpty());
		assertEquals(null,process1.getProcessManager());
		assertEquals(null,process2.getProcessManager());
	}

}
