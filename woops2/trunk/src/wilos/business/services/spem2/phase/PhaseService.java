package wilos.business.services.spem2.phase;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concretephase.ConcretePhaseDao;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.project.Project;
import wilos.model.spem2.phase.Phase;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PhaseService {

	private ConcretePhaseDao concretePhaseDao;

	public void phaseInstanciation (Project _project, Phase _phase) {

		ConcretePhase cp = new ConcretePhase();

		cp.setConcreteName(_phase.getPresentationName());
		cp.addPhase(_phase);

		this.concretePhaseDao.saveOrUpdateConcretePhase(cp);
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
