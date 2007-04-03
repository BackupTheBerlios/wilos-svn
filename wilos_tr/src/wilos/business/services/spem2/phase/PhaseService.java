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

package wilos.business.services.spem2.phase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.iteration.IterationService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.misc.concretephase.ConcretePhaseDao;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.spem2.phase.PhaseDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * PhaseManager is a transactional class, that manages operations about phase
 * 
 * @author Sebastien
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class PhaseService {

	private ConcretePhaseDao concretePhaseDao;

	private PhaseDao phaseDao;
	
	private ProjectDao projectDao;

	private IterationService iterationService;

	private ActivityService activityService;

	private ConcreteActivityService concreteActivityService;

	private RoleDescriptorService roleDescriptorService;

	private TaskDescriptorService taskDescriptorService;

	public Set<ConcretePhase> getAllConcretePhases(Phase _phase) {
		Set<ConcretePhase> tmp = new HashSet<ConcretePhase>();
		this.phaseDao.getSessionFactory().getCurrentSession().saveOrUpdate(_phase);
		for (ConcretePhase bde : _phase.getConcretePhases()) {
			tmp.add(bde);
		}
		return tmp;
	}
	
	public Set<ConcretePhase> getAllConcretePhasesForAProject(Phase _phase, Project _project) {
		Set<ConcretePhase> tmp = new HashSet<ConcretePhase>();
		//this.phaseDao.getSessionFactory().getCurrentSession().saveOrUpdate(_phase);
		this.projectDao.getSessionFactory().getCurrentSession().saveOrUpdate(_project);
		for (ConcretePhase cph : _phase.getConcretePhases()) {
			if (cph.getProject().getId().equals(_project.getId()))
				tmp.add(cph);
		}
		return tmp;
	}

	/**
	 * Instanciates a phase for a project
	 * 
	 * @param _project
	 *            project for which the Phase shall be instanciated
	 * @param _phase
	 *            phase to instanciates
	 */
	public void phaseInstanciation(Project _project, Phase _phase, ConcreteActivity _cact,
			List<HashMap<String, Object>> _list, int _occ, boolean _isInstanciated) {

		// if one occurence at least
		if (_occ > 0) {
			this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().saveOrUpdate(_cact);
			/*if (_isInstanciated) {
				this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().refresh(_cact);
			}*/
			int nbCph = 0;
			for (ConcreteBreakdownElement tmp : _cact.getConcreteBreakdownElements()) {
				if (tmp instanceof ConcretePhase) {
					if (((ConcretePhase) tmp).getPhase().getId().equals(_phase.getId())) {
						nbCph++;
					}
				}
			}
			for (int i = nbCph + 1; i <= nbCph + _occ; i++) {

				ConcretePhase cp = new ConcretePhase();

				Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
				bdes.addAll(this.activityService.getInstanciableBreakdownElements(_phase));

				if (_phase.getPresentationName().equals(""))
					cp.setConcreteName(_phase.getName() + "#" + (new Integer(i)).toString());
				else
					cp.setConcreteName(_phase.getPresentationName() + "#" + (new Integer(i)).toString());

				cp.addPhase(_phase);
				cp.setProject(_project);
				_cact.setConcreteBreakdownElements(this.concreteActivityService.getConcreteBreakdownElements(_cact));
				cp.addSuperConcreteActivity(_cact);

				this.concretePhaseDao.saveOrUpdateConcretePhase(cp);
				System.out.println("### ConcretePhase vide sauve");

				for (BreakdownElement bde : bdes) {
					if (bde instanceof Phase) {
						Phase ph = (Phase) bde;
						int occ = this.giveNbOccurences(ph.getId(), _list, false);
						this.phaseInstanciation(_project, ph, _project, _list, occ, _isInstanciated);
					} else {
						if (bde instanceof Iteration) {
							Iteration it = (Iteration) bde;
							int occ = this.giveNbOccurences(it.getId(), _list, false);
							this.iterationService.iterationInstanciation(_project, it, cp, _list, occ, _isInstanciated);
						} else {
							if (bde instanceof Activity) {
								Activity act = (Activity) bde;
								int occ = this.giveNbOccurences(act.getId(), _list, false);
								this.activityService.activityInstanciation(_project, act, cp, _list, occ, _isInstanciated);
							} else {
								if (bde instanceof TaskDescriptor) {
									TaskDescriptor td = (TaskDescriptor) bde;
									int occ = this.giveNbOccurences(td.getId(), _list, false);
									this.taskDescriptorService.taskDescriptorInstanciation(_project, td, cp, occ, _isInstanciated);
								}
							}
						}
					}
				}

				this.concretePhaseDao.saveOrUpdateConcretePhase(cp);
				System.out.println("### ConcretePhase update");
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
	public void phaseUpdate(Project _project, Phase _phase, Set<ConcreteActivity> _cacts, List<HashMap<String, Object>> _list, int _occ) {
		
		// one concretephase at least to insert in all attached concreteactivities of the parent of _phase
		if (_occ > 0) {
			for (ConcreteActivity tmp : _cacts) {
				/*this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().saveOrUpdate(tmp);
				this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().refresh(tmp);*/
				this.phaseInstanciation(_project, _phase, tmp, _list, _occ, true);
				
				if (tmp instanceof Project) {
					Project pj = (Project) tmp;
					this.projectDao.saveOrUpdateProject(pj);
				} else {
					if (tmp instanceof ConcretePhase) {
						ConcretePhase cph = (ConcretePhase) tmp;
						this.concretePhaseDao.saveOrUpdateConcretePhase(cph);
					}
				}
			}
		} else {
			
			// diving in all the concreteBreakdownElements to looking for update
			Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
			bdes.addAll(this.activityService.getInstanciableBreakdownElements(_phase));
			
			Set<ConcreteActivity> cacts = new HashSet<ConcreteActivity>();
			//FIXME faire et appeler la methode getAllConcretePhasesForAProject
			cacts.addAll(this.getAllConcretePhasesForAProject(_phase, _project));
			
			for (BreakdownElement bde : bdes) {
				if (bde instanceof Phase) {
					Phase ph = (Phase) bde;
					int occ = this.giveNbOccurences(ph.getId(), _list, true);
					this.phaseUpdate(_project, ph, cacts, _list, occ);
				} else {
					if (bde instanceof Iteration) {
						Iteration it = (Iteration) bde;
						int occ = this.giveNbOccurences(it.getId(), _list, true);
						this.iterationService.iterationUpdate(_project, it, cacts, _list, occ);
					} else {
						if (bde instanceof Activity) {
							Activity act = (Activity) bde;
							int occ = this.giveNbOccurences(act.getId(), _list, true);
							this.activityService.activityUpdate(_project, act, cacts, _list, occ);
						} else {
							if (bde instanceof TaskDescriptor) {
								TaskDescriptor td = (TaskDescriptor) bde;
								int occ = this.giveNbOccurences(td.getId(), _list, true);
								this.taskDescriptorService.taskDescriptorUpdate(_project, td, cacts, occ);
							}
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
	private int giveNbOccurences(String _id, List<HashMap<String, Object>> list, boolean _isInstanciated) {

		int nb;
		if (!_isInstanciated) nb = 1;
		else nb = 0;

		for (HashMap<String, Object> hashMap : list) {
			if (((String) hashMap.get("id")).equals(_id)) {
				nb = ((Integer) hashMap.get("nbOccurences")).intValue();
				break;
			}
		}

		return nb;
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
	 * @param concretePhaseDao
	 *            the concretePhaseDao to set
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
	 * @param iterationService
	 *            the iterationService to set
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
	 * @return the phaseDao
	 */
	public PhaseDao getPhaseDao() {
		return phaseDao;
	}

	/**
	 * @param phaseDao
	 *            the phaseDao to set
	 */
	public void setPhaseDao(PhaseDao phaseDao) {
		this.phaseDao = phaseDao;
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
