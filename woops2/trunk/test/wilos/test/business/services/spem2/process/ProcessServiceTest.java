package wilos.test.business.services.spem2.process;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.spem2.process.ProcessService;
import wilos.model.spem2.process.Process;
import wilos.test.TestConfiguration;


public class ProcessServiceTest {

	private ProcessService processService;

	private Process process;

	public ProcessServiceTest() {
		// Get the ActivityDao Singleton for managing Activity data
		this.processService = (ProcessService) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProcessService");
	}

	@Before
	public void setUp() {

		// Create empty Activity
		this.process = new Process();
	}

	@After
	public void tearDown() {

		// Delete the tmp activity from the database.
		this.processService.getProcessDao().deleteProcess(this.process);
	}

	@Test
	public final void testGetProcessesList() {
		// Rk: the setUp method is called here.

		// Save the activity.
		//FIXME rajouter le wilosuserid en param de saveProcess
		//this.processService.saveProcess(this.process);

		// Look if this activity is also into the database and look if the size
		// of the set is >= 1.
		List<Process> processes = this.processService.getProcessesList();
		assertNotNull(processes);
		assertTrue(processes.size() >= 1);

		// Rk: the tearDown method is called here.
	}

	@Test
	public final void testSaveProcess() {
		// Rk: the setUp method is called here.

		// Save the activity.
		//FIXME rajouter le wilosuserid en param de saveProcess
		//this.processService.saveProcess(this.process);

		// Rk: the tearDown method is called here.
	}
}
