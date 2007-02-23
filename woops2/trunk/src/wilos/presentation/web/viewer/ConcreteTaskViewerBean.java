package wilos.presentation.web.viewer;

import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.presentation.web.tree.TreeBean;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.utils.Constantes.State;

public class ConcreteTaskViewerBean {

	/* Services */

	private WebSessionService webSessionService;

	private ConcreteTaskDescriptorService concreteTaskDescriptorService;
	
	private TaskDescriptorService taskDescriptorService;
	
	private ConcreteRoleDescriptorService concreteRoleDescriptorService;

	private RoleDescriptorService roleDescriptorService;

	private ParticipantService participantService;

	private ProjectService projectService;

	/* Simple fields */

	private ConcreteTaskDescriptor concreteTaskDescriptor;

	private boolean visibleModifiable;

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	public String getDisplayedState() {
		String _state ="";
		if (this.concreteTaskDescriptor.getState().equals(State.CREATED)) {
			_state = ResourceBundle.getBundle(
					"wilos.resources.messages", FacesContext.getCurrentInstance()
						.getApplication().getDefaultLocale()).getString("constantes.state.created");
		}
		else if (this.concreteTaskDescriptor.getState().equals(State.READY)) {
					_state = ResourceBundle.getBundle(
							"wilos.resources.messages", FacesContext.getCurrentInstance()
								.getApplication().getDefaultLocale()).getString("constantes.state.ready");
				}
				else if (this.concreteTaskDescriptor.getState().equals(State.STARTED)) {
							_state = ResourceBundle.getBundle(
									"wilos.resources.messages", FacesContext.getCurrentInstance()
										.getApplication().getDefaultLocale()).getString("constantes.state.started");
						}
						else if (this.concreteTaskDescriptor.getState().equals(State.SUSPENDED)) {
									_state = ResourceBundle.getBundle(
											"wilos.resources.messages", FacesContext.getCurrentInstance()
												.getApplication().getDefaultLocale()).getString("constantes.state.suspended");
								}
								else if (this.concreteTaskDescriptor.getState().equals(State.FINISHED)) {
											_state = ResourceBundle.getBundle(
													"wilos.resources.messages", FacesContext.getCurrentInstance()
														.getApplication().getDefaultLocale()).getString("constantes.state.finished");
										}
		return _state;
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
		
		//	 Refresh the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
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

		return (this.concreteTaskDescriptor.getState().equals(State.CREATED)&& this.visibleAffected());

	}
	/**
	 * Methode for check if user can affected to a task
	 */
	public boolean visibleAffected() {

		String wilosUserId = (String) this.webSessionService
				.getAttribute(WebSessionService.WILOS_USER_ID);
		Participant participant = this.participantService
				.getParticipant(wilosUserId);
		boolean afficher = false;
		TaskDescriptor tmp = this.concreteTaskDescriptor.getTaskDescriptor();
		RoleDescriptor tmpRoleDescriptor;
		TaskDescriptor tmp2 = this.taskDescriptorService.getTaskDescriptorById(tmp.getId());

		if(tmp2.getMainRole() == null)
		{
			return false;
		}
		tmpRoleDescriptor = tmp2.getMainRole();
			RoleDescriptor rd = this.roleDescriptorService.getRoleDescriptorById(tmpRoleDescriptor.getId());
		// recuperation des deux listes.
		//	this.roleDescriptorService.
		List<ConcreteRoleDescriptor> listeRd = this.concreteRoleDescriptorService.getAllConcreteRoleDescriptorForARoleDescriptor(rd.getId());

		// on parcours les deux liste afin de trouver le bon
		// concreteRoledescriptor
		for (ConcreteRoleDescriptor tmpListeRd : listeRd) {

			ConcreteRoleDescriptor crd = this.concreteRoleDescriptorService.getConcreteRoleDescriptorById(tmpListeRd.getId());
			if(crd.getParticipant() == null)
			{
				return false;
			}
			else
			{
				if(crd.getParticipant().getWilosuser_id().equals(participant.getWilosuser_id()))
				{
					afficher = true;
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
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}

	public void stopActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService
				.finishConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Refresh the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}

	public void suspendedActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService
				.suspendConcreteTaskDescriptor(this.concreteTaskDescriptor);

		// Refresh the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}

	/**
	 * return the value of visible buton start
	 *
	 * @return
	 */
	public boolean getVisibleStart() {

		return (this.concreteTaskDescriptor.getState().equals(State.READY));

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
		if (!this.getVisibleAffected()
				&& !this.getVisibleStart()
				&& !this.concreteTaskDescriptor.getState().equals(
						State.FINISHED)
				&& !this.concreteTaskDescriptor.getState().equals(
						State.SUSPENDED)
				&& this.concreteTaskDescriptor.getState().equals(State.STARTED)) {

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
		return this.concreteTaskDescriptor.getState().equals(State.STARTED);
	}

	/**
	 * @return the visibleSuspended
	 */
	public boolean getVisibleSuspended() {
		return this.concreteTaskDescriptor.getState().equals(State.STARTED);
	}

	/**
	 * @return the visibleReprendre
	 */
	public boolean getVisibleReprendre() {
		return this.concreteTaskDescriptor.getState().equals(State.SUSPENDED);
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	/**
	 * @return the concreteRoleDescriptorService
	 */
	public ConcreteRoleDescriptorService getConcreteRoleDescriptorService() {
		return concreteRoleDescriptorService;
	}

	/**
	 * @param concreteRoleDescriptorService the concreteRoleDescriptorService to set
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
	 * @param roleDescriptorService the roleDescriptorService to set
	 */
	public void setRoleDescriptorService(RoleDescriptorService roleDescriptorService) {
		this.roleDescriptorService = roleDescriptorService;
	}

	/**
	 * @return the taskDescriptorService
	 */
	public TaskDescriptorService getTaskDescriptorService() {
		return taskDescriptorService;
	}

	/**
	 * @param taskDescriptorService the taskDescriptorService to set
	 */
	public void setTaskDescriptorService(TaskDescriptorService taskDescriptorService) {
		this.taskDescriptorService = taskDescriptorService;
	}
}