
package wilos.business.services.spem2.workbreakdownelement ;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElementDao;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkOrder;

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
		cwbe.setProject(_project);

		this.concreteWorkBreakdownElementDao.saveOrUpdateConcreteWorkBreakdownElement(cwbe);
	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<WorkOrder> getPredecessors(WorkBreakdownElement _wbde) {

		Set<WorkOrder> tmp = new HashSet<WorkOrder>();
		for (WorkOrder wo : _wbde.getPredecessors()) {
			tmp.add(wo);
		}
		return tmp;
	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<WorkOrder> getSuccessors(WorkBreakdownElement _wbde) {

		Set<WorkOrder> tmp = new HashSet<WorkOrder>();
		for (WorkOrder wo : _wbde.getSuccessors()) {
			tmp.add(wo);
		}
		return tmp;
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
