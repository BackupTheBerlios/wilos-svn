package wilos.business.services.misc.concreteiteration;

import wilos.hibernate.misc.concreteiteration.ConcreteIterationDao;
import wilos.model.misc.concreteiteration.ConcreteIteration;

public class ConcreteIterationService {

	public ConcreteIterationDao concreteIterationDao;

	public ConcreteIteration getConcreteIteration(String _concreteIterationId) {
		return this.getConcreteIterationDao().getConcreteIteration(
				_concreteIterationId);
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
