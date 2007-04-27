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

package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import wilos.business.services.misc.concreteiteration.ConcreteIterationService;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;

public class ConcreteIterationViewerBean extends ViewerBean {

	private ConcreteIteration concreteIteration;

	private ConcreteIterationService concreteIterationService;

	/* Manage the table for the visible elements in the tree. */

	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsList() {
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>();
		if (this.concreteIteration != null)
			list.addAll(this.concreteIteration.getConcreteBreakdownElements());

		// Filter to obtain only concreteworkbreakdownelement (without
		// concreterole).
		List<ConcreteBreakdownElement> cwbdes = new ArrayList<ConcreteBreakdownElement>();
		for (ConcreteBreakdownElement cbde : list)
			if (cbde instanceof ConcreteWorkBreakdownElement)
				cwbdes.add(cbde);

		return cwbdes;
	}

	public void saveIteration() {
		super.getConcreteBreakdownElementService()
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(
						this.concreteIteration);

		// Reload the treebean.
		super.refreshProjectTree();
	}

	/* Manage the concretename field editable. */

	public void saveName() {
		this.concreteIterationService
				.saveConcreteIteration(this.concreteIteration);

		// Refresh the treebean.
		super.refreshProjectTree();

		// put the name text editor to disable.
		super.setNameIsEditable(false);
	}

	/* Getters & Setters */

	public ConcreteIteration getConcreteIteration() {
		return concreteIteration;
	}

	public void setConcreteIteration(ConcreteIteration _concreteIteration) {
		this.concreteIteration = _concreteIteration;
	}

	/**
	 * @return the concreteIterationService
	 */
	public ConcreteIterationService getConcreteIterationService() {
		return concreteIterationService;
	}

	/**
	 * @param concreteIterationService
	 *            the concreteIterationService to set
	 */
	public void setConcreteIterationService(
			ConcreteIterationService concreteIterationService) {
		this.concreteIterationService = concreteIterationService;
	}
}
