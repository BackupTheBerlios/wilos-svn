package woops2.presentation.validators;
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
		
			String pass1 = (String)toValidate.getAttributes().get("compareAtt");
			this.logger.debug("### AAAAAAAAAAAAAAAAH "+pass1+"###") ;
			/*UIComponent foreignComp = toValidate.getParent().findComponent(_for);
			EditableValueHolder foreignEditableValueHolder = (EditableValueHolder) foreignComp;
			
			if (foreignEditableValueHolder.getValue()==null ||!foreignEditableValueHolder.getValue().toString().equals(value.toString()))
			{
				this.logger.debug("### NON VALID CONFIRMATION PASSWORD ###") ;
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("invalid password confirmation");
				throw new ValidatorException(message);
			}*/
	}
}
