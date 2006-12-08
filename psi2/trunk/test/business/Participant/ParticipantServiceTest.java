package business.Participant;



import configuration.TestConfiguration;



import woops2.business.wilosuser.ParticipantService;
import woops2.model.wilosuser.Participant;
import junit.framework.TestCase;

/**
 * @author Martial
 *
 * This class represents the class test of the Participant class.
 *
 */
public class ParticipantServiceTest extends TestCase {

	private ParticipantService ps;
	private Participant p ;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.ps = (ParticipantService) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantService") ;
		p=new Participant();
		p.setLogin("john");
		p.setName("georges");
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
//		 Delete the tmp participant from the database.
		this.ps.getParticipantDao().deleteParticipant(this.p) ;

	}

	/**
	 * Test method for {@link woops2.business.wilosuser.ParticipantService#getRolesList()}.
	 */
	public void testGetRolesList() {
	//	TODO...	 
	assertTrue(true);
	}

	/**
	 * Test method for {@link woops2.business.wilosuser.ParticipantService#saveParticipant(woops2.model.wilosuser.Participant)}.
	 */
	public void testSaveParticipant() {
		
		this.ps.saveParticipant(this.p);
		Participant ParticipantTmp = (Participant) this.ps.getParticipantDao().getParticipant("john");
		assertNotNull(ParticipantTmp);
		assertEquals(ParticipantTmp.getName(), "georges") ;
		assertEquals(ParticipantTmp.getLogin(), "john") ;
	}
}
