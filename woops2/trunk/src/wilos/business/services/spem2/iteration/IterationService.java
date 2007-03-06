package wilos.business.services.spem2.iteration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.breakdownelement.BreakdownElementService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.misc.concreteiteration.ConcreteIterationDao;
import wilos.hibernate.spem2.iteration.IterationDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.task.TaskDescriptor;
/**
 * IterationManager is a transactional class, that manages operations about Iteration
 *
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class IterationService {

	private ConcreteIterationDao concreteIterationDao;
	
	private IterationDao iterationDao;

	private BreakdownElementService breakdownElementService;

	//private PhaseService phaseService;

	private ActivityService activityService;
	
	private ConcreteActivityService concreteActivityService;

	private RoleDescriptorService roleDescriptorService;

	private TaskDescriptorService taskDescriptorService;
	
	public Set<ConcreteIteration> getAllConcreteIterations(Iteration _iteration) {
		Set<ConcreteIteration> tmp = new HashSet<ConcreteIteration>();
		this.iterationDao.getSessionFactory().getCurrentSession().saveOrUpdate(
				_iteration);
		for (ConcreteIteration bde : _iteration.getConcreteIterations()) {
			tmp.add(bde);
		}
		return tmp;
	}

	/**
	 * Instanciates an iteration for a project
	 * @param _project project for which the iteration shall be instanciated
	 * @param _phase iteration to instanciates
	 */
	public void iterationInstanciation (Project _project, Iteration _iteration, ConcreteActivity _cact, List<HashMap<String, Object>> _list, int _occ) {

		if (_occ > 0) {
			for (int i = 1;i <= _occ;i++) {
				
				ConcreteIteration ci = new ConcreteIteration();
		
				List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
				bdes.addAll(this.activityService.getInstanciableBreakdownElements(_iteration));
		
				if (_occ > 1) {
					if (_iteration.getPresentationName() == null)
						ci.setConcreteName(_iteration.getName() + "_" + (new Integer(i)).toString());
					else
						ci.setConcreteName(_iteration.getPresentationName() + "_" + (new Integer(i)).toString());
				} else {
					if (_iteration.getPresentationName() == null)
						ci.setConcreteName(_iteration.getName());
					else
						ci.setConcreteName(_iteration.getPresentationName());
				}
		
				ci.addIteration(_iteration);
				ci.setProject(_project);
				_cact.setConcreteBreakdownElements(this.concreteActivityService.getConcreteBreakdownElements(_cact));
				ci.addSuperConcreteActivity(_cact);
		
				this.concreteIterationDao.saveOrUpdateConcreteIteration(ci);
				System.out.println("### ConcreteIteration vide sauve");
		
				for (BreakdownElement bde : bdes ) {
					/*if (bde instanceof Phase) {
						Phase ph = (Phase) bde;
						tmp.add(this.phaseService.phaseInstanciation(_project, ph));
					} else {*/
						if (bde instanceof Iteration) {
							Iteration it = (Iteration) bde;
							int occ = this.giveNbOccurences(it.getId(), _list);
							this.iterationInstanciation(_project, it, ci, _list, occ);
						} else {
							if (bde instanceof Activity) {
								Activity act = (Activity) bde;
								int occ = this.giveNbOccurences(act.getId(), _list);
								this.activityService.activityInstanciation(_project, act, ci, _list, occ);
							} else {
								if (bde instanceof TaskDescriptor) {
									TaskDescriptor td = (TaskDescriptor) bde;
									int occ = this.giveNbOccurences(td.getId(), _list);
									this.taskDescriptorService.taskDescriptorInstanciation(_project, td, ci, occ);
								}/* else {
									RoleDescriptor rd = (RoleDescriptor) bde;
									int occ = this.giveNbOccurences(rd.getId(), _list);
									this.roleDescriptorService.roleDescriptorInstanciation(_project, rd, ci, occ);
								}*/
							}
						}
					//}
				}
	
				this.concreteIterationDao.saveOrUpdateConcreteIteration(ci);
				System.out.println("### ConcreteIteration update");
			}
		}
	}
	
	/**
	 * 
	 * @param _id
	 * @param list
	 * @return
	 */
	private int giveNbOccurences(String _id, List<HashMap<String, Object>> list) {
		
		int nb = 1;
		
		for (HashMap<String, Object> hashMap : list) {
			if (((String) hashMap.get("id")).equals(_id)) {
				nb = ((Integer) hashMap.get("nbOccurences")).intValue();
				break;
			}
		}
		
		return nb;
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
	 * @return the concreteActivityService
	 */
	public ConcreteActivityService getConcreteActivityService() {
		return this.concreteActivityService;
	}

	/**
	 * @param _concreteActivityService the concreteActivityService to set
	 */
	public void setConcreteActivityService(
			ConcreteActivityService _concreteActivityService) {
		this.concreteActivityService = _concreteActivityService;
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
	 * @return the iterationDao
	 */
	public IterationDao getIterationDao() {
		return iterationDao;
	}

	/**
	 * @param iterationDao the iterationDao to set
	 */
	public void setIterationDao(IterationDao iterationDao) {
		this.iterationDao = iterationDao;
	}




}
