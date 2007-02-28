package wilos.test.business.services.misc.concretetask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.hibernate.spem2.task.TaskDescriptorDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.test.TestConfiguration;
import wilos.utils.Constantes.State;

public class ConcreteTaskDescriptorServiceTest {

	ConcreteTaskDescriptorService concreteTaskDescriptorService;

	ConcreteTaskDescriptor concreteTaskDescriptor;

	ConcreteTaskDescriptorDao concreteTaskDescriptorDao = null;

	ProjectDao projectDao = null;
	
	ParticipantDao participantdao = null;
	
	TaskDescriptorDao taskDescriptordao = null;
	
	RoleDescriptorDao roleDescriptordao = null;
	
	ConcreteRoleDescriptorDao concreteRoleDescriptorDao = null;

	TaskDescriptor taskDescriptor;

	public static final String NAME = "name";

	public static final String DESCRIPTION = "description";

	public ConcreteTaskDescriptorServiceTest() {
		this.concreteTaskDescriptorService = (ConcreteTaskDescriptorService) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteTaskDescriptorService");
		this.concreteTaskDescriptorDao = (ConcreteTaskDescriptorDao) TestConfiguration
				.getInstance().getApplicationContext().getBean(
						"ConcreteTaskDescriptorDao");
		this.projectDao = (ProjectDao) TestConfiguration.getInstance()
				.getApplicationContext().getBean("ProjectDao");
		this.participantdao = (ParticipantDao) TestConfiguration.getInstance().getApplicationContext().getBean("ParticipantDao");
		this.taskDescriptordao = (TaskDescriptorDao) TestConfiguration.getInstance().getApplicationContext().getBean("TaskDescriptorDao");
		this.roleDescriptordao = (RoleDescriptorDao) TestConfiguration.getInstance().getApplicationContext().getBean("RoleDescriptorDao");
		this.concreteRoleDescriptorDao = (ConcreteRoleDescriptorDao) TestConfiguration.getInstance().getApplicationContext().getBean("ConcreteRoleDescriptorDao");

	}

	@Before
	public void setUp() {

		this.concreteTaskDescriptor = new ConcreteTaskDescriptor();

		this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorDao()
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
	}

	@After
	public void tearDown() {
		this.concreteTaskDescriptorService.getConcreteTaskDescriptorDao()
				.deleteConcreteTaskDescriptor(this.concreteTaskDescriptor);
	}

	@Test
	public void testGetConcreteTaskDescriptorsForProject() {
		// Rk: the setUp method is called here.

		Project project = new Project();
		project.setConcreteName("project");
		this.projectDao.saveOrUpdateProject(project);

		Project project2 = new Project();
		project2.setConcreteName("project2");
		this.projectDao.saveOrUpdateProject(project2);

		this.concreteTaskDescriptor.setProject(project);

		ConcreteTaskDescriptor ctdTmp = new ConcreteTaskDescriptor();
		ctdTmp.setConcreteName("pouet");
		ctdTmp.setProject(project);

		ConcreteTaskDescriptor ctdTmp2 = new ConcreteTaskDescriptor();
		ctdTmp2.setConcreteName("pouet2");
		ctdTmp2.setProject(project2);

		this.concreteTaskDescriptorService
				.getConcreteTaskDescriptorDao()
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		this.concreteTaskDescriptorService.getConcreteTaskDescriptorDao()
		.saveOrUpdateConcreteTaskDescriptor(ctdTmp);
		this.concreteTaskDescriptorService.getConcreteTaskDescriptorDao()
				.saveOrUpdateConcreteTaskDescriptor(ctdTmp2);

		List<ConcreteTaskDescriptor> list = this.concreteTaskDescriptorService
				.getAllConcreteTaskDescriptorsForProject(project.getId());

		assertNotNull(list);
		assertTrue(list.size() == 2);
		
		// cleaning up used objects
		this.projectDao.deleteProject(project);
		this.projectDao.deleteProject(project2);
		this.concreteTaskDescriptorService.getConcreteTaskDescriptorDao().deleteConcreteTaskDescriptor(ctdTmp);
		this.concreteTaskDescriptorService.getConcreteTaskDescriptorDao().deleteConcreteTaskDescriptor(ctdTmp2);
		
		// Rk: the tearDown method is called here.
	}

