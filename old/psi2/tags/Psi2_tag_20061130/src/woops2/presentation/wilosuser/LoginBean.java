
package woops2.presentation.wilosuser ;

import java.io.Serializable;

import javax.faces.application.FacesMessage ;
import javax.faces.context.FacesContext ;

import woops2.business.wilosuser.LoginService ;
import woops2.model.wilosuser.Administrator ;
import woops2.model.wilosuser.Participant ;
import woops2.model.wilosuser.ProcessManager ;
import woops2.model.wilosuser.ProjectDirector ;
import woops2.model.wilosuser.WilosUser ;

/**
 * @author Marseyeah
 * @author Sakamakak
 * 
 * This class represents ... TODO
 * 
 */
public class LoginBean implements Serializable {

	private String login ;

	private String password ;

	private LoginService loginService ;

	/**
	 * Getter of login.
	 * 
	 * @return the login.
	 */
	public String getLogin() {
		return this.login ;
	}

	/**
	 * Setter of login.
	 * 
	 * @param _login
	 *            The login to set.
	 */
	public void setLogin(String _login) {
		this.login = _login ;
	}

	/**
	 * Getter of loginService.
	 * 
	 * @return the loginService.
	 */
	public LoginService getLoginService() {
		return this.loginService ;
	}

	/**
	 * Setter of loginService.
	 * 
	 * @param _loginService
	 *            The loginService to set.
	 */
	public void setLoginService(LoginService _loginService) {
		this.loginService = _loginService ;
	}

	/**
	 * Getter of password.
	 * 
	 * @return the password.
	 */
	public String getPassword() {
		return this.password ;
	}

	/**
	 * Setter of password.
	 * 
	 * @param _password
	 *            The password to set.
	 */
	public void setPassword(String _password) {
		this.password = _password ;
	}

	
	/**
	 * Redirect to the page corresponding to the type of the user
	 *
	 * @return the faces-config id of the page
	 */
	public String authentificationAction() {
		String url = "" ;
		WilosUser user = this.loginService.getAuthentifiedUser(this.login, this.password) ;
		if(user != null){
			if(user instanceof Participant){
				url = "welcome" ;
			}
			else if(user instanceof ProcessManager){
				url = "welcome" ;
			}
			else if(user instanceof ProjectDirector){
				url = "welcome" ;
			}
			else if(user instanceof Administrator){
				url = "admin_main" ;
			}
		}
		else{
			FacesMessage message = new FacesMessage() ;
			message.setDetail("Login ou Mot de passe incorrects") ;
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;

			FacesContext facesContext = FacesContext.getCurrentInstance() ;
			facesContext.addMessage(null, message) ;
			url = "connect" ;
		}
		return url ;
	}

}
