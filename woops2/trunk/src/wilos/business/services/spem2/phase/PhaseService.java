package wilos.business.services.spem2.phase;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.iteration.IterationService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.spem2.phase.PhaseDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.task.TaskDescriptor;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PhaseService {
	
	private PhaseDao phaseDao;
	
	private IterationService iterationService;

	private ActivityService activityService;
	
	private TaskDescriptorService taskDescriptorService;

	public Set<BreakdownElement> getBreakdownElementsFromPhase(Phase _ph) {
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
		bdes.addAll(_ph.getBreakDownElements());
		
		Set<BreakdownElement> allBdes = new HashSet<BreakdownElement>();
		allBdes.addAll(_ph.getBreakDownElements());
		
		for (BreakdownElement bde : bdes) {
			if (bde instanceof Iteration) {
				Iteration it = (Iteration) bde;
				allBdes.addAll(this.iterationService.getBreakdownElementsFromIteration(it.getId()));
			} else {
				if (bde instanceof Activity) {
					Activity act = (Activity) bde;
					allBdes.addAll(this.activityService.getBreakdownElementsFromActivity(act.getId()));
				} else {
					if (bde instanceof TaskDescriptor) {
						TaskDescriptor td = (TaskDescriptor) bde;
						allBdes.add(td);
					}
				}
			}
		}
		
		return allBdes;
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
