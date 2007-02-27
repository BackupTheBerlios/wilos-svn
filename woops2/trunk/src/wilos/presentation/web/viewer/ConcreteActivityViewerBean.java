package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;

public class ConcreteActivityViewerBean extends ViewerBean {

	private ConcreteActivity concreteActivity = null;

	private ConcreteActivityService concreteActivityService;

	public void changeConcreteName() {
		this.concreteActivityService
				.saveConcreteActivity(this.concreteActivity);

		// Refresh the treebean.
		super.refreshProjectTree();
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
		super.getConcreteBreakdownElementService()
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(
						this.concreteActivity);

		// Refresh the treebean.
		super.refreshProjectTree();
	}

	public ConcreteActivity getConcreteActivity() {
		return concreteActivity;
	}

	public void setConcreteActivity(ConcreteActivity _concreteActivity) {
		this.concreteActivity = _concreteActivity;
	}

	public ConcreteActivityService getConcreteActivityService() {
		return this.concreteActivityService;
	}

	public void setConcreteActivityService(
			ConcreteActivityService _concreteActivityService) {
		this.concreteActivityService = _concreteActivityService;
	}
}
