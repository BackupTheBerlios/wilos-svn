
package wilos.test.business.services.spem2.task ;

import junit.framework.TestCase ;
import wilos.business.services.spem2.task.StepService;
import wilos.hibernate.spem2.task.TaskDefinitionDao ;
import wilos.model.spem2.task.Step ;
import wilos.model.spem2.task.TaskDefinition ;
import wilos.test.TestConfiguration ;

/**
 * @author deder
 * 
 */
public class StepServiceTest extends TestCase {

	private StepService stepService ;

	private Step step ;

	private TaskDefinitionDao taskDefinitionDao ;

	TaskDefinition taskDefinition ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.stepService = (StepService) TestConfiguration.getInstance().getApplicationContext().getBean("StepService") ;
		this.taskDefinitionDao = (TaskDefinitionDao) TestConfiguration.getInstance().getApplicationContext().getBean("TaskDefinitionDao") ;

		this.step = new Step() ;

		this.taskDefinition = new TaskDefinition() ;
		this.taskDefinitionDao.saveOrUpdateTaskDefinition(this.taskDefinition) ;

		this.step.setName("stepName") ;
		this.step.setTaskDefinition(taskDefinition) ;

		// Save the step.
		this.stepService.saveStep(this.step) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		this.taskDefinitionDao.deleteTaskDefinition(this.taskDefinition) ;
		this.stepService.deleteStep(this.step) ;

	}

	/**
	 * Test method for {@link wilos.business.services.spem2.task.StepService#getStep(java.lang.String)}.
	 */
	public void testGetStep() {
		// Rk: the setUp method is called here.

		// Look if this activity is also into the database and look if the size of the set is >= 1.
		Step stepTmp = this.stepService.getStep(this.step.getId()) ;
		assertNotNull(stepTmp) ;
		assertEquals(this.step, stepTmp) ;
		assertTrue(true) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.business.services.spem2.task.StepService#getAllSteps()}.
	 */
	public void testGetAllSteps() {
		// fail("Not yet implemented") ;
	}

	/**
	 * Test method for
	 * {@link wilos.business.services.spem2.task.StepService#saveStep(wilos.model.spem2.task.Step)}.
	 */
	public void testSaveStep() {
		// Rk: the setUp method is called here.
		
		// Save the step. 
		this.stepService.saveStep(this.step) ;
		// Look if this activity is also into the database and look if the size of the set is >=1.
		Step stepTmp = this.stepService.getStep(this.step.getId()) ;
		assertNotNull(stepTmp) ;
		assertEquals(this.step, stepTmp) ;
		
		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.business.services.spem2.task.StepService#deleteStep(wilos.model.spem2.task.Step)}.
	 */
	public void testDeleteStep() {
		// Rk: the setUp method is called here.
		
		// Save the task into the database.
		this.stepService.saveStep(this.step) ;
		String id = this.step.getId() ;
		
		// Test the method deleteTask with an acitivity existing into the db.
		this.stepService.deleteStep(this.step) ;
		// See if the step is in the db.
		Step stepTmp = this.stepService.getStep(id) ;
		assertNull(stepTmp) ;
		
		// Test the method deleteTask with a task unexisting into the db.
		//Normally here there are no exception thrown.
		this.stepService.deleteStep(this.step) ;
		
		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for
	 * {@link wilos.business.services.spem2.task.StepService#getStepsFromTask(java.lang.String)}.
	 */
	public void testGetStepsFromTask() {
		// fail("Not yet implemented") ;
	}

}
