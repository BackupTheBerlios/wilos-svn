package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import wilos.business.services.misc.concretephase.ConcretePhaseService;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.presentation.web.tree.TreeBean;

public class ConcretePhaseViewerBean extends ViewerBean{

	private ConcretePhase concretePhase;

	private ConcretePhaseService concretePhaseService;

	/* Manage the table for the visible elements in the tree. */

	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsList() {
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>();
		list.addAll(this.concretePhase.getConcreteBreakdownElements());

		// Filter to obtain only concreteworkbreakdownelement (without
		// concreterole).
		List<ConcreteBreakdownElement> cwbdes = new ArrayList<ConcreteBreakdownElement>();
		for (ConcreteBreakdownElement cbde : list)
			if (cbde instanceof ConcreteWorkBreakdownElement)
				cwbdes.add(cbde);

		return cwbdes;
	}

	public void savePhase() {
		super.getConcreteBreakdownElementService()
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(this.concretePhase);

		// Reload the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}

	/* Manage the concretename field editable. */

	public void changeConcreteName() {
		this.concretePhaseService.saveConcretePhase(this.concretePhase);

		// Refresh the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}

	/* Getters & Setters */

	public ConcretePhase getConcretePhase() {
		return this.concretePhase;
	}

	public void setConcretePhase(ConcretePhase _concretePhase) {
		this.concretePhase = _concretePhase;
	}

	/**
	 * @return the concretePhaseService
	 */
	public ConcretePhaseService getConcretePhaseService() {
		return concretePhaseService;
	}

	/**
	 * @param concretePhaseService
	 *            the concretePhaseService to set
	 */
	public void setConcretePhaseService(
			ConcretePhaseService concretePhaseService) {
		this.concretePhaseService = concretePhaseService;
	}
}