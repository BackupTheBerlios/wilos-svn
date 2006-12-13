
package wilos.presentation.web.wilosuser ;

import javax.faces.application.FacesMessage ;
import javax.faces.context.FacesContext ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;

import wilos.business.services.wilosuser.LoginService ;
import wilos.business.services.wilosuser.ProcessManagerService ;
import wilos.model.misc.wilosuser.ProcessManager ;

/**
 * Managed-Bean link to processmanager_create.jsp
 * 
 * @author Marseyeah
 */
public class ProcessManagerBean {
	private ProcessManagerService processManagerService ;

	private ProcessManager processManager ;

	private LoginService loginService ;

	private String passwordConfirmation ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ProcessManagerBean() {
		this.logger.debug("--- ProcessManagerBean --- == creating ..." + this) ;
		this.processManager = new ProcessManager() ;
	}

	/**
	 * Method for saving processManager data from form
	 * 
	 * @return
	 */
	public String saveProcessManagerAction() {
		String url = "" ;
		FacesMessage message = new FacesMessage() ;
		if(this.loginService.loginExist(this.processManager.getLogin())){
			message.setSummary("Ce Login existe deja");
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
		}
		else{
			this.processManagerService.saveProcessManager(this.processManager) ;
			message.setSummary("Process Manager bien enregistré");
			message.setSeverity(FacesMessage.SEVERITY_INFO) ;
		}
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		facesContext.addMessage(null, message) ;
		return url ;
	}

	/**
	 * Getter of processManager.
	 * 
	 * @return the processManager.
	 */
	public ProcessManager getProcessManager() {
		return this.processManager ;
	}

	/**
	 * Setter of processManager.
	 * 
	 * @param _processManager
	 *            The processManager to set.
	 */
	public void setProcessManager(ProcessManager _processManager) {
		this.processManager = _processManager ;
	}

	/**
	 * Getter of processManagerService.
	 * 
	 * @return the processManagerService.
	 */
	public ProcessManagerService getProcessManagerService() {
		return this.processManagerService ;
	}

	/**
	 * Setter of processManagerService.
	 * 
	 * @param _processManagerService
	 *            The processManagerService to set.
	 */
	public void setProcessManagerService(ProcessManagerService _processManagerService) {
		this.processManagerService = _processManagerService ;
	}

	/**
	 * @return
	 */
	public String getPasswordConfirmation() {
		return passwordConfirmation ;
	}

	/**
	 * @param passwordConfirmation
	 */
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation ;
	}

	/**
	 * Getter of loginService.
	 * 
	 * @return the loginService.
	 */
	public LoginService getLoginService() {
		return this.loginService ;
	}

	/**
	 * Setter of loginService.
	 * 
	 * @param _loginService
	 *            The loginService to set.
	 */
	public void setLoginService(LoginService _loginService) {
		this.loginService = _loginService ;
	}

}
