package wilos.test.business.services.wilosuser;

import java.util.HashMap;

import junit.framework.TestCase;
import wilos.business.services.role.RoleService;
import wilos.business.services.wilosuser.ParticipantService;
import wilos.business.util.Security;
import wilos.model.misc.wilosuser.Participant;
import wilos.test.configuration.TestConfiguration;

/**
 * @author Martial
 *
 * This class represents the class test of the Participant class.
 *
 */
public class ParticipantServiceTest extends TestCase {

	private ParticipantService ps;
	private RoleService rs;
	private Participant p ;
	
	private final static String LOGIN = "john";
	private final static String NAME = "georges";
	private final static String PASS = "pass";
	private final static String ROLE1 = "Testeur";
	private final static String ROLE2 = "Developpeur";
	private final static Boolean VROLE1 = true;
	private final static Boolean VROLE2 = false;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.ps = (ParticipantService) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantService") ;
		this.rs = (RoleService) TestConfiguration.getInstance().getApplicationContext().getBean("RoleService") ;
		p=new Participant();
		p.setLogin(LOGIN);
		p.setName(NAME);
		p.setPassword(PASS) ;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		// Delete the tmp participant from the database.
		this.ps.getParticipantDao().deleteParticipant(this.p) ;

	}

	/**
	 * Test method for {@link woops2.business.wilosuser.ParticipantService#getRolesList()}.
	 */
	public void testGetRolesList() {
		//TODO: finir ce test quand la fonction sera placée dans la bonne classe
		HashMap<String, Boolean> roles = new HashMap<String, Boolean>();
		roles.put(ROLE1, VROLE1);
		roles.put(ROLE2, VROLE2);
		rs.saveParticipantRoles(roles,LOGIN);
		
		
		assertTrue(false);
	}

	/**
	 * Test method for {@link woops2.business.wilosuser.ParticipantService#saveParticipant(woops2.model.wilosuser.Participant)}.
	 */
	public void testSaveParticipant() {
		this.ps.saveParticipant(this.p);
		Participant ParticipantTmp = (Participant) this.ps.getParticipantDao().getParticipant(LOGIN);
		
		assertNotNull(ParticipantTmp);
		assertEquals(ParticipantTmp.getName(), NAME) ;
		assertEquals(ParticipantTmp.getLogin(), LOGIN) ;
		assertEquals(ParticipantTmp.getPassword(), Security.encode(PASS)) ;
	}
}
