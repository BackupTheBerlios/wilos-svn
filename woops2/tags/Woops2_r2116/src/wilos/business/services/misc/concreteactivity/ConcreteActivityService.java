package wilos.business.services.misc.concreteactivity;

import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;

public class ConcreteActivityService {

	private ConcreteActivityDao concreteActivityDao;

	public ConcreteActivity getConcreteActivity(String _concreteActivityId) {
		return this.concreteActivityDao.getConcreteActivity(_concreteActivityId);
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
