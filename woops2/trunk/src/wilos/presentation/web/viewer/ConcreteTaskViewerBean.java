package wilos.presentation.web.viewer;

import javax.faces.event.ActionEvent;

import wilos.business.services.misc.concretetask.ConcreteTaskDescriptorService;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;

public class ConcreteTaskViewerBean {

	private ConcreteTaskDescriptor concreteTaskDescriptor;

	private ConcreteTaskDescriptorService concreteTaskDescriptorService;

	private String concreteTaskDescriptorId = "";
	
	private boolean visibleAffected;
	
	private boolean visibleStart;

	public void buildConcreteTaskDescriptor() {
		this.concreteTaskDescriptor = new ConcreteTaskDescriptor();
		if (!(this.concreteTaskDescriptorId.equals(""))
				|| this.concreteTaskDescriptorId != null) {
			this.concreteTaskDescriptor = this.concreteTaskDescriptorService
					.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(
							this.concreteTaskDescriptorId);
		}
	}
	
	/**
	 * soosuske
	 * methodes for the buton affected
	 */
	public void affectedActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService.affectedConcreteTaskDescriptor(this.concreteTaskDescriptor);
	}

	public boolean isVisibleAffected() {
		return this.concreteTaskDescriptor.getState().equals("Ready");
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
	
	/**
	 * action for button start
	 * @param event
	 */
	public void startActionListener(ActionEvent event) {
		this.concreteTaskDescriptorService.startConcreteTaskDescriptor(this.concreteTaskDescriptor);
	}
	
	public boolean isVisibleStart() {
		return this.concreteTaskDescriptor.getState().equals("Ready");
	}

	public void setVisibleStart(boolean visibleStart) {
		this.visibleStart = visibleStart;
	}
}