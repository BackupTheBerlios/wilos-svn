package wilos.presentation.web.viewer;

import wilos.business.services.spem2.phase.PhaseService;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.spem2.phase.Phase;

public class ConcretePhaseViewerBean {

	private ConcretePhase concretePhase;
// FIXME CONCRETEPhaseService et concretePhaseService a faire
	private PhaseService phaseService;

	private String concretePhaseId = "";

	public void buildConcretePhaseModel() {
		this.concretePhase = new ConcretePhase();
		if (!(concretePhaseId.equals("")) || concretePhaseId != null) {
			this.concretePhase = this.phaseService.getConcretePhaseDao().getConcretePhase(this.concretePhaseId);
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
//	 FIXME getConcretePhaseService() ,ConcretePhaseService et concretePhaseService a faire
	public PhaseService getPhaseService() {
		return phaseService;
	}
//	 FIXME setConcretePhaseService, ConcretePhaseService et concretePhaseService a faire
	public void setPhaseService(PhaseService phaseService) {
		this.phaseService = phaseService;
	}

}