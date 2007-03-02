package wilos.business.services.assistant;

import java.util.Iterator;

import javax.jws.WebMethod;
import javax.jws.WebParam;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.transfertobject.ParticipantTO;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * The services dedicated to the Assistant
 * 
 * @author nicolas
 */

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class AssistantService {
	private ParticipantDao participantDao;
	private ConcreteTaskDescriptorService concreteTaskDescriptorService;
	
	
	/**
	 * 
	 *
	 * @param roleName
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ParticipantTO getParticipantTO (String login){
		return new ParticipantTO(participantDao.getParticipant(login));		
	}

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
	
	public void startConcreteTaskDescriptor (String id) {
		ConcreteTaskDescriptor ct = concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(id);
		concreteTaskDescriptorService.startConcreteTaskDescriptor(ct);
	}

	public void suspendConcreteTaskDescriptor (String id) {
		ConcreteTaskDescriptor ct = concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(id);
		concreteTaskDescriptorService.suspendConcreteTaskDescriptor(ct);
	}

	public void resumeConcreteTaskDescriptor (String id) {
		ConcreteTaskDescriptor ct = concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(id);
// TODO JF : d�commenter apres impl�mentation de la fonct� par woops		concreteTaskDescriptorService.resumeConcreteTaskDescriptor(ct);
		concreteTaskDescriptorService.startConcreteTaskDescriptor(ct);
	}

	public void finishConcreteTaskDescriptor (String id) {
		ConcreteTaskDescriptor ct = concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(id);
		concreteTaskDescriptorService.finishConcreteTaskDescriptor(ct);
	}

	public ConcreteTaskDescriptorService getConcreteTaskDescriptorService() {
		return concreteTaskDescriptorService;
	}

	public void setConcreteTaskDescriptorService(
			ConcreteTaskDescriptorService concreteTaskDescriptorService) {
		this.concreteTaskDescriptorService = concreteTaskDescriptorService;
	}

    public void setAccomplishedTimeByTask(String taskGuid,int newTime) {
		ConcreteTaskDescriptor ct = concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(taskGuid);
		ct.setAccomplishedTime(newTime);
    }

    public void setRemainingTimeByTask(String taskGuid,int newTime) {
		ConcreteTaskDescriptor ct = concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(taskGuid);
		ct.setRemainingTime(newTime);
	}

}
