/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/
package wilos.business.services.misc.wilosuser;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.util.Security;
import wilos.hibernate.misc.wilosuser.ProcessManagerDao;
import wilos.model.misc.wilosuser.ProcessManager;

/**
 * The services associated to the ProcessManager
 * 
 * @author Marseyeah
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProcessManagerService {

	private ProcessManagerDao processManagerDao;

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Save processManager
	 * 
	 * @param _processmanager
	 */
	public void saveProcessManager(ProcessManager _processManager) {
		_processManager.setPassword(Security.encode(_processManager.getPassword()));
		this.processManagerDao.saveOrUpdateProcessManager(_processManager);
	}

	/**
	 * Setter of processManagerDao.
	 * 
	 * @param _processManagerDao
	 *            The processManagerDao to set.
	 */
	public void setProcessManagerDao(ProcessManagerDao _processManagerDao) {
		this.processManagerDao = _processManagerDao;
	}

	/**
	 * Getter of processManagerDao.
	 * 
	 * The processManagerDao to get.
	 */
	public ProcessManagerDao getProcessManagerDao() {
		return this.processManagerDao;
	}

	/**
	 * Return Process Mannager list
	 * 
	 * @return the list of Process Managers
	 */
	@Transactional(readOnly = true)
	public Set<ProcessManager> getProcessManagers() {
		return this.processManagerDao.getAllProcessManagers();
	}

	/**
	 * Return Process Mannager list
	 * 
	 * @return the list of Process Managers
	 */
	@Transactional(readOnly = true)
	public ProcessManager getProcessManager(String _id) {
		return this.processManagerDao.getProcessManagerById(_id);
	}

	
	/**
	 * delete a Process Manager gived in parameter
	 * @param processManager
	 * 				the process manager which have to be deleted
	 */
	@Transactional(readOnly = false)
	public void deleteProcessManager(ProcessManager processManager) {
		this.processManagerDao.deleteProcessManager(processManager);
	}
}
