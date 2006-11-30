
package woops2.presentation.wilosuser ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;

import woops2.business.wilosuser.ProjectDirectorService ;
import woops2.model.wilosuser.ProjectDirector ;

/**
 * Managed-Bean link to projectDirector_create.jsp
 * 
 * @author Marseyeah
 */
public class ProjectDirectorBean {

	private ProjectDirectorService projectDirectorService ;

	private ProjectDirector projectDirector ;

	private String passwordConfirmation ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ProjectDirectorBean() {
		this.logger.debug("--- ProjectDirectorBean --- == creating ..." + this) ;
		this.projectDirector = new ProjectDirector() ;
	}

	/**
	 * Method for saving projectDirector data from form
	 * 
	 * @return
	 */
	public String saveProjectDirectorAction() {
		String url = "admin_main" ;
		this.projectDirectorService.saveProjectDirector(this.projectDirector) ;
		return url ;
	}

	/**
	 * Getter of projectDirector.
	 * 
	 * @return the projectDirector.
	 */
	public ProjectDirector getProjectDirector() {
		return this.projectDirector ;
	}

	/**
	 * Setter of projectDirector.
	 * 
	 * @param _projectDirector
	 *            The projectDirector to set.
	 */
	public void setProjectDirector(ProjectDirector _projectDirector) {
		this.projectDirector = _projectDirector ;
	}

	/**
	 * Getter of projectDirectorService.
	 * 
	 * @return the projectDirectorService.
	 */
	public ProjectDirectorService getProjectDirectorService() {
		return this.projectDirectorService ;
	}

	/**
	 * Setter of projectDirectorService.
	 * 
	 * @param _projectDirectorService
	 *            The projectDirectorService to set.
	 */
	public void setProjectDirectorService(ProjectDirectorService _projectDirectorService) {
		this.projectDirectorService = _projectDirectorService ;
	}

	/**
	 * @return
	 */
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	/**
	 * @param passwordConfirmation
	 */
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

}
