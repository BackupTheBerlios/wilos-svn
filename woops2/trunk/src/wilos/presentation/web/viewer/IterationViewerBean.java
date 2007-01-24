package wilos.presentation.web.viewer;

import wilos.business.services.spem2.iteration.IterationService;
import wilos.model.spem2.iteration.Iteration;

public class IterationViewerBean {
	
	private Iteration iteration;
	
	private IterationService iterationService;
	
	private String iterationId = "";
	
	public void buildIteration() {
		this.iteration = new Iteration();
		if (!(this.iterationId.equals("")) || this.iterationId != null) {
			this.iteration = this.iterationService.getIterationDao().getIteration(this.iterationId);
		}
	}

	public Iteration getIteration() {
		return iteration;
	}

	public void setIteration(Iteration _iteration) {
		this.iteration = _iteration;
	}

	public String getIterationId() {
		return iterationId;
	}

	public void setIterationId(String _iterationId) {
		this.iterationId = _iterationId;
	}

	public IterationService getIterationService() {
		return iterationService;
	}

	public void setIterationService(IterationService _iterationService) {
		this.iterationService = _iterationService;
	}
}
