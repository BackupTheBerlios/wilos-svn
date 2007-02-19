package wilos.hibernate.spem2.activity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.utils.ExceptionManager;

/**
 * ActivityDao manage requests from the system to store Acitivties to the
 * database
 * 
 * @author garwind
 * @author deder
 */
public class ActivityDao extends HibernateDaoSupport {

	/**
	 * Save or update an activity
	 * 
	 * @param _activity
	 */
	public void saveOrUpdateActivity(Activity _activity) {
		try {
			this.getHibernateTemplate().saveOrUpdate(_activity);
		} catch (DataIntegrityViolationException _e) {
			ExceptionManager.getInstance()
					.manageDataIntegrityViolationException(
							this.getClass().getName(), "saveOrUpdateActivity",
							_e);
		} catch (ConstraintViolationException _ex) {
			ExceptionManager.getInstance().manageConstraintViolationException(
					this.getClass().getName(), "saveOrUpdateActivity", _ex);
		}
	}

	/**
	 * Return a list of activities
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Activity> getAllActivities() {
		this.getHibernateTemplate().setCacheQueries(true);
		List<Activity> loadAll = new ArrayList<Activity>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(Activity.class));
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getAllActivities", _e);
		}
		return loadAll;
	}

	/**
	 * Return the activity which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public Activity getActivity(String _id) {
		this.getHibernateTemplate().setCacheQueries(true);
		return (Activity) this.getHibernateTemplate().get(Activity.class, _id);
	}

	/**
	 * Test function that return an activity with the given prefix If there are
	 * many activity with the same prefix, it returns the first
	 * 
	 * @param _prefix
	 * @return
	 */
	public Activity getActivityFromPrefix(String _prefix) {
		List actvities = this.getHibernateTemplate().find(
				"from Activity a where a.prefix=?", _prefix);
		if (actvities.size() > 0)
			return (Activity) actvities.get(0);
		else
			return null;
	}

	/**
	 * Delete the activity
	 * 
	 * @param _activity
	 */
	public void deleteActivity(Activity _activity) {
		try {
			this.getHibernateTemplate().delete(_activity);
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "deleteActivity", _e);
		}
	}

	public List<BreakdownElement> getBreakdownElementsFromActivity(
			String _activityId) {
		List bdes = this
				.getHibernateTemplate()
				.find(
						"from BreakdownElement bde join bde.superActivities s where s.id=?",
						_activityId);
		List<BreakdownElement> listBdes = new ArrayList<BreakdownElement>();
		if (bdes.get(0) instanceof List) {
			for (Object o : (ArrayList) bdes.get(0)) {
				if (o instanceof BreakdownElement) {
					BreakdownElement bde = (BreakdownElement) o;
					listBdes.add(bde);
				}
			}
		}
		return listBdes;
	}
}
