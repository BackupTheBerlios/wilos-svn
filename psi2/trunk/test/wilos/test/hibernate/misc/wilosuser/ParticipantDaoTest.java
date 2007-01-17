package wilos.test.hibernate.misc.wilosuser;

import java.util.Set;

import wilos.test.configuration.TestConfiguration;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.model.misc.wilosuser.Participant;
import junit.framework.TestCase;

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
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.participantDao = (ParticipantDao) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantDao") ;
		this.participant= new Participant();
		this.participant.setLogin(LOGIN);
		this.participant.setName(NAME);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		//Delete the tmp participant from the database.
		try{
			this.participantDao.deleteParticipant(this.participant) ;
		}
		catch(Exception exception){
			System.err.println("exception e ="+exception);
		}
	}

	/**
	 * Test method for {@link woops2.hibernate.wilosuser.ParticipantDao#saveOrUpdateParticipant(woops2.model.wilosuser.Participant)}.
	 */
	public void testSaveOrUpdateParticipant() {
		// Save the activity with the method to test.
		this.participantDao.saveOrUpdateParticipant(this.participant) ;
		
		// Check the saving.
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
		assertTrue(true);
	}

	/**
	 * Test method for {@link woops2.hibernate.wilosuser.ParticipantDao#getAllParticipants()}.
	 */
	public void testGetAllParticipants() {
		this.participantDao.saveOrUpdateParticipant(this.participant) ;
		
		// Check the saving.
		Set<Participant> participantsTmp = this.participantDao.getAllParticipants() ;
		assertNotNull(participantsTmp) ;
		assertTrue(participantsTmp.size() >= 1);
		
	}

	/**
	 * Test method for {@link woops2.hibernate.wilosuser.ParticipantDao#getParticipant(java.lang.String)}.
	 */
	public void testGetParticipant() {
		participant.setFirstname("jean");
		participant.setEmailAddress("example@example.com");
		this.participantDao.saveOrUpdateParticipant(this.participant) ;
		Participant participantTmp=this.participantDao.getParticipant(LOGIN);
		assertNotNull(participantTmp);
		assertTrue(participantTmp.getEmailAddress().equals("example@example.com"));
		assertTrue(participantTmp.getLogin().equals(LOGIN));
		assertTrue(participantTmp.getFirstname().equals("jean"));
	}

	/**
	 * Test method for {@link woops2.hibernate.wilosuser.ParticipantDao#deleteParticipant(woops2.model.wilosuser.Participant)}.
	 */
	public void testDeleteParticipant() {
		this.participantDao.saveOrUpdateParticipant(this.participant) ;
		this.participantDao.deleteParticipant(this.participant);
		Participant participantTmp=this.participantDao.getParticipant(LOGIN);
		assertNull(participantTmp);
		
	}

}
