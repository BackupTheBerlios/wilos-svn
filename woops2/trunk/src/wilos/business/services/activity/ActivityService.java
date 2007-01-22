
package wilos.business.services.activity ;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.model.spem2.activity.Activity;

/**
 * ActivityManager is a transactional class, that manage operations about activity, requested by web pages (activity.jsp &
 * activityform.jsp)
 * 
 * @author garwind
 * @author deder.
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ActivityService {

	private ActivityDao activityDao ;
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/**
	 * Return activities list
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Activity> getActivitiesList() {
		return this.activityDao.getAllActivities() ;
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
