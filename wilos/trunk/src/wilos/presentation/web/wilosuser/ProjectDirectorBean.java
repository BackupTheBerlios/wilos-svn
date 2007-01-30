package wilos.presentation.web.wilosuser;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

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

	private List<ProjectDirector> projectDirectorList;
	
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
		String url = "";
		boolean error=false;
		FacesMessage message = new FacesMessage();
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		//		test if the fields are correctly completed 
		if (this.projectDirector.getName().trim().length()==0)
		{
			FacesMessage errName = new FacesMessage() ;
			errName.setSummary("Le champ nom est obligatoire");
			errName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errName) ;
		}
		
		if (this.projectDirector.getFirstname().trim().length()==0)
		{
			FacesMessage errFirstName = new FacesMessage() ;
			errFirstName.setSummary("Le champ prénom est obligatoire");
			errFirstName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errFirstName) ;
		}
		if (this.projectDirector.getLogin().trim().length()==0)
		{
			FacesMessage errLogin = new FacesMessage() ;
			errLogin.setSummary("Le champ login est obligatoire");
			errLogin.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errLogin) ;
		}
		if (this.projectDirector.getPassword().trim().length()==0)
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
			if (this.loginService.loginExist(this.projectDirector.getLogin())) {			
				message.setSummary("Ce Login existe deja");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
			} else {
				this.projectDirectorService.saveProjectDirector(this.projectDirector);
				message.setSummary("Project Director bien enregistré");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
			}
			facesContext.addMessage(null, message);
		}	
		this.projectDirector = new ProjectDirector();
		
		return url;
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
		
		UIComponent passcomponent = _toValidate.findComponent("equal1PD") ;
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
	
	/**
	 * Getter of ProjectDirectorList.
	 *
	 * @return the ProjectDirectorList.
	 */
	public List<ProjectDirector> getProjectDirectorList() {
		this.projectDirectorList = new ArrayList<ProjectDirector>();
		projectDirectorList.addAll(this.projectDirectorService.getProjectDirectors());
		return this.projectDirectorList ;
	}

	/**
	 * Setter of ProcessManagerList.
	 *
	 * @param _ProcessManagerList The ProcessManagerList to set.
	 */
	public void setProjectDirectorList(List<ProjectDirector> _projectDirectorList) {
		this.projectDirectorList = _projectDirectorList ;
	}	
	
	
}
