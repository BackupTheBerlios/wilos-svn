
package woops2.hibernate.activity ;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.StaleObjectStateException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import woops2.model.activity.Activity;

/**
 * ActivityDao manage requests from the system to store Acitivties to the database
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
		this.getHibernateTemplate().saveOrUpdate(_activity) ;
	}

	/**
	 * Return a list of activities
	 * 
	 * @return
	 */
	public Set<Activity> getAllActivities() {
		Set<Activity> loadAll = new HashSet<Activity>() ;
		loadAll.addAll(this.getHibernateTemplate().loadAll(Activity.class)) ;
		return loadAll ;
	}

	/**
	 * Return the activity which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public Activity getActivity(String _id) {
		return (Activity) this.getHibernateTemplate().get(Activity.class, _id) ;
	}
	
	/**
	 * Test function that return an activity with the given prefix
	 * If there are many activity with the same prefix, it returns the first
	 * @param _prefix
	 * @return
	 */
	public Activity getActivityFromPrefix(String _prefix) {
		List actvities = this.getHibernateTemplate().find("from Activity a where a.prefix=?",_prefix);
		if (actvities.size() > 0) return (Activity) actvities.get(0);
		else return null;
	}

	/**
	 * Delete the activity
	 * 
	 * @param _activity
	 */
	public void deleteActivity(Activity _activity) {
		try{
			this.getHibernateTemplate().delete(_activity) ;
		}
		catch(Exception sose){
			// Catch normally errors when we delete an unexisting activity into the db.
			logger.error("#### ERROR #### --- ActivityDao => deleteActivity : trying to delete unexisting object \n" + sose) ;
		}
	}
}
