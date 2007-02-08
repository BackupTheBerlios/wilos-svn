
package wilos.presentation.web.wilosuser ;

import java.util.ResourceBundle ;

import javax.faces.application.FacesMessage ;
import javax.faces.context.FacesContext ;
import javax.servlet.http.* ;
import wilos.business.services.misc.wilosuser.LoginService ;
import wilos.business.services.presentation.web.WebSessionService ;
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

	private WebSessionService webSessionService ;

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

		ResourceBundle bundle = ResourceBundle.getBundle("wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale()) ;

		String url = "" ;
		String applicationRole = "" ;
		WilosUser user = this.loginService.getAuthentifiedUser(this.login, Security.encode(this.password)) ;
		if(user != null){
			this.webSessionService.setAttribute(WebSessionService.WILOS_USER_ID, user.getWilosuser_id()) ;
			/*
			 * HttpServletRequest req = (HttpServletRequest)
			 * FacesContext.getCurrentInstance().getExternalContext().getRequest() ; HttpSession
			 * sess = req.getSession() ; sess.setAttribute("wilosUser", user.getWilosuser_id()) ;
			 */

			// if(user instanceof Participant){
			if(this.loginService.isParticipant(user)){
				this.webSessionService.setAttribute(WebSessionService.ROLE_NAME, "participant") ;
				url = "participant_main" ;
				applicationRole = "participant_role" ;
			}
			else if(this.loginService.isProcessManager(user)){
				this.webSessionService.setAttribute(WebSessionService.ROLE_NAME, "processManager") ;
				url = "process_manager_main" ;
				applicationRole = "processManager_role" ;
			}
			else if(this.loginService.isProjectDirector(user)){
				this.webSessionService.setAttribute(WebSessionService.ROLE_NAME, "projectDirector") ;
				url = "project_director_main" ;
				applicationRole = "projectDirector_role" ;
			}
			else if(this.loginService.isAdministrator(user)){
				this.webSessionService.setAttribute(WebSessionService.ROLE_NAME, "admin") ;
				url = "admin_main" ;
				applicationRole = "admin_role" ;
			}
			/* Test de la navigation */
			changeContentPage(url) ;
			changeConnectView(true, applicationRole) ;
		}
		else{
			FacesMessage message = new FacesMessage() ;
			message.setSummary(bundle.getString("component.authentificationerror.loginError")) ;
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
			ConnectViewBean connectBean = (ConnectViewBean) connectObject ;
			connectBean.connected(_b, _applicationRole) ;
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
		String url = "wilos" ;
		changeContentPage(url) ;
		changeConnectView(false, "none") ;
		return url ;
	}

	/**
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return this.webSessionService ;
	}

	/**
	 * Setter of webSessionService.
	 *
	 * @param _webSessionService The webSessionService to set.
	 */
	public void setWebSessionService(WebSessionService _webSessionService) {
		this.webSessionService = _webSessionService ;
	}
}
