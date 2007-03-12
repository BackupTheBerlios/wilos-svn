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

import wilos.model.misc.wilosuser.Administrator;

public class AdministratorTest {
	
	private Administrator admin1;
	private Administrator admin2;
	
	private final static String LOGIN = "john" ;
	
	private final static String LOGIN2 = "cathy" ;

	private final static String NAME = "georges" ;
	
	private final static String NAME2 = "willis" ;
	
	private final static String FIRSTNAME = "johnny" ;
	
	private final static String FIRSTNAME2 = "catherine" ;

	private final static String PASS = "pass" ;
	
	private final static String PASS2 = "pass2" ;

	@Before
	public void setUp() {
		admin1 = new Administrator();
		admin2 = new Administrator();
	}

	@After
	public void tearDown(){
		//None.
	}

	@Test
	public void testEqualsObject() {
		admin1.setLogin(LOGIN);
		admin1.setFirstname(FIRSTNAME);
		admin1.setName(NAME);
		admin1.setPassword(PASS);
		admin2.setLogin(LOGIN);
		admin2.setFirstname(FIRSTNAME);
		admin2.setName(NAME);
		admin2.setPassword(PASS);
		assertTrue(admin1.equals(admin2));
		/*Login test*/
		admin2.setLogin(LOGIN2);
		assertFalse(admin1.equals(admin2));
		/*Name test*/
		admin2.setLogin(LOGIN);
		admin2.setName(NAME2);
		assertFalse(admin1.equals(admin2));
		/*FirstName test*/
		admin2.setName(NAME);
		admin2.setFirstname(FIRSTNAME2);
		assertFalse(admin1.equals(admin2));
		/*Password test*/
		admin2.setFirstname(FIRSTNAME);
		admin2.setPassword(PASS2);
		assertFalse(admin1.equals(admin2));
	}

}
