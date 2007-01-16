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
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.ps = (ParticipantService) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantService") ;
		this.rs = (RoleService) TestConfiguration.getInstance().getApplicationContext().getBean("RoleService") ;
		p=new Participant();
		p.setLogin("john");
		p.setName("georges");
		p.setPassword("pass") ;
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
		HashMap<String, Boolean> roles = new HashMap<String, Boolean>();
		roles.put("Tester", true);
		roles.put("Developpeur", false);
		rs.saveParticipantRoles(roles,"mika");
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
		assertEquals(ParticipantTmp.getPassword(), Security.encode("pass")) ;
	}
}
