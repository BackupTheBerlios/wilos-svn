package wilos.test.business.services.spem2.process;

import static org.junit.Assert.*;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.spem2.process.ProcessManagementService;
import wilos.model.misc.project.Project;
import wilos.model.spem2.process.Process;
import wilos.test.configuration.TestConfiguration;

/**
 * @author BlackMilk
 *
 * Tests ProcessManagementService methods
 *
 */
public class ProcessManagementServiceTest extends TestCase{

	private ProjectService projectS;
	private ProcessManagementService pms ;
	
	private Process process;
	private Project project;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Before
	public void setUp() throws Exception {
		super.setUp() ;
		this.projectS = (ProjectService) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectService") ;
		this.pms = (ProcessManagementService) TestConfiguration.getInstance().getApplicationContext().getBean("ProcessManagementService") ;
		this.process = new Process();
		this.process.setName("TestProcess");
		this.process.setDescription("description test");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link wilos.business.services.spem2.process.ProcessManagementService#hasBeenInstanciated(java.lang.String)}.
	 */
	@ Test
	public void testHasBeenInstanciated() {
		this.project = new Project();
		this.projectS.getProjectDao().saveOrUpdateProject(this.project);
		this.process.addProject(this.project);
		this.pms.getProcessDao().saveOrUpdateProcess(this.process);
		assertTrue(this.pms.hasBeenInstanciated(this.process.getId()));
		this.projectS.getProjectDao().deleteProject(this.project);
	}

}
