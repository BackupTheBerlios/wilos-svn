package wilos.presentation.web.viewer;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.spem2.activity.Activity;

public class ConcreteActivityViewerBean {
	
	private ConcreteActivity concreteActivity = null;
	
	private ActivityService activityService;
	
	private String concreteActivityId = "";
	
	public void buildConcreteActivity() {
		this.concreteActivity = new ConcreteActivity();
		if (!(this.concreteActivityId.equals("")) || this.concreteActivityId != null) {
			// FIXME maj d'ActivityService => getConcreteActivityDao
			this.concreteActivity = this.activityService.getConcreteActivityDao().getConcreteActivity(this.concreteActivityId);				
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

	public void setActivityId(String _activityId) {
		this.concreteActivityId = _activityId;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService _activityService) {
		this.activityService = _activityService;
	}
	
	
}
