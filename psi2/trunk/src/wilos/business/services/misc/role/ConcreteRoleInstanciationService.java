/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/
package wilos.business.services.misc.role;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.business.services.misc.concretebreakdownelement.ConcreteBreakdownElementService;
import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.breakdownelement.BreakdownElementService;
import wilos.business.services.spem2.iteration.IterationService;
import wilos.business.services.spem2.phase.PhaseService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ConcreteRoleInstanciationService {
	
	//PROCESS SERVICES
	private RoleDescriptorService roleDescriptorService;

	private ActivityService activityService;
	
	private PhaseService phaseService;
	
	private IterationService iterationService;

	private BreakdownElementService breakdownElementService;

	//CONCRETE SERVICES
	private ConcreteRoleDescriptorService concreteRoleDescriptorService;

	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao;

	private ConcreteActivityService concreteActivityService;

	private ConcreteBreakdownElementService concreteBreakdownElementService;

	private ProjectService projectService;

	//OTHERS
	private WebSessionService webSessionService;

	private ConcreteRoleDescriptor concreteRoleDescriptor;

	public final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Constructor 
	 *
	 */
	public ConcreteRoleInstanciationService() {
		this.concreteRoleDescriptor = new ConcreteRoleDescriptor();
	}

	/**
	 * 
	 * @param liste
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public int saveInstanciateConcreteRole(List<HashMap<String, Object>> roleDescriptorsToInstanciate, String projectId) {
		Project project = this.projectService.getProject(projectId);

		for (Iterator iter = roleDescriptorsToInstanciate.iterator(); iter.hasNext();) {
			HashMap<String, Object> hm = (HashMap<String, Object>) iter.next();

			String parentActivityId = (String) hm.get("parentId");
			
			RoleDescriptor r = new RoleDescriptor();
			r = this.roleDescriptorService.getRoleDescriptorDao().getRoleDescriptor((String) hm.get("id"));
			this.roleDescriptorService.getRoleDescriptorDao().getSessionFactory().getCurrentSession().saveOrUpdate(r);
			this.roleDescriptorService.getRoleDescriptorDao().getSessionFactory().getCurrentSession().refresh(r);
			
			//creation de ls liste des concrete activity ou il faudra ajouter le nouveau role
			Set<ConcreteBreakdownElement> concretesActivitiesToModifiy = getConcreteActivitiesToModify(project, parentActivityId);
			for (ConcreteBreakdownElement element : concretesActivitiesToModifiy) {

				ConcreteActivity concreteactivity = this.concreteActivityService.getConcreteActivity(element.getId());
				this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().saveOrUpdate(concreteactivity);
				this.concreteActivityService.getConcreteActivityDao().getSessionFactory().getCurrentSession().refresh(concreteactivity);

				this.roleDescriptorInstanciation(project, r, concreteactivity, (Integer)hm.get("nbOccurences"));
			}
		}
		return 1;
	}
	
	/**
	 * 
	 * @param _project
	 * @param _rd
	 * @param _cact
	 * @param _occ
	 */
	@Transactional(readOnly = false)
	public void roleDescriptorInstanciation (Project _project, RoleDescriptor _rd, ConcreteActivity _cact, int _occ) {

		if (_occ > 0)
		{
			//pour chaque occurrence
			for (int i=0 ; i<_occ ; i++)
			{
				ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();
				int iterid = 1;
				
				//creation du nom avec son numero
				for (ConcreteRoleDescriptor element : _rd.getConcreteRoleDescriptors()) {
					if(element.getProject() != null)
					{
						if(element.getProject().getId().equals(_project.getId()))
						{
							iterid++;
						}
					}
				}
				
				if (_rd.getPresentationName() == null)
				{
					crd.setConcreteName(_rd.getName() +" "+iterid);
				}
				else
				{
					crd.setConcreteName(_rd.getPresentationName() +" "+iterid);
				}
				
				crd.addRoleDescriptor(_rd);
				crd.setProject(_project);
				_cact.setConcreteBreakdownElements(this.concreteActivityService.getConcreteBreakdownElements(_cact));
				crd.addSuperConcreteActivity(_cact);
				this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(crd);
			}
		}
	}
	

	/**
	 * 
	 * @param project
	 * @param parentActivityId
	 * @return
	 */
	private Set<ConcreteBreakdownElement> getConcreteActivitiesToModify(Project project, String parentActivityId) {

		Set<ConcreteBreakdownElement> concretesActivitiesToModifiy = new HashSet<ConcreteBreakdownElement>();
		for (ConcreteBreakdownElement concreteBreakdownElement : this.concreteBreakdownElementService.getAllConcreteBreakdownElementsFromProject(project.getId())) {
			if (concreteBreakdownElement instanceof ConcreteActivity) {
				if (concreteBreakdownElement instanceof ConcretePhase) {
					ConcretePhase concretePhase = (ConcretePhase) concreteBreakdownElement;
					if (concretePhase.getPhase() != null) {
						if (concretePhase.getPhase().getId().equals(parentActivityId)) {
							concretesActivitiesToModifiy.add(concretePhase);
						}
					}
				} else {
					if (concreteBreakdownElement instanceof ConcreteIteration) {
						ConcreteIteration concreteIteration = (ConcreteIteration) concreteBreakdownElement;
						if (concreteIteration.getIteration() != null) {
							if (concreteIteration.getIteration().getId().equals(parentActivityId)) {
								concretesActivitiesToModifiy.add(concreteIteration);
							}
						}
					} else {
						if (concreteBreakdownElement instanceof Project) {
							Project concreteProcess = (Project) concreteBreakdownElement;
							if (concreteProcess.getProcess() != null) {
								if (concreteProcess.getProcess().getId().equals(parentActivityId)) {
									concretesActivitiesToModifiy.add(concreteProcess);
								}
							}
						} else {
							ConcreteActivity concreteActivity = (ConcreteActivity) concreteBreakdownElement;

							if (concreteActivity.getActivity() != null) {
								if (concreteActivity.getActivity().getId().equals(parentActivityId)) {
									concretesActivitiesToModifiy.add(concreteActivity);
								}
							}
						}
					}
				}
			}
		}
		return concretesActivitiesToModifiy;
	}
	
	/**
	 * return true if theActivity have been instanciated for the project
	 * @param _activity
	 * @return
	 */
	@Transactional(readOnly = true)
	public boolean isActivityInstanciated(WorkBreakdownElement _wbe, Project project)
	{
		Set<ConcreteWorkBreakdownElement> cwbes = new HashSet<ConcreteWorkBreakdownElement>();
		Set<ConcreteWorkBreakdownElement> cwbesOfProject = new HashSet<ConcreteWorkBreakdownElement>();
		
		if (_wbe instanceof Iteration) {
			Iteration it = (Iteration) _wbe;
			this.iterationService.getIterationDao().getSessionFactory().getCurrentSession().saveOrUpdate(it);
			this.iterationService.getIterationDao().getSessionFactory().getCurrentSession().refresh(it);
			cwbes.addAll(it.getConcreteIterations());

		} else {
			if (_wbe instanceof Phase) {
				Phase ph = (Phase) _wbe;
				this.phaseService.getPhaseDao().getSessionFactory().getCurrentSession().saveOrUpdate(ph);
				this.phaseService.getPhaseDao().getSessionFactory().getCurrentSession().refresh(ph);
				cwbes.addAll(ph.getConcretePhases());
			} else {
				if (_wbe instanceof Activity) {
					Activity act = (Activity) _wbe;
					this.activityService.getActivityDao().getSessionFactory().getCurrentSession().saveOrUpdate(act);
					this.activityService.getActivityDao().getSessionFactory().getCurrentSession().refresh(act);
					cwbes.addAll(act.getConcreteActivities());
				}
			}
		}
		//test if the concreteBDEs belongs to the project
		for (ConcreteWorkBreakdownElement element : cwbes) {
			if(element.getProject() != null)
			{
				if(element.getProject().getId().equals(project.getId()))
				{
					cwbesOfProject.add(element);
				}
			}
		}
		return (cwbesOfProject.size() > 0);
	}
	
	@Transactional(readOnly = true)
	public boolean isRoleInstanciated(RoleDescriptor rd, Project project) {
		Set<ConcreteRoleDescriptor> crds = new HashSet<ConcreteRoleDescriptor>();
		Set<ConcreteRoleDescriptor> crdsOfProject = new HashSet<ConcreteRoleDescriptor>();
		
		rd = this.getRoleDescriptorService().getRoleDescriptorById(rd.getId());
		this.roleDescriptorService.getRoleDescriptorDao().getSessionFactory().getCurrentSession().saveOrUpdate(rd);
		this.roleDescriptorService.getRoleDescriptorDao().getSessionFactory().getCurrentSession().refresh(rd);
		crds.addAll(rd.getConcreteRoleDescriptors());

		for (ConcreteRoleDescriptor element : crds) {
			if (element.getProject() != null) {
				if (element.getProject().getId().equals(project.getId())) {
					crdsOfProject.add(element);
				}
			}
		}
		this.logger.debug("### ROle : "+rd.getPresentationName()+" /  instancié :"+crdsOfProject.size());
		return (crdsOfProject.size() > 0);
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
	 * @return the breakdownElementService
	 */
	public BreakdownElementService getBreakdownElementService() {
		return breakdownElementService;
	}

	/**
	 * @param breakdownElementService the breakdownElementService to set
	 */
	public void setBreakdownElementService(BreakdownElementService breakdownElementService) {
		this.breakdownElementService = breakdownElementService;
	}

	/**
	 * @return the concreteActivityService
	 */
	public ConcreteActivityService getConcreteActivityService() {
		return concreteActivityService;
	}

	/**
	 * @param concreteActivityService the concreteActivityService to set
	 */
	public void setConcreteActivityService(ConcreteActivityService concreteActivityService) {
		this.concreteActivityService = concreteActivityService;
	}

	/**
	 * @return the concreteRoleDescriptor
	 */
	public ConcreteRoleDescriptor getConcreteRoleDescriptor() {
		return concreteRoleDescriptor;
	}

	/**
	 * @param concreteRoleDescriptor the concreteRoleDescriptor to set
	 */
	public void setConcreteRoleDescriptor(ConcreteRoleDescriptor concreteRoleDescriptor) {
		this.concreteRoleDescriptor = concreteRoleDescriptor;
	}

	/**
	 * @return the concreteRoleDescriptorDao
	 */
	public ConcreteRoleDescriptorDao getConcreteRoleDescriptorDao() {
		return concreteRoleDescriptorDao;
	}

	/**
	 * @param concreteRoleDescriptorDao the concreteRoleDescriptorDao to set
	 */
	public void setConcreteRoleDescriptorDao(ConcreteRoleDescriptorDao concreteRoleDescriptorDao) {
		this.concreteRoleDescriptorDao = concreteRoleDescriptorDao;
	}

	/**
	 * @return the concreteRoleDescriptorService
	 */
	public ConcreteRoleDescriptorService getConcreteRoleDescriptorService() {
		return concreteRoleDescriptorService;
	}

	/**
	 * @param concreteRoleDescriptorService the concreteRoleDescriptorService to set
	 */
	public void setConcreteRoleDescriptorService(ConcreteRoleDescriptorService concreteRoleDescriptorService) {
		this.concreteRoleDescriptorService = concreteRoleDescriptorService;
	}

	/**
	 * @return the projectService
	 */
	public ProjectService getProjectService() {
		return projectService;
	}

	/**
	 * @param projectService the projectService to set
	 */
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
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
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return webSessionService;
	}

	/**
	 * @param webSessionService the webSessionService to set
	 */
	public void setWebSessionService(WebSessionService webSessionService) {
		this.webSessionService = webSessionService;
	}

	/**
	 * @return the concreteBreakdownElementService
	 */
	public ConcreteBreakdownElementService getConcreteBreakdownElementService() {
		return concreteBreakdownElementService;
	}

	/**
	 * @param concreteBreakdownElementService the concreteBreakdownElementService to set
	 */
	public void setConcreteBreakdownElementService(ConcreteBreakdownElementService concreteBreakdownElementService) {
		this.concreteBreakdownElementService = concreteBreakdownElementService;
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
	 * @return the phaseService
	 */
	public PhaseService getPhaseService() {
		return phaseService;
	}

	/**
	 * @param phaseService the phaseService to set
	 */
	public void setPhaseService(PhaseService phaseService) {
		this.phaseService = phaseService;
	}

}