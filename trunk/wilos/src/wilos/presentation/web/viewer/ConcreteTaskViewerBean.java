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

import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebCommonService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.presentation.web.tree.WilosObjectNode;
import wilos.utils.Constantes.State;

public class ConcreteTaskViewerBean extends ViewerBean {

	/* Services */

	private ConcreteTaskDescriptorService concreteTaskDescriptorService;

	private TaskDescriptorService taskDescriptorService;

	private ConcreteRoleDescriptorService concreteRoleDescriptorService;

	private RoleDescriptorService roleDescriptorService;

	private ParticipantService participantService;

	private WebCommonService webCommonService;

	/* Simple fields */

	private ConcreteTaskDescriptor concreteTaskDescriptor;

	private boolean accomplishedTimeVisible;

	private boolean accomplishedTimeModifiable;

	private boolean remainingTimeVisible;

	private boolean remainingTimeModifiable;

	private boolean visibleSaveButton;

	private boolean visiblePopup = false;

	private boolean visibleDeletePopup = false;

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	public String getDisplayedState(){
		return this.webCommonService.getDisplayedState(this.concreteTaskDescriptor.getState());
	}

	public boolean getDissociateButtonIsVisible() {
		String state = this.concreteTaskDescriptor.getState();

		String wilosUserId = (String) super.getWebSessionService()
				.getAttribute(WebSessionService.WILOS_USER_ID);

		Participant participant = this.participantService
				.getParticipant(wilosUserId);
		ConcreteRoleDescriptor mcrd = this.concreteTaskDescriptor
				.getMainConcreteRoleDescriptor();

		if ((mcrd != null)
				&& ((state == State.CREATED) || (state == State.READY))
		/* && participant.getConcreteRoleDescriptors().contains(mcrd) */) {
			return true;
		} else {
			return false;
		}
	}

	public void dissociateTaskActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService
				.dissociateConcreteTaskDescriptor(this.concreteTaskDescriptor);

		this.webCommonService.addInfoMessage(this.webCommonService
				.getStringFromBundle("concretetaskviewer.dissociated"));

