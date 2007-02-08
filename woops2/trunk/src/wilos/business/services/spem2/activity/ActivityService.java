package wilos.business.services.spem2.activity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.breakdownelement.BreakdownElementService;
import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;

/**
 * ActivityManager is a transactional class, that manages operations about activity, requested by web pages (activity.jsp &
 * activityform.jsp)
 *
 * @author garwind
 * @author deder.
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ActivityService {

	private ActivityDao activityDao;

	private ConcreteActivityDao concreteActivityDao;

	private BreakdownElementService breakdownElementService = new BreakdownElementService();

	/**
	 * Instanciates an activity for a project
	 * @param _project project for which the activity shall be instanciated
	 * @param _phase activity to instanciate
	 */
	public void activityInstanciation(Project _project, Activity _activity) {

		ConcreteActivity cact = new ConcreteActivity();
		
		Set<ConcreteBreakdownElement> cbdes = new HashSet<ConcreteBreakdownElement>();

		if (_activity.getPresentationName() == null)
			cact.setConcreteName(_activity.getName());
		else
			cact.setConcreteName(_activity.getPresentationName());

		cact.addActivity(_activity);
		cact.setProject(_project);

		// instanciation of all contained BreakdownElements in the activity if correspondant ConcreteBreakdownElement doesn't already exist for each ones
		for (BreakdownElement bde : _activity.getBreakdownElements()) {
			// if the ConcreteBreakdownElement collection of bde is empty
			if (bde.getConcreteBreakdownElements().size() == 0) {
				// instanciation of a relative ConcreteBreakdownElement for bde which have for superConcreteActivity _act 
				cbdes.add(this.breakdownElementService.breakdownElementInstanciation(
						_project, bde/*, cact*/));
			}/* else {
				// if the ConcreteBreakdownElement collection of bde isn't empty
				boolean find = false;
				for (ConcreteBreakdownElement cbde : bde.getConcreteBreakdownElements()) {
					// if a relative ConcreteBreakdownElement already exists for the project _project
					if (cbde.getProject().getId() == _project.getId()) {
						find = true;
						break;
					}
				}
				if (!find) {
					this.breakdownElementService.breakdownElementInstanciation(
							_project, bde, cact);
				}
			}*/
		}
		
		cact.addAllConcreteBreakdownElements(cbdes);

		this.concreteActivityDao.saveOrUpdateConcreteActivity(cact);
		System.out.println("### ConcreteActivity sauve");
	}

	/**
	 * Return activities list
	 *
	 * @return List of activities
	 */
	@Transactional(readOnly = true)
	public List<Activity> getActivitiesList() {
		return this.activityDao.getAllActivities();
	}

	/**
	 * Save activity
	 *
	 * @param _activity the activity to save
	 */
	public void saveActivity(Activity _activity) {
		activityDao.saveOrUpdateActivity(_activity);
	}

	/**
	 * Getter of activityDao.
	 *
	 * @return the activityDao.
	 */
	public ActivityDao getActivityDao() {
		return this.activityDao;
	}

	/**
	 * Setter of activityDao.
	 *
	 * @param _activityDao
	 *            The activityDao to set.
	 */
	public void setActivityDao(ActivityDao _activityDao) {
		this.activityDao = _activityDao;
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
	 * @param concreteActivityDao
	 *            the concreteActivityDao to set
	 */
	public void setConcreteActivityDao(ConcreteActivityDao concreteActivityDao) {
		this.concreteActivityDao = concreteActivityDao;
	}
}
