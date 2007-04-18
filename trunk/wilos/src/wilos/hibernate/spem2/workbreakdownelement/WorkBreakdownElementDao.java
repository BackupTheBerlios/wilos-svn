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

package wilos.hibernate.spem2.workbreakdownelement ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.utils.ExceptionManager;

/**
 * @author Sebastien
 * 
 * WorkBreakdownElementDao manage requests from the system to store wbdes to the database
 * 
 */
public class WorkBreakdownElementDao extends HibernateDaoSupport {

	/**
	 * Save or update an activity
	 * 
	 * @param _activity
	 */
	public void saveOrUpdateWorkBreakdownElement(WorkBreakdownElement _workBreakdownElement) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_workBreakdownElement) ;
			// this.getHibernateTemplate().flush() ;

		}
		catch(StaleObjectStateException _ex) {
			ExceptionManager.getInstance().manageStaleObjectStateException(this.getClass().getName(), "saveOrUpdateWorkBreakdownElement", _ex);
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateWorkBreakdownElement", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateWorkBreakdownElement", _ex);
		}
	}

	/**
	 * Return a list of activities
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<WorkBreakdownElement> getAllWorkBreakdownElements() {
		List<WorkBreakdownElement> loadAll = new ArrayList<WorkBreakdownElement>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(WorkBreakdownElement.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllWorkBreakdownElements", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the activity which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public WorkBreakdownElement getWorkBreakdownElement(String _id) {
		return (WorkBreakdownElement) this.getHibernateTemplate().get(WorkBreakdownElement.class, _id) ;
	}

	/**
	 * Delete the activity
	 * 
	 * @param _activity
	 */
	public void deleteWorkBreakdownElement(WorkBreakdownElement _workBreakdownElement) {
		try{
			this.getHibernateTemplate().delete(_workBreakdownElement) ;
		}
		catch(StaleObjectStateException _ex) {
			ExceptionManager.getInstance().manageStaleObjectStateException(this.getClass().getName(), "deleteWorkBreakdownElement", _ex);
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteWorkBreakdownElement", _e);
		}
	}
}
