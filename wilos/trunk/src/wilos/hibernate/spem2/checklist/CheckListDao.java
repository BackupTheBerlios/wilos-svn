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

package wilos.hibernate.spem2.checklist;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.checklist.CheckList;
import wilos.utils.ExceptionManager;

public class CheckListDao extends HibernateDaoSupport{
	
	/**
	 * Save or update CheckList object
	 * 
	 * @param _checklist
	 */
	public void saveOrUpdateCheckList(CheckList _checklist) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_checklist) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateCheckList", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateCheckList", _ex);
		}
	}

	/**
	 * Get List of all CheckList object
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<CheckList> getAllCheckList() {
		List<CheckList> loadAll = new ArrayList<CheckList>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(CheckList.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllCheckList", _e);
		}
		return loadAll ;
	}

	/**
	 * Get the CheckList from the id
	 * 
	 * @param _id
	 * @return
	 */
	public CheckList getCheckList(String _id) {
		return (CheckList) this.getHibernateTemplate().get(CheckList.class, _id) ;
	}

	/**
	 * Delete a CheckList
	 * 
	 * @param _step
	 */
	public void deleteCheckList(CheckList _checklist) {
		try{
			this.getHibernateTemplate().delete(_checklist) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteCheckList", _e);
		}
	}
}