	@Test
	public void testStartConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.startConcreteTaskDescriptor(this.concreteTaskDescriptor);

		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		String id = this.concreteTaskDescriptor.getId();

		// Get this concreteTaskDescriptor.
		ConcreteTaskDescriptor tmpConcreteTaskDescriptor = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptor(id);

		assertNotNull(tmpConcreteTaskDescriptor);
		assertEquals(tmpConcreteTaskDescriptor.getState(), State.STARTED);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testSuspendConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.suspendConcreteTaskDescriptor(this.concreteTaskDescriptor);

		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		String id = this.concreteTaskDescriptor.getId();

		// Get this concreteTaskDescriptor.
		ConcreteTaskDescriptor tmpConcreteTaskDescriptor = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptor(id);

		assertNotNull(tmpConcreteTaskDescriptor);
		assertEquals(tmpConcreteTaskDescriptor.getState(), State.SUSPENDED);

		// Rk: the tearDown method is called here.
	}

	@Test
	public void testFinishConcreteTaskDescriptor() {
		// Rk: the setUp method is called here.

		// Change the state of the concretetaskdescriptor.
		this.concreteTaskDescriptorService
				.finishConcreteTaskDescriptor(this.concreteTaskDescriptor);

		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		String id = this.concreteTaskDescriptor.getId();

		// Get this concreteTaskDescriptor.
		ConcreteTaskDescriptor tmpConcreteTaskDescriptor = this.concreteTaskDescriptorService
				.getConcreteTaskDescriptor(id);

		assertNotNull(tmpConcreteTaskDescriptor);
		assertEquals(tmpConcreteTaskDescriptor.getState(), State.FINISHED);

		// Rk: the tearDown method is called here.
	}
	
	
	@Test
	public void testAffectedConcreteTaskDescriptor()
	{
		Participant monpar = new Participant();
		this.participantdao.saveOrUpdateParticipant(monpar);
		
		this.taskDescriptor = new TaskDescriptor();
		this.taskDescriptordao.saveOrUpdateTaskDescriptor(this.taskDescriptor);
		
		RoleDescriptor roleDescriptor = new RoleDescriptor();
		this.roleDescriptordao.saveOrUpdateRoleDescriptor(roleDescriptor);
		
		ConcreteRoleDescriptor concreteRoleDescriptor = new ConcreteRoleDescriptor();
		this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(concreteRoleDescriptor);
		
		concreteRoleDescriptor.addParticipant(monpar);
		concreteRoleDescriptor.addRoleDescriptor(roleDescriptor);

		this.taskDescriptor.addMainRole(roleDescriptor);
		this.concreteTaskDescriptor.addTaskDescriptor(this.taskDescriptor);
		
		this.concreteTaskDescriptorService.affectedConcreteTaskDescriptor(concreteTaskDescriptor, monpar);
		
		ConcreteRoleDescriptor res = concreteTaskDescriptor.getConcreteRoleDescriptor();
		assertTrue(res.equals(concreteRoleDescriptor));
		assertTrue(concreteTaskDescriptor.getState().equals("Ready"));
		
		// cleanup used objects
		this.participantdao.deleteParticipant(monpar);
		this.taskDescriptordao.deleteTaskDescriptor(this.taskDescriptor);
		this.roleDescriptordao.deleteRoleDescriptor(roleDescriptor);
		this.concreteRoleDescriptorDao.deleteConcreteRoleDescriptor(concreteRoleDescriptor);
		
	}
}
