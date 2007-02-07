package wilos.test.business.services.misc.concreterole;

import java.util.List;

import junit.framework.TestCase;
import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.test.TestConfiguration;

/**
 *
 * @author eperico
 *
 */
public class ConcreteRoleDescriptorServiceTest extends TestCase {

	ConcreteRoleDescriptor concreteRoleDescriptor;

	ConcreteRoleDescriptorService concreteRoleDescriptorService = (ConcreteRoleDescriptorService) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteRoleDescriptorService");

	ProjectDao projectDao = (ProjectDao) TestConfiguration.getInstance().getApplicationContext().getBean("ProjectDao");
	RoleDescriptorDao roledescriptorDao = (RoleDescriptorDao) TestConfiguration.getInstance().getApplicationContext().getBean("RoleDescriptorDao");
	ParticipantDao participantDao = (ParticipantDao) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantDao");

	public static final String NAME = "name" ;

	public ConcreteRoleDescriptorServiceTest() {
		this.concreteRoleDescriptorService = (ConcreteRoleDescriptorService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteRoleDescriptorService");
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor();
		this.concreteRoleDescriptor.setConcreteName(NAME);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.deleteConcreteRoleDescriptor(this.concreteRoleDescriptor);
	}

	public void testGetConcreteRoleDescriptorsForProject() {
		// Rk: the setUp method is called here.

		// add a project
		Project project = new Project();
		project.setConcreteName("projectName");
		this.projectDao.saveOrUpdateProject(project);

		// add a roledescriptor
		RoleDescriptor roledescriptor = new RoleDescriptor();
		roledescriptor.setName("RDName");
		this.roledescriptorDao.saveOrUpdateRoleDescriptor(roledescriptor);

		// add a participant
		Participant participant = new Participant();
		participant.setName("PartName");
		participant.addConcreteRoleDescriptor(this.concreteRoleDescriptor);
		participant.addToProject(project);
		this.participantDao.saveOrUpdateParticipant(participant);

		this.concreteRoleDescriptor.addRoleDescriptor(roledescriptor);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao().saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);

		List<ConcreteRoleDescriptor> list = this.concreteRoleDescriptorService
		.getAllConcreteRoleDescriptorsForProject(project.getId());

		assertNotNull("notNull",list);
		assertTrue("list.size",list.size() >= 1);
		assertTrue(list.contains(this.concreteRoleDescriptor));
		//assertEquals("Equals",((ConcreteRoleDescriptor) list.get(0)).getConcreteName());

		// Rk: the tearDown method is called here.
	}
}
