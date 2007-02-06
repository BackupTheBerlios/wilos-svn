
package wilos.business.services.spem2.breakdownelement ;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.breakdownelement.BreakdownElement;

/**
 *
 * @author Soosuske
 *
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class BreakdownElementService {

	private ConcreteBreakdownElementDao concreteBreakdownElementDao ;

	public void breakdownElementInstanciation (Project _project, BreakdownElement _bde) {

		ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();

		crd.setConcreteName(_bde.getPresentationName());
		crd.addBreakdownElement(_bde);

		this.concreteBreakdownElementDao.saveOrUpdateConcreteBreakdownElement(crd);
	}

	/**
	 * @return the concreteBreakdownElementDao
	 */
	public ConcreteBreakdownElementDao getConcreteBreakdownElementDao() {
		return concreteBreakdownElementDao;
	}

	/**
	 * @param concreteBreakdownElementDao the concreteBreakdownElementDao to set
	 */
	public void setConcreteBreakdownElementDao(
			ConcreteBreakdownElementDao concreteBreakdownElementDao) {
		this.concreteBreakdownElementDao = concreteBreakdownElementDao;
	}

}
