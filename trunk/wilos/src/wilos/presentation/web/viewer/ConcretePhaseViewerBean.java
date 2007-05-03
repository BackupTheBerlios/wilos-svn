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

import wilos.business.services.misc.concretephase.ConcretePhaseService;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;

public class ConcretePhaseViewerBean extends ViewerBean {

	private ConcretePhase concretePhase;

	private ConcretePhaseService concretePhaseService;

	/* Manage the table for the visible elements in the tree. */

	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsList() {
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>();
		if (this.concretePhase != null)
			list.addAll(this.concretePhase.getConcreteBreakdownElements());

		// Filter to obtain only concreteworkbreakdownelement (without
		// concreterole).
		List<ConcreteBreakdownElement> cwbdes = new ArrayList<ConcreteBreakdownElement>();
		for (ConcreteBreakdownElement cbde : list)
			if (cbde instanceof ConcreteWorkBreakdownElement)
				cwbdes.add(cbde);

		return cwbdes;
	}

	public void savePhase() {
		super.getConcreteBreakdownElementService()
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(
						this.concretePhase);

		// successful message.
		super.getWebCommonService().addInfoMessage(
				this.getWebCommonService().getStringFromBundle(
						"viewer.visibility.successMessage"));

		// Reload the treebean.
		super.refreshProjectTree();
	}

	/* Manage the concretename field editable. */

	public void saveName() {
		if (this.concretePhase.getConcreteName().trim().length() == 0) {
			// re-put the former concrete name.
			this.concretePhase.setConcreteName(this.concretePhaseService.getConcretePhaseName(this.concretePhase.getId()));

			// add error message.
			super.getWebCommonService().addErrorMessage(
					this.getWebCommonService().getStringFromBundle(
							"viewer.err.checkNameBySaving"));
		} else {
			this.concretePhaseService
					.saveConcretePhase(this.concretePhase);

			// Refresh the treebean.
			super.refreshProjectTree();

			// put the name text editor to disable.
			super.setNameIsEditable(false);

			// successful message.
			super.getWebCommonService().addInfoMessage(
					this.getWebCommonService().getStringFromBundle(
							"viewer.visibility.successMessage"));
		}
	}

	/* Getters & Setters */

	public ConcretePhase getConcretePhase() {
		return this.concretePhase;
	}

	public void setConcretePhase(ConcretePhase _concretePhase) {
		this.concretePhase = _concretePhase;
	}

	/**
	 * @return the concretePhaseService
	 */
	public ConcretePhaseService getConcretePhaseService() {
		return concretePhaseService;
	}

	/**
	 * @param concretePhaseService
	 *            the concretePhaseService to set
	 */
	public void setConcretePhaseService(
			ConcretePhaseService concretePhaseService) {
		this.concretePhaseService = concretePhaseService;
	}
}