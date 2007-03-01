package wilos.business.services.misc.wilosuser;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.util.Security;
import wilos.hibernate.misc.wilosuser.ProjectDirectorDao;
import wilos.model.misc.wilosuser.ProjectDirector;

/**
 * The services associated to the ProjectDirector
 * 
 * @author Marseyeah
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProjectDirectorService {

	private ProjectDirectorDao projectDirectorDao;

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Save projectDirector
	 * 
	 * @param _projectdirector
	 */
	public void saveProjectDirector(ProjectDirector _projectDirector) {
		_projectDirector.setPassword(Security.encode(_projectDirector.getPassword()));
		this.projectDirectorDao.saveOrUpdateProjectDirector(_projectDirector);
	}

	/**
	 * Setter of projectDirectorDao.
	 * 
	 * @param _projectDirectorDao
	 *            The projectDirectorDao to set.
	 */
	public void setProjectDirectorDao(ProjectDirectorDao _projectDirectorDao) {
		this.projectDirectorDao = _projectDirectorDao;
	}

	/**
	 * Getter of projectDirectorDao.
	 * 
	 * @return the projectDirectorDao.
	 */
	public ProjectDirectorDao getProjectDirectorDao() {
		return this.projectDirectorDao;
	}

	/**
	 * Return Project Director list
	 * 
	 * @return the list of Project Director
	 */
	@Transactional(readOnly = true)
	public Set<ProjectDirector> getProjectDirectors() {
		return this.projectDirectorDao.getAllProjectDirectors();
	}	
	
	/**
	 * Return the ProjectDirector which have the id _id.
	 * 
	 * @return the projectDirector which have the id gived in parameter
	 */
	@Transactional(readOnly = true)
	public ProjectDirector getProjectDirector(String _id) {
		return this.projectDirectorDao.getProjectDirectorById(_id) ;
	};

	
	/**
	 * delete a Project Director gived in parameter
	 * @param projectDirector
	 * 				the project director which have to be deleted
	 */
	@Transactional(readOnly = false)
	public void deleteProjectDirector(ProjectDirector projectDirector) {
		this.projectDirectorDao.deleteProjectDirector(projectDirector);
	}
}
