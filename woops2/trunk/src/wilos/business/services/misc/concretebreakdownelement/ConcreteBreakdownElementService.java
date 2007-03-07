package wilos.business.services.misc.concretebreakdownelement;

import java.util.List;

import wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;

/**
 * 
 * @author Padawan
 * 
 */
public class ConcreteBreakdownElementService {

	ConcreteBreakdownElementDao concreteBreakdownElementDao;

	/**
	 * Return the list of Concrete Breakdown Elements which has the same
	 * projectId than the parameter
	 * 
	 * @param _projectId
	 * @return list of Concrete Breakdown Elements which has the same projectId
	 */
	public List<ConcreteBreakdownElement> getAllConcreteBreakdownElementsFromProject(
			String _projectId) {
		return this.concreteBreakdownElementDao.getAllConcreteBreakdownElementsFromProject(_projectId);
	}

	/**
	 * 
	 * @param _concreteActivity
	 */
	public void saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(
			ConcreteActivity _concreteActivity) {
		for (ConcreteBreakdownElement cbde : _concreteActivity
				.getConcreteBreakdownElements()) {
			this.concreteBreakdownElementDao
					.saveOrUpdateConcreteBreakdownElement(cbde);
		}
	}

	/**
	 * @return the concreteBreakdownElementDao
	 */
	public ConcreteBreakdownElementDao getConcreteBreakdownElementDao() {
		return concreteBreakdownElementDao;
	}

	/**
	 * Setter of concreteBreakdownElementDao.
	 * 
	 * @param concreteBreakdownElementDao
	 *            The concreteBreakdownElementDao to set.
	 */
	public void setConcreteBreakdownElementDao(
			ConcreteBreakdownElementDao concreteBreakdownElementDao) {
		this.concreteBreakdownElementDao = concreteBreakdownElementDao;
	}
}
