package business.ProcessManager;

import configuration.TestConfiguration;
import woops2.business.wilosuser.ParticipantService;
import woops2.business.wilosuser.ProcessManagerService;
import woops2.model.wilosuser.Participant;
import woops2.model.wilosuser.ProcessManager;
import junit.framework.TestCase;

/**
 * @author Martial
 *
 * This class represents ... TODO
 *
 */
public class ProcessManagerServiceTest extends TestCase {

	private ProcessManagerService processManagerService;
	private ProcessManager processManager;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.processManagerService= (ProcessManagerService) TestConfiguration.getInstance().getApplicationContext().getBean("ProcessManagerService") ;
		this.processManager=new ProcessManager();
		this.processManager.setLogin("john");
		this.processManager.setName("georges");
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		this.processManagerService.getProcessManagerDao().deleteProcessManager(this.processManager) ;
	}

	/**
	 * Test method for {@link woops2.business.wilosuser.ProcessManagerService#saveProcessManager(woops2.model.wilosuser.ProcessManager)}.
	 */
	public void testSaveProcessManager() {
		this.processManagerService.saveProcessManager(this.processManager);
		ProcessManager ProcessManagerTmp = (ProcessManager) this.processManagerService.getProcessManagerDao().getProcessManager("john");
		assertNotNull(ProcessManagerTmp);
		assertEquals(ProcessManagerTmp.getName(), "georges") ;
		assertEquals(ProcessManagerTmp.getLogin(), "john") ;
	}

}
