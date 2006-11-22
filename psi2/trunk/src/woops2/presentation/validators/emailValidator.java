package woops2.presentation.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class emailValidator implements Validator
{
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	public void validate(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
			
			String email = (String) value;
			if (email.indexOf('@') == -1)
			{
				this.logger.debug("### NON VALID EMAIL ###") ;
				/*context.addMessage(toValidate.getClientId(context), 
						new FacesMessage("L'email est invalide"));*/
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("L'email est invalide");
				throw new ValidatorException(message);
			}
	}
}
