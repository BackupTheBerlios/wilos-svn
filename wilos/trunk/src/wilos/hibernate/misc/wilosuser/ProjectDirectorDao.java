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
package wilos.hibernate.misc.wilosuser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.wilosuser.ProjectDirector;

/**
 * ProjectDirectorDao manage requests from the system to store WilosUser into
 * the database.
 * 
 * @author Yoann Lopes
 * @author Martial Lapeyre
 */
public class ProjectDirectorDao extends HibernateDaoSupport {

	/**
	 * Save or update an projectdirector.
	 * 
	 * @param _projectdirector
	 */
	public void saveOrUpdateProjectDirector(ProjectDirector _projectdirector) {
		this.getHibernateTemplate().saveOrUpdate(_projectdirector);
	}

	/**
	 * Return a list of projectdirectors.
	 * 
	 * @return
	 */
	public Set<ProjectDirector> getAllProjectDirectors() {
		Set<ProjectDirector> loadAll = new HashSet<ProjectDirector>();
		loadAll.addAll(this.getHibernateTemplate().loadAll(ProjectDirector.class));
		return loadAll;
	}

	/**
	 * Return the projectdirector which have the login _login.
	 * 
	 * @param _login
	 * @return
	 */
	public ProjectDirector getProjectDirector(String _login) {
		ArrayList projectDirectors = (ArrayList)this.getHibernateTemplate().find("from ProjectDirector pd where pd.login=?",_login);
		if(projectDirectors.size()>0){
			return (ProjectDirector)projectDirectors.get(0);
		}else{
			return null;
		}
	}

	/**
	 * Return the projectdirector which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public ProjectDirector getProjectDirectorById(String _id) {
		return (ProjectDirector)this.getHibernateTemplate().get(ProjectDirector.class, _id);
	}
	
	/**
	 * Delete the projectdirector.
	 * 
	 * @param _projectdirector
	 */
	public void deleteProjectDirector(ProjectDirector _projectdirector) {
		try {
			this.getHibernateTemplate().delete(_projectdirector);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting
			// projectdirector into the db.
			logger
					.error("#### ERROR #### --- ProjectDirectorDao => deleteProjectDirector : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
