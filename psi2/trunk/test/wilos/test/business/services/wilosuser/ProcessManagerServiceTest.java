package wilos.test.business.services.wilosuser;

import java.util.Set;

import junit.framework.TestCase;
import wilos.test.configuration.TestConfiguration;
import wilos.business.services.misc.wilosuser.ProcessManagerService;
import wilos.business.util.Security;
import wilos.model.misc.wilosuser.ProcessManager;

/**
 * @author Martial
 *
 * This class represents the TestCase for the ProcessManagerService class.  
 *
 */
public class ProcessManagerServiceTest extends TestCase {

	private ProcessManagerService processManagerService;
	private ProcessManager processManager1;
	private ProcessManager processManager2;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.processManagerService= (ProcessManagerService) TestConfiguration.getInstance().getApplicationContext().getBean("ProcessManagerService") ;
		this.processManager1=new ProcessManager();
		this.processManager1.setLogin("john");
		this.processManager1.setName("georges");
		this.processManager1.setPassword("pass") ;
		
		this.processManager2 = new ProcessManager();
		this.processManager2.setName("jose");
		this.processManager2.setLogin("ter");
		this.processManager2.setPassword("bouh");
		
		this.processManagerService.saveProcessManager(processManager1);
		this.processManagerService.saveProcessManager(processManager2);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		this.processManagerService.deleteProcessManager(this.processManager1) ;
		this.processManagerService.deleteProcessManager(this.processManager2);
	}

	/**
	 * Test method for {@link woops2.business.wilosuser.ProcessManagerService#saveProcessManager(woops2.model.wilosuser.ProcessManager)}.
	 */
	public void testSaveProcessManager() {
		
		ProcessManager ProcessManagerTmp = (ProcessManager) this.processManagerService.getProcessManagerDao().getProcessManager("john");
		assertNotNull(ProcessManagerTmp);
		assertEquals(ProcessManagerTmp.getName(), "georges") ;
		assertEquals(ProcessManagerTmp.getLogin(), "john") ;
		assertEquals(ProcessManagerTmp.getPassword(), Security.encode("pass")) ;
	}
	
	public void testGetProcessManagers() {

		ProcessManager processManager3 = new ProcessManager();
		processManager3.setName("bert");
		processManager3.setLogin("rand");
		processManager3.setPassword("stephane");
		
		Set<ProcessManager> ensemble = this.processManagerService.getProcessManagers();

		assertNotNull(ensemble);
		assertFalse(ensemble.size() == 0);
		assertFalse(ensemble.isEmpty());
		
		assertTrue(ensemble.contains(this.processManager1));
		assertTrue(ensemble.contains(this.processManager2));
		assertFalse(ensemble.contains(processManager3));
		
	}
	
	public void testDeleteProcessManager() {
		
		ProcessManager processManager3 = new ProcessManager();
		processManager3.setName("bert");
		processManager3.setLogin("rand");
		processManager3.setPassword("stephane");
		
		this.processManagerService.saveProcessManager(processManager3);
		
		Set<ProcessManager> ensemble = this.processManagerService.getProcessManagers();
		assertTrue(ensemble.contains(processManager3));
		this.processManagerService.deleteProcessManager(processManager3);
		Set<ProcessManager> ensemble1 = this.processManagerService.getProcessManagers();
		assertFalse(ensemble1.contains(processManager3));
		
	}

}
