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

import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.model.misc.wilosuser.Participant;
import wilos.test.TestConfiguration;

public class ParticipantDaoTest {

	private ParticipantDao participantDao;

	private Participant participant;

	public static String LOGIN = "john";

	public static String NAME = "georges";

	public static String FIRSTNAME = "jean";

	public static String EMAIL = "example@example.com";
	
	public ParticipantDaoTest(){
		this.participantDao = (ParticipantDao) TestConfiguration.getInstance()
		.getApplicationContext().getBean("ParticipantDao");
	}

	@Before
	public void setUp(){
		this.participant = new Participant();
		this.participant.setLogin(LOGIN);
		this.participant.setName(NAME);
		this.participant.setFirstname(FIRSTNAME);
		this.participant.setEmailAddress(EMAIL);
		this.participantDao.saveOrUpdateParticipant(this.participant);
	}

	@After
	public void tearDown(){
		// Delete the tmp participant from the database.
		this.participantDao.deleteParticipant(this.participant);
	}

	@Test
	public void testSaveOrUpdateParticipant() {
		Participant participantTmp = (Participant) this.participantDao
				.getParticipant(LOGIN);
		assertNotNull(participantTmp);
		assertTrue(participant.getLogin().equals(participantTmp.getLogin()));
		assertTrue(participant.getName().equals(participantTmp.getName()));
	}

	/*@Test
	public void testGetAllRoles() {
		// TODO : tester cette methode quand elle sera placée dans la bonne
		// classe
		fail("En attendant le retour des roles.");
	}*/

	/*@Test
	public void testGetAllParticipants() {
		// Check the saving.
		Set<Participant> participantsTmp = this.participantDao
				.getAllParticipants();
		assertNotNull(participantsTmp);
		assertTrue(participantsTmp.size() >= 1);
	}*/

	@Test
	public void testGetParticipant() {
		Participant participantTmp = this.participantDao.getParticipant(LOGIN);

		assertNotNull(participantTmp);
		assertTrue(participantTmp.getLogin().equals(LOGIN));
		//assertTrue(participantTmp.getFirstname().equals(FIRSTNAME));
		//assertTrue(participantTmp.getEmailAddress().equals(EMAIL));
	}

	/*@Test
	public void testDeleteParticipant() {
		this.participantDao.deleteParticipant(this.participant);
		Participant participantTmp = this.participantDao.getParticipant(LOGIN);
		assertNull(participantTmp);
	}*/
}
