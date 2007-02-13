
package wilos.business.services.misc.project ;

import java.util.HashSet ;
import java.util.Iterator ;
import java.util.Set ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.springframework.transaction.annotation.Propagation ;
import org.springframework.transaction.annotation.Transactional ;

import wilos.business.services.spem2.process.ProcessService ;
import wilos.hibernate.misc.project.ProjectDao ;
import wilos.hibernate.misc.wilosuser.ParticipantDao ;
import wilos.model.misc.project.Project ;
import wilos.model.misc.wilosuser.Participant ;
import wilos.model.spem2.process.Process ;

/**
 * The services associated to the Project
 * 
 * @author martial
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class ProjectService {

	private ProjectDao projectDao ;

	private ParticipantDao participantDao ;

	private ProcessService processService ;

	public final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Save processManager
	 * 
	 * @param _processmanager
	 */
	@ Transactional (readOnly = false)
	public void saveProject(Project _project) {
		this.projectDao.saveOrUpdateProject(_project) ;
	}

	/**
	 * Check if the project already exist
	 * 
	 * @param _projectName
	 * @return True is the _projectName is already present
	 */
	@ Transactional (readOnly = true)
	public boolean projectExist(String _projectName) {
		boolean found = false ;
		String projectName ;
		Set<Project> projects = this.projectDao.getAllProject() ;
		for(Project project : projects){
			projectName = project.getConcreteName().toUpperCase() ;
			if(projectName.equals(_projectName.toUpperCase())){
				this.logger.debug("### new project " + projectName + " already exists ###") ;
				return true ;
			}
			else{
				this.logger.debug("### new project " + projectName + " is ok ###") ;
			}
		}
		return found ;
	}

	/**
	 * This method returns the list of the projects that aren't yet finished
	 * 
	 * @return a set of Projects
	 */
	@ Transactional (readOnly = true)
	public Set<Project> getUnfinishedProjects() {
		Set<Project> unfinishedP = new HashSet<Project>() ;
		Set<Project> projects = this.projectDao.getAllProject() ;

		for(Iterator iter = projects.iterator(); iter.hasNext();){
			Project project = (Project) iter.next() ;
			if(! (project.getIsFinished())){
				unfinishedP.add(project) ;
			}
		}
		return unfinishedP ;
	}

	/**
	 * Getter of projectDao.
	 * 
	 * @return the projectDao.
	 */
	public ProjectDao getProjectDao() {
		return this.projectDao ;
	}

	/**
	 * Setter of projectDao.
	 * 
	 * @param _projectDao
	 *            The projectDao to set.
	 */
	public void setProjectDao(ProjectDao _projectDao) {
		this.projectDao = _projectDao ;
	}

	/**
	 * This method returns all the projects.
	 * 
	 * @return A set of Project
	 */
	@ Transactional (readOnly = true)
	public Set<Project> getAllProjects() {
		HashSet<Project> projectList = new HashSet<Project>() ;
		projectList = (HashSet<Project>) this.projectDao.getAllProject() ;
		return projectList ;
	}

	/**
	 * This method returns all the projects with no process.
	 * 
	 * @return A set of Project
	 */
	@ Transactional (readOnly = true)
	public Set<Project> getAllProjectsWithNoProcess() {
		HashSet<Project> projectList = new HashSet<Project>() ;
		HashSet<Project> tmpList = new HashSet<Project>() ;
		tmpList = (HashSet<Project>) this.projectDao.getAllProject() ;
		for(Iterator iter = tmpList.iterator(); iter.hasNext();){
			Project project = (Project) iter.next() ;
			if(project.getProcess() == null)
				projectList.add(project) ;
		}
		return projectList ;
	}

	/**
	 * Returns the projects that aren't associated to a process.
	 * 
	 * @return A set of Project
	 */
	@ Transactional (readOnly = true)
	public Set<Project> getAllProjectsWithProcess() {
		HashSet<Project> projectList = new HashSet<Project>() ;
		HashSet<Project> tmpList = new HashSet<Project>() ;
		tmpList = (HashSet<Project>) this.projectDao.getAllProject() ;
		for(Iterator iter = tmpList.iterator(); iter.hasNext();){
			Project project = (Project) iter.next() ;
			if(project.getProcess() != null)
				projectList.add(project) ;
		}
		return projectList ;
	}

	/**
	 * 
	 * Getter of project
	 * 
	 * @param _id
	 * @return
	 */
	@ Transactional (readOnly = true)
	public Project getProject(String _id) {
		return this.projectDao.getProject(_id) ;
	}

	/**
	 * Getter of participantDao.
	 * 
	 * @return the participantDao.
	 */
	public ParticipantDao getParticipantDao() {
		return this.participantDao ;
	}

	/**
	 * 
	 * Getter of processService
	 * 
	 * @return processService
	 */
	public ProcessService getProcessService() {
		return this.processService ;
	}

	/**
	 * 
	 * Setter of processService
	 * 
	 * @param _processService
	 */
	public void setProcessService(ProcessService _processService) {
		this.processService = _processService ;
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
	 * 
	 * return the participants affected to the project
	 * 
	 * @param project
	 * @return the list of participants affected to the project parameter
	 */
	@ Transactional (readOnly = true)
	public Set<Participant> getParticipants(Project project) {
		return project.getParticipants() ;
	}

	/**
	 * 
	 * add a participant to a project
	 * 
	 * @param participant
	 *            the participant to add
	 * @param project
	 *            the project where the participant will be affected to
	 */
	@ Transactional (readOnly = false)
	public void addParticipant(Participant participant, Project project) {
		project.addToParticipant(participant) ;
	}

	/**
	 * 
	 * Affects a process to a project
	 * 
	 * @param process
	 *            the process to affect
	 * @param project
	 *            the project to affect
	 */
	@ Transactional (readOnly = false)
	public void saveProcessProjectAffectation(wilos.model.spem2.process.Process _process, Project _project) {
		Project loadedProject = this.getProject(_project.getId()) ;
		Process loadedProcess = this.processService.getProcessDao().getProcessFromGuid(_process.getGuid()) ;
		loadedProject.addProcess(loadedProcess) ;
		this.projectDao.saveOrUpdateProject(loadedProject) ;
		this.processService.projectInstanciation(loadedProject) ;
	}
}
