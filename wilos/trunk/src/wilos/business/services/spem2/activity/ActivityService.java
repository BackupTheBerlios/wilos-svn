
package wilos.business.services.spem2.activity ;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.task.TaskDescriptor;

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
	
	/**
	 * Return activities list
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Activity> getActivitiesList() {
		return this.activityDao.getAllActivities() ;
	}
	
	public Set<BreakdownElement> getBreakdownElementsFromActivity(String _activityId) {
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
		bdes.addAll(this.activityDao.getBreakdownElementsFromActivity(_activityId));
				
		Set<BreakdownElement> allBdes = new HashSet<BreakdownElement>();
		allBdes.addAll(this.activityDao.getBreakdownElementsFromActivity(_activityId));
		
		for (BreakdownElement bde : bdes) {
			if (bde instanceof Activity) {
				Activity act = (Activity) bde;
				allBdes.addAll(this.getBreakdownElementsFromActivity(act.getId()));
			} else {
				if (bde instanceof TaskDescriptor) {
					TaskDescriptor td = (TaskDescriptor) bde;
					allBdes.add(td);
				}
			}
		}
		return allBdes;
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
