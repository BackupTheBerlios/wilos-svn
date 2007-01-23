
package wilos.presentation.web.wilosuser ;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage ;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext ;
import javax.faces.validator.ValidatorException;

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
	
	private List<ProcessManager> processManagerList;
	

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
		boolean error=false;
		FacesMessage message = new FacesMessage() ;
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		//test if the fields are correctly completed 
		if (this.processManager.getName().trim().length()==0)
		{
			FacesMessage errName = new FacesMessage() ;
			errName.setSummary("Le champ nom est obligatoire");
			errName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errName) ;
		}
		
		if (this.processManager.getFirstname().trim().length()==0)
		{
			FacesMessage errFirstName = new FacesMessage() ;
			errFirstName.setSummary("Le champ prénom est obligatoire");
			errFirstName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errFirstName) ;
		}
		if (this.processManager.getLogin().trim().length()==0)
		{
			FacesMessage errLogin = new FacesMessage() ;
			errLogin.setSummary("Le champ login est obligatoire");
			errLogin.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errLogin) ;
		}
		if (this.processManager.getPassword().trim().length()==0)
		{
			FacesMessage errpasswd = new FacesMessage() ;
			errpasswd.setSummary("Le champ password est obligatoire");
			errpasswd.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errpasswd) ;
		}
		if (this.passwordConfirmation.trim().length()==0)
		{
			FacesMessage errConfirmation = new FacesMessage() ;
			errConfirmation.setSummary("Le champ de confirmation du password est obligatoire");
			errConfirmation.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errConfirmation) ;
		}
		
		if (!error)
		{
			if(this.loginService.loginExist(this.processManager.getLogin())){
				message.setSummary("Ce Login existe deja");
				message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			}
			else{
				this.processManagerService.saveProcessManager(this.processManager) ;
				message.setSummary("Process Manager bien enregistré");
				message.setSeverity(FacesMessage.SEVERITY_INFO) ;
			}
			facesContext.addMessage(null, message) ;
		}
		this.processManager = new ProcessManager() ;
		
		return url ;
	}
	
	/**
	 * 
	 * methode qui controle que les deux mots de passes sont identiques 
	 *
	 * @param _context
	 * @param _toValidate
	 * @param _value
	 * @throws ValidatorException
	 */
	public void passwordEqualValidation(FacesContext _context, UIComponent _toValidate, Object _value) throws ValidatorException
	{
		String passConfirm = (String) _value;
		
		//TODO : recuperer le nom de l autre champs de password via une f:param
		/*ExternalContext ec = (ExternalContext)_context.getExternalContext();
		HashMap hm = new HashMap(ec.getRequestParameterMap());
		String passName = (String)hm.get("forPassword");
		UIComponent passcomponent = _toValidate.findComponent(passName) ;*/
		
		UIComponent passcomponent = _toValidate.findComponent("equal1PM") ;
		String passValue = (String) passcomponent.getAttributes().get("value");
		
		if(!passConfirm.equals(passValue))
		{
			FacesMessage message = new FacesMessage();
			message.setSummary("Les 2 mots de passe ne sont pas identiques");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message) ;
		}
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
	
	
	/**
	 * Getter of ProcessManagerList.
	 *
	 * @return the ProcessManagerList.
	 */
	public List<ProcessManager> getProcessManagerList() {
		this.processManagerList = new ArrayList<ProcessManager>();
		processManagerList.addAll(this.processManagerService.getProcessManagers());
		return this.processManagerList ;
	}

	/**
	 * Setter of ProcessManagerList.
	 *
	 * @param _ProcessManagerList The ProcessManagerList to set.
	 */
	public void setProcessManagerList(List<ProcessManager> _processManagerList) {
		this.processManagerList = _processManagerList ;
	}
}
