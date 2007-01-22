package wilos.business.services.phase;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.activity.ActivityService;
import wilos.business.services.iteration.IterationService;
import wilos.business.services.task.TaskDescriptorService;
import wilos.hibernate.spem2.phase.PhaseDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PhaseService {
	
	private PhaseDao phaseDao;
	
	private IterationService iterationService;

	private ActivityService activityService;
	
	private TaskDescriptorService taskDescriptorService;

	public Set<BreakdownElement> getBreakdownElementsFromPhase(String _phaseId) {
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
		bdes.addAll(this.phaseDao.getBreakdownElementsFromPhase(_phaseId));
		
		for (BreakdownElement bde : bdes) {
			if (bde instanceof Iteration) {
				Iteration it = (Iteration) bde;
				it.addAllBreakdownElements(this.iterationService.getBreakdownElementsFromIteration(it.getId()));
			} /*else {
				if (bde instanceof Activity) {
					Activity act = (Activity) bde;
					this.activityService.getEntirePhase(act);
				} else {
					if (bde instanceof TaskDescriptor) {
						TaskDescriptor td = (TaskDescriptor) bde;
						this.taskDescriptorService.getEntirePhase(td);
					}
				}
			}*/
		}
		
		return bdes;
	}
	
	public PhaseDao getPhaseDao() {
		return phaseDao;
	}

	public void setPhaseDao(PhaseDao phaseDao) {
		this.phaseDao = phaseDao;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

	public IterationService getIterationService() {
		return iterationService;
	}

	public void setIterationService(IterationService iterationService) {
		this.iterationService = iterationService;
	}

	public TaskDescriptorService getTaskDescriptorService() {
		return taskDescriptorService;
	}

	public void setTaskDescriptorService(TaskDescriptorService taskDescriptorService) {
		this.taskDescriptorService = taskDescriptorService;
	}

}
