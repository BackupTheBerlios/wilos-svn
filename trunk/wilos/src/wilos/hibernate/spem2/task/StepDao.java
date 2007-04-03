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

import wilos.model.spem2.task.Step ;
import wilos.utils.ExceptionManager;

/**
 * A Step is a Section and Work Definition that is used to organize Tasks into parts or subunits of
 * work
 * 
 * @author garwind
 */
public class StepDao extends HibernateDaoSupport {

	/**
	 * Save or update Step object
	 * 
	 * @param _step
	 */
	public void saveOrUpdateStep(Step _step) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_step) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateStep", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateStep", _ex);
		}
	}

	/**
	 * Get List of all step object
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Step> getAllSteps() {
		List<Step> loadAll = new ArrayList<Step>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Step.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllSteps", _e);
		}
		return loadAll ;
	}

	/**
	 * Get the step from the id
	 * 
	 * @param _id
	 * @return
	 */
	public Step getStep(String _id) {
		return (Step) this.getHibernateTemplate().get(Step.class, _id) ;
	}

	/**
	 * Delete a step
	 * 
	 * @param _step
	 */
	public void deleteStep(Step _step) {
		try{
			this.getHibernateTemplate().delete(_step) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteStep", _e);
		}
	}
}
