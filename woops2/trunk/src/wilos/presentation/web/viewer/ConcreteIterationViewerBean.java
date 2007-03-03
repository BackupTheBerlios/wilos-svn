package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import wilos.business.services.misc.concreteiteration.ConcreteIterationService;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;

public class ConcreteIterationViewerBean extends ViewerBean {

	private ConcreteIteration concreteIteration;

	private ConcreteIterationService concreteIterationService;

	/* Manage the table for the visible elements in the tree. */

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
		super.getConcreteBreakdownElementService()
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(
						this.concreteIteration);

		// Reload the treebean.
		super.refreshProjectTree();
	}

	/* Manage the concretename field editable. */

	public void changeConcreteName() {
		this.concreteIterationService
				.saveConcreteIteration(this.concreteIteration);

		// Refresh the treebean.
		super.refreshProjectTree();
	}

	/* Getters & Setters */

	public ConcreteIteration getConcreteIteration() {
		return concreteIteration;
	}

	public void setConcreteIteration(ConcreteIteration _concreteIteration) {
		this.concreteIteration = _concreteIteration;
	}

	/**
	 * @return the concreteIterationService
	 */
	public ConcreteIterationService getConcreteIterationService() {
		return concreteIterationService;
	}

	/**
	 * @param concreteIterationService
	 *            the concreteIterationService to set
	 */
	public void setConcreteIterationService(
			ConcreteIterationService concreteIterationService) {
		this.concreteIterationService = concreteIterationService;
	}
}
