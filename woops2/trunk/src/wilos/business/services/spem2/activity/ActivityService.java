
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
 * ActivityManager is a transactional class, that manages operations about activity, requested by web pages (activity.jsp &
 * activityform.jsp)
 *
 * @author garwind
 * @author deder.
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ActivityService {

	private ActivityDao activityDao ;

	private ConcreteActivityDao concreteActivityDao;
	
	/**
	 * Instanciates an acticity for a project
	 * @param _project project for which the activity shall be instanciated
	 * @param _phase activity to instanciates
	 */
	public void activityInstanciation (Project _project, Activity _activity) {

		ConcreteActivity cact = new ConcreteActivity();

		if (_activity.getPresentationName() == null)
			cact.setConcreteName(_activity.getName()) ;
		else
			cact.setConcreteName(_activity.getPresentationName());
		
		cact.addActivity(_activity);

		this.concreteActivityDao.saveOrUpdateConcreteActivity(cact);
	}

	/**
	 * Returns activities list
	 *
	 * @return List of activities
	 */
	@Transactional(readOnly = true)
	public List<Activity> getActivitiesList() {
		return this.activityDao.getAllActivities() ;
	}

	/**
	 * Saves activity
	 *
	 * @param _activity the activity to save
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
	 * Getter of concreteActivityDao
	 * 
	 * @return the concreteActivityDao
	 */
	public ConcreteActivityDao getConcreteActivityDao() {
		return concreteActivityDao;
	}

	/**
	 * Setter of concreteActivityDao
	 * 
	 * @param concreteActivityDao the concreteActivityDao to set
	 */
	public void setConcreteActivityDao(ConcreteActivityDao concreteActivityDao) {
		this.concreteActivityDao = concreteActivityDao;
	}
}
