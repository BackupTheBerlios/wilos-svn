package wilos.business.services.spem2.iteration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.iteration.IterationDao;
import wilos.model.spem2.breakdownelement.BreakdownElement;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class IterationService {
	
	private IterationDao iterationDao;
	
	public Set<BreakdownElement> getBreakdownElementsFromIteration(String _iterationId) {
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
		bdes.addAll(this.iterationDao.getBreakdownElementsFromIteration(_iterationId));
		return bdes;
	}

	public IterationDao getIterationDao() {
		return iterationDao;
	}

	public void setIterationDao(IterationDao iterationDao) {
		this.iterationDao = iterationDao;
	}
}
