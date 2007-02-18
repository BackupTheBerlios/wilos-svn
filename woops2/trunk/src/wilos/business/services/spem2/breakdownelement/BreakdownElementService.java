
package wilos.business.services.spem2.breakdownelement ;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.iteration.IterationService;
import wilos.business.services.spem2.phase.PhaseService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * BreakdownElement is a transactional class that manages operations about breakdownelement,
 * @author Soosuske
 *
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
@ SuppressWarnings("unused")
public class BreakdownElementService {

	private ConcreteBreakdownElementDao concreteBreakdownElementDao ;
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<Activity> getSuperActivities(BreakdownElement _bde) {

		Set<Activity> tmp = new HashSet<Activity>();
		for (Activity act : _bde.getSuperActivities()) {
			tmp.add(act);
		}
		return tmp;
	}

	/**
	 * Getter of concreteBreakdownElementDao
	 *
	 * @return the concreteBreakdownElementDao
	 */
	public ConcreteBreakdownElementDao getConcreteBreakdownElementDao() {
		return concreteBreakdownElementDao;
	}

	/**
	 * Setter of concreteBreakdownElementDao
	 *
	 * @param concreteBreakdownElementDao the concreteBreakdownElementDao to set
	 */
	public void setConcreteBreakdownElementDao(
			ConcreteBreakdownElementDao concreteBreakdownElementDao) {
		this.concreteBreakdownElementDao = concreteBreakdownElementDao;
	}

}
