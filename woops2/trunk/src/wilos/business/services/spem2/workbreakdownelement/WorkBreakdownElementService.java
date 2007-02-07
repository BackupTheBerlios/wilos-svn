
package wilos.business.services.spem2.workbreakdownelement ;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElementDao;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

/**
 *
 * @author nanawel
 *
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class WorkBreakdownElementService {

	private ConcreteWorkBreakdownElementDao concreteWorkBreakdownElementDao ;

	public void workBreakdownElementInstanciation (Project _project, WorkBreakdownElement _wbde) {

		ConcreteWorkBreakdownElement cwbe = new ConcreteWorkBreakdownElement();

		if (_wbde.getPresentationName() == null)
			cwbe.setConcreteName(_wbde.getName()) ;
		else
			cwbe.setConcreteName(_wbde.getPresentationName());
		
		cwbe.addBreakdownElement(_wbde);

		this.concreteWorkBreakdownElementDao.saveOrUpdateConcreteWorkBreakdownElement(cwbe);
	}

	/**
	 * @return the concreteBreakdownElementDao
	 */
	public ConcreteWorkBreakdownElementDao getConcreteWorkBreakdownElementDao() {
		return concreteWorkBreakdownElementDao;
	}

	/**
	 * @param concreteBreakdownElementDao the concreteBreakdownElementDao to set
	 */
	public void setConcreteWorkBreakdownElementDao(
			ConcreteWorkBreakdownElementDao concreteWorkBreakdownElementDao) {
		this.concreteWorkBreakdownElementDao = concreteWorkBreakdownElementDao;
	}

}
