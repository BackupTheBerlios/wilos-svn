
package wilos.business.services.spem2.breakdownelement ;

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

	private PhaseService phaseService;

	private IterationService iterationService;

	private ActivityService activityService;

	private RoleDescriptorService roleDescriptorService;

	private TaskDescriptorService taskDescriptorService;

	/**
	 * Instanciates a BreakdownElement
	 * @param _project project for which the BreakdownElement shall be instanciated
	 * @param _bde BreakdownElement to instanciate
	 */
	/*public ConcreteBreakdownElement breakdownElementInstanciation (Project _project, BreakdownElement _bde, ConcreteActivity _superActivity) {

		ConcreteBreakdownElement cbde = null;

		if (_bde instanceof Phase) {
			Phase ph = (Phase) _bde;
			this.phaseService.phaseInstanciation(_project, ph);
		} else {
			if (_bde instanceof Iteration) {
				Iteration it = (Iteration) _bde;
				this.iterationService.iterationInstanciation(_project, it);
			} else {
				if (_bde instanceof Activity) {
					Activity act = (Activity) _bde;
					this.activityService.activityInstanciation(_project, act);
				} else {
					if (_bde instanceof RoleDescriptor) {
						RoleDescriptor rd = (RoleDescriptor) _bde;
						this.roleDescriptorService.roleDescriptorInstanciation(_project, rd, _superActivity);
					} else {
						TaskDescriptor td = (TaskDescriptor) _bde;
						this.taskDescriptorService.taskDescriptorInstanciation(_project, td, _superActivity);
					}
				}
			}
		}
		return cbde;
	}*/

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

	/**
	 * @return the activityService
	 */
	public ActivityService getActivityService() {
		return activityService;
	}

	/**
	 * @param activityService the activityService to set
	 */
	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

}
