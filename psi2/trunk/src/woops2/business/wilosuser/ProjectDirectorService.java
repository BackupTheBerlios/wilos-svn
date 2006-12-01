
package woops2.business.wilosuser ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.springframework.transaction.annotation.Propagation ;
import org.springframework.transaction.annotation.Transactional ;

import woops2.hibernate.wilosuser.ProjectDirectorDao ;
import woops2.model.wilosuser.ProjectDirector ;

/**
 * @author Marseyeah
 * 
 * This class represents ... TODO
 * 
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class ProjectDirectorService {

	private ProjectDirectorDao projectDirectorDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Save projectDirector
	 * 
	 * @param _projectdirector
	 */
	public void saveProjectDirector(ProjectDirector _processmanager) {
		this.projectDirectorDao.saveOrUpdateProjectDirector(_processmanager) ;
	}

	/**
	 * Setter of projectDirectorDao.
	 * 
	 * @param _projectDirectorDao
	 *            The projectDirectorDao to set.
	 */
	public void setProjectDirectorDao(ProjectDirectorDao _projectDirectorDao) {
		this.projectDirectorDao = _projectDirectorDao ;
	}

	/**
	 * Getter of projectDirectorDao.
	 *
	 * @return the projectDirectorDao.
	 */
	public ProjectDirectorDao getProjectDirectorDao() {
		return this.projectDirectorDao ;
	}

}
