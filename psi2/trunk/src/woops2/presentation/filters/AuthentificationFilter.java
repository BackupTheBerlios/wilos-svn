
package woops2.presentation.filters ;

import java.io.IOException ;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.* ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpSession ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;

import woops2.model.wilosuser.WilosUser ;

/**
 * @author Marseyeah
 * 
 * This class represents ... TODO
 * 
 */
public class AuthentificationFilter implements Filter {

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest _request, ServletResponse _response, FilterChain _chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) _request ;
		HttpServletResponse httpResponse = (HttpServletResponse) _response ;
		
		HttpSession sess = httpRequest.getSession(true) ;
		
		String cache ;
		if(sess.getAttribute("cache") != null) {
			cache = (String)sess.getAttribute("cache") ;
		}
		else {
			cache = "" ;
		}

		String relativePath = httpRequest.getServletPath() + httpRequest.getPathInfo() ;
		
		WilosUser user = (WilosUser) sess.getAttribute("wilosUser") ;
		
		if(user == null){
			this.logger.debug("### Filtre : login null ###") ;
			if(!cache.equals(relativePath))
			{
				httpResponse.setHeader("refresh", "0; URL=/Wilos/connect.jsf") ;
				httpResponse.flushBuffer() ;
				sess.setAttribute("cache", relativePath) ;				
			}
			
			//httpResponse.sendRedirect("/Wilos/connect.jsf");
			// String login_page = filterConfig.getInitParameter("login_page");
			//httpRequest.getRequestDispatcher("/Wilos/connect.jsf").forward(_request, _response);
			// filterConfig.getServletContext().getRequestDispatcher(login_page).
		}
		else{
			this.logger.debug("### Filtre : login NON null " + user.getName() + " ###") ;
		}
		_chain.doFilter(_request, _response) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig _arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
