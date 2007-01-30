package wilos.business.services.spem2.iteration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.hibernate.spem2.iteration.IterationDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.task.TaskDescriptor;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class IterationService {
	
	private IterationDao iterationDao;
	
	private ActivityService activityService;
	
	public Set<BreakdownElement> getBreakdownElementsFromIteration(String _iterationId) {
		Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
		bdes.addAll(this.iterationDao.getBreakdownElementsFromIteration(_iterationId));
		
		Set<BreakdownElement> allBdes = new HashSet<BreakdownElement>();
		allBdes.addAll(this.iterationDao.getBreakdownElementsFromIteration(_iterationId));
		
		for (BreakdownElement bde : bdes) {
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
		return allBdes;
}

	public IterationDao getIterationDao() {
		return iterationDao;
	}

	public void setIterationDao(IterationDao iterationDao) {
		this.iterationDao = iterationDao;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}
}
