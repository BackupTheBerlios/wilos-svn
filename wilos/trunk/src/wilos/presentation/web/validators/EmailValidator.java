
package wilos.presentation.web.validators ;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Mikamikaze
 * @author Sakamakak
 * 
 * This class represents a validator that allows to verify if an email field is valid
 * 
 */
public class EmailValidator implements Validator {
	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public void validate(FacesContext _context, UIComponent _toValidate, Object _value) throws ValidatorException {		
        String enteredEmail = (String)_value;
        //Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        
        //Match the given string with the pattern
        Matcher m = p.matcher(enteredEmail);
        
        //Check whether match is found
        boolean matchFound = m.matches();
        
        if (!matchFound) {
            FacesMessage message = new FacesMessage();
            message.setDetail("L'email est invalide");
            //message.setSummary("L'email est invalide");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
	}
}
