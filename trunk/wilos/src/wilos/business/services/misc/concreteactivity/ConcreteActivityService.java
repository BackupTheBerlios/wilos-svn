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

package wilos.business.services.misc.concreteactivity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;

/**
 * 
 * @author Sebastien
 * @author Garwind
 * @author Deder
 * @author soosuske
 * @author eperico
 * 
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ConcreteActivityService {

	private ConcreteActivityDao concreteActivityDao;
	
	/**
	 * 
	 * @param _act
	 * @return
	 */
	public Set<ConcreteBreakdownElement> getConcreteBreakdownElements(
			ConcreteActivity _cact) {
		Set<ConcreteBreakdownElement> tmp = new HashSet<ConcreteBreakdownElement>();
		this.concreteActivityDao.getSessionFactory().getCurrentSession()
				.saveOrUpdate(_cact);
		for (ConcreteBreakdownElement cbde : _cact
				.getConcreteBreakdownElements()) {
			tmp.add(cbde);
		}
		return tmp;
	}

	/**
	 * Allows to save the concrete activity which passed in parameters
	 * 
	 * @param _concreteActivity
	 */
	public void saveConcreteActivity(ConcreteActivity _concreteActivity) {
		this.concreteActivityDao
				.saveOrUpdateConcreteActivity(_concreteActivity);
	}

	/**
	 * Allows to get the concrete activity which has the same id than the
	 * parameter
	 * 
	 * @param _concreteActivityId
	 *            the id of the concreteActivity asked
	 * @return the ConcreteActivity which has the same id
	 */
	public ConcreteActivity getConcreteActivity(String _concreteActivityId) {
		return this.concreteActivityDao
				.getConcreteActivity(_concreteActivityId);
	}

	/**
	 * Return the list of all the Concrete Activities
	 * 
	 * @return the list of all the concreteActivities
	 */
	public List<ConcreteActivity> getAllConcreteActivities() {
		return this.concreteActivityDao.getAllConcreteActivities();
	}
	
	public String getConcreteActivityName(String _concreteActivityId){
		return this.concreteActivityDao.getConcreteActivityName(_concreteActivityId);
	}
	
	/* Getters & Setters */

	/**
	 * Return the concrete activity Dao
	 * 
	 * @return the concreteActivityDao
	 */
	public ConcreteActivityDao getConcreteActivityDao() {
		return concreteActivityDao;
	}

	/**
	 * Initialize the concreteActivityDao with the value in parameter
	 * 
	 * @param concreteActivityDao
	 *            the concreteActivityDao to set
	 */
	public void setConcreteActivityDao(ConcreteActivityDao concreteActivityDao) {
		this.concreteActivityDao = concreteActivityDao;
	}
}
