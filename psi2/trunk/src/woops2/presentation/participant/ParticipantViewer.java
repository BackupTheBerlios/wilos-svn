package woops2.presentation.participant;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import woops2.business.participant.ParticipantManager;
import woops2.model.participant.Participant;
import woops2.model.role.RoleDescriptor;

/**
 * Managed-Bean link to participant_logging.jsp
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class ParticipantViewer{

	private List<RoleDescriptor> rolesList;
	
	private ParticipantManager participantManager ;

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
		String url = "participant" ;
		this.participantManager.saveParticipant(this.participant) ;
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
		rolesList.addAll(this.participantManager.getRolesList());
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
	 * Getter of participantManager.
	 * 
	 * @return the participantManager.
	 */
	public ParticipantManager getParticipantManager() {
		return this.participantManager ;
	}

	/**
	 * Setter of participantManager.
	 * 
	 * @param _participantManager
	 *            The participantManager to set.
	 */
	public void setParticipantManager(ParticipantManager _participantManager) {
		this.participantManager = _participantManager ;
	}

}

