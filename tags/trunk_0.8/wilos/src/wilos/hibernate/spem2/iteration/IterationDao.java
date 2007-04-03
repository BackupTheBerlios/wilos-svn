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

package wilos.hibernate.spem2.iteration;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.utils.ExceptionManager;

/**
 * IterationDao manage requests from the system to store iterations to the
 * database
 * 
 * @author soosuske
 */
public class IterationDao extends HibernateDaoSupport {
	/**
	 * Save or update an iteration
	 * 
	 * @param _iteration
	 */
	public void saveOrUpdateIteration(Iteration _iteration) {
		try {
			this.getHibernateTemplate().saveOrUpdate(_iteration);
		} catch (DataIntegrityViolationException _e) {
			ExceptionManager.getInstance()
					.manageDataIntegrityViolationException(
							this.getClass().getName(), "saveOrUpdateIteration",
							_e);
		} catch (ConstraintViolationException _ex) {
			ExceptionManager.getInstance().manageConstraintViolationException(
					this.getClass().getName(), "saveOrUpdateIteration", _ex);
		}
	}

	/**
	 * Return a list of iterations.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Iteration> getAllIterations() {
		List<Iteration> loadAll = new ArrayList<Iteration>();
		try {
			loadAll
					.addAll(this.getHibernateTemplate()
							.loadAll(Iteration.class));

		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getAllIterations", _e);
		}
		return loadAll;
	}

	@SuppressWarnings("unchecked")
	public List<BreakdownElement> getBreakdownElementsFromIteration(
			String _iterationId) {
		List bdes = this
				.getHibernateTemplate()
				.find(
						"from BreakdownElement bde join bde.superActivities s where s.id=?",
						_iterationId);
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

	/**
	 * Return the iteration which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public Iteration getIteration(String _id) {
		return (Iteration) this.getHibernateTemplate()
				.get(Iteration.class, _id);
	}

	/**
	 * Delete the iteration
	 * 
	 * @param _iteration
	 */
	public void deleteIteration(Iteration _iteration) {
		try {
			this.getHibernateTemplate().delete(_iteration);
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getIteration", _e);
		}
	}
}
