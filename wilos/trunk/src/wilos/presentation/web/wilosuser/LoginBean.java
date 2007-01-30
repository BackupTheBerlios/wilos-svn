
package wilos.presentation.web.wilosuser ;

import javax.faces.application.FacesMessage ;
import javax.faces.context.FacesContext ;
import javax.servlet.http.* ;
import wilos.business.services.wilosuser.LoginService ;
import wilos.business.util.Security ;
import wilos.model.misc.wilosuser.WilosUser ;
import wilos.presentation.web.template.ConnectViewBean ;
import wilos.presentation.web.template.MenuBean ;

/**
 * Managed-Bean link to participantSubscribe.jspx
 * 
 * @author Marseyeah
 * @author Sakamakak
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
		String applicationRole = "";
		WilosUser user = this.loginService.getAuthentifiedUser(this.login, Security.encode(this.password)) ;
		if(user != null){
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
			HttpSession sess = req.getSession() ;
			sess.setAttribute("wilosUser", user) ;

			//if(user instanceof Participant){
			if(this.loginService.isParticipant(user)){
				sess.setAttribute("role", "participant") ;
				url = "welcome" ;
				applicationRole = "participant_role";
			}
			else if(this.loginService.isProcessManager(user)){
				sess.setAttribute("role", "processManager") ;
				url = "welcomeProcessManager" ;
				applicationRole = "processManager_role";
			}
			else if(this.loginService.isProjectDirector(user)){
				sess.setAttribute("role", "projectDirector") ;
				url = "welcomeProjectDirector" ;
				applicationRole = "projectDirector_role";
			}
			else if(this.loginService.isAdministrator(user)){
				sess.setAttribute("role", "admin") ;
				url = "admin_main" ;
				applicationRole = "admin_role";
			}
			/* Test de la navigation */
			changeContentPage(url) ;
			changeConnectView(true,applicationRole) ;
		}
		else{
			FacesMessage message = new FacesMessage() ;
			message.setSummary("Login ou Mot de passe incorrects") ;
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;

			FacesContext facesContext = FacesContext.getCurrentInstance() ;
			facesContext.addMessage(null, message) ;
			url = "connect" ;
		}
		// return url;
		return "" ;
	}

	
	/**
	 * TODO Method description
	 *
	 * @param b
	 */
	public void changeConnectView(boolean _b, String _applicationRole) {
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		Object connectObject = facesContext.getApplication().createValueBinding("#{connect}").getValue(facesContext) ;
		if(connectObject != null && connectObject instanceof ConnectViewBean){
			ConnectViewBean connectBean = (ConnectViewBean) connectObject;
			connectBean.connected(_b,_applicationRole);
		}
	}

	/**
	 * Method designed to change main page content depending on user's role
	 * 
	 * @param url
	 */
	public void changeContentPage(String url) {
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		Object menuObject = facesContext.getApplication().createValueBinding("#{menu}").getValue(facesContext) ;
		if(menuObject != null && menuObject instanceof MenuBean){

			MenuBean menuBean = (MenuBean) menuObject ;
			menuBean.changePage(url) ;
		}
	}

	/**
	 * Method designed to describe the action to do when the wilosuser is trying to disconnect.
	 * 
	 * @return
	 */
	public String logoutAction() {
		// String url = "connect" ;
		String url = "wilos" ;
		changeContentPage(url) ;
		changeConnectView(false,"none") ;
		/*
		 * HttpServletRequest req =
		 * (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		 * HttpSession sess = req.getSession() ; sess.invalidate() ;
		 */

		return url ;
	}
}
