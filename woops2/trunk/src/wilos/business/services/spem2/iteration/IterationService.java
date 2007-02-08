package wilos.business.services.spem2.iteration;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreteiteration.ConcreteIterationDao;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.project.Project;
import wilos.model.spem2.iteration.Iteration;
/**
 * IterationManager is a transactional class, that manages operations about Iteration
 *
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class IterationService {

	private ConcreteIterationDao concreteIterationDao;

	/**
	 * Instanciates an iteration for a project
	 * @param _project project for which the iteration shall be instanciated
	 * @param _phase iteration to instanciates
	 */
	public void iterationInstanciation (Project _project, Iteration _iteration) {

		ConcreteIteration ci = new ConcreteIteration();

		if (_iteration.getPresentationName() == null)
			ci.setConcreteName(_iteration.getName()) ;
		else
			ci.setConcreteName(_iteration.getPresentationName());

		ci.addIteration(_iteration);
		ci.setProject(_project);

		this.concreteIterationDao.saveOrUpdateConcreteIteration(ci);
	}

	/**
	 * Getter of concreteIterationDao
	 *
	 * @return the concreteIterationDao
	 */
	public ConcreteIterationDao getConcreteIterationDao() {
		return concreteIterationDao;
	}

	/**
	 * Setter of concreteIterationDao
	 *
	 * @param concreteIterationDao the concreteIterationDao to set
	 */
	public void setConcreteIterationDao(ConcreteIterationDao concreteIterationDao) {
		this.concreteIterationDao = concreteIterationDao;
	}




}
