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

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;

public class ConcreteActivityViewerBean extends ViewerBean {

	private ConcreteActivity concreteActivity = null;

	private ConcreteActivityService concreteActivityService;

	public void saveName() {
		if (this.concreteActivity.getConcreteName().trim().length() == 0) {
			// re-put the former concrete name.
			ConcreteActivity tmp = this.concreteActivityService
					.getConcreteActivity(this.concreteActivity.getId());
			this.concreteActivity.setConcreteName(tmp.getConcreteName());

			// add error message.
			super.getWebCommonService().addErrorMessage(
					this.getWebCommonService().getStringFromBundle(
							"viewer.err.checkNameBySaving"));
		} else {
			this.concreteActivityService
					.saveConcreteActivity(this.concreteActivity);

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

	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsList() {
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>();
		if (this.concreteActivity != null)
			list.addAll(this.concreteActivity.getConcreteBreakdownElements());

		// Filter to obtain only concreteworkbreakdownelement (without
		// concreterole).
		List<ConcreteBreakdownElement> cwbdes = new ArrayList<ConcreteBreakdownElement>();
		for (ConcreteBreakdownElement cbde : list)
			if (cbde instanceof ConcreteWorkBreakdownElement)
				cwbdes.add(cbde);

		return cwbdes;
	}

	public void saveActivity() {
		super.getConcreteBreakdownElementService()
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(
						this.concreteActivity);

		// successful message.
		super.getWebCommonService().addInfoMessage(
				this.getWebCommonService().getStringFromBundle(
						"viewer.visibility.successMessage"));

		// Refresh the treebean.
		super.refreshProjectTree();
	}

	public ConcreteActivity getConcreteActivity() {
		return concreteActivity;
	}

	public void setConcreteActivity(ConcreteActivity _concreteActivity) {
		this.concreteActivity = _concreteActivity;
	}

	public ConcreteActivityService getConcreteActivityService() {
		return this.concreteActivityService;
	}

	public void setConcreteActivityService(
			ConcreteActivityService _concreteActivityService) {
		this.concreteActivityService = _concreteActivityService;
	}
}
