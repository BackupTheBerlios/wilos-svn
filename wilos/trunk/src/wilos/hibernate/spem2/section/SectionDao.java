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

package wilos.hibernate.spem2.section;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.section.Section;
import wilos.utils.ExceptionManager;

public class SectionDao extends HibernateDaoSupport{
	
	/**
	 * Save or update Section object
	 * 
	 * @param _section
	 */
	public void saveOrUpdateSection(Section _section) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_section) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateSection", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateSection", _ex);
		}
	}

	/**
	 * Get List of all section object
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Section> getAllSection() {
		List<Section> loadAll = new ArrayList<Section>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Section.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllSection", _e);
		}
		return loadAll ;
	}

	/**
	 * Get the Section from the id
	 * 
	 * @param _id
	 * @return
	 */
	public Section getSection(String _id) {
		return (Section) this.getHibernateTemplate().get(Section.class, _id) ;
	}

	/**
	 * Delete a Section
	 * 
	 * @param _step
	 */
	public void deleteSection(Section _section) {
		try{
			this.getHibernateTemplate().delete(_section) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteSection", _e);
		}
	}

}

