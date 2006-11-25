package woops2.presentation.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import woops2.business.participant.ParticipantManager;

/**
 * @author mikamikaze
 *
 * This class is used to check the unicity of the login
 * Not yet working, waiting to find how to acces to the participantManager witch is in the lower layer
 */
public class LoginValidator implements Validator {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private ParticipantManager participantManager;
	
	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext _context, UIComponent _toValidate, Object _value) throws ValidatorException {
		this.logger.debug("##> I GOT THE POWER !!! <##") ;
		if (participantManager.loginExist((String)_value))
		{
			this.logger.debug("### NON VALID LOGIN ###") ;
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Le login est invalide");
			throw new ValidatorException(message);
		}
	}


	public ParticipantManager _getParticipantManager() {
		return participantManager;
	}


	public void setParticipantManager(ParticipantManager _participantManager) {
		this.logger.debug("##> CREATION AUTOMAGIQUE DUN PARTMANAGER !!! <##") ;
		this.participantManager = _participantManager;
	}

}
