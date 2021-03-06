/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
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

package wilos.hibernate.misc.wilosuser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.wilosuser.ProcessManager;

/**
 * ProcessManagerDao manage requests from the system to store ProcessManager
 * into the database.
 * 
 * @author Yoann Lopes
 * @author Martial Lapeyre
 */
public class ProcessManagerDao extends HibernateDaoSupport {

	/**
	 * Save or update an processmanager.
	 * 
	 * @param _processmanager
	 */
	public void saveOrUpdateProcessManager(ProcessManager _processmanager) {
		this.getHibernateTemplate().saveOrUpdate(_processmanager);
	}

	/**
	 * Return a list of processmanagers.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Set<ProcessManager> getAllProcessManagers() {
		Set<ProcessManager> loadAll = new HashSet<ProcessManager>();
		loadAll.addAll(this.getHibernateTemplate().loadAll(ProcessManager.class));
		return loadAll;
	}

	
	/**
	 * Return the processmanager which have the login _login.
	 * TODO !!! FINIR LE HSQL ET APPELLER LES BONNES METHODES DANS LES TESTS !!!!  
	 * @param _login
	 * @return
	 */
	public ProcessManager getProcessManager(String _login){
		ArrayList processManagers = (ArrayList)this.getHibernateTemplate().find("from ProcessManager pm where pm.login=?",_login);
		if(processManagers.size()>0){
			return (ProcessManager)processManagers.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * Return the ProcessManager which have the id _id.
	 * TODO JUNIT
	 * @param _id
	 * @return
	 */
	public ProcessManager getProcessManagerById(String _id) {
		return (ProcessManager) this.getHibernateTemplate().get(ProcessManager.class, _id);
	}


	
	/**
	 * Delete the processmanager.
	 * 
	 * @param _processmanager
	 */
	public void deleteProcessManager(ProcessManager _processmanager) {
		try {
			this.getHibernateTemplate().delete(_processmanager);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting processmanager
			// into the db.
			logger
					.error("#### ERROR #### --- ProcessManagerDao => deleteProcessManager : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
