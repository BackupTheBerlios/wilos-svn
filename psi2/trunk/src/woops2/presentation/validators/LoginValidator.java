package woops2.presentation.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mikamikaze
 *
 * This class is used to check the unicity of the login
 *
 */
public class LoginValidator implements Validator {
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		String loginToTest = (String)value;
		if (loginToTest == "" )
		{
			this.logger.debug("### NON VALID LOGIN ###") ;
			/*context.addMessage(toValidate.getClientId(context), 
					new FacesMessage("L'email est invalide"));*/
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("L'email est invalide");
			throw new ValidatorException(message);
		}
}

}
