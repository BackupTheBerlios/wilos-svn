package wilos.test.hibernate.spem2.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.hibernate.spem2.task.TaskDefinitionDao;
import wilos.model.spem2.task.TaskDefinition;
import wilos.test.TestConfiguration;

/**
 * Unit test for TaskDefinitionDao
 * 
 * @author eperico
 * 
 */
public class TaskDefinitionDaoTest {

	private TaskDefinitionDao taskDefinitionDao = null;

	private TaskDefinition taskDefinition = null;

	/**
	 * attributes from Element
	 */
	public static final String ID = "thisId";

	public static final String NAME = "thisTask";

	public static final String DESCRIPTION = "taskDefinition description";

	@Before
	public void setUp() {

		// Get the TaskDefinitionDao Singleton for managing TaskDefinition data
		this.taskDefinitionDao = (TaskDefinitionDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"TaskDefinitionDao");

		// Create empty TaskDefinition
		this.taskDefinition = new TaskDefinition();
	}

	@After
	public void tearDown() {

		this.taskDefinitionDao.deleteTaskDefinition(this.taskDefinition);
	}

	@Test
	public void testSaveTaskDefinition() {
		// Rk: the setUp method is called here.

		// Save the taskDefinition with the method to test.
		this.taskDefinitionDao.saveOrUpdateTaskDefinition(this.taskDefinition);

		// Check the saving.
		String id = taskDefinition.getId();
		TaskDefinition taskTmp = (TaskDefinition) this.taskDefinitionDao
				.getHibernateTemplate().load(TaskDefinition.class, id);
		assertNotNull(taskTmp);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetAllTaskDefinitions() {
		// Rk: the setUp method is called here.

		// Save the taskDefinition into the database.
		this.taskDefinitionDao.getHibernateTemplate().saveOrUpdate(
				this.taskDefinition);

		// Look if this taskDefinition is also into the database and look if the
		// size of
		// the set is >= 1.
		List<TaskDefinition> taskDefinitions = this.taskDefinitionDao
				.getAllTaskDefinitions();
		assertNotNull(taskDefinitions);
		assertTrue(taskDefinitions.size() >= 1);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testGetTask() {
		// Rk: the setUp method is called here.

		// Add prooperties to the taskDefinition.
		this.taskDefinition.setName(NAME);
		this.taskDefinition.setDescription(DESCRIPTION);

		// Save the taskDefinition into the database.
		this.taskDefinitionDao.getHibernateTemplate().saveOrUpdate(
				this.taskDefinition);
		String id = this.taskDefinition.getId();

		// Test the method getTask with an existing taskDefinition.
		TaskDefinition taskTmp = this.taskDefinitionDao.getTaskDefinition(id);
		assertNotNull(taskTmp);
		assertEquals("Name", taskTmp.getName(), NAME);
		assertEquals("Description", taskTmp.getDescription(), DESCRIPTION);

		// Test the method getTask with an unexisting taskDefinition.
		this.taskDefinitionDao.getHibernateTemplate().delete(taskDefinition);
		taskTmp = this.taskDefinitionDao.getTaskDefinition(id);
		assertNull(taskTmp);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testDeleteTask() {
		// Rk: the setUp method is called here.

		// Save the taskDefinition into the database.
		this.taskDefinitionDao.getHibernateTemplate().saveOrUpdate(
				this.taskDefinition);
		String id = this.taskDefinition.getId();

		// Test the method deleteTask with an acitivity existing into the db.
		this.taskDefinitionDao.deleteTaskDefinition(this.taskDefinition);

		// See if this.task is now absent in the db.
		TaskDefinition taskTmp = (TaskDefinition) this.taskDefinitionDao
				.getHibernateTemplate().get(TaskDefinition.class, id);
		assertNull(taskTmp);

		// Test the method deleteTask with a taskDefinition unexisting into the
		// db.
		// Normally here there are no exception thrown.
		this.taskDefinitionDao.deleteTaskDefinition(this.taskDefinition);

		// Rk: the tearDown method is called here.
	}
}
