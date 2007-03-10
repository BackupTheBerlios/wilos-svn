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

package wilos.hibernate.misc.concreteactivity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.utils.ExceptionManager;

/**
 * ConcreteActivityDao manage requests from the system to store Concrete Activties to the
 * database
 * 
 * @author garwind
 * @author deder
 */
public class ConcreteActivityDao extends HibernateDaoSupport {
	/**
	 * Save or update an concrete activity
	 * 
	 * @param _concreteactivity
	 */
	public void saveOrUpdateConcreteActivity(ConcreteActivity _concreteactivity) {
		try {
			this.getHibernateTemplate().saveOrUpdate(_concreteactivity);
		} catch (DataIntegrityViolationException _e) {
			ExceptionManager.getInstance()
					.manageDataIntegrityViolationException(
							this.getClass().getName(), "saveOrUpdateConcreteActivity",
							_e);
		} catch (ConstraintViolationException _ex) {
			ExceptionManager.getInstance().manageConstraintViolationException(
					this.getClass().getName(), "saveOrUpdateConcreteActivity", _ex);
		}
	}

	/**
	 * Return a list of concrete activities
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ConcreteActivity> getAllConcreteActivities() {
		List<ConcreteActivity> loadAll = new ArrayList<ConcreteActivity>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(ConcreteActivity.class));
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getAllConcreteActivities", _e);
		}
		return loadAll;
	}

	/**
	 * Return the concrete activity which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public ConcreteActivity getConcreteActivity(String _id) {
		return (ConcreteActivity) this.getHibernateTemplate().get(ConcreteActivity.class, _id);
	}

	/**
	 * Test function that return an concrete activity with the given prefix If there are
	 * many activity with the same prefix, it returns the first
	 * 
	 * @param _prefix
	 * @return
	 */
	public ConcreteActivity getConcreteActivityFromPrefix(String _prefix) {
		List concreteactvities = this.getHibernateTemplate().find(
				"from ConcreteActivity a where a.prefix=?", _prefix);
		if (concreteactvities.size() > 0)
			return (ConcreteActivity) concreteactvities.get(0);
		else
			return null;
	}

	/**
	 * Delete the concrete activity
	 * 
	 * @param _concreteactivity
	 */
	public void deleteConcreteActivity(ConcreteActivity _concreteactivity) {
		try {
			this.getHibernateTemplate().delete(_concreteactivity);
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "deleteConcreteActivity", _e);
		}
	}
	
	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsFromConcreteActivity(
			String _concreteactivityId) {
		List concretebdes = this
				.getHibernateTemplate()
				.find(
						"from ConcreteBreakdownElement cbde join cbde.superConcreteActivities s where s.id=?",
						_concreteactivityId);
		List<ConcreteBreakdownElement> listConcreteBdes = new ArrayList<ConcreteBreakdownElement>();
		if (concretebdes.get(0) instanceof List) {
			for (Object o : (ArrayList) concretebdes.get(0)) {
				if (o instanceof ConcreteBreakdownElement) {
					ConcreteBreakdownElement concretebde = (ConcreteBreakdownElement) o;
					listConcreteBdes.add(concretebde);
				}
			}
		}
		return listConcreteBdes;
	}
}

