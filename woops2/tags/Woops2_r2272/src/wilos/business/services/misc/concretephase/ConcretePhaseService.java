package wilos.business.services.misc.concretephase;

import wilos.hibernate.misc.concretephase.ConcretePhaseDao;
import wilos.model.misc.concretephase.ConcretePhase;

public class ConcretePhaseService {

	private ConcretePhaseDao concretePhaseDao;

	public ConcretePhase getConcretePhase(String _concretePhaseId) {
		return this.getConcretePhaseDao().getConcretePhase(_concretePhaseId);
	}

	/**
	 * @return the concretePhaseDao
	 */
	public ConcretePhaseDao getConcretePhaseDao() {
		return concretePhaseDao;
	}

	/**
	 * @param concretePhaseDao the concretePhaseDao to set
	 */
	public void setConcretePhaseDao(ConcretePhaseDao concretePhaseDao) {
		this.concretePhaseDao = concretePhaseDao;
	}

}
