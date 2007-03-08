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

package wilos.hibernate.spem2.activity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.utils.ExceptionManager;

/**
 * ActivityDao manage requests from the system to store Acitivties to the
 * database
 * 
 * @author garwind
 * @author deder
 */
public class ActivityDao extends HibernateDaoSupport {

	/**
	 * Save or update an activity
	 * 
	 * @param _activity
	 */
	public void saveOrUpdateActivity(Activity _activity) {
		try {
			this.getHibernateTemplate().saveOrUpdate(_activity);
		} catch (DataIntegrityViolationException _e) {
			ExceptionManager.getInstance()
					.manageDataIntegrityViolationException(
							this.getClass().getName(), "saveOrUpdateActivity",
							_e);
		} catch (ConstraintViolationException _ex) {
			ExceptionManager.getInstance().manageConstraintViolationException(
					this.getClass().getName(), "saveOrUpdateActivity", _ex);
		}
	}

	/**
	 * Return a list of activities
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Activity> getAllActivities() {
		List<Activity> loadAll = new ArrayList<Activity>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(Activity.class));
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getAllActivities", _e);
		}
		return loadAll;
	}
	
	/**
	 * Return the activity which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public Activity getActivity(String _id) {
		return (Activity) this.getHibernateTemplate().get(Activity.class, _id) ;
	}

	/**
	 * Return a activity  with the given guid If there are many activities with
	 * the same guid, it returns the first
	 * 
	 * @param _process
	 * @return
	 */
	public Activity getActivityFromGuid(String _guid) {
		List activities = this.getHibernateTemplate().find("from Activity a where a.guid=?", _guid) ;
		if(activities.size() > 0)
			return (Activity) activities.get(0) ;
		else
			return null ;
	}

	/**
	 * Test function that return an activity with the given prefix If there are
	 * many activity with the same prefix, it returns the first
	 * 
	 * @param _prefix
	 * @return
	 */
	public Activity getActivityFromPrefix(String _prefix) {
		List actvities = this.getHibernateTemplate().find(
				"from Activity a where a.prefix=?", _prefix);
		if (actvities.size() > 0)
			return (Activity) actvities.get(0);
		else
			return null;
	}

	/**
	 * Delete the activity
	 * 
	 * @param _activity
	 */
	public void deleteActivity(Activity _activity) {
		try {
			this.getHibernateTemplate().delete(_activity);
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "deleteActivity", _e);
		}
	}

	public List<BreakdownElement> getBreakdownElementsFromActivity(
			String _activityId) {
		List bdes = this
				.getHibernateTemplate()
				.find(
						"from BreakdownElement bde join bde.superActivities s where s.id=?",
						_activityId);
		List<BreakdownElement> listBdes = new ArrayList<BreakdownElement>();
		if (bdes.get(0) instanceof List) {
			for (Object o : (ArrayList) bdes.get(0)) {
				if (o instanceof BreakdownElement) {
					BreakdownElement bde = (BreakdownElement) o;
					listBdes.add(bde);
				}
			}
		}
		return listBdes;
	}
}
