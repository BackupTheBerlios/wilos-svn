
package wilos.business.services.presentation.web ;

import javax.faces.context.FacesContext ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpSession ;

public class WebSessionService {

	/**
	 * The session attribute Project Id.
	 */
	public static final String WILOS_USER = "wilosUser" ;

	/**
	 * The session attribute Project Id.
	 */
	public static final String PROJECT_ID = "projectId" ;

	/**
	 * Get the value of the _attributeName
	 *
	 * @param _attributeName
	 * @return
	 */
	public Object getAttribute(String _attributeName) {
		return this.getHttpSession().getAttribute(_attributeName) ;
	}

	/**
	 * Set the value into the _attributeName in the current session.
	 *
	 * @param _attributeName
	 * @param _value
	 */
	public void setAttribute(String _attributeName, Object _value) {
		this.getHttpSession().setAttribute(_attributeName, _value) ;
	}

	/*
	 * Return the current session.
	 */
	private HttpSession getHttpSession() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		return httpServletRequest.getSession() ;
	}
}
