
package wilos.business.services.wilosuser ;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.wilosuser.WilosUserDao;
import wilos.model.misc.wilosuser.WilosUser;

/**
 * @author Marseyeah
 * @author Sakamakak
 * 
 * This class represents ... TODO
 * 
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class LoginService {

	private WilosUserDao wilosUserDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Getter of wilosUserDao.
	 * 
	 * @return the wilosUserDao.
	 */
	public WilosUserDao getWilosUserDao() {
		return this.wilosUserDao ;
	}

	/**
	 * Setter of wilosUserDao.
	 * 
	 * @param _wilosUserDao
	 *            The wilosUserDao to set.
	 */
	public void setWilosUserDao(WilosUserDao _wilosUserDao) {
		this.wilosUserDao = _wilosUserDao ;
	}

	/**
	 * TODO Method description
	 * 
	 * @param _login
	 * @param Already hached _password
	 * @return true if the login and the password exist
	 */
	public WilosUser getAuthentifiedUser(String _login, String _password) {
		Set<WilosUser> wilosUsers = this.wilosUserDao.getAllWilosUsers() ;
		for(WilosUser user : wilosUsers){
			if(user.getLogin().equals(_login) && user.getPassword().equals(_password)){
				this.logger.debug("### connection OK ###") ;
				return user ;
			}
			else{
				this.logger.debug("### connection PAS OK ###") ;
			}
		}
		return null ;
	}
	/**
	 * Check if the login is already used
	 *
	 * @param _login
	 * @return True is the login is already present 
	 */
	public boolean loginExist(String _login) {
		boolean found = false;
		String userLogin ;
		Set<WilosUser> wilosUsers = this.wilosUserDao.getAllWilosUsers() ;
		for(WilosUser user : wilosUsers)
		{
			userLogin = user.getLogin().toUpperCase();
			if(userLogin.equals(_login.toUpperCase()))
			{
				this.logger.debug("### new login "+userLogin+" already exists ###") ;
				return true ;
			}
			else{
				this.logger.debug("### new login "+userLogin+" is ok ###") ;
			}
		}
		return found;
	}
}
