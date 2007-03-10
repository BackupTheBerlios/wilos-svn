package wilos.test.model.misc.wilosuser;

import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import junit.framework.TestCase;

/**
 * @author BlackMilk
 *
 * This class represents the class test of the Participant class
 *
 */
public class ParticipantTest extends TestCase {
	
	private Participant participant1;
	
	private Participant participant2;
	
	private final static String LOGIN = "john" ;
	
	private final static String LOGIN2 = "cathy" ;

	private final static String NAME = "georges" ;
	
	private final static String NAME2 = "willis" ;
	
	private final static String FIRSTNAME = "johnny" ;
	
	private final static String FIRSTNAME2 = "catherine" ;

	private final static String PASS = "pass" ;
	
	private final static String PASS2 = "pass2" ;
	

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		participant1 = new Participant();
		participant2 = new Participant();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		participant1.setLogin(LOGIN);
		participant1.setFirstname(FIRSTNAME);
		participant1.setName(NAME);
		participant1.setPassword(PASS);
		participant2.setLogin(LOGIN);
		participant2.setFirstname(FIRSTNAME);
		participant2.setName(NAME);
		participant2.setPassword(PASS);
		assertTrue(participant1.equals(participant2));
		/*Login test*/
		participant2.setLogin(LOGIN2);
		assertFalse(participant1.equals(participant2));
		/*Name test*/
		participant2.setLogin(LOGIN);
		participant2.setName(NAME2);
		assertFalse(participant1.equals(participant2));
		/*FirstName test*/
		participant2.setName(NAME);
		participant2.setFirstname(FIRSTNAME2);
		assertFalse(participant1.equals(participant2));
		/*Password test*/
		participant2.setFirstname(FIRSTNAME);
		participant2.setPassword(PASS2);
		assertFalse(participant1.equals(participant2));
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#getConcreteRoleDescriptors()}.
	 */
	public void testGetConcreteRoleDescriptors() {
		ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();
		participant1.addConcreteRoleDescriptor(crd);
		assertNotNull(participant1.getConcreteRoleDescriptors());
		assertEquals(crd, participant1.getConcreteRoleDescriptors().toArray()[0]);
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#addConcreteRoleDescriptor(wilos.model.misc.concreterole.ConcreteRoleDescriptor)}.
	 */
	public void testAddConcreteRoleDescriptor() {
		ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();
		participant1.addConcreteRoleDescriptor(crd);
		assertNotNull(participant1.getConcreteRoleDescriptors());
		assertEquals(crd, participant1.getConcreteRoleDescriptors().toArray()[0]);
		assertEquals(crd.getParticipant(),participant1);
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#removeConcreteRoleDescriptor(wilos.model.misc.concreterole.ConcreteRoleDescriptor)}.
	 */
	public void testRemoveConcreteRoleDescriptor() {
		ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();
		participant1.addConcreteRoleDescriptor(crd);
		assertNotNull(participant1.getConcreteRoleDescriptors());
		participant1.removeConcreteRoleDescriptor(crd);
		assertTrue(participant1.getConcreteRoleDescriptors().isEmpty());
		assertEquals(crd.getParticipant(),null);
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#removeAllConcreteRoleDescriptors()}.
	 */
	public void testRemoveAllConcreteRoleDescriptors() {
		ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();
		ConcreteRoleDescriptor crd2 = new ConcreteRoleDescriptor();
		participant1.addConcreteRoleDescriptor(crd);
		participant1.addConcreteRoleDescriptor(crd2);
		assertNotNull(participant1.getConcreteRoleDescriptors());
		participant1.removeAllConcreteRoleDescriptors();
		assertTrue(participant1.getConcreteRoleDescriptors().isEmpty());
		assertEquals(null,crd.getParticipant());
		assertEquals(null,crd2.getParticipant());
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#addToProject(wilos.model.misc.project.Project)}.
	 */
	public void testAddToProject() {
		Project project = new Project();
		participant1.addToProject(project);
		assertNotNull(participant1.getAffectedProjectList());
		assertEquals(project, participant1.getAffectedProjectList().toArray()[0]);
		assertEquals(project.getParticipants().toArray()[0],participant1);
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#removeFromProject(wilos.model.misc.project.Project)}.
	 */
	public void testRemoveFromProject() {
		Project project = new Project();
		participant1.addToProject(project);
		assertNotNull(participant1.getAffectedProjectList());
		participant1.removeFromProject(project);
		assertTrue(participant1.getAffectedProjectList().isEmpty());
		assertTrue(project.getParticipants().isEmpty());
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#removeAllProject()}.
	 */
	public void testRemoveAllProject() {
		Project project = new Project();
		Project project2 = new Project();
		participant1.addToProject(project);
		participant1.addToProject(project2);
		assertNotNull(participant1.getAffectedProjectList());
		participant1.removeAllProject();
		assertTrue(participant1.getAffectedProjectList().isEmpty());
		assertTrue(project.getParticipants().isEmpty());
		assertTrue(project2.getParticipants().isEmpty());
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#removeFromAllProject()}.
	 */
	public void testRemoveFromAllProject() {
		Project project = new Project();
		Project project2 = new Project();
		participant1.addToProject(project);
		participant1.addToProject(project2);
		assertNotNull(participant1.getAffectedProjectList());
		participant1.removeFromAllProject();
		assertTrue(participant1.getAffectedProjectList().isEmpty());
		assertTrue(project.getParticipants().isEmpty());
		assertTrue(project2.getParticipants().isEmpty());
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#getAffectedProjectList()}.
	 */
	public void testGetAffectedProjectList() {
		Project project = new Project();
		participant1.addToProject(project);
		assertNotNull(participant1.getAffectedProjectList());
		assertEquals(project, participant1.getAffectedProjectList().toArray()[0]);
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#addManagedProject(wilos.model.misc.project.Project)}.
	 */
	public void testAddManagedProject() {
		Project project = new Project();
		participant1.addManagedProject(project);
		assertNotNull(participant1.getManagedProjects());
		assertEquals(project, participant1.getManagedProjects().toArray()[0]);
		assertEquals(project.getProjectManager(),participant1);
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#removeManagedProject(wilos.model.misc.project.Project)}.
	 */
	public void testRemoveManagedProject() {
		Project project = new Project();
		participant1.addToProject(project);
		assertNotNull(participant1.getManagedProjects());
		participant1.removeFromProject(project);
		assertTrue(participant1.getManagedProjects().isEmpty());
		assertNull(project.getProjectManager());
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#removeAllManagedProjects()}.
	 */
	public void testRemoveAllManagedProjects() {
		Project project = new Project();
		Project project2 = new Project();
		participant1.addManagedProject(project);
		participant1.addManagedProject(project2);
		assertNotNull(participant1.getManagedProjects());
		participant1.removeAllManagedProjects();
		assertTrue(participant1.getManagedProjects().isEmpty());
		assertEquals(null,project.getProjectManager());
		assertEquals(null,project2.getProjectManager());
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.Participant#getManagedProjects()}.
	 */
	public void testGetManagedProjects() {
		Project project = new Project();
		participant1.addManagedProject(project);
		assertNotNull(participant1.getManagedProjects());
		assertEquals(project, participant1.getManagedProjects().toArray()[0]);
	}

}
