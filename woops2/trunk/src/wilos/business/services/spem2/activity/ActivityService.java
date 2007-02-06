
package wilos.business.services.spem2.activity ;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.project.Project;
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

	private ConcreteActivityDao concreteActivityDao;

	public void activityInstanciation (Project _project, Activity _activity) {

		ConcreteActivity cact = new ConcreteActivity();

		cact.setConcreteName(_activity.getPresentationName());
		cact.addActivity(_activity);

		this.concreteActivityDao.saveOrUpdateConcreteActivity(cact);
	}

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

	/**
	 * @return the concreteActivityDao
	 */
	public ConcreteActivityDao getConcreteActivityDao() {
		return concreteActivityDao;
	}

	/**
	 * @param concreteActivityDao the concreteActivityDao to set
	 */
	public void setConcreteActivityDao(ConcreteActivityDao concreteActivityDao) {
		this.concreteActivityDao = concreteActivityDao;
	}
}
