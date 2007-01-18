package wilos.test.hibernate.misc.concretetaskdescriptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.spem2.task.TaskDescriptorDao;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.test.TestConfiguration;

/**
 * 
 * @author Sebastien
 *
 * This class represents ... TODO
 *
 */
public class ConcreteTaskDescriptorDaoTest extends TestCase {
	
	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao = null ;

	private ConcreteTaskDescriptor concreteTaskDescriptor = null ;
	
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

	/**
	 * attributes from ConcreteTaskDescriptor
	 */
	public static final String CONCRETENAME = "concreteName";
	
	public static final float PLANNEDTIME = 15.5f;
	
	public static final float REMAININGTIME = 14.8f;
	
	public static final float ACCOMPLISHEDTIME = 4.7f;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
	Date date = null;

	public ConcreteTaskDescriptorDaoTest(){
		try {
			date = sdf.parse("18-01-2007 10:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;
		
		// Get the TaskDescriptorDao Singleton for managing TaskDescriptor data
		this.concreteTaskDescriptorDao = (ConcreteTaskDescriptorDao) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteTaskDescriptorDao") ;

		// Create empty TaskDescriptor
		this.concreteTaskDescriptor = new ConcreteTaskDescriptor() ;
		this.concreteTaskDescriptor.setConcreteName(CONCRETENAME);
		this.concreteTaskDescriptor.setPlannedTime(PLANNEDTIME);
		this.concreteTaskDescriptor.setPlannedFinishingDate(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		this.concreteTaskDescriptorDao.deleteConcreteTaskDescriptor(this.concreteTaskDescriptor) ;
	}

	public void testSaveOrUpdateConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Save the taskDescriptor with the method to test.
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor) ;

		// Check the saving.
		String id = concreteTaskDescriptor.getId() ;
		ConcreteTaskDescriptor taskDescriptorTmp = (ConcreteTaskDescriptor) this.concreteTaskDescriptorDao.getHibernateTemplate().load(ConcreteTaskDescriptor.class, id) ;
		assertNotNull(taskDescriptorTmp) ;

		// Rk: the tearDown method is called here.
	}

	public void testGetAllConcreteTaskDescriptors() {
		// Rk: the setUp method is called here.

		// Save the taskDescriptor into the database.
		this.concreteTaskDescriptorDao.getHibernateTemplate().saveOrUpdate(this.concreteTaskDescriptor) ;

		// Look if this taskDescriptor is also into the database and look if the
		// size of the set is >= 1.
		List<ConcreteTaskDescriptor> concretetaskDescriptors = this.concreteTaskDescriptorDao.getAllConcreteTaskDescriptors();
		assertNotNull(concretetaskDescriptors) ;
		assertTrue(concretetaskDescriptors.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	public void testGetConcreteTaskDescriptor() {
		
		TaskDescriptorDao taskDescriptorDao = new TaskDescriptorDao() ;
		
		TaskDescriptor taskDescriptor = new TaskDescriptor() ;
		// Rk: the setUp method is called here.

		// Add prooperties to the taskDescriptor.
		taskDescriptor = new TaskDescriptor();
		taskDescriptor.setName(NAME) ;
		taskDescriptor.setDescription(DESCRIPTION) ;

		taskDescriptor.setPrefix(PREFIX) ;
		taskDescriptor.setIsPlanned(IS_PLANNED) ;
		taskDescriptor.setHasMultipleOccurrences(HAS_MULTIPLE_OCCURENCES) ;
		taskDescriptor.setIsOptional(IS_OPTIONAL) ;

		taskDescriptor.setIsRepeatable(IS_REPEATABLE) ;
		taskDescriptor.setIsOngoing(IS_ON_GOING) ;
		taskDescriptor.setIsEvenDriven(IS_EVEN_DRIVEN) ;
		
		taskDescriptorDao.saveOrUpdateTaskDescriptor(taskDescriptor);
		String id_taskDescriptor = taskDescriptor.getId();
		TaskDescriptor taskDescriptorTmp = taskDescriptorDao.getTaskDescriptor(id_taskDescriptor);
		
		this.concreteTaskDescriptor.setTaskDescriptor(taskDescriptorTmp);

		// Save the taskDescriptor into the database.
		this.concreteTaskDescriptorDao.getHibernateTemplate().saveOrUpdate(this.concreteTaskDescriptor) ;
		String id = this.concreteTaskDescriptor.getId() ;

		// Test the method getTaskDescriptor with an existing taskDescriptor.
		ConcreteTaskDescriptor concreteTaskDescriptorTmp = this.concreteTaskDescriptorDao.getConcreteTaskDescriptor(id) ;
		assertNotNull(concreteTaskDescriptorTmp) ;
		//assertEquals("ConcreteName", concreteTaskDescriptorTmp.getConcreteName(), CONCRETENAME) ;
		
		// Test the method getTaskDescriptor with an unexisting taskDescriptor.
		this.concreteTaskDescriptorDao.getHibernateTemplate().delete(concreteTaskDescriptor) ;
		concreteTaskDescriptorTmp = this.concreteTaskDescriptorDao.getConcreteTaskDescriptor(id) ;
		assertNull(concreteTaskDescriptorTmp) ;

		// Rk: the tearDown method is called here.
	}

	public void testDeleteConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Save the taskDescriptor into the database.
		this.concreteTaskDescriptorDao.getHibernateTemplate().saveOrUpdate(this.concreteTaskDescriptor) ;
		String id = this.concreteTaskDescriptor.getId() ;

		// Test the method deleteTaskDescriptor with an acitivity existing into
		// the db.
		this.concreteTaskDescriptorDao.deleteConcreteTaskDescriptor(this.concreteTaskDescriptor) ;

		// See if this.taskDescriptor is now absent in the db.
		ConcreteTaskDescriptor concreteTaskDescriptorTmp = (ConcreteTaskDescriptor) this.concreteTaskDescriptorDao.getHibernateTemplate().get(ConcreteTaskDescriptor.class, id) ;
		assertNull(concreteTaskDescriptorTmp) ;

		// Test the method deleteTaskDescriptor with a taskDescriptor unexisting
		// into the db.
		// Normally here there are no exception thrown.
		this.concreteTaskDescriptorDao.deleteConcreteTaskDescriptor(this.concreteTaskDescriptor) ;

		// Rk: the tearDown method is called here.
	}

}
