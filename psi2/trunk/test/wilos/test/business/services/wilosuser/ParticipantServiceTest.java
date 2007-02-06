
package wilos.test.business.services.wilosuser ;

import java.util.HashMap ;
import java.util.HashSet ;

import junit.framework.TestCase ;
import wilos.business.services.project.ProjectService ;
import wilos.business.services.role.RoleService ;
import wilos.business.services.wilosuser.ParticipantService ;
import wilos.business.util.Security ;
import wilos.model.misc.project.Project ;
import wilos.model.misc.wilosuser.Participant ;
import wilos.test.configuration.TestConfiguration ;

/**
 * @author Martial
 * 
 * This class represents the class test of the Participant class.
 * 
 */
public class ParticipantServiceTest extends TestCase {

	private ParticipantService participantService ;

	private ProjectService projectService ;

	//private RoleService rs ;

	private Participant participant ;

	private Project p1 ;

	private Project p2 ;

	private final static String LOGIN = "john" ;

	private final static String NAME = "georges" ;

	private final static String PASS = "pass" ;

	private final static String ROLE1 = "Testeur" ;

	private final static String ROLE2 = "Developpeur" ;

	private final static Boolean VROLE1 = true ;

	private final static Boolean VROLE2 = false ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.participantService = (ParticipantService) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantService") ;
		//this.rs = (RoleService) TestConfiguration.getInstance().getApplicationContext().getBean("RoleService") ;
		this.projectService = (ProjectService) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectService") ;

		p1 = new Project() ;
		p1.setConcreteName("projectTestPS1") ;
		this.projectService.saveProject(p1) ;
		
		p2 = new Project() ;
		p2.setConcreteName("projectTestPS2") ;
		this.projectService.saveProject(p2) ;

		participant = new Participant() ;
		participant.setLogin(LOGIN) ;
		participant.setName(NAME) ;
		participant.setPassword(PASS) ;
		participantService.saveParticipant(participant);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		// Delete the tmp participant from the database.
		this.participantService.getParticipantDao().deleteParticipant(this.participant) ;
		this.projectService.getProjectDao().deleteProject(this.p1) ;
		this.projectService.getProjectDao().deleteProject(this.p2) ;

	}

	/**
	 * Test method for {@link woops2.business.wilosuser.ParticipantService#getRolesList()}.
	 * TODO methode a refaire apres modele metier
	 */
	public void testGetRolesList() {
//		HashMap<String, Boolean> roles = new HashMap<String, Boolean>() ;
//		roles.put(ROLE1, VROLE1) ;
//		roles.put(ROLE2, VROLE2) ;
//		rs.saveParticipantRoles(roles, LOGIN) ;

		assertTrue(false) ;
	}

	/**
	 * Test method for
	 * {@link woops2.business.wilosuser.ParticipantService#saveParticipant(woops2.model.wilosuser.Participant)}.
	 */
	public void testSaveParticipant() {
		this.participant.setLogin(LOGIN);
		this.participant.setName(NAME);
		this.participant.setPassword(PASS);
		
		this.participantService.saveParticipant(this.participant) ;
		Participant ParticipantTmp = (Participant) this.participantService.getParticipantDao().getParticipant(LOGIN) ;

		assertNotNull(ParticipantTmp) ;
		assertEquals(ParticipantTmp.getName(), NAME) ;
		assertEquals(ParticipantTmp.getLogin(), LOGIN) ;
		assertEquals(ParticipantTmp.getPassword(), Security.encode(PASS)) ;
	}

	/**
	 * 
	 * TODO a finir
	 * 
	 */
	public void testGetProjectsForAParticipant() {
		/*this.participant.addToProject(this.p1) ;
		this.ps.saveParticipant(this.p) ;

		Participant ParticipantTmp = (Participant) this.ps.getParticipantDao().getParticipant(LOGIN) ;
		//HashMap<String, Boolean> premove
		HashMap<Project, Boolean> plist = this.ps.getProjectsForAParticipant(ParticipantTmp) ;
		for(Project p : plist.keySet()){
			if(participant.getName().equals(this.p1.getName())){
				assertTrue(plist.get(p) == true) ;
			}
			else{
				assertTrue(plist.get(p) == false) ;
			}
		}
		this.participant.removeAllProject();
		this.participant.removeAllManagedProjects();
		this.ps.saveParticipant(this.p) ;*/
	}

	/**
	 * 
	 * TODO a finir
	 *
	 */
	public void testSaveProjectsForAParticipant() {
		/*this.ps.saveParticipant(this.p) ;
		HashMap<String, Boolean> affectedPjects = new HashMap<String, Boolean>();
		affectedPjects.put(this.p1.getProject_id(), true);
		affectedPjects.put(this.p2.getProject_id(), false);
		
		this.ps.saveProjectsForAParticipant(this.p, affectedPjects) ;

		Participant ParticipantTmp = (Participant) this.ps.getParticipantDao().getParticipant(LOGIN) ;
		HashMap<Project, Boolean> plist = this.ps.getProjectsForAParticipant(ParticipantTmp) ;
		for(Project p : plist.keySet()){
			if(participant.getName().equals(this.p1.getName())){
				assertTrue(plist.get(p) == true) ;
			}
			else{
				assertTrue(plist.get(p) == false) ;
			}
		}
		this.participant.removeAllProject();
		this.participant.removeAllManagedProjects();
		this.ps.saveParticipant(this.p) ;*/
	}

	/**
	 * 
	 * TODO a finir
	 *
	 */
	public void testGetManageableProjectsForAParticipant() {
		/*this.participant.addToProject(this.p1) ;
		this.ps.saveParticipant(this.p) ;

		Participant ParticipantTmp = (Participant) this.ps.getParticipantDao().getParticipant(LOGIN) ;

		HashMap<Project, Participant> plist = this.ps.getManageableProjectsForAParticipant(ParticipantTmp) ;
		for(Project p : plist.keySet()){
			if(participant.getName().equals(this.p1.getName()) && participant.getProjectManager() == null){
				assertTrue(plist.get(p) == null) ;
			}
			else{
				assertFalse(plist.get(p).getWilosuser_id() != null) ;
			}
		}
		this.participant.removeAllProject();
		this.participant.removeAllManagedProjects();
		this.ps.saveParticipant(this.p) ;*/
	}

	/**
	 * 
	 * TODO a finir
	 *
	 */
	public void testSaveManagedProjectsForAParticipant() {
		/*this.ps.saveParticipant(this.p) ;
		HashMap<String, Boolean> managedPjects = new HashMap<String, Boolean>();
		managedPjects.put(this.p1.getProject_id(), true);
		managedPjects.put(this.p2.getProject_id(), false);
		
		this.ps.saveManagedProjectsForAParticipant(this.p, managedPjects) ;

		Participant ParticipantTmp = (Participant) this.ps.getParticipantDao().getParticipant(LOGIN) ;
		HashMap<Project, Participant> plist = this.ps.getManageableProjectsForAParticipant(ParticipantTmp) ;
		for(Project p : plist.keySet()){
			if(participant.getName().equals(this.p1.getName()) && participant.getProjectManager() == null){
				assertTrue(plist.get(p) == null) ;
			}
			else{
				assertFalse(plist.get(p).getWilosuser_id() != null) ;
			}
		}
		this.participant.removeAllProject();
		this.participant.removeAllManagedProjects();
		this.ps.saveParticipant(this.p) ;*/
	}
}
