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

package wilos.hibernate.misc.wilosuser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.wilosuser.Administrator;

/**
 * AdministratorDao manage requests from the system to store Administrator into
 * the database.
 * 
 * @author Yoann Lopes
 * @author Martial Lapeyre
 */
public class AdministratorDao extends HibernateDaoSupport {

	/**
	 * Save or update an Administator.
	 * 
	 * @param _administrator
	 */
	public void saveOrUpdateAdministrator(Administrator _administrator) {
		this.getHibernateTemplate().saveOrUpdate(_administrator);
	}

	/**
	 * Return a list of administrators.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Set<Administrator> getAllAdministrators() {
		Set<Administrator> loadAll = new HashSet<Administrator>();
		loadAll
				.addAll(this.getHibernateTemplate().loadAll(Administrator.class));
		return loadAll;
	}

	/**
	 * Return the administrator which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public Administrator getAdministrator(String _login) {
		//this.getHibernateTemplate().setCacheQueries(true);
		ArrayList administrators = (ArrayList)this.getHibernateTemplate().find("from Administrator a where a.login=?",_login);
		if(administrators.size()>0){
			return (Administrator)administrators.get(0);
		}else{
			return null;
		}
	}

	/**
	 * Delete the administrator.
	 * 
	 * @param _administrator
	 */
	public void deleteAdministrator(Administrator _administrator) {
		try {
			this.getHibernateTemplate().delete(_administrator);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting administrator
			// into the db.
			logger
					.error("#### ERROR #### --- AdministratorDao => deleteAdministrator : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
