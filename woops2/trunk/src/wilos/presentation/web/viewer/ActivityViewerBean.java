package wilos.presentation.web.viewer;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.model.spem2.activity.Activity;

public class ActivityViewerBean {
	
	private Activity activity = null;
	
	private ActivityService activityService;
	
	private String activityId = "";
	
	public void buildActivity() {
		this.activity = new Activity();
		if (!(this.activityId.equals("")) || this.activityId != null) {
			this.activity = this.activityService.getActivityDao().getActivity(this.activityId);					
		}
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity _activity) {
		this.activity = _activity;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String _activityId) {
		this.activityId = _activityId;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService _activityService) {
		this.activityService = _activityService;
	}
	
	
}
