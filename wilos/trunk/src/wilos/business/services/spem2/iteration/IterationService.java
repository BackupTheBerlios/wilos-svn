/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

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
import wilos.hibernate.misc.concretephase.ConcretePhaseDao;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.spem2.iteration.IterationDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * IterationManager is a transactional class, that manages operations about
 * Iteration
 * 
 * @author Sebastien
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class IterationService {

	private ConcreteIterationDao concreteIterationDao;

	private IterationDao iterationDao;
	
	private ConcretePhaseDao concretePhaseDao;
	
	private ProjectDao projectDao;

	private BreakdownElementService breakdownElementService;

	// private PhaseService phaseService;

	private ActivityService activityService;

	private ConcreteActivityService concreteActivityService;

	private RoleDescriptorService roleDescriptorService;

	private TaskDescriptorService taskDescriptorService;

	public Set<ConcreteIteration> getAllConcreteIterations(Iteration _iteration) {
		Set<ConcreteIteration> tmp = new HashSet<ConcreteIteration>();
		this.iterationDao.getSessionFactory().getCurrentSession().saveOrUpdate(_iteration);
		for (ConcreteIteration bde : _iteration.getConcreteIterations()) {
			tmp.add(bde);
		}
		return tmp;
	}
	
	public Set<ConcreteIteration> getAllConcreteIterationsForAProject(Iteration _iteration, Project _project) {
		Set<ConcreteIteration> tmp = new HashSet<ConcreteIteration>();
		this.iterationDao.getSessionFactory().getCurrentSession().saveOrUpdate(_iteration);
		this.projectDao.getSessionFactory().getCurrentSession().saveOrUpdate(_project);
		for (ConcreteIteration cit : _iteration.getConcreteIterations()) {
			if (cit.getProject().equals(_project))
				tmp.add(cit);
		}
		return tmp;
	}

	/**
	 * Instanciates an iteration for a project
	 * 
	 * @param _project
	 *            project for which the iteration shall be instanciated
	 * @param _phase
	 *            iteration to instanciates
	 */
	public void iterationInstanciation(Project _project, Iteration _iteration, ConcreteActivity _cact,
			List<HashMap<String, Object>> _list, int _occ, boolean _isInstanciated) {

		if (_occ > 0) {
			this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().saveOrUpdate(_cact);
			if (_isInstanciated) {
				this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().refresh(_cact);
			}
			int nbCit = 0;
			for (ConcreteBreakdownElement tmp : _cact.getConcreteBreakdownElements()) {
				if (tmp instanceof ConcreteIteration) {
					if (((ConcreteIteration) tmp).getIteration().equals(_iteration)) {
						nbCit++;
					}
				}
			}
			for (int i = nbCit + 1; i <= nbCit + _occ; i++) {

				ConcreteIteration ci = new ConcreteIteration();

				List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
				bdes.addAll(this.activityService.getInstanciableBreakdownElements(_iteration));

				if (_iteration.getPresentationName().equals(""))
					ci.setConcreteName(_iteration.getName() + "#" + (new Integer(i)).toString());
				else
					ci.setConcreteName(_iteration.getPresentationName() + "#" + (new Integer(i)).toString());

				ci.addIteration(_iteration);
				ci.setProject(_project);
				_cact.setConcreteBreakdownElements(this.concreteActivityService.getConcreteBreakdownElements(_cact));
				ci.addSuperConcreteActivity(_cact);

				this.concreteIterationDao.saveOrUpdateConcreteIteration(ci);
				System.out.println("### ConcreteIteration vide sauve");

				for (BreakdownElement bde : bdes) {
					if (bde instanceof Iteration) {
						Iteration it = (Iteration) bde;
						int occ = this.giveNbOccurences(it.getId(), _list);
						this.iterationInstanciation(_project, it, ci, _list, occ, _isInstanciated);
					} else {
						if (bde instanceof Activity) {
							Activity act = (Activity) bde;
							int occ = this.giveNbOccurences(act.getId(), _list);
							this.activityService.activityInstanciation(_project, act, ci, _list, occ, _isInstanciated);
						} else {
							if (bde instanceof TaskDescriptor) {
								TaskDescriptor td = (TaskDescriptor) bde;
								int occ = this.giveNbOccurences(td.getId(), _list);
								this.taskDescriptorService.taskDescriptorInstanciation(_project, td, ci, occ, _isInstanciated);
							}
						}
					}
				}

				this.concreteIterationDao.saveOrUpdateConcreteIteration(ci);
				System.out.println("### ConcreteIteration update");
			}
		}
	}
	
	/**
	 * 
	 * @param _project
	 * @param _phase
	 * @param _cact
	 * @param _list
	 * @param _occ
	 * @param _isInstanciated
	 */
	public void iterationUpdate(Project _project, Iteration _it, Set<ConcreteActivity> _cacts, List<HashMap<String, Object>> _list, int _occ) {
		
		// one concretephase at least to insert in all attached concreteactivities of the parent of _phase
		if (_occ > 0) {
			for (ConcreteActivity tmp : _cacts) {
				this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().saveOrUpdate(tmp);
				this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().refresh(tmp);
				this.iterationInstanciation(_project, _it, tmp, _list, _occ, true);
				
				if (tmp instanceof Project) {
					Project pj = (Project) tmp;
					this.projectDao.saveOrUpdateProject(pj);
				} else {
					if (tmp instanceof ConcretePhase) {
						ConcretePhase cph = (ConcretePhase) tmp;
						this.concretePhaseDao.saveOrUpdateConcretePhase(cph);
					} else {
						if (tmp instanceof ConcreteIteration) {
							ConcreteIteration cit = (ConcreteIteration) tmp;
							this.concreteIterationDao.saveOrUpdateConcreteIteration(cit);
						}
					}
				}
			}
		} else {
			
			// diving in all the concreteBreakdownElements to looking for update
			Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
			bdes.addAll(this.activityService.getInstanciableBreakdownElements(_it));
			
			Set<ConcreteActivity> cacts = new HashSet<ConcreteActivity>();
			//FIXME idem phaseService
			cacts.addAll(this.getAllConcreteIterationsForAProject(_it, _project));
			
			for (BreakdownElement bde : bdes) {
				if (bde instanceof Iteration) {
					Iteration it = (Iteration) bde;
					int occ = this.giveNbOccurences(it.getId(), _list);
					this.iterationUpdate(_project, it, cacts, _list, occ);
				} else {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						int occ = this.giveNbOccurences(act.getId(), _list);
						this.activityService.activityUpdate(_project, act, cacts, _list, occ);
					} else {
						if (bde instanceof TaskDescriptor) {
							TaskDescriptor td = (TaskDescriptor) bde;
							int occ = this.giveNbOccurences(td.getId(), _list);
							this.taskDescriptorService.taskDescriptorUpdate(_project, td, cacts, occ);
						}
					}
				}
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
	 * @param concreteIterationDao
	 *            the concreteIterationDao to set
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
	 * @param breakdownElementService
	 *            the breakdownElementService to set
	 */
	public void setBreakdownElementService(BreakdownElementService breakdownElementService) {
		this.breakdownElementService = breakdownElementService;
	}

	/**
	 * @return the activityService
	 */
	public ActivityService getActivityService() {
		return activityService;
	}

	/**
	 * @param activityService
	 *            the activityService to set
	 */
	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

	/**
	 * @return the phaseService
	 */
	/*
	 * public PhaseService getPhaseService() { return phaseService; }
	 */

	/**
	 * @param phaseService
	 *            the phaseService to set
	 */
	/*
	 * public void setPhaseService(PhaseService phaseService) {
	 * this.phaseService = phaseService; }
	 */

	/**
	 * @return the concreteActivityService
	 */
	public ConcreteActivityService getConcreteActivityService() {
		return this.concreteActivityService;
	}

	/**
	 * @param _concreteActivityService
	 *            the concreteActivityService to set
	 */
	public void setConcreteActivityService(ConcreteActivityService _concreteActivityService) {
		this.concreteActivityService = _concreteActivityService;
	}

	/**
	 * @return the roleDescriptorService
	 */
	public RoleDescriptorService getRoleDescriptorService() {
		return roleDescriptorService;
	}

	/**
	 * @param roleDescriptorService
	 *            the roleDescriptorService to set
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
	 * @param taskDescriptorService
	 *            the taskDescriptorService to set
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
	 * @param iterationDao
	 *            the iterationDao to set
	 */
	public void setIterationDao(IterationDao iterationDao) {
		this.iterationDao = iterationDao;
	}

	/**
	 * @return the concretePhaseDao
	 */
	public ConcretePhaseDao getConcretePhaseDao() {
		return concretePhaseDao;
	}

	/**
	 * @param concretePhaseDao the concretePhaseDao to set
	 */
	public void setConcretePhaseDao(ConcretePhaseDao concretePhaseDao) {
		this.concretePhaseDao = concretePhaseDao;
	}

	/**
	 * @return the projectDao
	 */
	public ProjectDao getProjectDao() {
		return projectDao;
	}

	/**
	 * @param projectDao the projectDao to set
	 */
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
