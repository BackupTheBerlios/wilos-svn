/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/

package wilos.application.console;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
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

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class InitAppliService {
	private ProcessService processService;
	private ProjectDao projectDao;
	private ProcessDao processDao;
	private ParticipantDao participantDao;
	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao;
	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao;
	private ConcreteTaskDescriptorService concreteTaskDescriptorService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Transactional(readOnly = false)
	public void projectInstanciation() {	
		String s = processService.getProcessDao().getProcessFromGuid("_9llsAQAvEdubGMceRDupFQ").getId();
		Process scrum = processDao.getProcess(s);

		Project project = new Project();
		project.setConcreteName("Wilos");
		project.addProcess(scrum);
		projectDao.saveOrUpdateProject(project);

		//processService.projectInstanciation(project);

		String s2 = processService.getProcessDao().getProcessFromGuid("_0uyGoMlgEdmt3adZL5Dmdw").getId();
		Process openup = processDao.getProcess(s2);

		Project project2 = new Project();
		project2.setConcreteName("IceOpenUP");
		project2.addProcess(openup);
		projectDao.saveOrUpdateProject(project2);

	//	processService.projectInstanciation(project2);
	}
	
	@Transactional(readOnly = false)
	public void initAppli() {	
		
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
		    	if ( ctd2.getTaskDescriptor().getMainRole() != null) {
		    		logger.debug("appel");
		    		concreteTaskDescriptorService.affectedConcreteTaskDescriptor(ctd2, pa);
		    		logger.debug("sortie");
		    		
		    		//concreteTaskDescriptorService.affectedState(ctd2);
		    		logger.debug("sortie");
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

	public ConcreteTaskDescriptorService getConcreteTaskDescriptorService() {
		return concreteTaskDescriptorService;
	}

	public void setConcreteTaskDescriptorService(
			ConcreteTaskDescriptorService concreteTaskDescriptorService) {
		this.concreteTaskDescriptorService = concreteTaskDescriptorService;
	}
	
}
