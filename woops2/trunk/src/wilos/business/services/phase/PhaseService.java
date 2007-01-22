package wilos.business.services.phase;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.phase.PhaseDao;
import wilos.model.spem2.breakdownelement.BreakdownElement;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PhaseService {
	
	private PhaseDao phaseDao;

	public PhaseDao getPhaseDao() {
		return phaseDao;
	}

	public void setPhaseDao(PhaseDao phaseDao) {
		this.phaseDao = phaseDao;
	}

	public Set<BreakdownElement> getBreakdownElementsFromPhase(String _phaseId) {
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
		bdes.addAll(this.phaseDao.getBreakdownElementsFromPhase(_phaseId));
		return bdes;
	}

}
