package wilos.presentation.web.viewer;

import wilos.business.services.misc.concreteiteration.ConcreteIterationService;
import wilos.model.misc.concreteiteration.ConcreteIteration;

public class ConcreteIterationViewerBean {

	private ConcreteIteration concreteIteration;

	private ConcreteIterationService concreteIterationService;

	private String concreteIterationId = "";

	public void buildConcreteIteration() {
		this.concreteIteration = new ConcreteIteration();
		if (!(this.concreteIterationId.equals("")) || this.concreteIterationId != null) {
			//FIXME maj d'IterationService => getConcreteIterationDao
			this.concreteIteration = this.concreteIterationService.getConcreteIteration(this.concreteIterationId);
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

	/**
	 * @return the concreteIterationService
	 */
	public ConcreteIterationService getConcreteIterationService() {
		return concreteIterationService;
	}

	/**
	 * @param concreteIterationService the concreteIterationService to set
	 */
	public void setConcreteIterationService(
			ConcreteIterationService concreteIterationService) {
		this.concreteIterationService = concreteIterationService;
	}
}
