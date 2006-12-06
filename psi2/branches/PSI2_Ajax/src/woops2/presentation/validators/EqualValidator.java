
package woops2.presentation.validators ;

import javax.faces.application.FacesMessage ;
import javax.faces.component.UIComponent ;
import javax.faces.context.FacesContext ;
import javax.faces.validator.Validator ;
import javax.faces.validator.ValidatorException ;
import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;

/**
 * @author l3isi25 (Martial)
 * @author Sakamakak
 *
 * This class represents a validator that allows to verify if two fields are the same
 *
 */
public class EqualValidator implements Validator {
	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public void validate(FacesContext _context, UIComponent _toValidate, Object _value) throws ValidatorException {

		UIComponent component = _toValidate.findComponent("equal1") ;
		String valeur = (String) component.getAttributes().get("value") ;

		if(!_value.equals(valeur) || valeur.equals("") || _value.equals("")){
			this.logger.debug("### THE FIELDS ARE NOT EQUALS ###") ;
			FacesMessage message = new FacesMessage() ;
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			message.setDetail("Les deux champs ne sont pas identiques") ;
			throw new ValidatorException(message) ;
		}
	}
}
