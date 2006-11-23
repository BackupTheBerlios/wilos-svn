
package woops2.presentation.wilosuser ;

import java.util.ArrayList ;
import java.util.List ;

import javax.faces.event.ActionEvent ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;

import woops2.business.wilosuser.ProcessManagerManager ;
import woops2.model.wilosuser.ProcessManager ;

/**
 * Managed-Bean link to processmanager_create.jsp
 * 
 * @author Marseyeah
 */
public class ProcessManagerViewer {

	private ProcessManagerManager processManagerManager ;

	private ProcessManager processManager ;

	private String passwordConfirmation ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ProcessManagerViewer() {
		this.logger.debug("--- ProcessManagerViewer --- == creating ..." + this) ;
		this.processManager = new ProcessManager() ;
	}

	/**
	 * Method for saving processManager data from form
	 * 
	 * @return
	 */
	public String saveProcessManagerAction() {
		String url = "processManager" ;
		this.processManagerManager.saveProcessManager(this.processManager) ;
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
	 * Getter of processManagerManager.
	 * 
	 * @return the processManagerManager.
	 */
	public ProcessManagerManager getProcessManagerManager() {
		return this.processManagerManager ;
	}

	/**
	 * Setter of processManagerManager.
	 * 
	 * @param _processManagerManager
	 *            The processManagerManager to set.
	 */
	public void setProcessManagerManager(ProcessManagerManager _processManagerManager) {
		this.processManagerManager = _processManagerManager ;
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
