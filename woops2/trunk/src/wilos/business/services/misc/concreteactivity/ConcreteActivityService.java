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
	 * 
	 * @param _concreteActivity
	 */
	public void saveConcreteActivity(ConcreteActivity _concreteActivity) {
		this.concreteActivityDao.saveOrUpdateConcreteActivity(_concreteActivity);
	}
	
	/**
	 * 
	 * @param _concreteActivityId the id of the concreteActivity asked
	 * @return the ConcreteActivity which has the same id
	 */
	public ConcreteActivity getConcreteActivity(String _concreteActivityId) {
		return this.concreteActivityDao.getConcreteActivity(_concreteActivityId);
	}

	/**
	 * 
	 * @return the list of all the concreteActivities
	 */
	public List<ConcreteActivity> getAllConcreteActivities() {
		return this.concreteActivityDao.getAllConcreteActivities();
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
