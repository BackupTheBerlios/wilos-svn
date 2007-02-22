package wilos.presentation.web.viewer;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService ;
import wilos.business.services.misc.role.ConcreteRoleAffectationService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.wilosuser.Participant;

public class ConcreteRoleViewerBean {
	private ConcreteRoleDescriptor concreteRoleDescriptor;

	private WebSessionService webSessionService;
	
	private ConcreteRoleDescriptorService concreteRoleDescriptorService;

	private String concreteRoleDescriptorId = "";
	
	private ParticipantService participantService ;
	
	private String selectAffectedRoleView;

	public void buildConcreteRoleModel() {
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor();
		if (!(concreteRoleDescriptorId.equals("")) || concreteRoleDescriptorId != null) {
			this.concreteRoleDescriptor = this.concreteRoleDescriptorService.getConcreteRoleDescriptorById(this.concreteRoleDescriptorId);
		}
	}
	
	public void affectParticipantToARole()
	{
		
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance()
						.getApplication().getDefaultLocale());

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
			message.setSummary(bundle
					.getString("concreteRoleViewer.success"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			facesContext.addMessage(null, message);
		
		String wilosUserId=(String)this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID);
		Participant user = this.participantService.getParticipant(wilosUserId);
		this.concreteRoleDescriptor.setParticipant(user);
		this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao().saveOrUpdateConcreteRoleDescriptor(concreteRoleDescriptor);
		
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
		if (this.concreteRoleDescriptor.getParticipant()==null)
		{
			this.selectAffectedRoleView="roleNotAffected";
		}else
		{
			this.selectAffectedRoleView="roleAffectedTo";
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

	public WebSessionService getWebSessionService() {
		return webSessionService;
	}

	public void setWebSessionService(WebSessionService webSessionService) {
		this.webSessionService = webSessionService;
	}

}