
package wilos.test.hibernate.misc.project ;

import java.util.Set ;

import junit.framework.TestCase ;
import wilos.hibernate.misc.project.ProjectDao ;
import wilos.model.misc.project.Project ;
import wilos.test.configuration.TestConfiguration ;

/**
 * 
 * This class represents the test class for ProjectDao class.
 * 
 * @author Marseyeah
 * 
 */
public class ProjectDaoTest extends TestCase {

	private ProjectDao pDao ;

	private Project p ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.pDao = (ProjectDao) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectDao") ;
		this.p = new Project() ;
		this.p.setConcreteName("testProject") ;
		this.p.setDescription("testDesc") ;
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
		this.pDao.saveOrUpdateProject(this.p) ;

		Project pTmp = this.pDao.getProject("testProject") ;
		assertNotNull(pTmp) ;
		assertTrue(this.p.getConcreteName().equals(pTmp.getConcreteName())) ;
		assertTrue(this.p.getDescription().equals(pTmp.getDescription())) ;
	}

	/**
	 * Test method for {@link wilos.hibernate.misc.project.ProjectDao#getAllProject()}.
	 */
	public void testGetAllProject() {
		this.pDao.saveOrUpdateProject(this.p) ;

		Set<Project> setTmp = this.pDao.getAllProject() ;
		assertNotNull(setTmp) ;
		assertTrue(setTmp.size() >= 1) ;
	}

	/**
	 * Test method for {@link wilos.hibernate.misc.project.ProjectDao#getProject(java.lang.String)}.
	 */
	public void testGetProject() {
		this.pDao.saveOrUpdateProject(this.p) ;

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
		this.pDao.saveOrUpdateProject(this.p) ;
		this.pDao.deleteProject(this.p) ;

		Project pTmp = this.pDao.getProject("testProject") ;
		assertNull(pTmp) ;
	}

}
