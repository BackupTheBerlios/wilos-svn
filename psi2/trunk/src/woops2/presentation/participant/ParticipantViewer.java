package woops2.presentation.participant;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import woops2.business.participant.ParticipantManager;
import woops2.model.participant.Participant;

/**
 * Managed-Bean link to activity.jsp and activitform.jsp
 * 
 * @author garwind
 * @author deder.
 */
public class ParticipantViewer{

	private ParticipantManager participantManager ;

	private Participant participant ;

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
	 * Method for saving activity data from form
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
	 * Getter of activity.
	 * 
	 * @return the activity.
	 */
	public Participant getParticipant() {
		return this.participant ;
	}

	/**
	 * Setter of activity.
	 * 
	 * @param _activity
	 *            The activity to set.
	 */
	public void setParticipant(Participant _participant) {
		this.logger.debug("### Activity = " + _participant + " ###") ;
		this.participant = _participant ;
	}

	/**
	 * Getter of activityManager.
	 * 
	 * @return the activityManager.
	 */
	public ParticipantManager getParticipantManager() {
		return this.participantManager ;
	}

	/**
	 * Setter of activityManager.
	 * 
	 * @param _activityManager
	 *            The activityManager to set.
	 */
	public void setParticipantManager(ParticipantManager _participantManager) {
		this.participantManager = _participantManager ;
	}

}

