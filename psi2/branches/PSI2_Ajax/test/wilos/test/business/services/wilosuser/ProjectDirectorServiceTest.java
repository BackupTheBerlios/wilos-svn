package wilos.test.business.services.wilosuser;

import wilos.business.services.wilosuser.ProjectDirectorService;
import wilos.model.misc.wilosuser.ProjectDirector;
import wilos.test.configuration.TestConfiguration;
import junit.framework.TestCase;

/**
 * @author Martial
 *
 * This class represents ... TODO
 *
 */
public class ProjectDirectorServiceTest extends TestCase {

	private ProjectDirectorService projectDirectorService;
	private ProjectDirector projectDirector;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.projectDirectorService= (ProjectDirectorService) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectDirectorService") ;
		this.projectDirector=new ProjectDirector();
		this.projectDirector.setLogin("john");
		this.projectDirector.setName("georges");
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		this.projectDirectorService.getProjectDirectorDao().deleteProjectDirector(this.projectDirector) ;
	}

	/**
	 * Test method for {@link woops2.business.wilosuser.ProjectDirectorService#saveProjectDirector(woops2.model.wilosuser.ProjectDirector)}.
	 */
	public void testSaveProjectDirector() {
		this.projectDirectorService.saveProjectDirector(this.projectDirector);
		ProjectDirector ProjectDirectorTmp = (ProjectDirector) this.projectDirectorService.getProjectDirectorDao().getProjectDirector("john");
		assertNotNull(ProjectDirectorTmp);
		assertEquals(ProjectDirectorTmp.getName(), "georges") ;
		assertEquals(ProjectDirectorTmp.getLogin(), "john") ;
	}

}
