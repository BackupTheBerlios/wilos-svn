package woops2.presentation.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import woops2.business.wilosuser.ParticipantService;

/**
 * @author mikamikaze
 *
 * This class is used to check the unicity of the login
 * Not yet working, waiting to find how to acces to the participantService witch is in the lower layer
 */
public class LoginValidator implements Validator {
	protected final Log logger = LogFactory.getLog(this.getClass());
	private ParticipantService participantService;
	
	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext _context, UIComponent _toValidate, Object _value) throws ValidatorException {
		this.logger.debug("##> I GOT THE POWER !!! <##") ;
		if (participantService.loginExist((String)_value))
		{
			this.logger.debug("### INVALID LOGIN ###") ;
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Le login est invalide");
			throw new ValidatorException(message);
		}
	}


	public ParticipantService _getParticipantService() {
		return participantService;
	}


	public void setParticipantService(ParticipantService _participantService) {
		this.logger.debug("##> CREATION AUTOMAGIQUE DUN PARTMANAGER !!! <##") ;
		this.participantService = _participantService;
	}

}
