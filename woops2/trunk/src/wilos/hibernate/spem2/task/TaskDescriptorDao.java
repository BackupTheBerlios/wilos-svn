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

package wilos.hibernate.spem2.task ;

import java.util.ArrayList ;
import java.util.List ;

import org.hibernate.exception.ConstraintViolationException ;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException ;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport ;

import wilos.model.spem2.task.TaskDescriptor ;
import wilos.utils.ExceptionManager;

/**
 * TaskDescriptorDao manage requests from the system to store TaskDescriptor to the database
 * 
 * @author eperico
 * 
 */
public class TaskDescriptorDao extends HibernateDaoSupport {

	/**
	 * Save or update a TaskDescriptor
	 * 
	 * @param _taskdescriptor
	 */
	public void saveOrUpdateTaskDescriptor(TaskDescriptor _taskdescriptor) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_taskdescriptor) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateTaskDescriptor", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateTaskDescriptor", _ex);
		}
	}

	/**
	 * Return a set of TaskDescriptor
	 * 
	 * @return set <TaskDescriptor>
	 */
	@ SuppressWarnings ("unchecked")
	public List<TaskDescriptor> getAllTaskDescriptors() {
		List<TaskDescriptor> loadAll = new ArrayList<TaskDescriptor>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(TaskDescriptor.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllTaskDescriptors", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the TaskDescriptor which have the id _id
	 * 
	 * @param _id
	 * @return TaskDescriptor
	 */
	public TaskDescriptor getTaskDescriptor(String _id) {
		return (TaskDescriptor) this.getHibernateTemplate().get(TaskDescriptor.class, _id) ;
	}

	/**
	 * Delete the TaskDescriptor
	 * 
	 * @param _taskdescriptor
	 */
	public void deleteTaskDescriptor(TaskDescriptor _taskdescriptor) {
		try{
			this.getHibernateTemplate().delete(_taskdescriptor) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteTaskDescriptor", _e);
		}
	}
}
