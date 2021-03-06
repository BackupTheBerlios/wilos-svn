/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
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

package wilos.business.services.spem2.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.misc.concreteiteration.ConcreteIterationDao;
import wilos.hibernate.misc.concretephase.ConcretePhaseDao;
import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.spem2.activity.ActivityDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * ActivityManager is a transactional class, that manages operations about
 * activity, requested by web pages (activity.jsp & activityform.jsp)
 * 
 * @author garwind
 * @author deder.
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ActivityService {

	private ActivityDao activityDao;

	private ConcretePhaseDao concretePhaseDao;
	
	private ConcreteIterationDao concreteIterationDao;
	
	private ProjectDao projectDao;
	
	/*
	 * private PhaseService phaseService;
	 * 
	 * private IterationService iterationService;
	 */

	private ConcreteActivityService concreteActivityService;

	private RoleDescriptorService roleDescriptorService;

	private TaskDescriptorService taskDescriptorService;

	/**
	 * Instanciates an activity for a project
	 * 
	 * @param _project
	 *            project for which the activity shall be instanciated
	 * @param _phase
	 *            activity to instanciate
	 */
	public void activityInstanciation(Project _project, Activity _activity, ConcreteActivity _cact,
			List<HashMap<String, Object>> _list, int _occ, boolean _isInstanciated) {

		if (_occ > 0) {
			this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().saveOrUpdate(_cact);
			/*if (_isInstanciated) {
				this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().refresh(_cact);
			}*/
			int nbCact = 0;
			for (ConcreteBreakdownElement tmp : _cact.getConcreteBreakdownElements()) {
				if (tmp instanceof ConcreteActivity) {
					if (((ConcreteActivity) tmp).getActivity().getId().equals(_activity.getId())) {
						nbCact++;
					}
				}
			}
			for (int i = nbCact + 1; i <= nbCact + _occ; i++) {

				ConcreteActivity cact = new ConcreteActivity();

				List<BreakdownElement> bdes = new ArrayList<BreakdownElement>();
				bdes.addAll(this.getInstanciableBreakdownElements(_activity));

				if (_activity.getPresentationName().equals(""))
					cact.setConcreteName(_activity.getName() + "#" + (new Integer(i)).toString());
				else
					cact.setConcreteName(_activity.getPresentationName() + "#" + (new Integer(i)).toString());

				cact.addActivity(_activity);
				cact.setProject(_project);
				_cact.setConcreteBreakdownElements(this.concreteActivityService.getConcreteBreakdownElements(_cact));
				cact.addSuperConcreteActivity(_cact);

				this.concreteActivityService.saveConcreteActivity(cact);
				System.out.println("### ConcreteActivity vide sauve");

				for (BreakdownElement bde : bdes) {
					if (bde instanceof Activity) {
						Activity act = (Activity) bde;
						int occ = this.giveNbOccurences(act.getId(), _list, false);
						this.activityInstanciation(_project, act, cact, _list, occ, false);
					} else {
						if (bde instanceof TaskDescriptor) {
							TaskDescriptor td = (TaskDescriptor) bde;
							int occ = this.giveNbOccurences(td.getId(), _list, false);
							this.taskDescriptorService.taskDescriptorInstanciation(_project, td, cact, occ, false);
						}
					}
				}

				this.concreteActivityService.saveConcreteActivity(cact);
				System.out.println("### ConcreteActivity update");
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
	public void activityUpdate(Project _project, Activity _act, Set<ConcreteActivity> _cacts, List<HashMap<String, Object>> _list, int _occ) {
		
		// one concretephase at least to insert in all attached concreteactivities of the parent of _phase
		if (_occ > 0) {
			for (ConcreteActivity tmp : _cacts) {
				/*this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().saveOrUpdate(tmp);
				this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().refresh(tmp);*/
				this.activityInstanciation(_project, _act, tmp, _list, _occ, true);
				
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
						} else {
							if (tmp instanceof ConcreteActivity) {
								ConcreteActivity cact = (ConcreteActivity) tmp;
								this.concreteActivityService.saveConcreteActivity(cact);
							}
						}
					}
				}
				
				this.concreteActivityService.saveConcreteActivity(tmp);
				System.out.println("### ConcreteActivity update");
			}			
		} else {
			
			// diving in all the concreteBreakdownElements to looking for update
			Set<BreakdownElement> bdes = new HashSet<BreakdownElement>();
			bdes.addAll(this.getInstanciableBreakdownElements(_act));
			
			Set<ConcreteActivity> cacts = new HashSet<ConcreteActivity>();
			//FIXME idem phaseService
			cacts.addAll(this.getAllConcreteActivitiesForAProject(_act, _project));
			
			for (BreakdownElement bde : bdes) {
				if (bde instanceof Activity) {
					Activity act = (Activity) bde;
					int occ = this.giveNbOccurences(act.getId(), _list, true);
					this.activityUpdate(_project, act, cacts, _list, occ);
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

	public Set<ConcreteActivity> getAllConcreteActivities(Activity _act) {
		Set<ConcreteActivity> tmp = new HashSet<ConcreteActivity>();
		this.activityDao.getSessionFactory().getCurrentSession().saveOrUpdate(_act);
		for (ConcreteActivity bde : _act.getConcreteActivities()) {
			tmp.add(bde);
		}
		return tmp;
	}
	
	public Set<ConcreteActivity> getAllConcreteActivitiesForAProject(Activity _act, Project _project) {
		Set<ConcreteActivity> tmp = new HashSet<ConcreteActivity>();
		this.activityDao.getSessionFactory().getCurrentSession().saveOrUpdate(_act);
		this.projectDao.getSessionFactory().getCurrentSession().saveOrUpdate(_project);
		for (ConcreteActivity cact : _act.getConcreteActivities()) {
			if (cact.getProject().getId().equals(_project.getId()))
				tmp.add(cact);
		}
		return tmp;
	}

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
	@Transactional(readOnly = true)
	public Activity getActivity(String _id) {
		return this.activityDao.getActivity(_id);
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
	@Transactional(readOnly = true)
	public Activity getActivityFromGuid(String _guid) {
		return this.activityDao.getActivityFromGuid(_guid);
	}

	/**
	 * Save activity
	 * 
	 * @param _activity
	 *            the activity to save
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
	 * @return the iterationService
	 */
	/*
	 * public IterationService getIterationService() { return iterationService; }
	 */

	/**
	 * @param iterationService
	 *            the iterationService to set
	 */
	/*
	 * public void setIterationService(IterationService iterationService) {
	 * this.iterationService = iterationService; }
	 */

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
	 * @return the concreteIterationDao
	 */
	public ConcreteIterationDao getConcreteIterationDao() {
		return concreteIterationDao;
	}

	/**
	 * @param concreteIterationDao the concreteIterationDao to set
	 */
	public void setConcreteIterationDao(ConcreteIterationDao concreteIterationDao) {
		this.concreteIterationDao = concreteIterationDao;
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
}
