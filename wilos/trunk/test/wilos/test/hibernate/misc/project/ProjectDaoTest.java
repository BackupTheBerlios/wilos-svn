
package wilos.test.hibernate.misc.project ;

import java.util.Set;

import junit.framework.TestCase;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.project.Project;
import wilos.test.TestConfiguration;

/**
 * 
 * This class represents the test class for ProjectDao class.
 * 
 * @author Marseyeah
 * 
 */
public class ProjectDaoTest extends TestCase {

	private ProjectDao pDao = (ProjectDao) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectDao") ;

	private Project p ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.p = new Project() ;
		//this.p.setConcreteName("testProject") ;
		//this.p.setDescription("testDesc") ;
		
		this.pDao.saveOrUpdateProject(p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		this.pDao.deleteProject(this.p) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.project.ProjectDao#saveOrUpdateProject(wilos.model.misc.project.Project)}.
	 */
	public void testSaveOrUpdateProject() {
		Project pTmp = new Project();
		this.pDao.saveOrUpdateProject(pTmp);
		assertNotNull(this.pDao.getProject(pTmp.getId()));
	}

	/**
	 * Test method for {@link wilos.hibernate.misc.project.ProjectDao#getAllProjects()}.
	 */
	public void testGetAllProjects() {
		Set<Project> projects = this.pDao.getAllProjects() ;
		assertNotNull(projects) ;
	}

	/**
	 * Test method for {@link wilos.hibernate.misc.project.ProjectDao#getProject(java.lang.String)}.
	 */
	public void testGetProject() {
		Project pTmp = this.pDao.getProject("testProject") ;
		assertNotNull(pTmp) ;
		assertEquals(pTmp.getConcreteName(), "testProject") ;
		assertEquals(pTmp.getDescription(), "testDesc") ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.project.ProjectDao#deleteProject(wilos.model.misc.project.Project)}.
	 */
	public void testDeleteProject() {
		Project p1 = new Project() ;
		p1.setConcreteName("testProjectToDelete") ;
		p1.setDescription("testDesc") ;
		this.pDao.saveOrUpdateProject(p1);
		this.pDao.deleteProject(p1);
		Project pTmp = this.pDao.getProject(p1.getId());
		assertNull(pTmp) ;
	}

}
