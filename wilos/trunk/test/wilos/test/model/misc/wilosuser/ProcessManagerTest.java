package wilos.test.model.misc.wilosuser;

import wilos.model.misc.wilosuser.ProcessManager;
import wilos.model.spem2.process.Process;
import junit.framework.TestCase;

/**
 * @author BlackMilk
 *
 * This class represents the class test of the ProcessManager class
 *
 */
public class ProcessManagerTest extends TestCase {
	
	private ProcessManager pm1;
	private ProcessManager pm2;
	
	private final static String LOGIN = "john" ;
	
	private final static String LOGIN2 = "cathy" ;

	private final static String NAME = "georges" ;
	
	private final static String NAME2 = "willis" ;
	
	private final static String FIRSTNAME = "johnny" ;
	
	private final static String FIRSTNAME2 = "catherine" ;

	private final static String PASS = "pass" ;
	
	private final static String PASS2 = "pass2" ;
	
	private final static String DESCRIPTION = "process1";
	private final static String DESCRIPTION2 = "process2";

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		pm1 = new ProcessManager();
		pm2 = new ProcessManager();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.ProcessManager#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		pm1.setLogin(LOGIN);
		pm1.setFirstname(FIRSTNAME);
		pm1.setName(NAME);
		pm1.setPassword(PASS);
		pm2.setLogin(LOGIN);
		pm2.setFirstname(FIRSTNAME);
		pm2.setName(NAME);
		pm2.setPassword(PASS);
		assertTrue(pm1.equals(pm2));
		/*Login test*/
		pm2.setLogin(LOGIN2);
		assertFalse(pm1.equals(pm2));
		/*Name test*/
		pm2.setLogin(LOGIN);
		pm2.setName(NAME2);
		assertFalse(pm1.equals(pm2));
		/*FirstName test*/
		pm2.setName(NAME);
		pm2.setFirstname(FIRSTNAME2);
		assertFalse(pm1.equals(pm2));
		/*Password test*/
		pm2.setFirstname(FIRSTNAME);
		pm2.setPassword(PASS2);
		assertFalse(pm1.equals(pm2));
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.ProcessManager#addManagedProcess(wilos.model.spem2.process.Process)}.
	 */
	public void testAddManagedProcess() {
		Process process = new Process();
		process.setDescription(DESCRIPTION);
		pm1.addManagedProcess(process);
		assertEquals(process,(Process)(pm1.getProcessesManaged().toArray())[0]);
		assertEquals(process.getProcessManager(),pm1);
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.ProcessManager#removeManagedProject(wilos.model.spem2.process.Process)}.
	 */
	public void testRemoveManagedProject() {		
		Process process = new Process();
		process.setDescription(DESCRIPTION);
		pm1.addManagedProcess(process);
		assertEquals(1,pm1.getProcessesManaged().size());
		pm1.removeManagedProcess(process);		
		assertTrue(pm1.getProcessesManaged().isEmpty());
		assertEquals(process.getProcessManager(),null);
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.ProcessManager#removeAllManagedProjects()}.
	 */
	public void testRemoveAllManagedProjects() {
		Process process1 = new Process();
		process1.setDescription(DESCRIPTION);
		Process process2 = new Process();
		process2.setDescription(DESCRIPTION2);
		pm1.addManagedProcess(process1);
		pm1.addManagedProcess(process2);
		assertNotNull(pm1.getProcessesManaged());
		pm1.removeAllManagedProcess();
		assertTrue(pm1.getProcessesManaged().isEmpty());
		assertEquals(null,process1.getProcessManager());
		assertEquals(null,process2.getProcessManager());
	}

}
