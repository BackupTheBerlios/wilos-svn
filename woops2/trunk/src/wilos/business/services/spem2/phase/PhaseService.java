package wilos.business.services.spem2.phase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.iteration.IterationService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.misc.concretephase.ConcretePhaseDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
/**
 * PhaseManager is a transactional class, that manages operations about phase
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PhaseService {

	private ConcretePhaseDao concretePhaseDao;
	
	private IterationService iterationService;
	
	private ActivityService activityService;
	
	private RoleDescriptorService roleDescriptorService;
	
	private TaskDescriptorService taskDescriptorService;

	/**
	 * Instanciates a phase for a project
	 * @param _project project for which the Phase shall be instanciated
	 * @param _phase phase to instanciates
	 */
	public void phaseInstanciation (Project _project, Phase _phase, ConcreteActivity _cact) {

		ConcretePhase cp = new ConcretePhase();
		
		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_phase.getBreakdownElements());

		if (_phase.getPresentationName() == null)
			cp.setConcreteName(_phase.getName()) ;
		else
			cp.setConcreteName(_phase.getPresentationName());

		cp.addPhase(_phase);
		cp.setProject(_project);
		cp.addSuperConcreteActivity(_cact);

		this.concretePhaseDao.saveOrUpdateConcretePhase(cp);
		System.out.println("### ConcretePhase vide sauve");
		
		for (BreakdownElement bde : bdes ) {
			if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				this.phaseInstanciation(_project, ph, cp);
			} else {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					this.iterationService.iterationInstanciation(_project, it, cp);
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						this.activityService.activityInstanciation(_project, act, cp);
					} else {
						if (bde instanceof RoleDescriptor) {
							RoleDescriptor rd = (RoleDescriptor) bde;
							this.roleDescriptorService.roleDescriptorInstanciation(_project, rd, cp);
						} else {
							TaskDescriptor td = (TaskDescriptor) bde;
							this.taskDescriptorService.taskDescriptorInstanciation(_project, td, cp);
						}
					}
				}
			}
		}
		
		this.concretePhaseDao.saveOrUpdateConcretePhase(cp);
		System.out.println("### ConcretePhase update");
		
	}

	/**
	 * Getter of concretePhaseDao
	 *
	 * @return the concretePhaseDao
	 */
	public ConcretePhaseDao getConcretePhaseDao() {
		return concretePhaseDao;
	}

	/**
	 * Setter of concretePhaseDao
	 *
	 * @param concretePhaseDao the concretePhaseDao to set
	 */
	public void setConcretePhaseDao(ConcretePhaseDao concretePhaseDao) {
		this.concretePhaseDao = concretePhaseDao;
	}

	/**
	 * @return the iterationService
	 */
	public IterationService getIterationService() {
		return iterationService;
	}

	/**
	 * @param iterationService the iterationService to set
	 */
	public void setIterationService(IterationService iterationService) {
		this.iterationService = iterationService;
	}

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
