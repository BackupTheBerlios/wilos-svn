package wilos.business.services.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * The services associated to the Project
 * 
 * @author martial
 */

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProjectService {

	private ProjectDao projectDao;
	private ParticipantDao participantDao;

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Save processManager
	 * 
	 * @param _processmanager
	 */
	public void saveProject(Project _project) {
		this.projectDao.saveOrUpdateProject(_project);
	}

	/**
	 * Check if the project already exist
	 * 
	 * @param _projectName
	 * @return True is the _projectName is already present
	 */
	public boolean projectExist(String _projectName) {
		boolean found = false;
		String projectName ;
		Set<Project> projects = this.projectDao.getAllProject();
		for(Project project : projects)
		{
			projectName = project.getName().toUpperCase();
			if(projectName.equals(_projectName.toUpperCase()))
			{
				this.logger.debug("### new project "+projectName+" already exists ###") ;
				return true ;
			}
			else{
				this.logger.debug("### new project "+projectName+" is ok ###") ;
			}
		}
		return found;
	}

	/**
	 * Getter of projectDao.
	 * 
	 * @return the projectDao.
	 */
	public ProjectDao getProjectDao() {
		return this.projectDao;
	}

	/**
	 * Setter of projectDao.
	 * 
	 * @param _projectDao
	 *            The projectDao to set.
	 */
	public void setProjectDao(ProjectDao _projectDao) {
		this.projectDao = _projectDao;
	}
	
	/**
	 * TODO Method description
	 *
	 * @return
	 */
	@Transactional(readOnly = true)
	public Set<Project> getAllProjects()
	{
		HashSet<Project> projectList = new HashSet<Project>();
		projectList = (HashSet)this.projectDao.getAllProject();
		return projectList;
	}
	
	/**
	 * TODO Method description
	 *
	 * @return
	 */
	@Transactional(readOnly = true)
	public Set<Project> getAllProjectsWithNoProcess()
	{
		HashSet<Project> projectList = new HashSet<Project>();
		HashSet<Project> tmpList = new HashSet<Project>();
		tmpList = (HashSet)this.projectDao.getAllProject();
		for (Iterator iter = tmpList.iterator(); iter.hasNext();) {
			Project project = (Project) iter.next();
			if(project.getProcess() == null)
				projectList.add(project);
		}
		
		return projectList;
	}

	/**
	 * TODO Method description
	 *
	 * @return
	 */
	@Transactional(readOnly = true)
	public Set<Project> getAllProjectsWithProcess()
	{
		HashSet<Project> projectList = new HashSet<Project>();
		HashSet<Project> tmpList = new HashSet<Project>();
		tmpList = (HashSet)this.projectDao.getAllProject();
		for (Iterator iter = tmpList.iterator(); iter.hasNext();) {
			Project project = (Project) iter.next();
			if(project.getProcess() != null)
				projectList.add(project);
		}
		
		return projectList;
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
	 * Setter of participantDao.
	 *
	 * @param _participantDao The participantDao to set.
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
	public Set<Participant> getParticipants(Project project)
	{
		return project.getParticipants();
	}
	
	/**
	 * 
	 * add a participant to a project
	 *
	 * @param participant
	 * 				the participant to add
	 * @param project
	 * 				the project where the participant will be affected to
	 */
	public void addParticipant(Participant participant, Project project)
	{
		project.addToParticipant(participant);
	}
}