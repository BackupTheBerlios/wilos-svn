
package wilos.presentation.web ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.activity.ActivityService;
import wilos.model.spem2.activity.Activity;
import wilos.presentation.web.icefaces.tree.TreeBean;

/**
 * Managed-Bean link to activity.jsp and activitform.jsp
 * 
 * @author garwind
 * @author deder.
 */
public class ActivityBean {

	private List<Activity> activitiesList ;

	private Activity activity ;
	
	private ActivityService activityService;
	
	private String viewerMessage = "";
	
	private String test = "ceci est un test!";

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ActivityBean() {
		this.logger.debug("--- ActivityViewer --- == creating ..." + this) ;
		this.activity = new Activity() ;
	}

	/**
	 * Method for saving activity data from form
	 * 
	 * @return
	 */
	public String saveActivityAction() {
		String url = "activity" ;
		this.activityService.saveActivity(this.activity) ;
		this.viewerMessage = "Activity saved !";
		return url ;
	}
	
	public void saveActivityActionListener(ActionEvent e) {
		this.activityService.saveActivity(this.activity) ;
		this.viewerMessage = "Activity saved !";
	}
	
	public void resetActivityActionListener(ActionEvent e) {
		this.activity = new Activity();
		this.viewerMessage = "";
	}
	
	public void testTransactionActionListener(ActionEvent e){
		this.activityService.Test();
	}
	
	public void chooseProcessActionListener(ActionEvent e){
		FacesContext context = FacesContext.getCurrentInstance(); 
		Map map = context.getExternalContext().getRequestParameterMap();
		String processId = (String) map.get("process_id");
		TaskDescriptorBean taskDescriptorBean = (TaskDescriptorBean) context.getApplication()
		.getVariableResolver().resolveVariable(context,"TaskDescriptorBean");
		TreeBean treeBean = (TreeBean) context.getApplication()
		.getVariableResolver().resolveVariable(context,"TreeBean");
		taskDescriptorBean.setProcessId(processId);
		treeBean.setProcessId(processId);
	}
	
	/**
	 * Getter of activitiesList.
	 * 
	 * @return the activitiesList.
	 */
	public List<Activity> getActivitiesList() {
		this.activitiesList = new ArrayList<Activity>();
		activitiesList.addAll(this.activityService.getActivitiesList());
		return this.activitiesList ;
	}

	/**
	 * Setter of activitiesList.
	 * 
	 * @param _activitiesList
	 *            The activitiesList to set.
	 */
	public void setActivitiesList(List<Activity> _activitiesList) {
		this.activitiesList = _activitiesList ;
	}

	/**
	 * Getter of activity.
	 * 
	 * @return the activity.
	 */
	public Activity getActivity() {
		return this.activity ;
	}

	/**
	 * Setter of activity.
	 * 
	 * @param _activity
	 *            The activity to set.
	 */
	public void setActivity(Activity _activity) {
		this.logger.debug("### Activity = " + _activity + " ###") ;
		this.logger.debug("### prefix = " + _activity.getPrefix() + " ###") ;
		this.activity = _activity ;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getViewerMessage() {
		return viewerMessage;
	}

	public void setViewerMessage(String viewerMessage) {
		this.viewerMessage = viewerMessage;
	}
}