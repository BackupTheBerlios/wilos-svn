package wilos.business.services.misc.concreteactivity;

import java.util.List;

import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
/**
 * 
 * 	@author Padawan
 *	
 */
public class ConcreteActivityService {

	private ConcreteActivityDao concreteActivityDao;
	
	/**
	 * Allows to save the concrete activity which passed in parameters
	 * @param _concreteActivity
	 */
	public void saveConcreteActivity(ConcreteActivity _concreteActivity) {
		this.concreteActivityDao.saveOrUpdateConcreteActivity(_concreteActivity);
	}
	
	/**
	 * Allows to get the concrete activity which has the same id than the parameter
	 * @param _concreteActivityId the id of the concreteActivity asked
	 * @return the ConcreteActivity which has the same id
	 */
	public ConcreteActivity getConcreteActivity(String _concreteActivityId) {
		return this.concreteActivityDao.getConcreteActivity(_concreteActivityId);
	}

	/**
	 * Return the list of all the Concrete Activities
	 * @return the list of all the concreteActivities
	 */
	public List<ConcreteActivity> getAllConcreteActivities() {
		return this.concreteActivityDao.getAllConcreteActivities();
	}

	/**
	 * Return the concrete activity Dao
	 * @return the concreteActivityDao
	 */
	public ConcreteActivityDao getConcreteActivityDao() {
		return concreteActivityDao;
	}

	/**
	 * Initialize the concreteActivityDao with the value in parameter 
	 * @param concreteActivityDao the concreteActivityDao to set
	 */
	public void setConcreteActivityDao(ConcreteActivityDao concreteActivityDao) {
		this.concreteActivityDao = concreteActivityDao;
	}
}
