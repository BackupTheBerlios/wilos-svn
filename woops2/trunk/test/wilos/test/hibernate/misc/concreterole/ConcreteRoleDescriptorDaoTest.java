package wilos.test.hibernate.misc.concreterole;

import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.test.TestConfiguration;

/**
 *
 * @author Sebastien
 *
 * This class represents ... TODO
 *
 */
public class ConcreteRoleDescriptorDaoTest extends TestCase {

	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao = null ;

	private ConcreteRoleDescriptor concreteRoleDescriptor = null ;

	/**
	 * attributes from Element
	 */
	public static final String ID = "thisId" ;

	public static final String NAME = "thisRoleDescriptor" ;

	public static final String DESCRIPTION = "roleDescriptor description" ;

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

	public ConcreteRoleDescriptorDaoTest(){

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
		this.concreteRoleDescriptorDao = (ConcreteRoleDescriptorDao) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteRoleDescriptorDao") ;

		// Create empty TaskDescriptor
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor() ;
		this.concreteRoleDescriptor.setConcreteName(CONCRETENAME);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#tearDown()
	 */
	@ Override
	protected void tearDown() throws Exception {
		super.tearDown() ;

		this.concreteRoleDescriptorDao.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor) ;
	}

	public void testSaveOrUpdateConcreteRoleDescriptor() {
		// Rk: the setUp method is called here.

		// Save the roleDescriptor with the method to test.
		this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor) ;

		// Check the saving.
		String id = concreteRoleDescriptor.getId() ;
		ConcreteRoleDescriptor roleDescriptorTmp = (ConcreteRoleDescriptor) this.concreteRoleDescriptorDao.getHibernateTemplate().get(ConcreteRoleDescriptor.class, id) ;
		assertNotNull(roleDescriptorTmp) ;
		assertEquals(roleDescriptorTmp.getConcreteName(), CONCRETENAME);

		// Rk: the tearDown method is called here.
	}

	public void testGetAllConcreteTaskDescriptors() {
		// Rk: the setUp method is called here.

		// Save the roleDescriptor into the database.
		this.concreteRoleDescriptorDao.getHibernateTemplate().saveOrUpdate(this.concreteRoleDescriptor) ;

		// Look if this roleDescriptor is also into the database and look if the
		// size of the set is >= 1.
		List<ConcreteRoleDescriptor> concreteRoleDescriptors = this.concreteRoleDescriptorDao.getAllConcreteRoleDescriptors();
		assertNotNull(concreteRoleDescriptors) ;
		assertTrue(concreteRoleDescriptors.size() >= 1) ;

		// Rk: the tearDown method is called here.
	}

	public void testDeleteConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Save the taskDescriptor into the database.
		this.concreteRoleDescriptorDao.getHibernateTemplate().saveOrUpdate(this.concreteRoleDescriptor) ;
		String id = this.concreteRoleDescriptor.getId() ;

		// Test the method deleteTaskDescriptor with an acitivity existing into
		// the db.
		this.concreteRoleDescriptorDao.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor) ;

		// See if this.taskDescriptor is now absent in the db.
		ConcreteRoleDescriptor concreteRoleDescriptorTmp = (ConcreteRoleDescriptor) this.concreteRoleDescriptorDao.getHibernateTemplate().get(ConcreteRoleDescriptor.class, id) ;
		assertNull(concreteRoleDescriptorTmp) ;

		// Test the method deleteTaskDescriptor with a taskDescriptor unexisting
		// into the db.
		// Normally here there are no exception thrown.
		this.concreteRoleDescriptorDao.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor) ;

		// Rk: the tearDown method is called here.
	}

}
