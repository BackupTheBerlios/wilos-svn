package wilos.presentation.web.viewer;

import javax.faces.event.ActionEvent;

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
		String wilosUserId = (String) this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID) ;
		Participant participant = this.participantService.getParticipant(wilosUserId);

		this.concreteTaskDescriptorService.affectedConcreteTaskDescriptor(
				this.concreteTaskDescriptor, participant);
	}

	public boolean isVisibleAffected() {
		String wilosUserId = (String) this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID) ;
		Participant participant = this.participantService.getParticipant(wilosUserId);

		boolean vis = this.affectedVisible(
				this.concreteTaskDescriptor, participant);

		return vis;
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

	public boolean isVisibleStart() {
		String wilosUserId = (String) this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID) ;
		Participant participant = this.participantService.getParticipant(wilosUserId);

		boolean vis = this.startVisible(
				this.concreteTaskDescriptor, participant);
		if (vis) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Visible ob affected buton
	 */
	private boolean affectedVisible(
			ConcreteTaskDescriptor _concreteTaskDescriptor, Participant _user) {

		boolean visi = true;

		/*
		 * FIXME TaskDescriptor td =
		 * _concreteTaskDescriptor.getTaskDescriptor(); TaskDescriptor tmp =
		 * this.taskDescriptorService.getTaskDescriptorDao().getTaskDescriptor(td.getId());
		 * RoleDescriptor roleDescriptor = tmp.getMainRole(); RoleDescriptor
		 * tmpRd =
		 * this.roleDescriptorService.getRoleDescriptorDao().getRoleDescriptor(roleDescriptor.getId());
		 * Set<Participant> part = tmpRd.getParticipants();
		 *
		 * for (Participant parti : part) {
		 * if(parti.getWilosuser_id().equals(_user.getWilosuser_id())) { visi=
		 * false; } }
		 */

		return visi;
	}

	/**
	 * Visible ob affected buton
	 */
	private boolean startVisible(ConcreteTaskDescriptor _concreteTaskDescriptor,
			Participant _user) {

		boolean visi = true;

		/*
		 * FIXME TaskDescriptor td =
		 * _concreteTaskDescriptor.getTaskDescriptor(); TaskDescriptor tmp =
		 * this.taskDescriptorService.getTaskDescriptorDao().getTaskDescriptor(td.getId());
		 * RoleDescriptor roleDescriptor = tmp.getMainRole(); RoleDescriptor
		 * tmpRd =
		 * this.roleDescriptorService.getRoleDescriptorDao().getRoleDescriptor(roleDescriptor.getId());
		 * Set<Participant> part = tmpRd.getParticipants();
		 * if(!_concreteTaskDescriptor.getState().equals("Started")) { for
		 * (Participant parti : part) {
		 * if(parti.getWilosuser_id().equals(_user.getWilosuser_id())) { visi=
		 * false; } } }
		 */
		return visi;

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

	public boolean getVisibleAffected() {
		return this.visibleAffected;
	}

	public void setVisibleAffected(boolean visibleAffected) {
		this.visibleAffected = visibleAffected;
	}

	public boolean getVisibleStart() {
		return this.visibleStart;
	}

	public void setVisibleStart(boolean visibleStart) {
		this.visibleStart = visibleStart;
	}

	/**
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return webSessionService ;
	}

	/**
	 * Setter of webSessionService.
	 *
	 * @param webSessionService The webSessionService to set.
	 */
	public void setWebSessionService(WebSessionService webSessionService) {
		this.webSessionService = webSessionService ;
	}

	/**
	 * @return the participantService
	 */
	public ParticipantService getParticipantService() {
		return participantService ;
	}

	/**
	 * Setter of participantService.
	 *
	 * @param participantService The participantService to set.
	 */
	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService ;
	}
}