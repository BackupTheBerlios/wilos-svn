
package woops2.business.activity ;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import woops2.hibernate.activity.ActivityDao;
import woops2.model.activity.Activity;
import woops2.model.breakdownelement.BreakdownElement;

/**
 * ActivityManager is a transactional class, that manage operations about activity, requested by web pages (activity.jsp &
 * activityform.jsp)
 * 
 * @author garwind
 * @author deder.
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ActivityManager {

	private ActivityDao activityDao ;

	/**
	 * Return activities list
	 * 
	 * @return
	 */
	public Set<Activity> getActivitiesList() {
		return this.activityDao.getAllActivities() ;
	}
	
	public Activity getActivityFromPrefix(String _prefix) {
		return this.activityDao.getActivityFromPrefix(_prefix) ;
	}
	
	public void Test(){
		Activity a = activityDao.getActivityFromPrefix("test");
		List<BreakdownElement> liste = new ArrayList<BreakdownElement>(a.getBreakDownElements());
		System.out.println("liste size = "+liste.size());
		for (BreakdownElement b : liste){
			System.out.println("b="+b);
		}
	}
	
	/**
	 * Save activity
	 * 
	 * @param _activity
	 */
	public void saveActivity(Activity _activity) {
		activityDao.saveOrUpdateActivity(_activity) ;
	}

	/**
	 * Getter of activityDao.
	 * 
	 * @return the activityDao.
	 */
	public ActivityDao getActivityDao() {
		return this.activityDao ;
	}

	/**
	 * Setter of activityDao.
	 * 
	 * @param _activityDao
	 *            The activityDao to set.
	 */
	public void setActivityDao(ActivityDao _activityDao) {
		this.activityDao = _activityDao ;
	}
}
