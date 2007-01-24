package wilos.presentation.web.viewer;

import wilos.business.services.spem2.phase.PhaseService;
import wilos.model.spem2.phase.Phase;

public class PhaseViewerBean {

	private Phase phase;

	private PhaseService phaseService;

	private String phaseId = "";

	public void buildPhaseModel() {
		this.phase = new Phase();
		if (!(phaseId.equals("")) || phaseId != null) {
			this.phase = this.phaseService.getPhaseDao().getPhase(this.phaseId);
		}
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}

	public PhaseService getPhaseService() {
		return phaseService;
	}

	public void setPhaseService(PhaseService phaseService) {
		this.phaseService = phaseService;
	}

}