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

package wilos.hibernate.spem2.process ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.process.Process;
import wilos.utils.ExceptionManager;

/**
 * ProcessDao manage requests from the system to store Acitivties to the database
 * 
 * @author garwind
 * @author deder
 */
public class ProcessDao extends HibernateDaoSupport {

	/**
	 * Save or update an process
	 * 
	 * @param _process
	 */
	public void saveOrUpdateProcess(Process _process) throws ConstraintViolationException, DataIntegrityViolationException {
		try{
			this.getHibernateTemplate().saveOrUpdate(_process) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateProcess", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateProcess", _ex);
		}
	}

	/**
	 * Return a list of processes.
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Process> getAllProcesses() {
		List<Process> loadAll = new ArrayList<Process>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Process.class)) ;

		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllProcesses", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the process which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public Process getProcess(String _id) {
		return (Process) this.getHibernateTemplate().get(Process.class, _id) ;
	}
	
	/**
	 * Return a process  with the given guid If there are many process with
	 * the same guid, it returns the first
	 * 
	 * @param _process
	 * @return
	 */
	public Process getProcessFromGuid(String _guid) {
		List processes = this.getHibernateTemplate().find("from Process p where p.guid=?", _guid) ;
		if(processes.size() > 0)
			return (Process) processes.get(0) ;
		else
			return null ;
	}

	/**
	 * Delete the process
	 * 
	 * @param _process
	 */
	public void deleteProcess(Process _process) {
		try{
			this.getHibernateTemplate().delete(_process) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteProcess", _e);
		}
	}
}
