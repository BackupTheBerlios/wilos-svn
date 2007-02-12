
package wilos.test.hibernate.spem2.task ;

import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.spem2.task.TaskDescriptorDao;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.test.TestConfiguration;

/**
 * Unit test for TaskDescriptorDao
 * 
 * @author eperico
 * 
 */
public class TaskDescriptorDaoTest extends TestCase {

	private TaskDescriptorDao taskDescriptorDao = null ;
	
	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao = null;

	private TaskDescriptor taskDescriptor = null ;

	/**
	 * attributes from Element
	 */
	public static final String ID = "thisId" ;

	public static final String NAME = "thisTaskDescriptor" ;

	public static final String DESCRIPTION = "taskDescriptor description" ;

	/**
	 * attributes from BreakdownElement
	 */
	public static final String PREFIX = "prefix" ;

	public static final Boolean IS_PLANNED = true ;

	public static final Boolean HAS_MULTIPLE_OCCURENCES = true ;

	public static final Boolean IS_OPTIONAL = true ;

	/**
	 * attributes from WorkBreakdownElement
	 */
	public static final Boolean IS_REPEATABLE = true ;

	public static final Boolean IS_ON_GOING = true ;

	public static final Boolean IS_EVEN_DRIVEN = true ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;

		// Get the TaskDescriptorDao Singleton for managing TaskDescriptor data
		this.taskDescriptorDao = (TaskDescriptorDao) TestConfiguration.getInstance().getApplicationContext().getBean("TaskDescriptorDao") ;
		
		this.concreteTaskDescriptorDao = (ConcreteTaskDescriptorDao) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteTaskDescriptorDao") ;

		// Create empty TaskDescriptor
		this.taskDescriptor = new TaskDescriptor() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		this.taskDescriptorDao.deleteTaskDescriptor(this.taskDescriptor) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.spem2.task.TaskDescriptorDao#saveOrUpdateTaskDescriptor(wilos.model.spem2.task.TaskDescriptor)}.
	 * 
	 * PRINCIPLE Create a tmp taskDescriptor, save it into the database with the method to test.
	 * Then look for the database to check if this tmp taskDescriptor exists. To finish delete this
	 * tmp taskDescriptor from the database.
	 */
	public void testSaveOrUpdateTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Save the taskDescriptor with the method to test.
		this.taskDescriptorDao.saveOrUpdateTaskDescriptor(this.taskDescriptor) ;

