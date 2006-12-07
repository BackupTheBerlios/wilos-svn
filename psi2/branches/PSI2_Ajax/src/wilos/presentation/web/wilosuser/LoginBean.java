
package wilos.presentation.web.wilosuser ;

import javax.faces.application.FacesMessage ;
import javax.faces.context.FacesContext ;
import javax.servlet.http.* ;

import wilos.business.services.wilosuser.LoginService ;
import wilos.business.util.Security;
import wilos.model.misc.wilosuser.Administrator ;
import wilos.model.misc.wilosuser.Participant ;
import wilos.model.misc.wilosuser.ProcessManager ;
import wilos.model.misc.wilosuser.ProjectDirector ;
import wilos.model.misc.wilosuser.WilosUser ;

/**
 * @author Marseyeah
 * @author Sakamakak
 * 
 * This class represents ... TODO
 * 
 */
public class LoginBean {

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
		WilosUser user = this.loginService.getAuthentifiedUser(this.login, Security.encode(this.password)) ;
		if(user != null){
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
			HttpSession sess = req.getSession() ;
			sess.setAttribute("wilosUser",user) ;
			
			if(user instanceof Participant){
				sess.setAttribute("role","participant") ;
				url = "welcome" ;
			}
			else if(user instanceof ProcessManager){
				sess.setAttribute("role","processManager") ;
				url = "welcome" ;
			}
			else if(user instanceof ProjectDirector){
				sess.setAttribute("role","projectDirector") ;
				url = "welcome" ;
			}
			else if(user instanceof Administrator){
				sess.setAttribute("role","admin") ;
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
	
	
	/**
	 * TODO Method description
	 *
	 * @return
	 */
	public String logoutAction() {
		String url = "connect" ;
		
		/*HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		HttpSession sess = req.getSession() ;
		sess.invalidate() ;*/
		
		return url ;
	}

}
