package wilos.application.console;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.business.util.Security;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.spem2.process.ProcessDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.process.Process;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class InitAppliService {
	private ProcessService processService;
	private ProjectDao projectDao;
	private ProcessDao processDao;
	private ParticipantService participantService;
	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao;
	private ConcreteTaskDescriptorService concreteTaskDescriptorService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Transactional(readOnly = false)
	public void initAppli() {
		logger.debug("bababa");
		
		Process openup = processService.spelpParsingXML(new File("applitest/wilos/application/console/openUP.xml"));
		processService.saveProcess(openup);
		Process scrum = processService.spelpParsingXML(new File("applitest/wilos/application/console/scrum.xml"));
		processService.saveProcess(scrum);
		
		
		// instanciation des projets
	//	ProjectDao pm = (ProjectDao) factory.getBean("ProjectDao");
		//ProcessDao p = (ProcessDao) factory.getBean("ProcessDao");

		String s = processService.getProcessDao().getProcessFromGuid("_9llsAQAvEdubGMceRDupFQ").getId();
		scrum = processDao.getProcess(s);
		
		Project project = new Project();
		project.setConcreteName("Wilos");
		project.setProcess(scrum);
		projectDao.saveOrUpdateProject(project);

		processService.projectInstanciation(project);

		String s2 = processService.getProcessDao().getProcessFromGuid("_0uyGoMlgEdmt3adZL5Dmdw").getId();
		openup = processDao.getProcess(s2);

		Project project2 = new Project();
		project2.setConcreteName("IceOpenUP");
		project2.setProcess(openup);
		projectDao.saveOrUpdateProject(project2);

		processService.projectInstanciation(project2);
		
		project2 = processService.getProjectDao().getProject(project2.getId());
		project= processService.getProjectDao().getProject(project.getId());
		
		// creation du participant 
		// ParticipantService ps = (ParticipantService) factory.getBean("ParticipantService");
		Participant pa = new Participant();
		
		pa.setLogin("test");
	    pa.setPassword(Security.encode("testtest"));
	    pa.setName("test");
	    pa.setEmailAddress("test@test.com");
	    pa.setFirstname("test");
	    participantService.getParticipantDao().saveOrUpdateParticipant(pa);
	    
	    // affectation du particpant aux concreteRoles
	    
	   // ConcreteRoleDescriptorDao cs = (ConcreteRoleDescriptorDao) factory.getBean("ConcreteRoleDescriptorDao");
	 	for (ConcreteRoleDescriptor crd : concreteRoleDescriptorDao.getAllConcreteRoleDescriptors()) {
	    	pa.addConcreteRoleDescriptor(crd);
	    }	   
	 	logger.debug("bababa");
	 	participantService.getParticipantDao().saveOrUpdateParticipant(pa);
	 	logger.debug("bababa");
			
		// affectation des concreteRoles aux concreteTask
	 	logger.debug("bababa");
	    pa =  participantService.getParticipantDao().getParticipant(pa.getLogin());
	  if (pa != null) {
	    	logger.debug("bababa");

			logger.debug("bababa");
		    
			
			for (ConcreteTaskDescriptor ctd :  concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getAllConcreteTaskDescriptors()) {
		    	logger.debug("bababa");
		    	ConcreteTaskDescriptor ctd2 =  concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(ctd.getId());
		    	//concreteTaskDescriptorService.affectedConcreteTaskDescriptor(ctd2, pa);
		    	for (ConcreteRoleDescriptor crd : pa.getConcreteRoleDescriptors()) {
		    		crd.addConcreteTaskDescriptor(ctd2);
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

	public ConcreteTaskDescriptorService getConcreteTaskDescriptorService() {
		return concreteTaskDescriptorService;
	}

	public void setConcreteTaskDescriptorService(
			ConcreteTaskDescriptorService concreteTaskDescriptorService) {
		this.concreteTaskDescriptorService = concreteTaskDescriptorService;
	}

	public ParticipantService getParticipantService() {
		return participantService;
	}

	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
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
	
}
