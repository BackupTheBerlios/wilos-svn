package wilos.presentation.web.viewer;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;

public class ConcreteTaskViewerBean {

	/* Services */

	private WebSessionService webSessionService;

	private ConcreteTaskDescriptorService concreteTaskDescriptorService;

	private ParticipantService participantService;

	private ProjectService projectService;

	/* Simple fields */

	private ConcreteTaskDescriptor concreteTaskDescriptor;

	private String concreteTaskDescriptorId = "";

	private boolean visibleModifiable;

	protected final Log logger = LogFactory.getLog(this.getClass());

	public void buildConcreteTaskDescriptor() {
		this.concreteTaskDescriptor = new ConcreteTaskDescriptor();
		if (!(this.concreteTaskDescriptorId.equals(""))
				|| this.concreteTaskDescriptorId != null) {
			this.concreteTaskDescriptor = this.concreteTaskDescriptorService
					.getConcreteTaskDescriptor(this.concreteTaskDescriptorId);
		}
	}

	public boolean getChangeButtonIsDisabled() {
		String wilosUserId = (String) this.webSessionService
				.getAttribute(WebSessionService.WILOS_USER_ID);

		Project project = this.projectService
				.getProject((String) this.webSessionService
						.getAttribute(WebSessionService.PROJECT_ID));

		if ((project.getProjectManager() != null)
				&& (project.getProjectManager().getWilosuser_id()
						.equals(wilosUserId)))
			return false;
		else
			return true;
	}

	public boolean getIsInputNameReadOnly() {
		return (this.getChangeButtonIsDisabled());
	}

	public void changeConcreteName() {
		this.concreteTaskDescriptorService
				.saveConcreteTaskDescriptor(this.concreteTaskDescriptor);
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

		String wilosUserId = (String) this.webSessionService
				.getAttribute(WebSessionService.WILOS_USER_ID);
		Participant participant = this.participantService
				.getParticipant(wilosUserId);
		
		return (this.concreteTaskDescriptor.getState().equals("Created")&& this.concreteTaskDescriptorService
				.affectedvisible(this.concreteTaskDescriptor, participant));

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

	/**
	 * return the value of visible buton start
	 *
	 * @return
	 */
	public boolean getVisibleStart() {

		return (this.concreteTaskDescriptor.getState().equals("Ready"));

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
				&& !this.concreteTaskDescriptor.getState().equals("Suspended")
				&& this.concreteTaskDescriptor.getState().equals("Started")) {

			/*
			 * String participantId =
			 * (String)this.getWebSessionService().getAttribute(this.webSessionService.WILOS_USER_ID);
			 * Participant user =
			 * this.participantService.getParticipant(participantId);
			 */
			// TODO PSI2 : verifier si la concretetask est bien affect�e au
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
	 * @return the visibleSuspended
	 */
	public boolean getVisibleSuspended() {
		return this.concreteTaskDescriptor.getState().equals("Started");
	}

	/**
	 * @return the visibleReprendre
	 */
	public boolean getVisibleReprendre() {
		return this.concreteTaskDescriptor.getState().equals("Suspended");
	}

	public String getConcreteTaskDescriptorId() {
		return concreteTaskDescriptorId;
	}

	public void setConcreteTaskDescriptorId(String _concreteTaskDescriptorId) {
		this.concreteTaskDescriptorId = _concreteTaskDescriptorId;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
}