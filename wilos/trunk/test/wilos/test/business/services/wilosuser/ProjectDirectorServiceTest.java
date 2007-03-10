package wilos.test.business.services.wilosuser;

import java.util.Set;

import junit.framework.TestCase;
import wilos.business.services.misc.wilosuser.ProjectDirectorService;
import wilos.business.util.Security;
import wilos.model.misc.wilosuser.ProjectDirector;
import wilos.test.configuration.TestConfiguration;

/**
 * @author Martial
 *
 * This class represents the TestCase for the ProjectDirectorService class.  
 *
 */
public class ProjectDirectorServiceTest extends TestCase {

	private ProjectDirectorService projectDirectorService;
	private ProjectDirector projectDirector1;
	private ProjectDirector projectDirector2 ;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.projectDirectorService= (ProjectDirectorService) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectDirectorService") ;
		this.projectDirector1=new ProjectDirector();
		this.projectDirector1.setLogin("john");
		this.projectDirector1.setName("georges");
		this.projectDirector1.setPassword("pass") ;
		
		this.projectDirector2 = new ProjectDirector();
		this.projectDirector2.setLogin("bert");
		this.projectDirector2.setName("rand");
		this.projectDirector2.setPassword("stephane") ;
		
		this.projectDirectorService.saveProjectDirector(this.projectDirector1);
		this.projectDirectorService.saveProjectDirector(this.projectDirector2);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		this.projectDirectorService.deleteProjectDirector(this.projectDirector1) ;
		this.projectDirectorService.deleteProjectDirector(this.projectDirector2);
	}

	/**
	 * Test method for {@link woops2.business.wilosuser.ProjectDirectorService#saveProjectDirector(woops2.model.wilosuser.ProjectDirector)}.
	 */
	public void testSaveProjectDirector() {
		//this.projectDirectorService.saveProjectDirector(this.projectDirector);
		ProjectDirector ProjectDirectorTmp = (ProjectDirector) this.projectDirectorService.getProjectDirectorDao().getProjectDirector("bert");
		assertNotNull(ProjectDirectorTmp);
		assertEquals(ProjectDirectorTmp.getName(), "rand") ;
		assertEquals(ProjectDirectorTmp.getLogin(), "bert") ;
		assertEquals(ProjectDirectorTmp.getPassword(), Security.encode("stephane")) ;
	}
	
	public void testGetProjectDirectors() {
		//this.projectDirectorService.saveProjectDirector(this.projectDirector);
		//this.projectDirectorService.saveProjectDirector(this.test);
		
		ProjectDirector test2 = new ProjectDirector();
		test2.setLogin("test");
		test2.setName("test");
		test2.setPassword("test") ;
		
		Set<ProjectDirector> ensemble = this.projectDirectorService.getProjectDirectors();
		
		assertNotNull(ensemble);
		assertTrue(ensemble.contains(this.projectDirector1));
		assertTrue(ensemble.contains(this.projectDirector2));
		assertFalse(ensemble.contains(test2));
		
	}
	
	public void testDeleteProjectDirector() {
		//this.projectDirectorService.saveProjectDirector(this.projectDirector);
		ProjectDirector projectDirector3 = new ProjectDirector();
		projectDirector3.setLogin("test");
		projectDirector3.setName("test");
		projectDirector3.setPassword("test") ;
		this.projectDirectorService.saveProjectDirector(projectDirector3);
		
		Set<ProjectDirector> ensemble = this.projectDirectorService.getProjectDirectors();
		assertTrue(ensemble.contains(projectDirector3));
		this.projectDirectorService.deleteProjectDirector(projectDirector3);
		Set<ProjectDirector> ensemble1 = this.projectDirectorService.getProjectDirectors();
		assertFalse(ensemble1.contains(projectDirector3));
		
		//this.projectDirectorService.saveProjectDirector(this.projectDirector);
	}
}
