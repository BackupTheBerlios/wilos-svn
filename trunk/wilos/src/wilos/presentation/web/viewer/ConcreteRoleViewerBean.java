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

import javax.faces.event.ActionEvent;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebCommonService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.presentation.web.tree.WilosObjectNode;

public class ConcreteRoleViewerBean extends ViewerBean {

	private ConcreteRoleDescriptor concreteRoleDescriptor;

	private ConcreteRoleDescriptorService concreteRoleDescriptorService;

	private WebCommonService webCommonService;

	private String concreteRoleDescriptorId = "";

	private ParticipantService participantService;

	private String selectAffectedRoleView;

	private boolean visibleDeletePopup = false;

	public void buildConcreteRoleModel() {
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor();
		if (!(concreteRoleDescriptorId.equals(""))
				|| concreteRoleDescriptorId != null) {
			this.concreteRoleDescriptor = this.concreteRoleDescriptorService
					.getConcreteRoleDescriptorById(this.concreteRoleDescriptorId);
		}
	}

	public void affectParticipantToARole() {
		this.webCommonService.addErrorMessage(this.webCommonService
				.getStringFromBundle("concreteRoleViewer.success"));

		String wilosUserId = (String) super.getWebSessionService()
				.getAttribute(WebSessionService.WILOS_USER_ID);
		Participant user = this.participantService.getParticipant(wilosUserId);
		this.concreteRoleDescriptor.setParticipant(user);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao()
				.saveOrUpdateConcreteRoleDescriptor(concreteRoleDescriptor);

		// refresh the tree
		super.refreshProjectTree();
	}

	/**
	 * @return the visibleRemove
	 */
	public boolean getVisibleRemove() {
		return (!this.getChangeButtonIsDisabled() && (this.concreteRoleDescriptor
				.getParticipant() == null));
	}

	/**
	 * delete a concrete role
	 * 
	 */
	public void removeActionListener(ActionEvent event) {
		this.visibleDeletePopup = true;
	}

	public void confirmDelete(ActionEvent _event) {
		if (!this.getChangeButtonIsDisabled()
				&& (this.concreteRoleDescriptor.getParticipant() == null)) {
			this.concreteRoleDescriptorService
					.removeConcreteRoleDescriptor(this.concreteRoleDescriptor);
			// Refresh components.
			super.refreshProjectTable();
			super.rebuildProjectTree();
			super.refreshProjectTree();

			this.webCommonService
					.changeContentPage(WilosObjectNode.PROJECTNODE);

			/* Displays info message */
			this.webCommonService.addInfoMessage(this.webCommonService
					.getStringFromBundle("concreteRoleViewer.removed"));

			this.visibleDeletePopup = false;
		}
	}

	public void cancelDelete(ActionEvent _event) {
		this.visibleDeletePopup = false;
	}

	/**
	 * action calledto change the concrete name of a concrete role
	 * 
	 * @return
	 */
	public void saveName() {
		this.concreteRoleDescriptorService
				.saveConcreteRoleDescriptor(this.concreteRoleDescriptor);

		// Refresh the treebean.
		super.refreshProjectTree();

		// put the name text editor to disable.
		super.setNameIsEditable(false);
	}

	public ConcreteRoleDescriptor getConcreteRoleDescriptor() {
		return concreteRoleDescriptor;
	}

	public void setConcreteRoleDescriptor(
			ConcreteRoleDescriptor concreteRoleDescriptor) {
		this.concreteRoleDescriptor = concreteRoleDescriptor;
	}

	public String getConcreteRoleDescriptorId() {
		return concreteRoleDescriptorId;
	}

	public void setConcreteRoleDescriptorId(String concreteRoleDescriptorId) {
		this.concreteRoleDescriptorId = concreteRoleDescriptorId;
	}

	public ConcreteRoleDescriptorService getConcreteRoleDescriptorService() {
		return concreteRoleDescriptorService;
	}

	public void setConcreteRoleDescriptorService(
			ConcreteRoleDescriptorService concreteRoleDescriptorService) {
		this.concreteRoleDescriptorService = concreteRoleDescriptorService;
	}

	public String getSelectAffectedRoleView() {
		if (this.concreteRoleDescriptor.getParticipant() == null) {
			this.selectAffectedRoleView = "roleNotAffected";
		} else {
			this.selectAffectedRoleView = "roleAffectedTo";
		}
		return selectAffectedRoleView;
	}

	public void setSelectAffectedRoleView(String selectAffectedRoleView) {
		this.selectAffectedRoleView = selectAffectedRoleView;
	}

	public ParticipantService getParticipantService() {
		return participantService;
	}

	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}

	/**
	 * @return the visibleDeletePopup
	 */
	public boolean getVisibleDeletePopup() {
		return this.visibleDeletePopup;
	}

	/**
	 * @param visibleDeletePopup
	 *            the visibleDeletePopup to set
	 */
	public void setVisibleDeletePopup(boolean _visibleDeletePopup) {
		this.visibleDeletePopup = _visibleDeletePopup;
	}

	/**
	 * @return the webCommonService
	 */
	public WebCommonService getWebCommonService() {
		return webCommonService;
	}

	/**
	 * @param webCommonService
	 *            the webCommonService to set
	 */
	public void setWebCommonService(WebCommonService webCommonService) {
		this.webCommonService = webCommonService;
	}
}