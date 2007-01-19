
package wilos.test.business.services.project ;

import java.util.Set;

import junit.framework.TestCase;
import wilos.business.services.project.ProjectService;
import wilos.model.misc.project.Project;
import wilos.test.configuration.TestConfiguration;

/**
 * This class represents the class test of the ProjectService class.
 * 
 * @author Marseyeah
 * 
 */
public class ProjectServiceTest extends TestCase {

	private ProjectService ps ;

	private Project p ;
	private Project p2 ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.ps = (ProjectService) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectService") ;
		p = new Project() ;
		p.setName("Wilos") ;
		p.setDescription("projet de test") ;
		p.setIsFinished(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown() ;
		// Delete the tmp Project from the database.
		this.ps.getProjectDao().deleteProject(this.p) ;
	}

	/**
	 * Test method for
	 * {@link woops2.business.services.project.ProjectService#saveProject(woops2.model.misc.project.Project)}.
	 * 
	 */
	public void testSaveProject() {
		this.ps.saveProject(this.p) ;
		Project ProjectTmp = (Project) this.ps.getProjectDao().getProject("Wilos") ;
		assertNotNull(ProjectTmp) ;
		assertEquals(ProjectTmp.getName(), "Wilos") ;
		assertEquals(ProjectTmp.getDescription(), "projet de test") ;
		//assertEquals(ProjectTmp.getIsFinished(), true) ;
	}

	/**
	 * Test method for {@link woops2.business.services.project.ProjectService#projectExists(String)}.
	 * 
	 */
	public void testProjectExists() {
		// Test for an existing project
		this.ps.saveProject(this.p) ;
		assertTrue(this.ps.projectExist("Wilos")) ;

		// Test for a non-existing project
		assertFalse(this.ps.projectExist("poulou")) ;
	}

	/**
	 * Test method for {@link woops2.business.services.project.ProjectService#getUnfinishedProjects()}.
	 * 
	 */
	public void testGetUnfinishedProjects(){
		p2 = new Project() ;
		p2.setName("Wilos2") ;
		p2.setDescription("projet de test2") ;
		p2.setIsFinished(false);
		
		this.ps.saveProject(this.p2) ;
		
		Set<Project> projects = this.ps.getUnfinishedProjects();
		for(Project p:projects){
			
		
		}
		
	}
}
