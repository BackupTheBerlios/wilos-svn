package wilos.presentation.web.viewer;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;

public class ConcreteRoleViewerBean {
	private ConcreteRoleDescriptor concreteRoleDescriptor;

	private ConcreteRoleDescriptorService concreteRoleDescriptorService;

	private String concreteRoleDescriptorId = "";

	public void buildConcreteRoleModel() {
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor();
		if (!(concreteRoleDescriptorId.equals("")) || concreteRoleDescriptorId != null) {
			this.concreteRoleDescriptor = this.concreteRoleDescriptorService.getConcreteRoleDescriptorById(this.concreteRoleDescriptorId);
		}
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

}