
package wilos.test.hibernate.misc.wilosuser ;

import java.util.Set ;

import junit.framework.TestCase ;
import wilos.hibernate.misc.wilosuser.ProjectDirectorDao ;
import wilos.model.misc.wilosuser.ProjectDirector ;
import wilos.test.configuration.TestConfiguration ;

/**
 * 
 * This class represents the test class for ProjectDirectorDao class.
 * 
 * @author Marseyeah
 */
public class ProjectDirectorDaoTest extends TestCase {

	private ProjectDirectorDao pdd ;

	private ProjectDirector pd ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.pdd = (ProjectDirectorDao) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectDirectorDao") ;
		this.pd = new ProjectDirector() ;
		this.pd.setLogin("testLogin") ;
		this.pd.setName("testName") ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		this.pdd.deleteProjectDirector(this.pd) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.ProjectDirectorDao#saveOrUpdateProjectDirector(wilos.model.misc.wilosuser.ProjectDirector)}.
	 */
	public void testSaveOrUpdateProjectDirector() {
		this.pdd.saveOrUpdateProjectDirector(this.pd) ;

		ProjectDirector pdTmp = this.pdd.getProjectDirector("testLogin") ;
		assertNotNull(pdTmp) ;
		assertTrue(this.pd.getLogin().equals(pdTmp.getLogin())) ;
		assertTrue(this.pd.getName().equals(pdTmp.getName())) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.ProjectDirectorDao#getAllProjectDirectors()}.
	 */
	public void testGetAllProjectDirectors() {
		this.pdd.saveOrUpdateProjectDirector(this.pd) ;

		Set<ProjectDirector> setTmp = this.pdd.getAllProjectDirectors() ;
		assertNotNull(setTmp) ;
		assertTrue(setTmp.size() >= 1) ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.ProjectDirectorDao#getProjectDirector(java.lang.String)}.
	 */
	public void testGetProjectDirector() {
		this.pdd.saveOrUpdateProjectDirector(this.pd) ;

		ProjectDirector pdTmp = this.pdd.getProjectDirector("testLogin") ;
		assertNotNull(pdTmp) ;
		assertEquals(pdTmp.getLogin(), "testLogin") ;
		assertEquals(pdTmp.getName(), "testName") ;
	}

	/**
	 * Test method for
	 * {@link wilos.hibernate.misc.wilosuser.ProjectDirectorDao#deleteProjectDirector(wilos.model.misc.wilosuser.ProjectDirector)}.
	 */
	public void testDeleteProjectDirector() {
		this.pdd.saveOrUpdateProjectDirector(this.pd) ;
		this.pdd.deleteProjectDirector(this.pd) ;

		ProjectDirector pdTmp = this.pdd.getProjectDirector("testLogin") ;
		assertNull(pdTmp) ;
	}

}
