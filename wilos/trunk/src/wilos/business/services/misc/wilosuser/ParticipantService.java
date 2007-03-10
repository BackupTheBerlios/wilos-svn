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
package wilos.business.services.misc.wilosuser ;

import java.util.ArrayList;
import java.util.HashMap ;
import java.util.HashSet ;
import java.util.Iterator ;
import java.util.List;
import java.util.Map ;
import java.util.Set ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.springframework.transaction.annotation.Propagation ;
import org.springframework.transaction.annotation.Transactional ;

import wilos.hibernate.misc.wilosuser.ParticipantDao ;
import wilos.model.misc.project.Project ;
import wilos.model.misc.wilosuser.Participant ;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor ;
import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.misc.project.ProjectService ;
import wilos.business.util.Security ;

/**
 * The services associated to the Participant
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ParticipantService {

	private ParticipantDao participantDao ;

	private ProjectService projectService ;

	private ConcreteRoleDescriptorService concreteRoleDescriptorService;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public ConcreteRoleDescriptorService getConcreteRoleDescriptorService() {
		return this.concreteRoleDescriptorService ;
	}

	public void setConcreteRoleDescriptorService(ConcreteRoleDescriptorService _concreteRoleDescriptorService) {
		this.concreteRoleDescriptorService = _concreteRoleDescriptorService ;
	}

	/**
	 * Getter of ParticipantDao.
	 * 
	 * @return the ParticipantDao
	 */
	public ParticipantDao getParticipantDao() {
		return this.participantDao ;
	}

	/**
	 * Setter of participantDao.
	 * 
	 * @param _participantDao
	 *            The participantDao to set.
	 */
	public void setParticipantDao(ParticipantDao _participantDao) {
		this.participantDao = _participantDao ;
	}

	/**
	 * Getter of projectService.
	 * 
	 * @return the projectService.
	 */
	public ProjectService getProjectService() {
		return this.projectService ;
	}

	/**
	 * Setter of projectService.
	 * 
	 * @param _projectService
	 *            The projectService to set.
	 */
	public void setProjectService(ProjectService _projectService) {
		this.projectService = _projectService ;
	}

	/**
	 * Return all the roles
	 * @return the roles
	 */
	@Transactional(readOnly = true)
	public HashMap<String,Boolean> getConcreteRoleDescriptorsForAParticipantAndForAProject(String _projectId, String _participantId) {
		// TODO: getRolesListForAParticipant à deplacer dans le RoleService
		HashMap<String,Boolean> concreteRolesList = new HashMap<String,Boolean>();
		List<ConcreteRoleDescriptor> projectConcreteRolesList = this.concreteRoleDescriptorService.getAllConcreteRoleDescriptorsForProject(_projectId);
		if(projectConcreteRolesList != null){
			List<ConcreteRoleDescriptor> participantConcreteRolesList = new ArrayList<ConcreteRoleDescriptor>();
			participantConcreteRolesList.addAll(this.participantDao.getAllConcreteRolesForAParticipant(_participantId));
			for(Iterator iter = projectConcreteRolesList.iterator(); iter.hasNext();){
				ConcreteRoleDescriptor element = (ConcreteRoleDescriptor) iter.next() ;
				for(ConcreteRoleDescriptor concreteRoleDescriptor : participantConcreteRolesList){
					if(element.getId() == concreteRoleDescriptor.getId())
						concreteRolesList.put(concreteRoleDescriptor.getId(),new Boolean(true));
					else
						concreteRolesList.put(concreteRoleDescriptor.getId(),new Boolean(true));
				}			
			}
		}
		return concreteRolesList;
	}

	/**
	 * Return participants list
	 * 
	 * @return the list of participants
	 */
	@Transactional(readOnly = true)
	public Set<Participant> getParticipants() {
		return this.participantDao.getAllParticipants() ;
	}
	
	/**
	 * Return the Participant which have the id _id.
	 * 
	 * @return the participant which have the id gived in parameter
	 */
	@Transactional(readOnly = true)
	public Participant getParticipant(String _id) {
		return this.participantDao.getParticipantById(_id) ;
	}

	/**
	 * Save participant
	 * 
	 * @param _participant
	 */
	@Transactional(readOnly = false)
	public void saveParticipant(Participant _participant) {
		_participant.setPassword(Security.encode(_participant.getPassword())) ;
		participantDao.saveOrUpdateParticipant(_participant) ;
	}
	
	/**
	 * Save participant without encryption of the password
	 * 
	 * @param _participant
	 */
	@Transactional(readOnly = false)
	public void saveParticipantWithoutEncryption(Participant _participant) {
		participantDao.saveOrUpdateParticipant(_participant) ;
	}
	
	/**
	 * delete a participant
	 * @param participantId
	 */
	public void deleteParticipant(String participantId)
	{
		Participant participant = this.getParticipant(participantId);
		this.participantDao.deleteParticipant(participant);
	}

	/**
	 * 
	 * return the list of project where a participant is affected to
	 * TODO method description to improve
	 * @param participant
	 *            the participant which the affected to project are returned
	 * @return list of project where the participant is affected to
	 */
	@Transactional (readOnly = true)
	public HashMap<Project, Boolean> getProjectsForAParticipant(Participant participant) {
		HashMap<Project, Boolean> affectedProjectList = new HashMap<Project, Boolean>() ;
		HashSet<Project> allProjectList = new HashSet<Project>() ;
		Participant chargedParticipant = new Participant() ;

		// chargement du participant et des projets
		String login = participant.getLogin() ;
		chargedParticipant = this.participantDao.getParticipant(login) ;
		allProjectList = (HashSet<Project>) this.projectService.getUnfinishedProjects() ;

		for(Project p : allProjectList){
			if(chargedParticipant.getAffectedProjectList().contains(p)){
				affectedProjectList.put(p, true) ;
			}
			else{
				affectedProjectList.put(p, false) ;
			}
		}
		return affectedProjectList ;
	}
	
	/**
	 * 
	 * return the list of project where a participant is affected to
	 * TODO
	 * @param participant
	 *            the participant which the affected to project are returned
	 * @return list of project where the participant is affected to
	 */
	@Transactional (readOnly = true)
	public List<Project> getAllAffectedProjectsForParticipant(Participant participant) {
		List<Project> affectedProjectList = new ArrayList<Project>() ;
		HashSet<Project> allProjectList = new HashSet<Project>() ;
		Participant chargedParticipant = new Participant() ;

		// chargement du participant et des projets
		String login = participant.getLogin() ;
		chargedParticipant = this.participantDao.getParticipant(login) ;
		allProjectList = (HashSet<Project>) this.projectService.getUnfinishedProjects() ;

		for(Project p : allProjectList){
			if(chargedParticipant.getAffectedProjectList().contains(p)){
				affectedProjectList.add(p) ;
			}
		}
		return affectedProjectList ;
	}

	/**
	 * 
	 * TODO Method description
	 * 
	 * @param participant
	 * @param affectedProjects
	 */
	@Transactional(readOnly = false)
	public void saveProjectsForAParticipant(Participant participant, Map<String, Boolean> affectedProjects) {
		Participant currentParticipant = this.getParticipantDao().getParticipant(participant.getLogin()) ;
		Project currentProject ;

		//for every project 
		for(Iterator iter = affectedProjects.keySet().iterator(); iter.hasNext();){

			String project_id = (String) iter.next() ;
			currentProject = this.projectService.getProject(project_id) ;

			//if this is an affectation
			if(Boolean.valueOf(affectedProjects.get(project_id)) == true)
			{
				currentParticipant.addAffectedProject(currentProject) ;
			}
			//if this is an unaffectation
			else
			{
				//removing the participant from the project
				currentParticipant.removeAffectedProject(currentProject) ;
				
				//if the project have a project manager
				if (currentProject.getProjectManager() != null)
				{
					//if the project manager is the current participant
					if (currentProject.getProjectManager().getWilosuser_id().equals(currentParticipant.getWilosuser_id()))
					{
						currentParticipant.removeManagedProject(currentProject);
						this.projectService.saveProject(currentProject);
					}
				}
			}
		}
		this.participantDao.saveOrUpdateParticipant(currentParticipant) ;
	}

	/**
	 * 
	 * TODO Method description
	 * 
	 * @param participant
	 * @return HashMap with couples of this form : 
	 * 				(Project,ProjectManager) or (Project,null)
	 */
	@Transactional(readOnly = true)
	public HashMap<Project, Participant> getManageableProjectsForAParticipant(Participant participant) {
		HashMap<Project, Boolean> affectedProjectList = new HashMap<Project, Boolean>() ;
		HashMap<Project, Participant> manageableProjectList = new HashMap<Project, Participant>() ;

		// chargement des projets
		affectedProjectList = this.getProjectsForAParticipant(participant) ;

		Project currentProject ;
		// for every project
		for(Iterator iter = affectedProjectList.keySet().iterator(); iter.hasNext();){
			currentProject = (Project) iter.next() ;
			// if the project is affected to the participant
			if(affectedProjectList.get(currentProject).booleanValue() == true){
				// if there is no projectManager -> the project is manageable
				if(currentProject.getProjectManager() == null){
					manageableProjectList.put(currentProject, null) ;
				}
				// if there is a projectManager -> the project is not manageable
				else{
					manageableProjectList.put(currentProject, currentProject.getProjectManager()) ;
				}
			}
		}
		return manageableProjectList ;
	}

	/**
	 * 
	 * TODO Method description
	 * 
	 * @param participant
	 * @param managedProjects : Map of couples of this form :(project_id, Boolean)
	 */
	@Transactional(readOnly = false)
	public void saveManagedProjectsForAParticipant(Participant participant, Map<String,Boolean> managedProjects)
	{
		Participant currentParticipant = this.getParticipantDao().getParticipant(participant.getLogin());
		Project currentProject;
		
		for(Iterator iter = managedProjects.keySet().iterator(); iter.hasNext();){
			String project_id = (String) iter.next() ;
			// loading the current project from database
			currentProject = this.projectService.getProject(project_id);
			if((Boolean)managedProjects.get(project_id))
			{
				//currentProject.addToProjectManager(currentParticipant);
				currentParticipant.addManagedProject(currentProject);
			}
			else
			{
				currentParticipant.removeManagedProject(currentProject);
			}
		}
		this.participantDao.saveOrUpdateParticipant(currentParticipant);
	}
}
