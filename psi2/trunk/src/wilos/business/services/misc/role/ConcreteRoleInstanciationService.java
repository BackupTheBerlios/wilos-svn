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
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.role.RoleDescriptor;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ConcreteRoleInstanciationService {
	
	//PROCESS SERVICES
	private RoleDescriptorService roleDescriptorService;

	private ActivityService activityService;

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
	@Transactional(readOnly = false)
	@SuppressWarnings("unchecked")
	public int saveInstanciateConcreteRole(List<HashMap<String, Object>> roleDescriptorsToInstanciate, String projectId) {
		Project project = this.projectService.getProject(projectId);

		for (Iterator iter = roleDescriptorsToInstanciate.iterator(); iter.hasNext();) {
			HashMap<String, Object> hm = (HashMap<String, Object>) iter.next();

			String parentActivityId = (String) hm.get("parentId");
			int nbOccurence = (Integer) hm.get("nbOccurences");

			//creation de ls liste des concrete activity ou il faudra ajouter le nouveau role
			Set<ConcreteBreakdownElement> concretesActivitiesToModifiy = getConcreteActivitiesToModify(project, parentActivityId);
			for (ConcreteBreakdownElement element : concretesActivitiesToModifiy) {
				RoleDescriptor r = this.roleDescriptorService.getRoleDescriptorById((String) hm.get("id"));
				ConcreteActivity concreteactivity = this.concreteActivityService.getConcreteActivity(element.getId());
				this.roleDescriptorService.roleDescriptorInstanciation(project, r, concreteactivity, (Integer)hm.get("nbOccurences"));
			}
		}

		return 1;
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
			this.logger.debug("## CA du projet : "+concreteBreakdownElement.getConcreteName());
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

}