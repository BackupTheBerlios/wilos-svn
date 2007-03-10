package wilos.test.model.misc.wilosuser;

import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.ProjectDirector;
import junit.framework.TestCase;

/**
 * @author BlackMilk
 *
 * This class represents the class test of the ProjectDirector class
 *
 */
public class ProjectDirectorTest extends TestCase {

	private ProjectDirector pd1;
	private ProjectDirector pd2;
	
	private final static String LOGIN = "john" ;
	
	private final static String LOGIN2 = "cathy" ;

	private final static String NAME = "georges" ;
	
	private final static String NAME2 = "willis" ;
	
	private final static String FIRSTNAME = "johnny" ;
	
	private final static String FIRSTNAME2 = "catherine" ;

	private final static String PASS = "pass" ;
	
	private final static String PASS2 = "pass2" ;
	
	private final static String DESCRIPTION = "process1";
	private final static String DESCRIPTION2 = "process2";
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		pd1 = new ProjectDirector();
		pd2 = new ProjectDirector();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.ProjectDirector#equals(java.lang.Object)}.
	 */
	public void testEqualsObject() {
		pd1.setLogin(LOGIN);
		pd1.setFirstname(FIRSTNAME);
		pd1.setName(NAME);
		pd1.setPassword(PASS);
		pd2.setLogin(LOGIN);
		pd2.setFirstname(FIRSTNAME);
		pd2.setName(NAME);
		pd2.setPassword(PASS);
		assertTrue(pd1.equals(pd2));
		/*Login test*/
		pd2.setLogin(LOGIN2);
		assertFalse(pd1.equals(pd2));
		/*Name test*/
		pd2.setLogin(LOGIN);
		pd2.setName(NAME2);
		assertFalse(pd1.equals(pd2));
		/*FirstName test*/
		pd2.setName(NAME);
		pd2.setFirstname(FIRSTNAME2);
		assertFalse(pd1.equals(pd2));
		/*Password test*/
		pd2.setFirstname(FIRSTNAME);
		pd2.setPassword(PASS2);
		assertFalse(pd1.equals(pd2));
	}
	
	/**
	 * Test method for {@link wilos.model.misc.wilosuser.ProjectDirector#addMonitoredProject(wilos.model.misc.project.Project)}.
	 */
	public void testAddMonitoredProject() {
		Project project = new Project();
		project.setDescription(DESCRIPTION);
		pd1.addMonitoredProject(project);
		assertEquals(project,(Project)(pd1.getProjectMonitored().toArray())[0]);
		assertEquals(project.getProjectDirector(),pd1);
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.ProjectDirector#removeMonitoredProject(wilos.model.misc.project.Project)}.
	 */
	public void testRemoveMonitoredProject() {
		Project project = new Project();
		project.setDescription(DESCRIPTION);
		pd1.addMonitoredProject(project);
		assertEquals(1,pd1.getProjectMonitored().size());
		pd1.removeMonitoredProject(project);		
		assertTrue(pd1.getProjectMonitored().isEmpty());
		assertEquals(project.getProjectDirector(),null);
	}

	/**
	 * Test method for {@link wilos.model.misc.wilosuser.ProjectDirector#removeAllMonitoredProjects()}.
	 */
	public void testRemoveAllMonitoredProjects() {
		Project project1 = new Project();
		project1.setDescription(DESCRIPTION);
		Project project2 = new Project();
		project2.setDescription(DESCRIPTION2);
		pd1.addMonitoredProject(project1);
		pd1.addMonitoredProject(project2);
		/*assertNotNull(pd1.getProjectMonitored());*/
		pd1.removeAllMonitoredProjects();
		assertTrue(pd1.getProjectMonitored().isEmpty());
		assertEquals(null,project1.getProjectDirector());
		assertEquals(null,project2.getProjectDirector());
	}

}
