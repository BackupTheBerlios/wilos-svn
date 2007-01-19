
package wilos.business.services.activity ;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;

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
	 * Test function that return activity from given prefix
	 * @param _prefix
	 * @return
	 */
	public Activity getActivityFromPrefix(String _prefix) {
		return this.activityDao.getActivityFromPrefix(_prefix) ;
	}
	
	/**
	 * Function that make some test on transactionnal lazy loadings
	 *
	 */
	public void Test(){
		Activity a = this.activityDao.getActivityFromPrefix("test");
		if (a == null){
			a = new Activity();
			a.setPrefix("test");
			this.activityDao.saveOrUpdateActivity(a);
			BreakdownElement b = new BreakdownElement();
			this.activityDao.getHibernateTemplate().save(b);
			a.getBreakDownElements().add(b);
			this.activityDao.saveOrUpdateActivity(a);
		}
		List<BreakdownElement> liste = new ArrayList<BreakdownElement>(a.getBreakDownElements());
		logger.debug("### ActivityManager - TEST ###  liste size = "+liste.size());
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