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

package wilos.business.services.spem2.workbreakdownelement ;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElementDao;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkOrder;

/**
 *
 * @author nanawel
 *
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class WorkBreakdownElementService {

	private ConcreteWorkBreakdownElementDao concreteWorkBreakdownElementDao ;

	public void workBreakdownElementInstanciation (Project _project, WorkBreakdownElement _wbde) {

		ConcreteWorkBreakdownElement cwbe = new ConcreteWorkBreakdownElement();

		if (_wbde.getPresentationName() == null)
			cwbe.setConcreteName(_wbde.getName()) ;
		else
			cwbe.setConcreteName(_wbde.getPresentationName());

		cwbe.addBreakdownElement(_wbde);
		cwbe.setProject(_project);

		this.concreteWorkBreakdownElementDao.saveOrUpdateConcreteWorkBreakdownElement(cwbe);
	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<WorkOrder> getPredecessors(WorkBreakdownElement _wbde) {

		Set<WorkOrder> tmp = new HashSet<WorkOrder>();
		for (WorkOrder wo : _wbde.getPredecessors()) {
			tmp.add(wo);
		}
		return tmp;
	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<WorkOrder> getSuccessors(WorkBreakdownElement _wbde) {

		Set<WorkOrder> tmp = new HashSet<WorkOrder>();
		for (WorkOrder wo : _wbde.getSuccessors()) {
			tmp.add(wo);
		}
		return tmp;
	}

	/**
	 * @return the concreteBreakdownElementDao
	 */
	public ConcreteWorkBreakdownElementDao getConcreteWorkBreakdownElementDao() {
		return concreteWorkBreakdownElementDao;
	}

	/**
	 * @param concreteBreakdownElementDao the concreteBreakdownElementDao to set
	 */
	public void setConcreteWorkBreakdownElementDao(
			ConcreteWorkBreakdownElementDao concreteWorkBreakdownElementDao) {
		this.concreteWorkBreakdownElementDao = concreteWorkBreakdownElementDao;
	}

}
