package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import wilos.business.services.misc.concretebreakdownelement.ConcreteBreakdownElementService;
import wilos.business.services.misc.concretephase.ConcretePhaseService;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.presentation.web.tree.TreeBean;

public class ConcretePhaseViewerBean {

	private ConcretePhase concretePhase;

	private ConcretePhaseService concretePhaseService;

	private ConcreteBreakdownElementService concreteBreakdownElementService;

	private String concretePhaseId = "";

	public void buildConcretePhaseModel() {
		this.concretePhase = new ConcretePhase();
		if (!(concretePhaseId.equals("")) || concretePhaseId != null) {
			this.concretePhase = this.concretePhaseService
					.getConcretePhase(this.concretePhaseId);
		}
	}

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
		this.concreteBreakdownElementService
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(this.concretePhase);

		// Reload the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
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
	 * @param concretePhaseService
	 *            the concretePhaseService to set
	 */
	public void setConcretePhaseService(
			ConcretePhaseService concretePhaseService) {
		this.concretePhaseService = concretePhaseService;
	}

	/**
	 * @return the concreteBreakdownElementService
	 */
	public ConcreteBreakdownElementService getConcreteBreakdownElementService() {
		return concreteBreakdownElementService;
	}

	/**
	 * @param concreteBreakdownElementService
	 *            the concreteBreakdownElementService to set
	 */
	public void setConcreteBreakdownElementService(
			ConcreteBreakdownElementService concreteBreakdownElementService) {
		this.concreteBreakdownElementService = concreteBreakdownElementService;
	}

}