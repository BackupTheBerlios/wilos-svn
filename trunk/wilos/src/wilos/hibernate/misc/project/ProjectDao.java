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

package wilos.hibernate.misc.project;

import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import wilos.model.misc.project.Project;
import wilos.utils.ExceptionManager;

/**
 * ProjectDao manage requests from the system to store Project into the
 * database.
 * 
 * @author martial
 */
public class ProjectDao extends HibernateDaoSupport {
	/**
	 * Save or update an project.
	 * 
	 * @param _project
	 */
	public void saveOrUpdateProject(Project _project) {
		this.getHibernateTemplate().saveOrUpdate(_project);
	}

	/**
	 * Return a list of project.
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public Set<Project> getAllProjects() {
		Set<Project> loadAll = new HashSet<Project>();
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Project.class));
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllProject", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the project which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public Project getProject(String _id) {
		return (Project) this.getHibernateTemplate().get(Project.class, _id);
	}

	/**
	 * Delete the project.
	 * 
	 * @param _project
	 */
	public void deleteProject(Project _project) {
		try {
			this.getHibernateTemplate().delete(_project);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting project into the db.
			logger.error("#### ERROR #### --- ProjectDao => deleteProject : trying to delete unexisting object \n" + sose);
		}
	}
}
