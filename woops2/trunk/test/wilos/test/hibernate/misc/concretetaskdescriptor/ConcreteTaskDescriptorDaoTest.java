package wilos.test.hibernate.misc.concretetaskdescriptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.test.TestConfiguration;
import wilos.utils.Constantes.State;

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
	
	public static final String CONCRETENAME = "concreteName";
	
	//public static final State STATE = State.CREATED;
	
	public static final float PLANNEDTIME = 15.5f;
	
	public static final float REMAININGTIME = 14.8f;
	
	public static final float ACCOMPLISHEDTIME = 4.7f;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@ Override
	protected void setUp() throws Exception {
		super.setUp() ;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		Date date = sdf.parse("18-01-2007 10:00");

		// Get the TaskDescriptorDao Singleton for managing TaskDescriptor data
		this.concreteTaskDescriptorDao = (ConcreteTaskDescriptorDao) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteTaskDescriptorDao") ;

		// Create empty TaskDescriptor
		this.concreteTaskDescriptor = new ConcreteTaskDescriptor() ;
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
		TaskDescriptor taskDescriptorTmp = (TaskDescriptor) this.concreteTaskDescriptorDao.getHibernateTemplate().load(TaskDescriptor.class, id) ;
		assertNotNull(taskDescriptorTmp) ;

		// Rk: the tearDown method is called here.
	}

	public void testGetAllConcreteTaskDescriptors() {
		fail("Not yet implemented");
	}

	public void testGetConcreteTaskDescriptor() {
		fail("Not yet implemented");
	}

	public void testDeleteConcreteTaskDescriptor() {
		fail("Not yet implemented");
	}

}
