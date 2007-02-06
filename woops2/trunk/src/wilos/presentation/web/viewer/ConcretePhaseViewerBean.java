package wilos.presentation.web.viewer;

import wilos.business.services.misc.concretephase.ConcretePhaseService;
import wilos.model.misc.concretephase.ConcretePhase;

public class ConcretePhaseViewerBean {

	private ConcretePhase concretePhase;

	private ConcretePhaseService concretePhaseService;

	private String concretePhaseId = "";

	public void buildConcretePhaseModel() {
		this.concretePhase = new ConcretePhase();
		if (!(concretePhaseId.equals("")) || concretePhaseId != null) {
			this.concretePhase = this.concretePhaseService.getConcretePhase(this.concretePhaseId);
		}
	}

	public ConcretePhase getConcretePhase() {
		return concretePhase;
	}

	public void setConcretePhase(ConcretePhase concretePhase) {
		this.concretePhase = concretePhase;
	}

	public String getConcretePhaseId() {
		return concretePhaseId;
	}

	public void setConcretePhaseId(String concretePhaseId) {
		this.concretePhaseId = concretePhaseId;
	}

	/**
	 * @return the concretePhaseService
	 */
	public ConcretePhaseService getConcretePhaseService() {
		return concretePhaseService;
	}

	/**
	 * @param concretePhaseService the concretePhaseService to set
	 */
	public void setConcretePhaseService(ConcretePhaseService concretePhaseService) {
		this.concretePhaseService = concretePhaseService;
	}

}