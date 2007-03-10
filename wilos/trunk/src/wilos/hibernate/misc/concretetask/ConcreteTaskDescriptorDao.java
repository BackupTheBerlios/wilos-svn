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

package wilos.hibernate.misc.concretetask;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.utils.ExceptionManager;

/**
 * ConcreteTaskDescriptorDao manage requests from the system to store
 * ConcreteTaskDescriptorDao to the database
 *
 * @author mat,seb
 *
 */
public class ConcreteTaskDescriptorDao extends HibernateDaoSupport {

	/**
	 * Save or update a ConcreteTaskDescriptorDao
	 *
	 * @param _concreteTaskdescriptor
	 */
	public void saveOrUpdateConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		try {
			this.getHibernateTemplate().saveOrUpdate(_concreteTaskDescriptor);
		} catch (DataIntegrityViolationException _e) {
			ExceptionManager.getInstance()
					.manageDataIntegrityViolationException(
							this.getClass().getName(),
							"saveOrUpdateConcreteTaskDescriptor", _e);
		} catch (ConstraintViolationException _ex) {
			ExceptionManager.getInstance().manageConstraintViolationException(
					this.getClass().getName(),
					"saveOrUpdateConcreteTaskDescriptor", _ex);
		}
	}

	/**
	 * Return a set of ConcreteTaskDescriptor
	 *
	 * @return set <ConcreteTaskDescriptor>
	 */
	@SuppressWarnings("unchecked")
	public List<ConcreteTaskDescriptor> getAllConcreteTaskDescriptors() {
		List<ConcreteTaskDescriptor> loadAll = new ArrayList<ConcreteTaskDescriptor>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(
					ConcreteTaskDescriptor.class));
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getAllConcreteTaskDescriptors",
					_e);
		}
		return loadAll;
	}

	/**
	 * Return the ConcreteTaskDescriptor which have the id _id
	 *
	 * @param _id
	 * @return ConcreteTaskDescriptor
	 */
	public ConcreteTaskDescriptor getConcreteTaskDescriptor(String _id) {
		return (ConcreteTaskDescriptor) this.getHibernateTemplate().get(
				ConcreteTaskDescriptor.class, _id);
	}

	/**
	 * Delete the ConcreteTaskDescriptor
	 *
	 * @param _concreteTaskdescriptor
	 */
	public void deleteConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskdescriptor) {
		try {
			this.getHibernateTemplate().delete(_concreteTaskdescriptor);
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "deleteConcreteTaskDescriptor",
					_e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ConcreteTaskDescriptor> getAllConcreteTaskDescriptorsForProject(
			String _projectId) {
		List ctds = this.getHibernateTemplate().find(
				"from ConcreteTaskDescriptor ctd where ctd.project.id=?",
				_projectId);
		return ctds;
	}
}
