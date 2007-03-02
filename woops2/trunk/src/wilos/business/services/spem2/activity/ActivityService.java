package wilos.business.services.spem2.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.misc.concreteactivity.ConcreteActivityDao;
import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * ActivityManager is a transactional class, that manages operations about activity, requested by web pages (activity.jsp &
 * activityform.jsp)
 *
 * @author garwind
 * @author deder.
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ActivityService {

	private ActivityDao activityDao;

	private ConcreteActivityDao concreteActivityDao;

	/*private PhaseService phaseService;

	private IterationService iterationService;*/

	private RoleDescriptorService roleDescriptorService;

	private TaskDescriptorService taskDescriptorService;

	/**
	 * Instanciates an activity for a project
	 * @param _project project for which the activity shall be instanciated
	 * @param _phase activity to instanciate
	 */
	public void activityInstanciation(Project _project, Activity _activity, ConcreteActivity _cact) {

		ConcreteActivity cact = new ConcreteActivity();

		List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
		bdes.addAll(_activity.getBreakdownElements());

		if (_activity.getPresentationName() == null)
			cact.setConcreteName(_activity.getName());
		else
			cact.setConcreteName(_activity.getPresentationName());

		cact.addActivity(_activity);
		cact.setProject(_project);
		cact.addSuperConcreteActivity(_cact);

		this.concreteActivityDao.saveOrUpdateConcreteActivity(cact);
		System.out.println("### ConcreteActivity vide sauve");

		for (BreakdownElement bde : bdes ) {
			/*if (bde instanceof Phase) {
				Phase ph = (Phase) bde;
				tmp.add(this.phaseService.phaseInstanciation(_project, ph));
			} else {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					tmp.add(this.iterationService.iterationInstanciation(_project, it));
				} else {
					*/if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						this.activityInstanciation(_project, act, cact);
					} else {
						if (bde instanceof RoleDescriptor) {
							RoleDescriptor rd = (RoleDescriptor) bde;
							this.roleDescriptorService.roleDescriptorInstanciation(_project, rd, cact);
						} else {
							TaskDescriptor td = (TaskDescriptor) bde;
							this.taskDescriptorService.taskDescriptorInstanciation(_project, td, cact);
						}
					//}
				//}
			}
		}

		this.concreteActivityDao.saveOrUpdateConcreteActivity(cact);
		System.out.println("### Activity update");

	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	public SortedSet<BreakdownElement> getBreakdownElements(Activity _act) {
		
		SortedSet<BreakdownElement> tmp = new TreeSet<BreakdownElement>();
		
		this.activityDao.getSessionFactory().getCurrentSession().saveOrUpdate(_act);
		
		for (BreakdownElement bde : _act.getBreakdownElements()) {
			tmp.add(bde);
		}
		return tmp;
	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true)
	public Set<Guidance> getGuidances(Activity _act) {

		Set<Guidance> tmp = new HashSet<Guidance>();
		for (Guidance g : _act.getGuidances()) {
			tmp.add(g);
		}
		return tmp;
	}
	
	/**
	 * 
	 * Getter of project
	 * 
	 * @param _id
	 * @return
	 */
	@ Transactional (readOnly = true)
	public Activity getActivity(String _id) {
		return this.activityDao.getActivity(_id) ;
	}

	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true)
	public Set<BreakdownElement> getInstanciableBreakdownElements(Activity _act) {

		Set<BreakdownElement> tmp = new HashSet<BreakdownElement>();
		for (BreakdownElement bde : _act.getBreakdownElements()) {
			tmp.add(bde);
		}
		return tmp;
	}
	
	/**
	 * Return activities list
	 *
	 * @return List of activities
	 */
	@Transactional(readOnly = true)
	public List<Activity> getActivitiesList() {
		return this.activityDao.getAllActivities();
	}
	
	/**
	 * 
	 * @param _guid
	 * @return
	 */
	@ Transactional (readOnly = true)
	public Activity getActivityFromGuid(String _guid) {
		return this.activityDao.getActivityFromGuid(_guid);
	}

	/**
	 * Save activity
	 *
	 * @param _activity the activity to save
	 */
	public void saveActivity(Activity _activity) {
		activityDao.saveOrUpdateActivity(_activity);
	}

	/**
	 * Getter of activityDao.
	 *
	 * @return the activityDao.
	 */
	public ActivityDao getActivityDao() {
		return this.activityDao;
	}

	/**
	 * Setter of activityDao.
	 *
	 * @param _activityDao
	 *            The activityDao to set.
	 */
	public void setActivityDao(ActivityDao _activityDao) {
		this.activityDao = _activityDao;
	}

	/**
	 * Getter of concreteActivityDao
	 *
	 * @return the concreteActivityDao
	 */
	public ConcreteActivityDao getConcreteActivityDao() {
		return concreteActivityDao;
	}

	/**
	 * Setter of concreteActivityDao
	 *
	 * @param concreteActivityDao
	 *            the concreteActivityDao to set
	 */
	public void setConcreteActivityDao(ConcreteActivityDao concreteActivityDao) {
		this.concreteActivityDao = concreteActivityDao;
	}

	/**
	 * @return the iterationService
	 */
	/*public IterationService getIterationService() {
		return iterationService;
	}*/

	/**
	 * @param iterationService the iterationService to set
	 */
	/*public void setIterationService(IterationService iterationService) {
		this.iterationService = iterationService;
	}*/

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
