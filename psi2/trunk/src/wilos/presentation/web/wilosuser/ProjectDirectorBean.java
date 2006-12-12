package wilos.presentation.web.wilosuser;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.wilosuser.LoginService;
import wilos.business.services.wilosuser.ProjectDirectorService;
import wilos.model.misc.wilosuser.ProjectDirector;

/**
 * Managed-Bean link to projectDirector_create.jsp
 * 
 * @author Marseyeah
 */
public class ProjectDirectorBean {

	private ProjectDirectorService projectDirectorService;

	private ProjectDirector projectDirector;

	private LoginService loginService;

	private String passwordConfirmation;

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Constructor.
	 * 
	 */
	public ProjectDirectorBean() {
		this.logger.debug("--- ProjectDirectorBean --- == creating ..." + this);
		this.projectDirector = new ProjectDirector();
	}

	/**
	 * Method for saving projectDirector data from form
	 * 
	 * @return
	 */
	public String saveProjectDirectorAction() {
		String url = "admin_main";
		if (this.loginService.loginExist(this.projectDirector.getLogin())) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Ce Login existe deja");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, message);
			url = "projectDirector_create";
		} else {
			this.projectDirectorService
					.saveProjectDirector(this.projectDirector);
			url = "admin_main";
		}
		return url;
	}

	/**
	 * Getter of projectDirector.
	 * 
	 * @return the projectDirector.
	 */
	public ProjectDirector getProjectDirector() {
		return this.projectDirector;
	}

	/**
	 * Setter of projectDirector.
	 * 
	 * @param _projectDirector
	 *            The projectDirector to set.
	 */
	public void setProjectDirector(ProjectDirector _projectDirector) {
		this.projectDirector = _projectDirector;
	}

	/**
	 * Getter of projectDirectorService.
	 * 
	 * @return the projectDirectorService.
	 */
	public ProjectDirectorService getProjectDirectorService() {
		return this.projectDirectorService;
	}

	/**
	 * Setter of projectDirectorService.
	 * 
	 * @param _projectDirectorService
	 *            The projectDirectorService to set.
	 */
	public void setProjectDirectorService(
			ProjectDirectorService _projectDirectorService) {
		this.projectDirectorService = _projectDirectorService;
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

	/**
	 * Getter of loginService.
	 * 
	 * @return the loginService.
	 */
	public LoginService getLoginService() {
		return this.loginService;
	}

	/**
	 * Setter of loginService.
	 * 
	 * @param _loginService
	 *            The loginService to set.
	 */
	public void setLoginService(LoginService _loginService) {
		this.loginService = _loginService;
	}
}
