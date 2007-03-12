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

package wilos.business.services.spem2.task;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.spem2.task.TaskDescriptorDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * 
 * @author Soosuske
 * 
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class TaskDescriptorService {

	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao;

	private TaskDescriptorDao taskDescriptorDao;

	private ConcreteActivityService concreteActivityService;

	/**
	 * 
	 * @param _project
	 * @param _td
	 * @param _cact
	 * @param _occ
	 */
	public void taskDescriptorInstanciation(Project _project, TaskDescriptor _td, ConcreteActivity _cact, int _occ) {

		if (_occ > 0) {
			for (int i = 1; i <= _occ; i++) {

				ConcreteTaskDescriptor ctd = new ConcreteTaskDescriptor();

				if (_occ > 1) {
					if (_td.getPresentationName() == null)
						ctd.setConcreteName(_td.getName() + "_" + (new Integer(i)).toString());
					else
						ctd.setConcreteName(_td.getPresentationName() + "_" + (new Integer(i)).toString());
				} else {
					if (_td.getPresentationName() == null)
						ctd.setConcreteName(_td.getName());
					else
						ctd.setConcreteName(_td.getPresentationName());
				}

				ctd.addTaskDescriptor(_td);
				ctd.setProject(_project);
				_cact.setConcreteBreakdownElements(this.concreteActivityService.getConcreteBreakdownElements(_cact));
				ctd.addSuperConcreteActivity(_cact);

				this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(ctd);
				System.out.println("### ConcreteTaskDescriptor sauve");
			}
		}

	}

	/**
	 * 
	 * @param _project
	 * @param _phase
	 * @param _cact
	 * @param _list
	 * @param _occ
	 * @param _isInstanciated
	 */
	public void taskDescriptorUpdate(Project _project, TaskDescriptor _td, Set<ConcreteActivity> _cacts, int _occ) {

		// one concretephase at least to insert in all attached
		// concreteactivities of the parent of _phase
		if (_occ > 0) {
			for (ConcreteActivity tmp : _cacts) {
				this.taskDescriptorInstanciation(_project, _td, tmp, _occ);
			}
		}
	}

	/**
	 * 
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<RoleDescriptor> getAdditionalRoles(TaskDescriptor _td) {

		Set<RoleDescriptor> tmp = new HashSet<RoleDescriptor>();
		for (RoleDescriptor rd : _td.getAdditionalRoles()) {
			tmp.add(rd);
		}
		return tmp;
	}

	public TaskDescriptor getTaskDescriptorById(String _id) {
		TaskDescriptor taskDescriptor;
		taskDescriptor = this.taskDescriptorDao.getTaskDescriptor(_id);
		return taskDescriptor;
	}

	public TaskDescriptorDao getTaskDescriptorDao() {
		return taskDescriptorDao;
	}

	public void setTaskDescriptorDao(TaskDescriptorDao taskDescriptorDao) {
		this.taskDescriptorDao = taskDescriptorDao;
	}

	public ConcreteTaskDescriptorDao getConcreteTaskDescriptorDao() {
		return this.concreteTaskDescriptorDao;
	}

	public void setConcreteTaskDescriptorDao(ConcreteTaskDescriptorDao _concreteTaskDescriptorDao) {
		this.concreteTaskDescriptorDao = _concreteTaskDescriptorDao;
	}

	/**
	 * @return the concreteActivityService
	 */
	public ConcreteActivityService getConcreteActivityService() {
		return concreteActivityService;
	}

	/**
	 * @param concreteActivityService
	 *            the concreteActivityService to set
	 */
	public void setConcreteActivityService(ConcreteActivityService concreteActivityService) {
		this.concreteActivityService = concreteActivityService;
	}
}
