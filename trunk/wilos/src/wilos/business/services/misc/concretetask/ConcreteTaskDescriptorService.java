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

package wilos.business.services.misc.concretetask;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.utils.Constantes.State;

/**
 *
 * @author Soosuske
 * @author deder
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ConcreteTaskDescriptorService {

	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao;
	
	private ConcreteActivityDao concreteActivityDao;

	private TaskDescriptorService taskDescriptorService;

	private RoleDescriptorService roleDescriptorService;

	private ConcreteRoleDescriptorService concreteRoleDescriptorService;
	


	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Return concreteTaskDescriptor for a project list
	 *
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ConcreteTaskDescriptor> getAllConcreteTaskDescriptorsForProject(
			String _projectId) {
		return this.getConcreteTaskDescriptorDao()
				.getAllConcreteTaskDescriptorsForProject(_projectId);
	}

	/**
	 * 
	 * @param _concreteTaskDescriptorId
	 * @return
	 */
	public ConcreteTaskDescriptor getConcreteTaskDescriptor(
			String _concreteTaskDescriptorId) {
		return this.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(
				_concreteTaskDescriptorId);
	}

	/**
	 * Save the ConcreteTaskDescriptor modifications into database
	 * @param _concreteTaskDescriptor
	 */
	public void updateConcreteTaskDescriptor(ConcreteTaskDescriptor _concreteTaskDescriptor)
	{
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}

	/**
	 * Start the ConcreteTaskDescriptor and save into the data base changings
	 * (i.e. State, realStartingDate).
	 *
	 * @param _concreteTaskDescriptor
	 *            The ConcreteTaskDescriptor to start.
	 */
	public void startConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {

		_concreteTaskDescriptor.setState(State.STARTED);


		// save changings.
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}
	
	/**
	 * 
	 */
	public void removeConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		logger.debug("### CTDService ### removeConcreteTaskDescriptor");
		
		this.concreteTaskDescriptorDao.getHibernateTemplate().saveOrUpdate(_concreteTaskDescriptor);
		
		for (ConcreteActivity sca : _concreteTaskDescriptor.getSuperConcreteActivities()){
			sca.getConcreteBreakdownElements().remove(_concreteTaskDescriptor);
			this.concreteActivityDao.saveOrUpdateConcreteActivity(sca);
		}
		ConcreteRoleDescriptor tmpConcreteRoleDescriptor;
		if (_concreteTaskDescriptor.getMainConcreteRoleDescriptor() != null){
			tmpConcreteRoleDescriptor = _concreteTaskDescriptor.getMainConcreteRoleDescriptor();
			this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao().getHibernateTemplate().saveOrUpdate(tmpConcreteRoleDescriptor);
			tmpConcreteRoleDescriptor.getPrimaryConcreteTaskDescriptors().remove(_concreteTaskDescriptor);
			this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao().saveOrUpdateConcreteRoleDescriptor(tmpConcreteRoleDescriptor);
		}
		
		TaskDescriptor td = _concreteTaskDescriptor.getTaskDescriptor();
		td.getConcreteTaskDescriptors().remove(_concreteTaskDescriptor);
		this.taskDescriptorService.getTaskDescriptorDao().saveOrUpdateTaskDescriptor(td);
		
		this.getConcreteTaskDescriptorDao().deleteConcreteTaskDescriptor(_concreteTaskDescriptor);
	}
	
	/**
	 * When the user click on the button affected.
	 *
	 * @param _concreteTaskDescriptor
	 */
	@Transactional(readOnly = false)
	public void affectedConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor, Participant _user) {
		
		this.concreteTaskDescriptorDao.getHibernateTemplate().saveOrUpdate(_concreteTaskDescriptor);
		
		RoleDescriptor mainRole = _concreteTaskDescriptor.getTaskDescriptor().getMainRole();
		
		Set<ConcreteRoleDescriptor> listeRd = mainRole.getConcreteRoleDescriptors();

		// on parcours les deux liste afin de trouver le bon
		// concreteRoledescriptor
		for (ConcreteRoleDescriptor tmpListeRd : listeRd) {

			if(tmpListeRd.getParticipant() != null)
			{
				if(tmpListeRd.getParticipant().getWilosuser_id().equals(_user.getWilosuser_id()))
				{
					this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao().saveOrUpdateConcreteRoleDescriptor(tmpListeRd);
					_concreteTaskDescriptor.addConcreteRoleDescriptor(tmpListeRd);
					break;
				}
			}
		}
		_concreteTaskDescriptor.setState(State.READY);
	}

	/**
	 * Suspend the ConcreteTaskDescriptor and save into the data base changings
	 * (i.e. State).
	 *
	 * @param _concreteTaskDescriptor
	 *            The ConcreteTaskDescriptor to start.
	 */
	public void suspendConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		// update changings.
		_concreteTaskDescriptor.setState(State.SUSPENDED);

		// save changings.
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}

	/**
	 * Finish the ConcreteTaskDescriptor and save into the data base changings
	 * (i.e. State, realFinishingDate).
	 *
	 * @param _concreteTaskDescriptor
	 *            The ConcreteTaskDescriptor to start.
	 */
	public void finishConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		// update changings.
		_concreteTaskDescriptor.setState(State.FINISHED);

		// save changings.
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}

	/**
	 * 
	 * @param _concreteTaskDescriptor
	 */
	public void saveConcreteTaskDescriptor(ConcreteTaskDescriptor _concreteTaskDescriptor) {
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}

	/**
	 * Dissociates a participant from a ConcreteTask
	 * 
	 * @param _concreteTaskDescriptor the ConcreteTaskDescriptor to be dissociated
	 * @param _participant the Participant to dissociate
	 */
	public void dissociateConcreteTaskDescriptor(ConcreteTaskDescriptor _concreteTaskDescriptor) {
		ConcreteRoleDescriptor cmrd = _concreteTaskDescriptor.getMainConcreteRoleDescriptor();
		
		cmrd.removePrimaryConcreteTaskDescriptor(_concreteTaskDescriptor);
		_concreteTaskDescriptor.setState(State.CREATED);
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
		this.concreteRoleDescriptorService.saveConcreteRoleDescriptor(cmrd);
	}
	
	/**
	 * Getter of taskDescriptorDao.
	 *
	 * @return the taskDescriptorDao.
	 */
	public ConcreteTaskDescriptorDao getConcreteTaskDescriptorDao() {
		return this.concreteTaskDescriptorDao;
	}


	/**
	 * Setter of taskDescriptorDao.
	 *
	 * @param _taskDescriptorDao
	 *            The taskDescriptorDao to set.
	 */
	public void setConcreteTaskDescriptorDao(
			ConcreteTaskDescriptorDao _concreteTaskDescriptorDao) {
		this.concreteTaskDescriptorDao = _concreteTaskDescriptorDao;
	}

	/**
	 * 
	 * @return
	 */
	public RoleDescriptorService getRoleDescriptorService() {
		return roleDescriptorService;
	}

	/**
	 * 
	 * @param roleDescriptorService
	 */
	public void setRoleDescriptorService(
			RoleDescriptorService roleDescriptorService) {
		this.roleDescriptorService = roleDescriptorService;
	}

	/**
	 * 
	 * @return
	 */
	public TaskDescriptorService getTaskDescriptorService() {
		return taskDescriptorService;
	}

	/**
	 * 
	 * @param taskDescriptorService
	 */
	public void setTaskDescriptorService(
			TaskDescriptorService taskDescriptorService) {
		this.taskDescriptorService = taskDescriptorService;
	}

	/**
	 * @return the concreteRoleDescriptorService
	 */
	public ConcreteRoleDescriptorService getConcreteRoleDescriptorService() {
		return concreteRoleDescriptorService;
	}

	/**
	 * @param concreteRoleDescriptorService the concreteRoleDescriptorService to set
	 */
	public void setConcreteRoleDescriptorService(
			ConcreteRoleDescriptorService concreteRoleDescriptorService) {
		this.concreteRoleDescriptorService = concreteRoleDescriptorService;
	}

	/**
	 * @return the concreteActivityDao
	 */
	public ConcreteActivityDao getConcreteActivityDao() {
		return concreteActivityDao;
	}

	/**
	 * @param concreteActivityDao the concreteActivityDao to set
	 */
	public void setConcreteActivityDao(ConcreteActivityDao concreteActivityDao) {
		this.concreteActivityDao = concreteActivityDao;
	}
}
