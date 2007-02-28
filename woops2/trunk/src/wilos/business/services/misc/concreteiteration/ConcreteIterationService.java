package wilos.business.services.misc.concreteiteration;

import wilos.hibernate.misc.concreteiteration.ConcreteIterationDao;
import wilos.model.misc.concreteiteration.ConcreteIteration;

/**
 * 
 * @author Padawan
 *
 */
public class ConcreteIterationService {

	public ConcreteIterationDao concreteIterationDao;

	/**
	 * 
	 * @param _concreteIterationId
	 * @return 
	 */
	public ConcreteIteration getConcreteIteration(String _concreteIterationId) {
		return this.getConcreteIterationDao().getConcreteIteration(
				_concreteIterationId);
	}

	/**
	 * 
	 * @param _concreteIteration
	 */
	public void saveConcreteIteration(ConcreteIteration _concreteIteration) {
		this.concreteIterationDao.saveOrUpdateConcreteIteration(_concreteIteration);
	}

	/**
	 * @return the concreteIterationDao
	 */
	public ConcreteIterationDao getConcreteIterationDao() {
		return concreteIterationDao;
	}

	/**
	 * @param concreteIterationDao
	 *            the concreteIterationDao to set
	 */
	public void setConcreteIterationDao(
			ConcreteIterationDao concreteIterationDao) {
		this.concreteIterationDao = concreteIterationDao;
	}
}