		// Check the saving.
		String id = taskDescriptor.getId() ;
		TaskDescriptor taskDescriptorTmp = (TaskDescriptor) this.taskDescriptorDao.getHibernateTemplate().load(TaskDescriptor.class, id) ;
		assertNotNull(taskDescriptorTmp) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.hibernate.spem2.task.TaskDescriptorDao#getAllTaskDescriptors()}.
	 * 
	 * PRINCIPLE Create a tmp taskDescriptor, save it into the database. Then get all activities
	 * from the database with the method to test, and look if the size of the activities set got is >=
	 * 1. To finish delete this tmp taskDescriptor from the database.
	 */
	public void testGetAllTaskDescriptors() {
		// Rk: the setUp method is called here.

		// Save the taskDescriptor into the database.
		this.taskDescriptorDao.saveOrUpdateTaskDescriptor(this.taskDescriptor);

		// Look if this taskDescriptor is also into the database and look if the
		// size of the set is >= 1.
		List<TaskDescriptor> taskDescriptors = this.taskDescriptorDao.getAllTaskDescriptors() ;
		assertNotNull(taskDescriptors) ;
		assertTrue(taskDescriptors.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.hibernate.spem2.task.TaskDescriptorDao#getTaskDescriptor()}.
	 * 
	 */
	public void testGetTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Add prooperties to the taskDescriptor.
		this.taskDescriptor.setName(NAME) ;
		this.taskDescriptor.setDescription(DESCRIPTION) ;

		this.taskDescriptor.setPrefix(PREFIX) ;
		this.taskDescriptor.setIsPlanned(IS_PLANNED) ;
		this.taskDescriptor.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES) ;
		this.taskDescriptor.setIsOptional(IS_OPTIONAL) ;

		this.taskDescriptor.setIsRepeatable(IS_REPEATABLE) ;
		this.taskDescriptor.setIsOngoing(IS_ON_GOING) ;
		this.taskDescriptor.setIsEvenDriven(IS_EVEN_DRIVEN) ;
		
		ConcreteTaskDescriptor concreteTaskDescriptor = new ConcreteTaskDescriptor() ;
		concreteTaskDescriptor.setConcreteName("pouet_pouet");
		
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(concreteTaskDescriptor);
		
		this.taskDescriptor.addConcreteTaskDescriptor(concreteTaskDescriptor);

		// Save the taskDescriptor into the database.
		this.taskDescriptorDao.saveOrUpdateTaskDescriptor(this.taskDescriptor);
		String id = this.taskDescriptor.getId() ;

		// Test the method getTaskDescriptor with an existing taskDescriptor.
		TaskDescriptor taskDescriptorTmp = this.taskDescriptorDao.getTaskDescriptor(id) ;
		assertNotNull(taskDescriptorTmp) ;
		assertTrue("ctds.size()", taskDescriptorTmp.getConcreteTaskDescriptors().size() == 1);
		assertEquals("Name", taskDescriptorTmp.getName(), NAME) ;
		assertEquals("Description", taskDescriptorTmp.getDescription(), DESCRIPTION) ;
		assertEquals("Prefix", taskDescriptorTmp.getPrefix(), PREFIX) ;
		assertEquals("IsPlanned", taskDescriptorTmp.getIsPlanned(), IS_PLANNED) ;
		assertEquals("HasMultipleOccurences", taskDescriptorTmp.getHasMultipleOccurrences(), HAS_MULTIPLE_OCCURENCES) ;
		assertEquals("IsOptional", taskDescriptorTmp.getIsOptional(), IS_OPTIONAL) ;
		assertEquals("IsRepeatable", taskDescriptorTmp.getIsRepeatable(), IS_REPEATABLE) ;
		assertEquals("IsOnGoing", taskDescriptorTmp.getIsOngoing(), IS_ON_GOING) ;
		assertEquals("IsEvenDriven", taskDescriptorTmp.getIsEvenDriven(), IS_EVEN_DRIVEN) ;

		// Test the method getTaskDescriptor with an unexisting taskDescriptor.
		this.taskDescriptorDao.deleteTaskDescriptor(taskDescriptor);
		taskDescriptorTmp = this.taskDescriptorDao.getTaskDescriptor(id) ;
		assertNull(taskDescriptorTmp) ;
		
		// clean temporary data
		this.concreteTaskDescriptorDao.deleteConcreteTaskDescriptor(concreteTaskDescriptor);

		// Rk: the tearDown method is called here.
	}

	/**
	 * Test method for {@link wilos.hibernate.spem2.task.TaskDescriptorDao#deleteTaskDescriptor()}.
	 * 
	 */
	public void testDeleteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Save the taskDescriptor into the database.
		this.taskDescriptorDao.saveOrUpdateTaskDescriptor(this.taskDescriptor);
		String id = this.taskDescriptor.getId() ;

		// Test the method deleteTaskDescriptor with an activity existing into
		// the db.
		this.taskDescriptorDao.deleteTaskDescriptor(this.taskDescriptor) ;

		// See if this.taskDescriptor is now absent in the db.
		TaskDescriptor taskDescriptorTmp = (TaskDescriptor) this.taskDescriptorDao.getHibernateTemplate().get(TaskDescriptor.class, id) ;
		assertNull(taskDescriptorTmp) ;

		// Rk: the tearDown method is called here.
	}
}
