
package wilos.business.services.wilosuser ;

import java.util.HashMap ;
import java.util.HashSet ;
import java.util.Iterator ;
import java.util.Map ;
import java.util.Set ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.springframework.transaction.annotation.Propagation ;
import org.springframework.transaction.annotation.Transactional ;

import wilos.hibernate.misc.wilosuser.ParticipantDao ;
import wilos.model.misc.project.Project ;
import wilos.model.misc.wilosuser.Participant ;
import wilos.model.spem2.role.RoleDescriptor ;
import wilos.business.services.project.ProjectService ;
import wilos.business.util.Security ;

/**
 * The services associated to the Participant
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class ParticipantService {

	private ParticipantDao participantDao ;

	private ProjectService projectService ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

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
	 * 
	 * @return the roles
	 */
	@ Transactional (readOnly = true)
	public Set<RoleDescriptor> getRolesListForAParticipant(String _login) {
		// TODO: A deplacer dans le RoleService
		return this.participantDao.getAllRolesForAParticipant(_login);
	}

	/**
	 * Return participants list
	 * 
	 * @return the list of participants
	 */
	@ Transactional (readOnly = true)
	public Set<Participant> getParticipants() {
		return this.participantDao.getAllParticipants() ;
	}

	/**
	 * Save participant
	 * 
	 * @param _participant
	 */
	public void saveParticipant(Participant _participant) {
		_participant.setPassword(Security.encode(_participant.getPassword())) ;
		participantDao.saveOrUpdateParticipant(_participant) ;
	}

	/**
	 * 
	 * return the list of project where a participant is affected to
	 * 
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
		this.logger.debug("### LOGIN WILOS :" + login + " ###") ;
		chargedParticipant = this.participantDao.getParticipant(login) ;
		this.logger.debug("### PARTICIPANT :" + chargedParticipant.getName() + " ###") ;
		allProjectList = (HashSet<Project>) this.projectService.getUnfinishedProjects() ;

		this.logger.debug("### NOM DU PROJET COURANT :" + allProjectList.size() + " ###") ;

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
	 * TODO Method description
	 * 
	 * @param participant
	 * @param affectedProjects
	 */
	@ Transactional (readOnly = false)
	public void saveProjectsForAParticipant(Participant participant, Map<String, Boolean> affectedProjects) {
		Participant currentParticipant = this.getParticipantDao().getParticipant(participant.getLogin()) ;
		Project currentProject ;

		for(Iterator iter = affectedProjects.keySet().iterator(); iter.hasNext();){
			String project_id = (String) iter.next() ;
			
			currentProject = this.projectService.getProject(project_id) ;
			
			if((Boolean) affectedProjects.get(project_id))
			{
				currentParticipant.addToProject(currentProject) ;
			}
			else
			{
				if (currentProject.getProjectManager() != null)
				{
					if (currentProject.getProjectManager().getLogin() == currentParticipant.getLogin())
					{
						currentProject.removeFromProjectManager(currentParticipant);
						this.projectService.saveProject(currentProject);
					}
				}
				currentParticipant.removeFromProject(currentProject) ;
			}
		}
		this.logger.debug("### currentParticipant " + currentParticipant.getAffectedProjectList().size() + " ###") ;

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
		//this.logger.debug("### currentProjectManager "+ currentParticipant.getAffectedProjectList().size() +" ###");
		this.participantDao.saveOrUpdateParticipant(currentParticipant);
	
	}
}
