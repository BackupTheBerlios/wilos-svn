
package wilos.presentation.web.wilosuser ;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage ;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext ;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;

import wilos.business.services.misc.wilosuser.LoginService ;
import wilos.business.services.misc.wilosuser.ProcessManagerService ;
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
	
	private String processManagerView;
	

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ProcessManagerBean() {
		this.processManager = new ProcessManager() ;
	}

	/**
	 * Method for saving processManager data from form
	 * 
	 * @return
	 */
	public String saveProcessManagerAction() {
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
		String url = "" ;
		boolean error=false;
		FacesMessage message = new FacesMessage() ;
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		//test if the fields are correctly completed 
		if (this.processManager.getName().trim().length()==0)
		{
			FacesMessage errName = new FacesMessage() ;
			errName.setSummary(bundle.getString("component.processmanagercreate.err.lastnameRequired"));
			errName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errName) ;
		}
		
		if (this.processManager.getFirstname().trim().length()==0)
		{
			FacesMessage errFirstName = new FacesMessage() ;
			errFirstName.setSummary(bundle.getString("component.processmanagercreate.err.firstnameRequired"));
			errFirstName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errFirstName) ;
		}
		if (this.processManager.getLogin().trim().length()==0)
		{
			FacesMessage errLogin = new FacesMessage() ;
			errLogin.setSummary(bundle.getString("component.processmanagercreate.err.loginRequired"));
			errLogin.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errLogin) ;
		}
		if (this.processManager.getPassword().trim().length()==0)
		{
			FacesMessage errpasswd = new FacesMessage() ;
			errpasswd.setSummary(bundle.getString("component.processmanagercreate.err.passwordRequired"));
			errpasswd.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errpasswd) ;
		}
		if (this.passwordConfirmation.trim().length()==0)
		{
			FacesMessage errConfirmation = new FacesMessage() ;
			errConfirmation.setSummary(bundle.getString("component.processmanagercreate.err.confirmpasswordRequired"));
			errConfirmation.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errConfirmation) ;
		}
		
		if (!error)
		{
			if(this.loginService.loginExist(this.processManager.getLogin())){
				message.setSummary(bundle.getString("component.processmanagercreate.err.loginalreadyexist"));
				message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			}
			else{
				this.processManagerService.saveProcessManager(this.processManager) ;
				message.setSummary(bundle.getString("component.processmanagercreate.success"));
				message.setSeverity(FacesMessage.SEVERITY_INFO) ;
			}
			facesContext.addMessage(null, message) ;
		}
		this.processManager = new ProcessManager() ;
		this.passwordConfirmation = new String();
		
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
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
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
			message.setSummary(bundle.getString("component.processmanagercreate.err.passwordnotequals"));
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
	
	/**
	 * Getter of processManagerView.
	 *
	 * @return the processManagerView.
	 */
	public String getProcessManagerView() {
		if (this.getProcessManagerList().size()==0 )
		{
			this.processManagerView  = "processManagerView_null";
		}
		else
		{
			this.processManagerView ="processManagerView_not_null";
		}
		return this.processManagerView;
	}

	/**
	 * Setter of processManagerView.
	 *
	 * @param _processManagerView The processManagerView to set.
	 */
	public void setProcessManagerView(String _processManagerView) {
		this.processManagerView = _processManagerView ;
	}
	
	
}
