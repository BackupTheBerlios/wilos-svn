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

package wilos.business.services.misc.concretebreakdownelement;

import java.util.List;

import wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;

/**
 * 
 * @author Padawan
 * 
 */
public class ConcreteBreakdownElementService {

	ConcreteBreakdownElementDao concreteBreakdownElementDao;

	/**
	 * Return the list of Concrete Breakdown Elements which has the same
	 * projectId than the parameter
	 * 
	 * @param _projectId
	 * @return list of Concrete Breakdown Elements which has the same projectId
	 */
	public List<ConcreteBreakdownElement> getAllConcreteBreakdownElementsFromProject(
			String _projectId) {
		return this.concreteBreakdownElementDao.getAllConcreteBreakdownElementsFromProject(_projectId);
	}

	/**
	 * 
	 * @param _concreteActivity
	 */
	public void saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(
			ConcreteActivity _concreteActivity) {
		for (ConcreteBreakdownElement cbde : _concreteActivity
				.getConcreteBreakdownElements()) {
			this.concreteBreakdownElementDao
					.saveOrUpdateConcreteBreakdownElement(cbde);
		}
	}

	/**
	 * @return the concreteBreakdownElementDao
	 */
	public ConcreteBreakdownElementDao getConcreteBreakdownElementDao() {
		return concreteBreakdownElementDao;
	}

	/**
	 * Setter of concreteBreakdownElementDao.
	 * 
	 * @param concreteBreakdownElementDao
	 *            The concreteBreakdownElementDao to set.
	 */
	public void setConcreteBreakdownElementDao(
			ConcreteBreakdownElementDao concreteBreakdownElementDao) {
		this.concreteBreakdownElementDao = concreteBreakdownElementDao;
	}
}
