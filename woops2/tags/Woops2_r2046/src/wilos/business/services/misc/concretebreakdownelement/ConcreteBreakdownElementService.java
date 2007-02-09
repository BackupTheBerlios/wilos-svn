package wilos.business.services.misc.concretebreakdownelement;

import java.util.List;

import wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;

public class ConcreteBreakdownElementService {

	ConcreteBreakdownElementDao concreteBreakdownElementDao;

	public List<ConcreteBreakdownElement> getAllBreakdownElementsFromProject(String _projectId) {
		return this.concreteBreakdownElementDao.getAllConcreteBreakdownElements();
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
