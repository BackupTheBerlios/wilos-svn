
package wilos.test.business.services.project ;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase ;
import wilos.business.services.misc.project.ProjectService ;
import wilos.model.misc.project.Project ;
import wilos.test.configuration.TestConfiguration ;
import wilos.model.misc.wilosuser.Participant;

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
	private Participant parti ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp() ;
		this.ps = (ProjectService) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectService") ;
		p = new Project() ;
		p.setConcreteName("Wilos") ;
		p.setDescription("projet de test") ;
		p.setIsFinished(true) ;
		this.ps.getProjectDao().saveOrUpdateProject(p);

		p2 = new Project() ;
		p2.setConcreteName("Wilos2") ;
		p2.setDescription("projet de test2") ;
		p2.setIsFinished(false) ;
		this.ps.getProjectDao().saveOrUpdateProject(p2);
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
		this.ps.getProjectDao().deleteProject(this.p2) ;
	}

	/**
	 * Test method for
	 * {@link woops2.business.services.project.ProjectService#saveProject(woops2.model.misc.project.Project)}.
	 * 
	 */
	public void testSaveProject() {
		this.ps.saveProject(this.p) ;
		Project ProjectTmp = (Project) this.ps.getProjectDao().getProject(this.p.getId()) ;
		assertNotNull(ProjectTmp) ;
		assertEquals(ProjectTmp.getConcreteName(), "Wilos") ;
		assertEquals(ProjectTmp.getDescription(), "projet de test") ;
		// assertEquals(ProjectTmp.getIsFinished(), true) ;
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
	 * Test method for
	 * {@link woops2.business.services.project.ProjectService#getUnfinishedProjects()}.
	 * 
	 */
	public void testGetUnfinishedProjects() {
		this.ps.saveProject(this.p) ;
		this.ps.saveProject(this.p2) ;

		Set<Project> unfProjects = this.ps.getUnfinishedProjects() ;
		
		for(Iterator iter = unfProjects.iterator(); iter.hasNext();){
			Project project = (Project) iter.next() ;
			assertFalse(project.getIsFinished());
		}
		
	}
	
	/**
	 * Test method for addParticipant
	 * 
	 */
	public void testAddParticipant() {
		parti = new Participant() ;		
		Set<Participant> participants = new HashSet<Participant>() ;
		
		assertFalse(participants.contains(parti)) ;		
		this.ps.addParticipant(parti, p) ;
		participants = this.p.getParticipants() ;
		assertTrue(participants.contains(parti)) ;		
	}
}
