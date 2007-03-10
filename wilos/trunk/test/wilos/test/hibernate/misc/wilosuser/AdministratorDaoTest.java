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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.misc.wilosuser.AdministratorDao;
import wilos.model.misc.wilosuser.Administrator;
import wilos.test.TestConfiguration;

public class AdministratorDaoTest {

	private AdministratorDao ad;

	private Administrator a;

	private Administrator a2;

	public AdministratorDaoTest() {
		this.ad = (AdministratorDao) TestConfiguration.getInstance()
				.getApplicationContext().getBean("AdministratorDao");
	}

	@Before
	public void setUp(){
		this.a = new Administrator();
		this.a.setLogin("testAdmin");
		this.a.setPassword("pass");
		this.ad.saveOrUpdateAdministrator(this.a);
	}

	@After
	public void tearDown() {
		this.ad.deleteAdministrator(this.a);
	}

	@Test
	public void testSaveOrUpdateAdministrator() {
		Administrator admTmp = this.ad.getAdministrator("testAdmin");
		assertNotNull(admTmp);
		assertTrue(this.a.getLogin().equals(admTmp.getLogin()));
		assertTrue(this.a.getPassword().equals(admTmp.getPassword()));
	}

	@Test
	public void testGetAdministrator() {
		Administrator admTmp = this.ad.getAdministrator("testAdmin");
		assertNotNull(admTmp);
		assertEquals(admTmp.getLogin(), "testAdmin");
		assertEquals(admTmp.getPassword(), "pass");
	}

	@Test
	public void testDeleteAdministrator() {
		this.a2 = new Administrator();
		this.a2.setLogin("testAdmin2");
		this.a2.setPassword("pass");
		this.ad.deleteAdministrator(this.a2);

		Administrator admTmp2 = this.ad.getAdministrator("testAdmin2");
		assertNull(admTmp2);
	}

}
