package wilos.presentation.web.wilosuser ;

import java.util.ArrayList ;
import java.util.List ;

import javax.faces.application.FacesMessage ;
import javax.faces.context.FacesContext ;
import javax.faces.event.ActionEvent ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;

import wilos.business.services.wilosuser.LoginService;
import wilos.business.services.wilosuser.ParticipantService ;
import wilos.model.spem2.role.RoleDescriptor ;
import wilos.model.misc.wilosuser.Participant ;

/**
 * Managed-Bean link to participant_logging.jsp
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class ParticipantBean {

	private List<RoleDescriptor> rolesList ;

	private ParticipantService participantService ;

	private LoginService loginService ;

	private Participant participant ;

	private String passwordConfirmation ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ParticipantBean() {
		this.logger.debug("--- ParticipantBean --- == creating ..." + this) ;
		this.participant = new Participant() ;
	}

	/**
	 * Method for saving participant data from form
	 * 
	 * @return
	 */
	public String saveParticipantAction() {
		String url = "connect" ;
		boolean loginExists = this.loginService.loginExist(this.participant.getLogin()) ;
		if(loginExists == true){
			FacesMessage message = new FacesMessage() ;
			message.setDetail("Ce Login existe deja") ;
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			FacesContext facesContext = FacesContext.getCurrentInstance() ;
			facesContext.addMessage(null, message) ;
			url = "createParticipant" ;
		}
		else{
			this.participantService.saveParticipant(this.participant) ;
			url = "connect" ;
		}
		return url ;
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
		this.rolesList = new ArrayList<RoleDescriptor>() ;
		rolesList.addAll(this.participantService.getRolesList()) ;
		this.logger.debug("roles list =" + this.rolesList) ;
		return this.rolesList ;
	}

	/**
	 * Getter of participant.
	 * 
	 * @return the participant.
	 */
	public Participant getParticipant() {
		return this.participant ;
	}

	/**
	 * Setter of participant.
	 * 
	 * @param _participant
	 *            The participant to set.
	 */
	public void setParticipant(Participant _participant) {
		this.logger.debug("### Participant = " + _participant + " ###") ;
		this.participant = _participant ;
	}

	/**
	 * Getter of participantService.
	 * 
	 * @return the participantService.
	 */
	public ParticipantService getParticipantService() {
		return this.participantService ;
	}

	/**
	 * Setter of participantService.
	 * 
	 * @param _participantService
	 *            The participantService to set.
	 */
	public void setParticipantService(ParticipantService _participantService) {
		this.participantService = _participantService ;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation ;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation ;
	}

	public LoginService getLoginService() {
		return this.loginService ;
	}

	public void setLoginService(LoginService _loginService) {
		this.loginService = _loginService ;
	}

}
