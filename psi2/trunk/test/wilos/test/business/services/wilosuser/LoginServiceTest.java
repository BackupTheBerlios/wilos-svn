package wilos.test.business.services.wilosuser;

import wilos.test.configuration.TestConfiguration;
import wilos.business.services.wilosuser.LoginService;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.hibernate.misc.wilosuser.ProcessManagerDao;
import wilos.hibernate.misc.wilosuser.ProjectDirectorDao;
import wilos.model.misc.wilosuser.Administrator;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.ProcessManager;
import wilos.model.misc.wilosuser.ProjectDirector;
import wilos.model.misc.wilosuser.WilosUser;
import junit.framework.TestCase;

/**
 * @author Michaël
 *
 * This class represents the TestCase for the LoginService class.  
 *
 */
public class LoginServiceTest extends TestCase {

	private LoginService loginService;
	private ParticipantDao participantDao;
	private ProjectDirectorDao projectDirectorDao;
	private ProcessManagerDao processManagerDao;
	private Participant p;
	private ProjectDirector pd;
	private ProcessManager pm;
	
	private Administrator admin;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		loginService=(LoginService) TestConfiguration.getInstance().getApplicationContext().getBean("LoginService") ;
		this.participantDao = (ParticipantDao) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantDao") ;
		this.projectDirectorDao = (ProjectDirectorDao) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectDirectorDao") ;
		this.processManagerDao = (ProcessManagerDao) TestConfiguration.getInstance().getApplicationContext().getBean("ProcessManagerDao") ;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		
		/*this.participantDao.deleteParticipant(this.p);
		this.processManagerDao.deleteProcessManager(this.pm);
		this.projectDirectorDao.deleteProjectDirector(this.pd);*/
	}

	/**
	 * Test method for {@link woops2.business.wilosuser.LoginService#getAuthentifiedUser(java.lang.String, java.lang.String)}.
	 */
	public void testGetAuthentifiedUser() {
		//test for a participant
		p=new Participant();
		p.setLogin("login");
		p.setPassword("passwd");
		this.participantDao.saveOrUpdateParticipant(p);
		WilosUser w= this.loginService.getAuthentifiedUser("login", "passwd");
		assertNotNull(w);
		assertTrue(w.getLogin().equals("login"));
		assertTrue(w.getPassword().equals("passwd"));
		assertTrue(w instanceof Participant);
		this.participantDao.deleteParticipant(p);
		
		
		//test for a project director
		pd=new ProjectDirector();
		pd.setLogin("login2");
		pd.setPassword("passwd");
		this.projectDirectorDao.saveOrUpdateProjectDirector(pd);
		WilosUser w2= this.loginService.getAuthentifiedUser("login2", "passwd");
		assertNotNull(w2);
		assertTrue(w2.getLogin().equals("login2"));
		assertTrue(w2.getPassword().equals("passwd"));
		assertTrue(w2 instanceof ProjectDirector);
		this.projectDirectorDao.deleteProjectDirector(pd);
		
		//test for a process manager
		pm=new ProcessManager();
		pm.setLogin("login3");
		pm.setPassword("passwd");
		this.processManagerDao.saveOrUpdateProcessManager(pm);
		WilosUser w3= this.loginService.getAuthentifiedUser("login3", "passwd");
		assertNotNull(w3);
		assertTrue(w3.getLogin().equals("login3"));
		assertTrue(w3.getPassword().equals("passwd"));
		assertTrue(w3 instanceof ProcessManager);
		this.processManagerDao.deleteProcessManager(pm);
	}

	/**
	 * Test method for {@link woops2.business.wilosuser.LoginService#loginExist(java.lang.String)}.
	 */
	public void testLoginExist() {
		p=new Participant();
		p.setLogin("loginexist");
		p.setPassword("passwd");
		this.participantDao.saveOrUpdateParticipant(p);
		boolean trouve= this.loginService.loginExist("loginexist");
		assertTrue(trouve);
		this.participantDao.deleteParticipant(p);
		trouve= this.loginService.loginExist("loginexist");
		assertFalse(trouve);
		
	}

	public void testIsParticipant(){
		p=new Participant();
		assertTrue(this.loginService.isParticipant(p));
		pd = new ProjectDirector();
		assertFalse(this.loginService.isParticipant(pd));
		pm = new ProcessManager();
		assertFalse(this.loginService.isParticipant(pm));
		admin = new Administrator();
		assertFalse(this.loginService.isParticipant(admin));
	}
	
	public void testIsProcessManager(){

		pm = new ProcessManager();
		assertTrue(this.loginService.isProcessManager(pm));
		p=new Participant();
		assertFalse(this.loginService.isProcessManager(p));
		pd = new ProjectDirector();
		assertFalse(this.loginService.isProcessManager(pd));
		admin = new Administrator();
		assertFalse(this.loginService.isProcessManager(admin));
	}
	
	public void testIsProjectDirector(){

		pm = new ProcessManager();
		assertFalse(this.loginService.isProjectDirector(pm));
		p=new Participant();
		assertFalse(this.loginService.isProjectDirector(p));
		pd = new ProjectDirector();
		assertTrue(this.loginService.isProjectDirector(pd));
		admin = new Administrator();
		assertFalse(this.loginService.isProjectDirector(admin));
	}
	
	public void testIsAdministrator(){

		pm = new ProcessManager();
		assertFalse(this.loginService.isAdministrator(pm));
		p=new Participant();
		assertFalse(this.loginService.isAdministrator(p));
		pd = new ProjectDirector();
		assertFalse(this.loginService.isAdministrator(pd));
		admin = new Administrator();
		assertTrue(this.loginService.isAdministrator(admin));
	}
}
