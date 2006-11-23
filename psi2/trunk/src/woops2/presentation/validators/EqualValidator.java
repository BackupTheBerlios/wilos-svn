package woops2.presentation.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.component.EditableValueHolder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.validator.ValidatorBase;

/**
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 *
 * This class represents ... TODO
 *
 */
public class EqualValidator extends ValidatorBase
{
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	//	the foreign component_id on which the validation is based.
 	private String _for= null;
 	
	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
			
			UIComponent foreignComp = toValidate.getParent().findComponent(_for);
			EditableValueHolder foreignEditableValueHolder = (EditableValueHolder) foreignComp;
			
			if (foreignEditableValueHolder.getValue()==null ||!foreignEditableValueHolder.getValue().toString().equals(value.toString()))
			{
				this.logger.debug("### NON VALID CONFIRMATION PASSWORD ###") ;
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("invalid password confirmation");
				throw new ValidatorException(message);
			}
	}
	/***
	* @return the foreign component_id, on which a value should be validated
	*/
	public String getFor() {
		return _for;
	}
	 
	/***
	 * @param string the foreign component_id, on which a value should be validated
	 */
	public void setFor(String string) {
		_for = string;
	}
}
