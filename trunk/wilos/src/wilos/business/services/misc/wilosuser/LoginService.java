/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.business.services.misc.wilosuser ;

import java.util.Set ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.springframework.transaction.annotation.Propagation ;
import org.springframework.transaction.annotation.Transactional ;

import wilos.hibernate.misc.wilosuser.WilosUserDao ;
import wilos.model.misc.wilosuser.Administrator;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.ProcessManager;
import wilos.model.misc.wilosuser.ProjectDirector;
import wilos.model.misc.wilosuser.WilosUser ;

/**
 * The services used by any WilosUser to log into the application
 * 
 * @author Marseyeah
 * @author Sakamakak
 */
@Transactional (readOnly = false, propagation = Propagation.REQUIRED)
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
	 * This method is checking if the pair user/passwd is present
	 * 
	 * @param _login
	 * @param _password
	 *            Already hached password
	 * @return The WilosUser if the login and the password matches, and null if not.
	 */
	public WilosUser getAuthentifiedUser(String _login, String _password) {
		Set<WilosUser> wilosUsers = this.wilosUserDao.getAllWilosUsers() ;
		for(WilosUser user : wilosUsers){
			if(user.getLogin().equals(_login) && user.getPassword().equals(_password)){
				return user ;
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
		boolean found = false ;
		String userLogin ;
		Set<WilosUser> wilosUsers = this.wilosUserDao.getAllWilosUsers() ;
		for(WilosUser user : wilosUsers){
			userLogin = user.getLogin().toUpperCase() ;
			if(userLogin.equals(_login.toUpperCase())){
				return true ;
			}
		}
		return found ;
	}
	
	/**
	 * 
	 * return true is the parameter's type is Participant 
	 *
	 * @param wilosuser
	 * @return true if the parameter's is Participant
	 */
	public boolean isParticipant(WilosUser wilosuser)
	{
		if (wilosuser instanceof Participant)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * return true is the parameter's type is ProcessManager 
	 *
	 * @param wilosuser
	 * @return true if the parameter's is ProcessManager
	 */
	public boolean isProcessManager(WilosUser wilosuser)
	{
		if (wilosuser instanceof ProcessManager)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * return true is the parameter's type is ProjectDirector 
	 *
	 * @param wilosuser
	 * @return true if the parameter's is ProjectDirector
	 */
	public boolean isProjectDirector(WilosUser wilosuser)
	{
		if (wilosuser instanceof ProjectDirector)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * return true is the parameter's type is Administrator 
	 *
	 * @param wilosuser
	 * @return true if the parameter's is Administrator
	 */
	public boolean isAdministrator(WilosUser wilosuser)
	{
		if (wilosuser instanceof Administrator)
			return true;
		else
			return false;
	}
}
