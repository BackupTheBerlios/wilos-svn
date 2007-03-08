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

package wilos.hibernate.misc.concreteiteration;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.utils.ExceptionManager;

/**
 * ConcreteIterationDao manage requests from the system to store iterations to the
 * database
 *
 * @author Almiriad
 */
public class ConcreteIterationDao extends HibernateDaoSupport {
	/**
	 * Save or update an ConcreteIteration
	 *
	 * @param _ConcreteIteration
	 */
	public void saveOrUpdateConcreteIteration(ConcreteIteration _concreteIteration) {
		try {
			this.getHibernateTemplate().saveOrUpdate(_concreteIteration);
		} catch (DataIntegrityViolationException _e) {
			ExceptionManager.getInstance()
					.manageDataIntegrityViolationException(
							this.getClass().getName(), "saveOrUpdateConcreteIteration",
							_e);
		} catch (ConstraintViolationException _ex) {
			ExceptionManager.getInstance().manageConstraintViolationException(
					this.getClass().getName(), "saveOrUpdateConcreteIteration", _ex);
		}
	}

	/**
	 * Return a list of ConcreteIterations.
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ConcreteIteration> getAllConcreteIterations() {
		List<ConcreteIteration> loadAll = new ArrayList<ConcreteIteration>();
		try {
			loadAll
					.addAll(this.getHibernateTemplate()
							.loadAll(ConcreteIteration.class));

		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getAllConcreteIterations", _e);
		}
		return loadAll;
	}

	@SuppressWarnings("unchecked")
	public List<ConcreteBreakdownElement> getBreakdownElementsFromConcreteIteration(
			String _concreteIterationId) {
		List bdes = this
				.getHibernateTemplate()
				.find(
						"from BreakdownElement bde join bde.superActivities s where s.id=?",
						_concreteIterationId);
		List<ConcreteBreakdownElement> listBdes = new ArrayList<ConcreteBreakdownElement>();
		if (bdes.get(0) instanceof List) {
			for (Object o : (ArrayList) bdes.get(0)) {
				if (o instanceof ConcreteBreakdownElement) {
					ConcreteBreakdownElement bde = (ConcreteBreakdownElement) o;
					listBdes.add(bde);
				}
			}
		}
		return listBdes;
	}

	/**
	 * Return the ConcreteIteration which have the id _id
	 *
	 * @param _id
	 * @return
	 */
	public ConcreteIteration getConcreteIteration(String _id) {
		return (ConcreteIteration) this.getHibernateTemplate()
				.get(ConcreteIteration.class, _id);
	}

	/**
	 * Delete the ConcreteIteration
	 *
	 * @param _ConcreteIteration
	 */
	public void deleteConcreteIteration(ConcreteIteration _concreteIteration) {
		try {
			this.getHibernateTemplate().delete(_concreteIteration);
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getConcreteIteration", _e);
		}
	}
}
