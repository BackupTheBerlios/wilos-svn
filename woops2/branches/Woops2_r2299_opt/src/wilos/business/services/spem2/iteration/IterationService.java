package wilos.business.services.spem2.iteration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.breakdownelement.BreakdownElementService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.misc.concreteiteration.ConcreteIterationDao;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
/**
 * IterationManager is a transactional class, that manages operations about Iteration
 *
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class IterationService {

	private ConcreteIterationDao concreteIterationDao;

	private BreakdownElementService breakdownElementService;

	//private PhaseService phaseService;

	private ActivityService activityService;

	private RoleDescriptorService roleDescriptorService;

	private TaskDescriptorService taskDescriptorService;

	/**
	 * Instanciates an iteration for a project
	 * @param _project project for which the iteration shall be instanciated
	 * @param _phase iteration to instanciates
	 */
	public ConcreteIteration iterationInstanciation (Project _project, Iteration _iteration) {

		ConcreteIteration ci = new ConcreteIteration();

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_iteration.getBreakdownElements());

		Set<ConcreteBreakdownElement> tmp = new HashSet<ConcreteBreakdownElement>();

		if (_iteration.getPresentationName() == null)
			ci.setConcreteName(_iteration.getName()) ;
		else
			ci.setConcreteName(_iteration.getPresentationName());

		ci.addIteration(_iteration);
		ci.setProject(_project);

		this.concreteIterationDao.saveOrUpdateConcreteIteration(ci);
		System.out.println("### ConcreteIteration vide sauve");

		for (BreakdownElement bde : bdes ) {
			/*if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				tmp.add(this.phaseService.phaseInstanciation(_project, ph));
			} else {*/
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					tmp.add(this.iterationInstanciation(_project, it));
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						tmp.add(this.activityService.activityInstanciation(_project, act));
					} else {
						if (bde instanceof RoleDescriptor) {
							RoleDescriptor rd = (RoleDescriptor) bde;
							tmp.add(this.roleDescriptorService.roleDescriptorInstanciation(_project, rd));
						} else {
							TaskDescriptor td = (TaskDescriptor) bde;
							tmp.add(this.taskDescriptorService.taskDescriptorInstanciation(_project, td));
						}
					}
				//}
			}
		}

		ci.addAllConcreteBreakdownElements(tmp);

		this.concreteIterationDao.saveOrUpdateConcreteIteration(ci);
		System.out.println("### ConcreteIteration update");

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

	/**
	 * @return the phaseService
	 */
	/*public PhaseService getPhaseService() {
		return phaseService;
	}*/

	/**
	 * @param phaseService the phaseService to set
	 */
	/*public void setPhaseService(PhaseService phaseService) {
		this.phaseService = phaseService;
	}*/

	/**
	 * @return the roleDescriptorService
	 */
	public RoleDescriptorService getRoleDescriptorService() {
		return roleDescriptorService;
	}

	/**
	 * @param roleDescriptorService the roleDescriptorService to set
	 */
	public void setRoleDescriptorService(RoleDescriptorService roleDescriptorService) {
		this.roleDescriptorService = roleDescriptorService;
	}

	/**
	 * @return the taskDescriptorService
	 */
	public TaskDescriptorService getTaskDescriptorService() {
		return taskDescriptorService;
	}

	/**
	 * @param taskDescriptorService the taskDescriptorService to set
	 */
	public void setTaskDescriptorService(TaskDescriptorService taskDescriptorService) {
		this.taskDescriptorService = taskDescriptorService;
	}




}
