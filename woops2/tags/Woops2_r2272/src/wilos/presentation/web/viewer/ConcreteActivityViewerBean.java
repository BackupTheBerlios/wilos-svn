package wilos.presentation.web.viewer;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.model.misc.concreteactivity.ConcreteActivity;

public class ConcreteActivityViewerBean {

	private ConcreteActivity concreteActivity = null;

	private ConcreteActivityService concreteActivityService;

	private String concreteActivityId = "";

	public void buildConcreteActivity() {
		this.concreteActivity = new ConcreteActivity();
		if (!(this.concreteActivityId.equals("")) || this.concreteActivityId != null) {
			this.concreteActivity = this.concreteActivityService.getConcreteActivity(this.concreteActivityId);
		}
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


}
