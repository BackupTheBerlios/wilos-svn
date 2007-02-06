package wilos.presentation.web.viewer;

import wilos.business.services.spem2.iteration.IterationService;
import wilos.model.misc.concreteiteration.ConcreteIteration;

public class ConcreteIterationViewerBean {

	private ConcreteIteration concreteIteration;

	private IterationService iterationService;

	private String concreteIterationId = "";

	public void buildConcreteIteration() {
		this.concreteIteration = new ConcreteIteration();
		if (!(this.concreteIterationId.equals("")) || this.concreteIterationId != null) {
			//FIXME maj d'IterationService => getConcreteIterationDao
			this.concreteIteration = this.iterationService.getConcreteIterationDao().getConcreteIteration(this.concreteIterationId);
		}
	}

	public ConcreteIteration getConcreteIteration() {
		return concreteIteration;
	}

	public void setConcreteIteration(ConcreteIteration _concreteIteration) {
		this.concreteIteration = _concreteIteration;
	}

	public String getIterationId() {
		return concreteIterationId;
	}

	public void setConcreteIterationId(String _concreteIterationId) {
		this.concreteIterationId = _concreteIterationId;
	}

	public IterationService getIterationService() {
		return iterationService;
	}

	public void setIterationService(IterationService _iterationService) {
		this.iterationService = _iterationService;
	}
}
