package wilos.business.services.misc.concretephase;

import wilos.hibernate.misc.concretephase.ConcretePhaseDao;
import wilos.model.misc.concretephase.ConcretePhase;

/**
 * 
 * @author Padawan
 *
 */
public class ConcretePhaseService {

	private ConcretePhaseDao concretePhaseDao;

	/**
	 * 
	 * @param _concretePhaseId
	 * @return the phase which has the same concretePhaseId
	 */
	public ConcretePhase getConcretePhase(String _concretePhaseId) {
		return this.getConcretePhaseDao().getConcretePhase(_concretePhaseId);
	}

	/**
	 * 
	 * @param _concretePhase
	 */
	public void saveConcretePhase(ConcretePhase _concretePhase) {
		this.concretePhaseDao.saveOrUpdateConcretePhase(_concretePhase);
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
