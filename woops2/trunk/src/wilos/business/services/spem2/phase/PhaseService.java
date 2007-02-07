package wilos.business.services.spem2.phase;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concretephase.ConcretePhaseDao;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.project.Project;
import wilos.model.spem2.phase.Phase;
/**
 * PhaseManager is a transactional class, that manages operations about phase
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PhaseService {

	private ConcretePhaseDao concretePhaseDao;

	/**
	 * Instanciates a phase for a project
	 * @param _project project for which the Phase shall be instanciated
	 * @param _phase phase to instanciates
	 */
	public void phaseInstanciation (Project _project, Phase _phase) {

		ConcretePhase cp = new ConcretePhase();

		if (_phase.getPresentationName() == null)
			cp.setConcreteName(_phase.getName()) ;
		else
			cp.setConcreteName(_phase.getPresentationName());
		
		cp.addPhase(_phase);

		this.concretePhaseDao.saveOrUpdateConcretePhase(cp);
	}

	/**
	 * Getter of concretePhaseDao
	 * 
	 * @return the concretePhaseDao
	 */
	public ConcretePhaseDao getConcretePhaseDao() {
		return concretePhaseDao;
	}

	/**
	 * Setter of concretePhaseDao
	 * 
	 * @param concretePhaseDao the concretePhaseDao to set
	 */
	public void setConcretePhaseDao(ConcretePhaseDao concretePhaseDao) {
		this.concretePhaseDao = concretePhaseDao;
	}
}
