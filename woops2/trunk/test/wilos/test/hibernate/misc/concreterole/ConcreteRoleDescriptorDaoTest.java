package wilos.test.hibernate.misc.concreterole;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.test.TestConfiguration;

/**
 *
 * @author Almiriad
 *
 * This class represents ... TODO
 *
 */
public class ConcreteRoleDescriptorDaoTest extends TestCase {

	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao = null ;

	private ConcreteRoleDescriptor concreteRoleDescriptor = null ;

	/**
	 * attributes from ConcreteTaskDescriptor
	 */
	public static final String CONCRETE_NAME = "concreteName";
	
	/**
	 * attributes from Project
	 */
	public static final String PROJECT_NAME = "projectname";
	
	public static final String PROJECT_DESCRIPTION = "description";
	
	public static final Date CREATION_DATE = new Date();
	
	public static final Date LAUNCHING_DATE = new Date();
	
	public static final boolean IS_FINISHED = false;
	
	/**
	 * attributes from Participant
	 */
	public static final String PARTICIPANT_NAME = "Participant";
	
	public static final String FIRST_NAME = "Participant";
	
	public static final String EMAIL = "Participant@wilos.com";
	
	public static final String LOGIN = "Participant";
	
	public static final String PASSWORD = "Participant";

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
		this.concreteRoleDescriptor.setConcreteName(CONCRETE_NAME);
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
		assertEquals(roleDescriptorTmp.getConcreteName(), CONCRETE_NAME);

		// Rk: the tearDown method is called here.
	}

	public void testGetAllConcreteRoleDescriptors() {
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

	public void testDeleteConcreteRoleDescriptor() {
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

	public void testGetConcreteRoleDescriptor() {
		// Adds properties to the concreteBreakdownElement.
		this.concreteRoleDescriptor.setConcreteName(CONCRETE_NAME);

		// Saves the concreteBreakdownElement into the database.
		this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(
				this.concreteRoleDescriptor);
		String id = this.concreteRoleDescriptor.getId();

		// Tests the method getBreakdownElement with an existing
		// concreteBreakdownElement.
		ConcreteBreakdownElement cbdeTmp = this.concreteRoleDescriptorDao
				.getConcreteRoleDescriptor(id);
		assertNotNull(cbdeTmp);
		assertEquals("Name", cbdeTmp.getConcreteName(), CONCRETE_NAME);

		// Tests the method getConcreteBreakdownElement with an unexisting
		// concreteBreakdownElement.
		this.concreteRoleDescriptorDao.getHibernateTemplate().delete(
				this.concreteRoleDescriptor);
		cbdeTmp = this.concreteRoleDescriptorDao.getConcreteRoleDescriptor(id);
		assertNull(cbdeTmp);

		// Rk: the tearDown method is called here.
	}
	
	public void testGetConcreteRoleDescriptorsFromProject() {
		
		 //Rk: the setUp method is called here.

		// Adds properties to the concreteBreakdownElement.
		this.concreteRoleDescriptor.setConcreteName(CONCRETE_NAME);

		// Saves the concreteBreakdownElement into the database.
		this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(
				this.concreteRoleDescriptor);
		String id = this.concreteRoleDescriptor.getId();
		
		Project project = new Project();
		
		project.setName(PROJECT_NAME);
		project.setDescription(PROJECT_DESCRIPTION);
		project.setCreationDate(CREATION_DATE);
		project.setLaunchingDate(LAUNCHING_DATE);
		project.setIsFinished(IS_FINISHED);
			
		Participant participant = new Participant() ;
		
		participant.setName(PARTICIPANT_NAME);
		participant.setFirstname(FIRST_NAME);
		participant.setEmailAddress(EMAIL);
		participant.setLogin(LOGIN);
		participant.setPassword(PASSWORD);
		participant.addConcreteRoleDescriptor(this.concreteRoleDescriptor);
		participant.addToProject(project);
		project.addToParticipant(participant);
		this.concreteRoleDescriptor.addParticipant(participant);
		
//		 Tests the method getConcreteRoleDescriptorsFromProject with an existing
		// concreteRoleDescriptor.
		List<ConcreteRoleDescriptor> listCRDTmp = this.concreteRoleDescriptorDao
				.getConcreteRoleDescriptorsFromProject(project.getProject_id());
		assertNotNull(listCRDTmp);
		Iterator it =listCRDTmp.iterator();		
		assertEquals("Name", ((ConcreteRoleDescriptor)it.next()).getConcreteName(), CONCRETE_NAME);

//		 Tests the method getConcreteBreakdownElement with an unexisting
		// concreteBreakdownElement.
		this.concreteRoleDescriptorDao.getHibernateTemplate().delete(
				this.concreteRoleDescriptor);
		listCRDTmp = this.concreteRoleDescriptorDao.getConcreteRoleDescriptorsFromProject(project.getProject_id());
		assertNull(listCRDTmp);
	}
}
