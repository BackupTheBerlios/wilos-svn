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

package wilos.business.services.misc.concreteiteration;

import wilos.hibernate.misc.concreteiteration.ConcreteIterationDao;
import wilos.model.misc.concreteiteration.ConcreteIteration;

/**
 * 
 * @author Padawan
 *
 */
public class ConcreteIterationService {

	public ConcreteIterationDao concreteIterationDao;

	/**
	 * 
	 * @param _concreteIterationId
	 * @return 
	 */
	public ConcreteIteration getConcreteIteration(String _concreteIterationId) {
		return this.getConcreteIterationDao().getConcreteIteration(
				_concreteIterationId);
	}

	/**
	 * 
	 * @param _concreteIteration
	 */
	public void saveConcreteIteration(ConcreteIteration _concreteIteration) {
		this.concreteIterationDao.saveOrUpdateConcreteIteration(_concreteIteration);
	}

	/**
	 * @return the concreteIterationDao
	 */
	public ConcreteIterationDao getConcreteIterationDao() {
		return concreteIterationDao;
	}

	/**
	 * @param concreteIterationDao
	 *            the concreteIterationDao to set
	 */
	public void setConcreteIterationDao(
			ConcreteIterationDao concreteIterationDao) {
		this.concreteIterationDao = concreteIterationDao;
	}
}
