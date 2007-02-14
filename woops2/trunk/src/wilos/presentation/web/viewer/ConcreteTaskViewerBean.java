package wilos.presentation.web.viewer;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;

public class ConcreteTaskViewerBean {

	/* Services */

	private WebSessionService webSessionService;

	private ConcreteTaskDescriptorService concreteTaskDescriptorService;

	private ParticipantService participantService;

	/* Simple fields */

	private ConcreteTaskDescriptor concreteTaskDescriptor;

	private String concreteTaskDescriptorId = "";

	private boolean visibleAffected;

	private boolean visibleStart;

	private boolean visibleStop;

	private boolean visibleSuspended;

	private boolean visibleModifiable;

	private boolean visibleReprendre;

	protected final Log logger = LogFactory.getLog(this.getClass());

	public void buildConcreteTaskDescriptor() {
		this.concreteTaskDescriptor = new ConcreteTaskDescriptor();
		if (!(this.concreteTaskDescriptorId.equals(""))
				|| this.concreteTaskDescriptorId != null) {
			this.concreteTaskDescriptor = this.concreteTaskDescriptorService
					.getConcreteTaskDescriptor(this.concreteTaskDescriptorId);
		}
	}

	/**
	 * soosuske methodes for the buton affected
	 */
	public void affectedActionListener(ActionEvent event) {
		String wilosUserId = (String) this.webSessionService
				.getAttribute(WebSessionService.WILOS_USER_ID);
		Participant participant = this.participantService
				.getParticipant(wilosUserId);

		this.concreteTaskDescriptor = this.concreteTaskDescriptorService
				.affectedConcreteTaskDescriptor(this.concreteTaskDescriptor,
						participant);
		this.concreteTaskDescriptorService
				.affectedState(this.concreteTaskDescriptor);
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance()
						.getApplication().getDefaultLocale());
		FacesMessage message = new FacesMessage();
		message.setSummary(bundle
				.getString("concretetaskviewer.updateAffected"));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, message);
	}

	/**
	 * @return the visibleAffected
	 */
	public boolean getVisibleAffected() {
		return (this.concreteTaskDescriptor.getConcreteRoleDescriptor() == null);

	}

	/**
	 * action for button start
	 *
	 * @param event
	 */
	public void startActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService
				.startConcreteTaskDescriptor(this.concreteTaskDescriptor);
	}

	public void stopActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService
				.finishConcreteTaskDescriptor(this.concreteTaskDescriptor);
	}

	public void suspendedActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService
				.suspendConcreteTaskDescriptor(this.concreteTaskDescriptor);
	}

	public boolean getVisibleStart() {

		return (!(this.concreteTaskDescriptor.getConcreteRoleDescriptor() == null)
				&& !this.concreteTaskDescriptor.getState().equals("Started")
				&& !this.concreteTaskDescriptor.getState().equals("Finished") && !this.concreteTaskDescriptor
				.getState().equals("Suspended"));

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

	public String getConcreteTaskDescriptorId() {
		return concreteTaskDescriptorId;
	}

	public void setConcreteTaskDescriptorId(String concreteTaskDescriptorId) {
		this.concreteTaskDescriptorId = concreteTaskDescriptorId;
	}

	public void setVisibleAffected(boolean visibleAffected) {
		this.visibleAffected = visibleAffected;
	}

	public void setVisibleStart(boolean visibleStart) {
		this.visibleStart = visibleStart;
	}

	/**
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return webSessionService;
	}

	/**
	 * Setter of webSessionService.
	 *
	 * @param webSessionService
	 *            The webSessionService to set.
	 */
	public void setWebSessionService(WebSessionService webSessionService) {
		this.webSessionService = webSessionService;
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
	 * @return the visibleModifiable
	 */
	public boolean getVisibleModifiable() {
		this.visibleModifiable = false;
		if (!this.getVisibleAffected() && !this.getVisibleStart()
				&& !this.concreteTaskDescriptor.getState().equals("Finished")
				&& !this.concreteTaskDescriptor.getState().equals("Suspended")) {
			/*
			 * String participantId =
			 * (String)this.getWebSessionService().getAttribute(this.webSessionService.WILOS_USER_ID);
			 * Participant user =
			 * this.participantService.getParticipant(participantId);
			 */
			// TODO PSI2 : verifier si la concretetask est bien affectée au
			// participant via les roles
			this.visibleModifiable = true;
		} else {
			this.visibleModifiable = false;
		}
		return this.visibleModifiable;
	}

	/**
	 * @param visibleModifiable
	 *            the visibleModifiable to set
	 */
	public void setVisibleModifiable(boolean _visibleModifiable) {
		this.visibleModifiable = _visibleModifiable;
	}

	/**
	 * save the ConcreteTaskDescriptor
	 *
	 * @param event
	 */
	public void updateActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService
				.updateConcreteTaskDescriptor(this.concreteTaskDescriptor);
		this.buildConcreteTaskDescriptor();
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance()
						.getApplication().getDefaultLocale());
		FacesMessage message = new FacesMessage();
		message
				.setSummary(bundle
						.getString("concretetaskviewer.updateMessage"));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, message);

	}

	/**
	 * @return the visibleStop
	 */
	public boolean getVisibleStop() {
		return this.concreteTaskDescriptor.getState().equals("Started");
	}

	/**
	 * @param visibleStop
	 *            the visibleStop to set
	 */
	public void setVisibleStop(boolean visibleStop) {
		this.visibleStop = visibleStop;
	}

	/**
	 * @return the visibleSuspended
	 */
	public boolean getVisibleSuspended() {
		return this.concreteTaskDescriptor.getState().equals("Started");
	}

	/**
	 * @param visibleSuspended
	 *            the visibleSuspended to set
	 */
	public void setVisibleSuspended(boolean visibleSuspended) {
		this.visibleSuspended = visibleSuspended;
	}

	/**
	 * @return the visibleReprendre
	 */
	public boolean getVisibleReprendre() {
		return this.concreteTaskDescriptor.getState().equals("Suspended");
	}

	/**
	 * @param visibleReprendre
	 *            the visibleReprendre to set
	 */
	public void setVisibleReprendre(boolean visibleReprendre) {
		this.visibleReprendre = visibleReprendre;
	}
}