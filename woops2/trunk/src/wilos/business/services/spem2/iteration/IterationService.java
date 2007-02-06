package wilos.business.services.spem2.iteration;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreteiteration.ConcreteIterationDao;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.project.Project;
import wilos.model.spem2.iteration.Iteration;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class IterationService {

	private ConcreteIterationDao concreteIterationDao;

	public void iterationInstanciation (Project _project, Iteration _iteration) {

		ConcreteIteration ci = new ConcreteIteration();

		ci.setConcreteName(_iteration.getPresentationName());
		ci.addIteration(_iteration);

		this.concreteIterationDao.saveOrUpdateConcreteIteration(ci);
	}

	/**
	 * @return the concreteIterationDao
	 */
	public ConcreteIterationDao getConcreteIterationDao() {
		return concreteIterationDao;
	}

	/**
	 * @param concreteIterationDao the concreteIterationDao to set
	 */
	public void setConcreteIterationDao(ConcreteIterationDao concreteIterationDao) {
		this.concreteIterationDao = concreteIterationDao;
	}




}
