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

package wilos.business.services.assistant;

import java.io.File;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.guide.GuidanceService;
import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.business.transfertobject.ParticipantTO;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.process.Process;

/**
 * The services dedicated to the Assistant
 * 
 * @author nicolas
 */

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class AssistantService {
	private ParticipantDao participantDao;
	private ConcreteTaskDescriptorService concreteTaskDescriptorService;
	private ProcessService processService;
	private GuidanceService guidanceService;
	
	
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

    public void setAccomplishedTimeByTask(String taskGuid,float newTime) {
		ConcreteTaskDescriptor ct = concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(taskGuid);
		ct.setAccomplishedTime(newTime);
		concreteTaskDescriptorService.getConcreteTaskDescriptorDao().saveOrUpdateConcreteTaskDescriptor(ct);
    }

    public void setRemainingTimeByTask(String taskGuid,float newTime) {
		ConcreteTaskDescriptor ct = concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(taskGuid);
		ct.setRemainingTime(newTime);
		concreteTaskDescriptorService.getConcreteTaskDescriptorDao().saveOrUpdateConcreteTaskDescriptor(ct);
	}
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public String getAttachmentFilePath(String idGuidance) {
    	String filePathToBeReturn = "";
    	String folder = "";
    	String attachment = "";
    	Guidance g = null;
    	String guidCurrentProcess = null;
    	
    	System.out.println("Id : "+idGuidance);
    	
    	g = guidanceService.getGuidanceFromGuid(idGuidance);
    	
    	BreakdownElement bde = null;
    	
    	if (g.getTaskDefinitions().size() != 0)
			bde = g.getTaskDefinitions().iterator().next().getTaskDescriptors().iterator().next();
		if (g.getRoleDefinitions().size() != 0)
			bde = g.getRoleDefinitions().iterator().next().getRoleDescriptors().iterator().next();
		if (g.getActivities().size() != 0)
			bde = g.getActivities().iterator().next();
		Thread monThread = null;
		if (bde != null) {
			while (bde.getSuperActivities().size() != 0) {
				bde = bde.getSuperActivities().iterator().next();
			}
			if (bde instanceof Process) {
				guidCurrentProcess = bde.getGuid();
			}
		}
		
    	
    	if (g != null && guidCurrentProcess != null) {
    		attachment = g.getAttachment();
    		
    		folder = processService.getProcessFromGuid(guidCurrentProcess).getFolderPath();
    		filePathToBeReturn = folder+ File.separator + guidCurrentProcess + File.separator + attachment;
    		System.out.println("FOLDER+ATTCH: " + filePathToBeReturn);
    	}
    	return filePathToBeReturn;
    }

	public GuidanceService getGuidanceService() {
		return guidanceService;
	}

	public void setGuidanceService(GuidanceService guidanceService) {
		this.guidanceService = guidanceService;
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}


}
