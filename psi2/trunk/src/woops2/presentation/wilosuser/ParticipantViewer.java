package woops2.presentation.wilosuser;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import woops2.business.wilosuser.ParticipantService;
import woops2.model.role.RoleDescriptor;
import woops2.model.wilosuser.Participant;

/**
 * Managed-Bean link to participant_logging.jsp
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class ParticipantViewer{

	private List<RoleDescriptor> rolesList;
	
	private ParticipantService participantService ;

	private Participant participant ;
	private String passwordConfirmation;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ParticipantViewer() {
		this.logger.debug("--- ParticipantViewer --- == creating ..." + this) ;
		this.participant = new Participant() ;
	}

	/**
	 * Method for saving participant data from form
	 * 
	 * @return
	 */
	public String saveParticipantAction() {
		String url = "connect" ;
		this.participantService.saveParticipant(this.participant) ;
		return url ;
	}
	
	public void testTransactionActionListener(ActionEvent e){
		//this.participantManager.Test();
	}
	
	/**
	 * Getter of rolesList.
	 * 
	 * @return the rolesList.
	 */
	public List<RoleDescriptor> getRolesList() {
		this.rolesList = new ArrayList<RoleDescriptor>();
		rolesList.addAll(this.participantService.getRolesList());
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
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

}