		super.refreshProjectTable();
		super.refreshProjectTree();
	}

	public void saveName() {
		this.concreteTaskDescriptorService
				.saveConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Refresh the treebean.
		super.refreshProjectTree();

		// put the name text editor to disable.
		super.setNameIsEditable(false);
	}

	/**
	 * soosuske methodes for the buton affected
	 */
	public void affectedActionListener(ActionEvent event) {
		String wilosUserId = (String) super.getWebSessionService()
				.getAttribute(WebSessionService.WILOS_USER_ID);
		Participant participant = this.participantService
				.getParticipant(wilosUserId);

		this.concreteTaskDescriptorService.affectedConcreteTaskDescriptor(
				this.concreteTaskDescriptor, participant);

		this.webCommonService.addInfoMessage(this.webCommonService
				.getStringFromBundle("concretetaskviewer.updateAffected"));

		super.refreshProjectTable();
		super.refreshProjectTree();
	}

	/**
	 * @return the visibleAffected
	 */
	public boolean getVisibleAffected() {
		return (this.concreteTaskDescriptor.getState().equals(State.CREATED) && this
				.visibleAffected());
	}

	/**
	 * Methode for check if user can affected to a task
	 */
	public boolean visibleAffected() {

		String wilosUserId = (String) super.getWebSessionService()
				.getAttribute(WebSessionService.WILOS_USER_ID);
		Participant participant = this.participantService
				.getParticipant(wilosUserId);
		boolean afficher = false;
		TaskDescriptor tmp = this.concreteTaskDescriptor.getTaskDescriptor();
		RoleDescriptor tmpRoleDescriptor;
		TaskDescriptor tmp2 = this.taskDescriptorService
				.getTaskDescriptorById(tmp.getId());

		if (tmp2.getMainRole() == null) {
			return false;
		}
		tmpRoleDescriptor = tmp2.getMainRole();
		RoleDescriptor rd = this.roleDescriptorService
				.getRoleDescriptorById(tmpRoleDescriptor.getId());
		// recuperation des deux listes.
		// this.roleDescriptorService.
		List<ConcreteRoleDescriptor> listeRd = this.concreteRoleDescriptorService
				.getAllConcreteRoleDescriptorForARoleDescriptor(rd.getId());

		// on parcours les deux liste afin de trouver le bon
		// concreteRoledescriptor
		for (ConcreteRoleDescriptor tmpListeRd : listeRd) {

			ConcreteRoleDescriptor crd = this.concreteRoleDescriptorService
					.getConcreteRoleDescriptorById(tmpListeRd.getId());
			if (crd.getParticipant() == null) {
				afficher = false;
			} else {
				if (crd.getParticipant().getWilosuser_id().equals(
						participant.getWilosuser_id())) {
					return true;
				}
			}

		}
		return afficher;
	}

	/**
	 * action for button start
	 * 
	 * @param event
	 */
	public void startActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService
				.startConcreteTaskDescriptor(this.concreteTaskDescriptor);

		super.refreshProjectTree();
		super.refreshProjectTable();
	}

	public void stopActionListener(ActionEvent _event) {
		// Displays the modal popup.
		// The stop action will be applied or aborted according
		// to the user's choice through the 'Confirm' or 'Cancel' buttons
		if (this.concreteTaskDescriptor.getRemainingTime() == 0) {
			this.confirmStop(_event);
		} else {
			this.visiblePopup = true;
		}
	}

	public void confirmDelete(ActionEvent _event) {

		if (!this.getChangeButtonIsDisabled()
				&& (this.concreteTaskDescriptor.getState()
						.equals(State.CREATED) || this.concreteTaskDescriptor
						.getState().equals(State.READY))) {
			String etat = this.concreteTaskDescriptor.getState();
			this.concreteTaskDescriptorService
					.removeConcreteTaskDescriptor(this.concreteTaskDescriptor);
			// Refresh components.

			super.refreshProjectTable();
			if (etat.equals(State.READY)) {
				super.rebuildProjectTree();
			} else {
				super.refreshProjectTree();
			}

			this.webCommonService
					.changeContentPage(WilosObjectNode.PROJECTNODE);
			/* Displays info message */
			this.webCommonService.addInfoMessage(this.webCommonService
					.getStringFromBundle("concretetaskviewer.removed"));
			this.visibleDeletePopup = false;
		}
	}

	public void cancelDelete(ActionEvent _event) {
		this.visibleDeletePopup = false;
	}

	public void confirmStop(ActionEvent _event) {
		this.concreteTaskDescriptor.setRemainingTime(0);

		this.concreteTaskDescriptorService
				.finishConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Refresh components.
		super.refreshProjectTree();
		super.refreshProjectTable();

		// once the treatment done, hide the popup
		this.visiblePopup = false;
	}

	public void cancelStop(ActionEvent _event) {
		this.visiblePopup = false;
	}

	public void suspendedActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService
				.suspendConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Refresh components.
		super.refreshProjectTree();
		super.refreshProjectTable();
	}

	public void removeActionListener(ActionEvent event) {
		this.visibleDeletePopup = true;
	}

	public ConcreteTaskDescriptor getConcreteTaskDescriptor() {
		return concreteTaskDescriptor;
	}

	public void setConcreteTaskDescriptor(
			ConcreteTaskDescriptor concreteTaskDescriptor) {
		this.concreteTaskDescriptor = concreteTaskDescriptor;
	}

	public ConcreteTaskDescriptorService getConcreteTaskDescriptorService() {
		return concreteTaskDescriptorService;
	}

	public void setConcreteTaskDescriptorService(
			ConcreteTaskDescriptorService concreteTaskDescriptorService) {
		this.concreteTaskDescriptorService = concreteTaskDescriptorService;
	}

	/**
	 * @return the participantService
	 */
	public ParticipantService getParticipantService() {
		return participantService;
	}

	/**
	 * Setter of participantService.
	 * 
	 * @param participantService
	 *            The participantService to set.
	 */
	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}

	/**
	 * Getter of accomplishedTimeModifiable.
	 * 
	 * @return the accomplishedTimeModifiable.
	 */
	public boolean getAccomplishedTimeModifiable() {
		this.accomplishedTimeModifiable = false;
		if (this.getAccomplishedTimeVisible()
				&& !this.concreteTaskDescriptor.getState().equals(
						State.SUSPENDED)
				&& !this.concreteTaskDescriptor.getState().equals(
						State.FINISHED) && this.visibleAffected()) {
			this.accomplishedTimeModifiable = false;
		} else {
			this.accomplishedTimeModifiable = true;
		}
		return this.accomplishedTimeModifiable;
	}

	/**
	 * Setter of accomplishedTimeModifiable.
	 * 
	 * @param _accomplishedTimeModifiable
	 *            The accomplishedTimeModifiable to set.
	 */
	public void setAccomplishedTimeModifiable(
			boolean _accomplishedTimeModifiable) {
		this.accomplishedTimeModifiable = _accomplishedTimeModifiable;
	}

	/**
	 * Getter of accomplishedTimeVisible.
	 * 
	 * @return the accomplishedTimeVisible.
	 */
	public boolean getAccomplishedTimeVisible() {
		this.accomplishedTimeVisible = false;
		if (!this.getVisibleAffected()
				&& !this.concreteTaskDescriptor.getState()
						.equals(State.CREATED)
				&& !this.concreteTaskDescriptor.getState().equals(State.READY)
				&& this.visibleAffected()) {
			this.accomplishedTimeVisible = true;
		} else {
			this.accomplishedTimeVisible = false;
		}
		return this.accomplishedTimeVisible;
	}

	/**
	 * Setter of accomplishedTimeVisible.
	 * 
	 * @param _accomplishedTimeVisible
	 *            The accomplishedTimeVisible to set.
	 */
	public void setAccomplishedTimeVisible(boolean _accomplishedTimeVisible) {
		this.accomplishedTimeVisible = _accomplishedTimeVisible;
	}

	/**
	 * Getter of remainingTimeModifiable.
	 * 
	 * @return the remainingTimeModifiable.
	 */
	public boolean getRemainingTimeModifiable() {
		this.remainingTimeModifiable = false;
		if (getRemainingTimeVisible()) {
			this.remainingTimeModifiable = false;
		} else {
			this.remainingTimeModifiable = true;
		}
		return this.remainingTimeModifiable;
	}

	/**
	 * Setter of remainingTimeModifiable.
	 * 
	 * @param _remainingTimeModifiable
	 *            The remainingTimeModifiable to set.
	 */
	public void setRemainingTimeModifiable(boolean _remainingTimeModifiable) {
		this.remainingTimeModifiable = _remainingTimeModifiable;
	}

	/**
	 * Getter of remainingTimeVisible.
	 * 
	 * @return the remainingTimeVisible.
	 */
	public boolean getRemainingTimeVisible() {
		this.remainingTimeVisible = false;
		if (!this.getVisibleAffected()
				&& !this.concreteTaskDescriptor.getState()
						.equals(State.CREATED)
				&& !this.concreteTaskDescriptor.getState().equals(
						State.FINISHED) && this.visibleAffected()) {
			this.remainingTimeVisible = true;
		} else {
			this.remainingTimeVisible = false;
		}
		return this.remainingTimeVisible;
	}

	/**
	 * Setter of remainingTimeVisible.
	 * 
	 * @param _remainingTimeVisible
	 *            The remainingTimeVisible to set.
	 */
	public void setRemainingTimeVisible(boolean _remainingTimeVisible) {
		this.remainingTimeVisible = _remainingTimeVisible;
	}

	/**
	 * save the ConcreteTaskDescriptor
	 * 
	 * @param event
	 */
	public void updateActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService
				.updateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		this.webCommonService.addInfoMessage(this.webCommonService
				.getStringFromBundle("concretetaskviewer.updateMessage"));

		super.refreshProjectTable();
	}

	public void setVisibleSaveButton(boolean _visibleSaveButton) {
		this.visibleSaveButton = _visibleSaveButton;
	}

	/**
	 * return the value of visible buton start
	 * 
	 * @return
	 */
	public boolean getVisibleStart() {
		return (this.concreteTaskDescriptor.getState().equals(State.READY) && this
				.visibleAffected());
	}

	/**
	 * Getter of visibleSaveButton.
	 * 
	 * @return the visibleSaveButton.
	 */
	public boolean getVisibleSaveButton() {
		this.visibleSaveButton = (!accomplishedTimeModifiable || !remainingTimeModifiable)
				&& this.visibleAffected();
		return this.visibleSaveButton;
	}

	/**
	 * @return the visibleStop
	 */
	public boolean getVisibleStop() {
		return (this.concreteTaskDescriptor.getState().equals(State.STARTED) && this
				.visibleAffected());
	}

	/**
	 * @return the visibleSuspended
	 */
	public boolean getVisibleSuspended() {
		return (this.concreteTaskDescriptor.getState().equals(State.STARTED) && this
				.visibleAffected());
	}

	/**
	 * @return the visibleSuspended
	 */
	public boolean getVisibleRemove() {
		return (!this.getChangeButtonIsDisabled() && (this.concreteTaskDescriptor
				.getState().equals(State.CREATED) || this.concreteTaskDescriptor
				.getState().equals(State.READY)));
	}

	/**
	 * @return the visibleReprendre
	 */
	public boolean getVisibleReprendre() {
		return (this.concreteTaskDescriptor.getState().equals(State.SUSPENDED) && this
				.visibleAffected());
	}

	/**
	 * @return the concreteRoleDescriptorService
	 */
	public ConcreteRoleDescriptorService getConcreteRoleDescriptorService() {
		return concreteRoleDescriptorService;
	}

	/**
	 * @param concreteRoleDescriptorService
	 *            the concreteRoleDescriptorService to set
	 */
	public void setConcreteRoleDescriptorService(
			ConcreteRoleDescriptorService concreteRoleDescriptorService) {
		this.concreteRoleDescriptorService = concreteRoleDescriptorService;
	}

	/**
	 * @return the roleDescriptorService
	 */
	public RoleDescriptorService getRoleDescriptorService() {
		return roleDescriptorService;
	}

	/**
	 * @param roleDescriptorService
	 *            the roleDescriptorService to set
	 */
	public void setRoleDescriptorService(
			RoleDescriptorService roleDescriptorService) {
		this.roleDescriptorService = roleDescriptorService;
	}

	/**
	 * @return the taskDescriptorService
	 */
	public TaskDescriptorService getTaskDescriptorService() {
		return taskDescriptorService;
	}

	/**
	 * @param taskDescriptorService
	 *            the taskDescriptorService to set
	 */
	public void setTaskDescriptorService(
			TaskDescriptorService taskDescriptorService) {
		this.taskDescriptorService = taskDescriptorService;
	}

	public boolean getVisiblePopup() {
		return visiblePopup;
	}

	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}

	public boolean isVisibleDeletePopup() {
		return visibleDeletePopup;
	}

	public void setVisibleDeletePopup(boolean _visibleDeletePopup) {
		this.visibleDeletePopup = _visibleDeletePopup;
	}

	public WebCommonService getWebCommonService() {
		return webCommonService;
	}

	public void setWebCommonService(WebCommonService webCommonService) {
		this.webCommonService = webCommonService;
	}
}