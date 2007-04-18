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

package wilos.test.hibernate.misc.wilosuser ;

import java.util.Set;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.wilosuser.WilosUserDao;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.test.TestConfiguration;

public class WilosUserDaoTest extends TestCase {

	private WilosUserDao wud ;

	private WilosUser wu ;
	
	public WilosUserDaoTest(){
		this.wud = (WilosUserDao) TestConfiguration.getInstance().getApplicationContext().getBean("WilosUserDao") ;
	}

	@Before
	public void setUp() {
		this.wu = new Participant() ;
		this.wu.setLogin("loginWU") ;
		this.wu.setName("nameWU") ;
	}

	@After
	public void tearDown() {
		this.wud.deleteWilosUser(this.wu) ;
	}

	@Test
	public void testSaveOrUpdateWilosUser() {
		this.wud.saveOrUpdateWilosUser(this.wu) ;

		WilosUser wuTmp = this.wud.getWilosUser("loginWU") ;
		assertNotNull(wuTmp) ;
		assertTrue(this.wu.getLogin().equals(wuTmp.getLogin())) ;
		assertTrue(this.wu.getName().equals(wuTmp.getName())) ;
	}

	@Test
	public void testGetAllWilosUsers() {
		this.wud.saveOrUpdateWilosUser(this.wu) ;

		Set<WilosUser> setTmp = this.wud.getAllWilosUsers() ;
		assertNotNull(setTmp) ;
		assertTrue(setTmp.size() >= 1) ;
	}

	@Test
	public void testGetWilosUser() {
		this.wud.saveOrUpdateWilosUser(this.wu) ;

		WilosUser wuTmp = this.wud.getWilosUser("loginWU") ;
		assertNotNull(wuTmp) ;
		assertEquals(wuTmp.getLogin(), "loginWU") ;
		assertEquals(wuTmp.getName(), "nameWU") ;
	}

	@Test
	public void testDeleteWilosUser() {
		this.wud.saveOrUpdateWilosUser(this.wu) ;
		this.wud.deleteWilosUser(this.wu) ;

		WilosUser wuTmp = this.wud.getWilosUser("loginWU") ;
		assertNull(wuTmp) ;
	}

}
