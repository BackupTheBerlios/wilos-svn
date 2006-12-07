package wilos.business.services.project;
import java.util.Set;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.springframework.transaction.annotation.Propagation ;
import org.springframework.transaction.annotation.Transactional ;

import wilos.hibernate.misc.project.ProjectDao;
import wilos.model.misc.project.Project;

/**
 * @author martial
 *
 * This class represents ... TODO
 *
 */


@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class ProjectService {

	private ProjectDao projectDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Save processManager
	 * 
	 * @param _processmanager
	 */
	public void saveProject(Project _project) {
		this.projectDao.saveOrUpdateProject(_project) ;
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
				this.logger.debug("### new login "+projectName+" already exists ###") ;
				return true ;
			}
			else{
				this.logger.debug("### new login "+projectName+" is ok ###") ;
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
		return this.projectDao ;
	}

	/**
	 * Setter of projectDao.
	 *
	 * @param _projectDao The projectDao to set.
	 */
	public void setProjectDao(ProjectDao _projectDao) {
		this.projectDao = _projectDao ;
	}

}