package wilos.test.hibernate.misc.wilosuser;

import java.util.Set;

import junit.framework.TestCase;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.model.misc.wilosuser.Participant;
import wilos.test.TestConfiguration;

/**
 * This class represents the TestCase for the ParticipantDao class.
 * 
 * @author Michaël
 */
public class ParticipantDaoTest extends TestCase {

	private ParticipantDao participantDao;
	private Participant participant;
	public static String LOGIN="john";
	public static String NAME="georges";
	public static String FIRSTNAME="jean";
	public static String EMAIL="example@example.com";
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.participantDao = (ParticipantDao) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantDao") ;
		this.participant= new Participant();
		this.participant.setLogin(LOGIN);
		this.participant.setName(NAME);
		this.participant.setFirstname(FIRSTNAME);
		this.participant.setEmailAddress(EMAIL);
		this.participantDao.saveOrUpdateParticipant(this.participant) ;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		//Delete the tmp participant from the database.
		this.participantDao.deleteParticipant(this.participant) ;
	}

	/**
	 * Test method for {@link woops2.hibernate.wilosuser.ParticipantDao#saveOrUpdateParticipant(woops2.model.wilosuser.Participant)}.
	 */
	public void testSaveOrUpdateParticipant() {
		Participant participantTmp = (Participant) this.participantDao.getParticipant(LOGIN) ;
		assertNotNull(participantTmp) ;
		assertTrue(participant.getLogin().equals(participantTmp.getLogin()));
		assertTrue(participant.getName().equals(participantTmp.getName()));
	}

	/**
	 * Test method for {@link woops2.hibernate.wilosuser.ParticipantDao#getAllRoles()}.
	 */
	public void testGetAllRoles() {
		// TODO : tester cette methode quand elle sera placée dans la bonne classe
		fail("En attendant le retour des roles.");
	}

	/**
	 * Test method for {@link woops2.hibernate.wilosuser.ParticipantDao#getAllParticipants()}.
	 */
	public void testGetAllParticipants() {
		//Check the saving.
		Set<Participant> participantsTmp = this.participantDao.getAllParticipants() ;
		assertNotNull(participantsTmp) ;
		assertTrue(participantsTmp.size() >= 1);
	}

	/**
	 * Test method for {@link woops2.hibernate.wilosuser.ParticipantDao#getParticipant(java.lang.String)}.
	 */
	public void testGetParticipant() {
		Participant participantTmp=this.participantDao.getParticipant(LOGIN);
		
		assertNotNull(participantTmp);
		assertTrue(participantTmp.getLogin().equals(LOGIN));
		assertTrue(participantTmp.getFirstname().equals(FIRSTNAME));
		assertTrue(participantTmp.getEmailAddress().equals(EMAIL));
	}

	/**
	 * Test method for {@link woops2.hibernate.wilosuser.ParticipantDao#deleteParticipant(woops2.model.wilosuser.Participant)}.
	 */
	public void testDeleteParticipant() {
		this.participantDao.deleteParticipant(this.participant);
		Participant participantTmp=this.participantDao.getParticipant(LOGIN);
		assertNull(participantTmp);
		
	}

}
