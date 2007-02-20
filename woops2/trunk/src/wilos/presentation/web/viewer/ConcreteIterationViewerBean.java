package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import wilos.business.services.misc.concretebreakdownelement.ConcreteBreakdownElementService;
import wilos.business.services.misc.concreteiteration.ConcreteIterationService;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.presentation.web.tree.TreeBean;

public class ConcreteIterationViewerBean {

	private ConcreteIteration concreteIteration;

	private ConcreteIterationService concreteIterationService;

	private ConcreteBreakdownElementService concreteBreakdownElementService;

	private String concreteIterationId = "";

	public void buildConcreteIteration() {
		this.concreteIteration = new ConcreteIteration();
		if (!(this.concreteIterationId.equals("")) || this.concreteIterationId != null) {
			this.concreteIteration = this.concreteIterationService.getConcreteIteration(this.concreteIterationId);
		}
	}

	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsList() {
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>();
		list.addAll(this.concreteIteration.getConcreteBreakdownElements());

		// Filter to obtain only concreteworkbreakdownelement (without
		// concreterole).
		List<ConcreteBreakdownElement> cwbdes = new ArrayList<ConcreteBreakdownElement>();
		for (ConcreteBreakdownElement cbde : list)
			if (cbde instanceof ConcreteWorkBreakdownElement)
				cwbdes.add(cbde);

		return cwbdes;
	}

	public void saveIteration() {
		this.concreteBreakdownElementService
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(this.concreteIteration);

		// Reload the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}

	public ConcreteIteration getConcreteIteration() {
		return concreteIteration;
	}

	public void setConcreteIteration(ConcreteIteration _concreteIteration) {
		this.concreteIteration = _concreteIteration;
	}

	public String getConcreteIterationId() {
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

	/**
	 * @return the concreteBreakdownElementService
	 */
	public ConcreteBreakdownElementService getConcreteBreakdownElementService() {
		return concreteBreakdownElementService;
	}

	/**
	 * @param concreteBreakdownElementService the concreteBreakdownElementService to set
	 */
	public void setConcreteBreakdownElementService(
			ConcreteBreakdownElementService concreteBreakdownElementService) {
		this.concreteBreakdownElementService = concreteBreakdownElementService;
	}
}
