package wilos.business.services.spem2.iteration;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.breakdownelement.BreakdownElementService;
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
	
	private BreakdownElementService breakdownElementService = new BreakdownElementService();

	/**
	 * Instanciates an iteration for a project
	 * @param _project project for which the iteration shall be instanciated
	 * @param _phase iteration to instanciates
	 */
	public ConcreteIteration iterationInstanciation (Project _project, Iteration _iteration) {

		ConcreteIteration ci = new ConcreteIteration();
		
		//Set<ConcreteBreakdownElement> cbdes = new HashSet<ConcreteBreakdownElement>();

		if (_iteration.getPresentationName() == null)
			ci.setConcreteName(_iteration.getName()) ;
		else
			ci.setConcreteName(_iteration.getPresentationName());

		ci.addIteration(_iteration);
		ci.setProject(_project);
		
		this.concreteIterationDao.saveOrUpdateConcreteIteration(ci);
		System.out.println("### ConcreteIteration vide sauve");
		
		return ci;
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

	/**
	 * @return the breakdownElementService
	 */
	public BreakdownElementService getBreakdownElementService() {
		return breakdownElementService;
	}

	/**
	 * @param breakdownElementService the breakdownElementService to set
	 */
	public void setBreakdownElementService(
			BreakdownElementService breakdownElementService) {
		this.breakdownElementService = breakdownElementService;
	}




}
