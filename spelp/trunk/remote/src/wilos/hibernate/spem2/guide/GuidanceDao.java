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

package wilos.hibernate.spem2.guide;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.process.Process;
import wilos.utils.ExceptionManager;

public class GuidanceDao extends HibernateDaoSupport {
	
	/**
	 * Save or update Guideline object
	 * 
	 * @param _guidance
	 */
	public void saveOrUpdateGuidance(Guidance _guidance) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_guidance) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateGuidance", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateGuidance", _ex);
		}
	}

	/**
	 * Return a guidance  with the given guid If there are many process with
	 * the same guid, it returns the first
	 * 
	 * @param _guid
	 * @return
	 */
	public Guidance getGuidanceFromGuid(String _guid) {
		List guidances = this.getHibernateTemplate().find("from Guidance g where g.guid=?", _guid) ;
		if(guidances.size() > 0)
			return (Guidance) guidances.get(0) ;
		else
			return null ;
	}
	
	/**
	 * Get List of all step object
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Guidance> getAllGuidances() {
		List<Guidance> loadAll = new ArrayList<Guidance>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Guidance.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllGuidances", _e);
		}
		return loadAll ;
	}

	/**
	 * Get the step from the id
	 * 
	 * @param _id
	 * @return
	 */
	public Guidance getGuidance(String _id) {
		return (Guidance) this.getHibernateTemplate().get(Guidance.class, _id) ;
	}

	/**
	 * Delete a step
	 * 
	 * @param _step
	 */
	public void deleteGuidance(Guidance _guidance) {
		try{
			this.getHibernateTemplate().delete(_guidance) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteGuidance", _e);
		}
	}
}
