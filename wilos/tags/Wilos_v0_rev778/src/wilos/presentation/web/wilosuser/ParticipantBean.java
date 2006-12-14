package wilos.presentation.web.wilosuser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.wilosuser.LoginService;
import wilos.business.services.wilosuser.ParticipantService;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.presentation.web.template.MenuBean;

/**
 * Managed-Bean link to participantSubscribe.jspx
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class ParticipantBean {

	private List<RoleDescriptor> rolesList;

	private ParticipantService participantService;

	private LoginService loginService;

	private Participant participant;

	private String passwordConfirmation;

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Constructor.
	 * 
	 */
	public ParticipantBean() {
		this.logger.debug("--- ParticipantBean --- == creating ..." + this);
		this.participant = new Participant();
	}

	/**
	 * Method for saving participant data from form
	 * 
	 * @return
	 */
	public String saveParticipantAction() {
		String url = "";
		FacesMessage message = new FacesMessage();
		if (this.loginService.loginExist(this.participant.getLogin())) {
			message.setSummary("Ce Login existe deja");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		} else {
			this.participantService.saveParticipant(this.participant);
			message.setSummary("Participant bien enregistré");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			url="wilos"; //TODO :Passer eventuellement sur une page de confirmation
			changeContentPage(url);
		}
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, message);
		return "";
	}

	/**
	 * Method designed to change main page content after
	 * participant subscription
	 * @param url
	 */
	public void changeContentPage(String url) {
		// TODO factoriser cette fonction pr eviter de l'avoir
		// dans chaque Bean ;)
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		Object menuObject =
            facesContext.getApplication()
                    .createValueBinding("#{menu}")
                    .getValue(facesContext);
		if (menuObject != null &&
                menuObject instanceof MenuBean) {
			
                MenuBean menuBean = (MenuBean) menuObject;
                menuBean.changePage(url);
		}
	}

	public void testTransactionActionListener(ActionEvent e) {
		// this.participantManager.Test();
	}

	/**
	 * Getter of rolesList.
	 * 
	 * @return the rolesList.
	 */
	public List<RoleDescriptor> getRolesList() {
		this.rolesList = new ArrayList<RoleDescriptor>();
		rolesList.addAll(this.participantService.getRolesList());
		this.logger.debug("roles list =" + this.rolesList);
		return this.rolesList;
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
	public void emailValidation(FacesContext _context, UIComponent _toValidate, Object _value) throws ValidatorException
	{
		String enteredEmail = (String) _value ;
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+") ;
		// Match the given string with the pattern
		Matcher m = p.matcher(enteredEmail) ;
		// Check whether match is found
		boolean matchFound = m.matches() ;
		if(!matchFound){
			FacesMessage message = new FacesMessage() ;
			message.setSummary("L'email est invalide");
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			throw new ValidatorException(message) ;
		}
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

		//TODO : recuperer le nom de laure champs de password via une f:param
		/*ExternalContext ec = (ExternalContext)_context.getExternalContext();
		HashMap hm = new HashMap(ec.getRequestParameterMap());
		String passName = (String)hm.get("forPassword");
		UIComponent passcomponent = _toValidate.findComponent(passName) ;*/
		
		UIComponent passcomponent = _toValidate.findComponent("equal1") ;
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
	 * Getter of participant.
	 * 
	 * @return the participant.
	 */
	public Participant getParticipant() {
		return this.participant;
	}

	/**
	 * Setter of participant.
	 * 
	 * @param _participant
	 *            The participant to set.
	 */
	public void setParticipant(Participant _participant) {
		this.logger.debug("### Participant = " + _participant + " ###");
		this.participant = _participant;
	}

	/**
	 * Getter of participantService.
	 * 
	 * @return the participantService.
	 */
	public ParticipantService getParticipantService() {
		return this.participantService;
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
	 * @param _loginService The loginService to set.
	 */
	public void setLoginService(LoginService _loginService) {
		this.loginService = _loginService ;
	}

	/**
	 * Getter of passwordConfirmation.
	 *
	 * @return the passwordConfirmation.
	 */
	public String getPasswordConfirmation() {
		return this.passwordConfirmation ;
	}

	/**
	 * Setter of passwordConfirmation.
	 *
	 * @param _passwordConfirmation The passwordConfirmation to set.
	 */
	public void setPasswordConfirmation(String _passwordConfirmation) {
		this.passwordConfirmation = _passwordConfirmation ;
	}

	/**
	 * Setter of rolesList.
	 *
	 * @param _rolesList The rolesList to set.
	 */
	public void setRolesList(List<RoleDescriptor> _rolesList) {
		this.rolesList = _rolesList ;
	}

	/**
	 * Setter of participantService.
	 * 
	 * @param _participantService
	 *            The participantService to set.
	 */
	public void setParticipantService(ParticipantService _participantService) {
		this.participantService = _participantService;
	}

}
