package wilos.application.console;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.business.util.Security;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.hibernate.spem2.process.ProcessDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.task.TaskDescriptor;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class InitAppliService {
	private ProcessService processService;
	private ProjectDao projectDao;
	private ProcessDao processDao;
	private ParticipantDao participantDao;
	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao;
	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Transactional(readOnly = false)
	public void initAppli() {
		logger.debug("bababa");
		
		Process openup = processService.spelpParsingXML(new File("applitest/wilos/application/console/openUP.xml"));
		processService.saveProcess(openup);
		Process scrum = processService.spelpParsingXML(new File("applitest/wilos/application/console/scrum.xml"));
		processService.saveProcess(scrum);
		
		
		// instanciation des projets	

		String s = processService.getProcessDao().getProcessFromGuid("_9llsAQAvEdubGMceRDupFQ").getId();
		scrum = processDao.getProcess(s);
		
		Project project = new Project();
		project.setConcreteName("Wilos");
		project.setProcess(scrum);
		projectDao.saveOrUpdateProject(project);
		project= processService.getProjectDao().getProject(project.getId());

		processService.projectInstanciation(project);

		String s2 = processService.getProcessDao().getProcessFromGuid("_0uyGoMlgEdmt3adZL5Dmdw").getId();
		openup = processDao.getProcess(s2);

		Project project2 = new Project();
		project2.setConcreteName("IceOpenUP");
		project2.setProcess(openup);
		projectDao.saveOrUpdateProject(project2);
		project2 = processService.getProjectDao().getProject(project2.getId());

		processService.projectInstanciation(project2);
		
		project2 = processService.getProjectDao().getProject(project2.getId());
		project= processService.getProjectDao().getProject(project.getId());
		
		// creation du participant 	
		Participant pa = new Participant();
		
		pa.setLogin("test");
	    pa.setPassword(Security.encode("testtest"));
	    pa.setName("test");
	    pa.setEmailAddress("test@test.com");
	    pa.setFirstname("test");
	    participantDao.saveOrUpdateParticipant(pa);
	    pa =  participantDao.getParticipant(pa.getLogin());
	    
	    // affectation du particpant aux concreteRoles
	    
	   // ConcreteRoleDescriptorDao cs = (ConcreteRoleDescriptorDao) factory.getBean("ConcreteRoleDescriptorDao");
	 	for (ConcreteRoleDescriptor crd : concreteRoleDescriptorDao.getAllConcreteRoleDescriptors()) {
	    	pa.addConcreteRoleDescriptor(crd);
	    	concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(crd);
	    }	   
	 	logger.debug("bababa");
	 	participantDao.saveOrUpdateParticipant(pa);
	 	logger.debug("bababa");
			
		// affectation des concreteRoles aux concreteTask
	 	logger.debug("bababa");
	    pa =  participantDao.getParticipant(pa.getLogin());
	  if (pa != null) {
	    	logger.debug("bababa");

			logger.debug("bababa");
		    
			
			for (ConcreteTaskDescriptor ctd :  concreteTaskDescriptorDao.getAllConcreteTaskDescriptors()) {
		    	logger.debug("bababa");
		    	ConcreteTaskDescriptor ctd2 =  concreteTaskDescriptorDao.getConcreteTaskDescriptor(ctd.getId());
		    	//concreteTaskDescriptorService.affectedConcreteTaskDescriptor(ctd2, pa);
		    	for (ConcreteRoleDescriptor crd : pa.getConcreteRoleDescriptors()) {
		    		/*for (TaskDescriptor td :  crd.getRoleDescriptor().getPrimaryTasks()) {
		    			if (td.getGuid().equals(ctd2.getTaskDescriptor().getGuid())) {
		    				crd.addConcreteTaskDescriptor(ctd2);
		    			}
		    		}*/
		    		ctd2.setState("Ready");
		    		crd.addConcreteTaskDescriptor(ctd2);
		    		concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(crd);
		    		concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(ctd2);
		    	}
		    }
	    }
	    
	}

	public ConcreteRoleDescriptorDao getConcreteRoleDescriptorDao() {
		return concreteRoleDescriptorDao;
	}

	public void setConcreteRoleDescriptorDao(
			ConcreteRoleDescriptorDao concreteRoleDescriptorDao) {
		this.concreteRoleDescriptorDao = concreteRoleDescriptorDao;
	}	

	public ProcessDao getProcessDao() {
		return processDao;
	}

	public void setProcessDao(ProcessDao processDao) {
		this.processDao = processDao;
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	public ConcreteTaskDescriptorDao getConcreteTaskDescriptorDao() {
		return concreteTaskDescriptorDao;
	}

	public void setConcreteTaskDescriptorDao(
			ConcreteTaskDescriptorDao concreteTaskDescriptorDao) {
		this.concreteTaskDescriptorDao = concreteTaskDescriptorDao;
	}
	
}
