package wilos.business.services.misc.concretebreakdownelement;

import java.util.List;

import wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.project.Project;

public class ConcreteBreakdownElementService {

	ConcreteBreakdownElementDao concreteBreakdownElementDao;

	public List<ConcreteBreakdownElement> getAllConcreteBreakdownElementsFromProject(String _projectId) {
		return this.concreteBreakdownElementDao.getAllConcreteBreakdownElements();
	}

	public void saveAllConcreteBreakdownElementsForAProject(Project _project){
		for(ConcreteBreakdownElement cbde : _project.getConcreteBreakdownElements()){
			this.concreteBreakdownElementDao.saveOrUpdateConcreteBreakdownElement(cbde);
		}
	}

	/**
	 * @return the concreteBreakdownElementDao
	 */
	public ConcreteBreakdownElementDao getConcreteBreakdownElementDao() {
		return concreteBreakdownElementDao ;
	}

	/**
	 * Setter of concreteBreakdownElementDao.
	 *
	 * @param concreteBreakdownElementDao The concreteBreakdownElementDao to set.
	 */
	public void setConcreteBreakdownElementDao(ConcreteBreakdownElementDao concreteBreakdownElementDao) {
		this.concreteBreakdownElementDao = concreteBreakdownElementDao ;
	}
}
