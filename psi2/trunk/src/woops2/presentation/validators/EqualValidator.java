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
public class EqualValidator implements Validator
{
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		
			UIComponent pass1 = toValidate.findComponent("equal1");
			String valeur = (String)pass1.getAttributes().get("value");
			this.logger.debug("### AAAAAAAAAAAAAAAAH "+valeur+"###") ;
			
			if (!value.equals(valeur) || valeur.equals(""))
			{
				this.logger.debug("### THE FIELDS ARE NOT EQUALS ###") ;
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("The two fiels are not the same");
				throw new ValidatorException(message);
			}
	}
}
