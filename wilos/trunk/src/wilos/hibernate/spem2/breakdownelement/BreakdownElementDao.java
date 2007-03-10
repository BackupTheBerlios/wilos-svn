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

package wilos.hibernate.spem2.breakdownelement ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.utils.ExceptionManager;

/**
 * BreakdownElementDao manage requests from the system to store BreakdownElement to the database.
 * 
 * @author deder
 */
public class BreakdownElementDao extends HibernateDaoSupport {

	/**
	 * Save or update an bde.
	 * 
	 * @param _bde
	 */
	public void saveOrUpdateBreakdownElement(BreakdownElement _bde) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_bde) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateBreakdownElement", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateBreakdownElement", _ex);
		}
	}

	/**
	 * Return a list of bdes.
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<BreakdownElement> getAllBreakdownElements() {
		List<BreakdownElement> loadAll = new ArrayList<BreakdownElement>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(BreakdownElement.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllBreakdownElements", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the bde which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public BreakdownElement getBreakdownElement(String _id) {
		return (BreakdownElement) this.getHibernateTemplate().get(BreakdownElement.class, _id) ;
	}

	/**
	 * Delete the bde.
	 * 
	 * @param _bde
	 */
	public void deleteBreakdownElement(BreakdownElement _bde) {
		try{
			this.getHibernateTemplate().delete(_bde) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteBreakdownElement", _e);
		}
	}
}
