package woops2.presentation.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 *
 * This class represents ... TODO
 *
 */
public class EmailValidator implements Validator
{
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
			
			String email = (String) value;
			this.logger.debug("### TEST "+toValidate.getAttributes().toString()+" ###");
			
			if (email.indexOf('@') == -1)
			{
				this.logger.debug("### NON VALID EMAIL ###") ;
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("L'email est invalide");
				throw new ValidatorException(message);
			}
	}
}