package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.business.services.misc.concretebreakdownelement.ConcreteBreakdownElementService;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.presentation.web.tree.TreeBean;

public class ConcreteActivityViewerBean {

	private ConcreteActivity concreteActivity = null;

	private ConcreteActivityService concreteActivityService;

	private ConcreteBreakdownElementService concreteBreakdownElementService;

	private String concreteActivityId = "";

	public void buildConcreteActivity() {
		this.concreteActivity = new ConcreteActivity();
		if (!(this.concreteActivityId.equals("")) || this.concreteActivityId != null) {
			this.concreteActivity = this.concreteActivityService.getConcreteActivity(this.concreteActivityId);
		}
	}

	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsList() {
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>();
		list.addAll(this.concreteActivity.getConcreteBreakdownElements());

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
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(this.concreteActivity);

		// Reload the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}

	public ConcreteActivity getConcreteActivity() {
		return concreteActivity;
	}

	public void setConcreteActivity(ConcreteActivity _concreteActivity) {
		this.concreteActivity = _concreteActivity;
	}

	public String getConcreteActivityId() {
		return concreteActivityId;
	}

	public void setConcreteActivityId(String _concreteActivityId) {
		this.concreteActivityId = _concreteActivityId;
	}

	public ConcreteActivityService getConcreteActivityService() {
		return this.concreteActivityService;
	}

	public void setConcreteActivityService(ConcreteActivityService _concreteActivityService) {
		this.concreteActivityService = _concreteActivityService;
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
