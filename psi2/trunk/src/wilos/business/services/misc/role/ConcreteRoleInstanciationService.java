package wilos.business.services.misc.role;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.breakdownelement.BreakdownElementService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.role.RoleDescriptor;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ConcreteRoleInstanciationService
{
	private RoleDescriptorService roleDescriptorService;
	private BreakdownElementService breakdownElementService;
	
	private ConcreteRoleDescriptorService concreteRoleDescriptorService;
	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao;
	
	private ProjectService projectService;
	
	private WebSessionService webSessionService;
	
	private ConcreteRoleDescriptor concreteRoleDescriptor;
	public final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/**
	 * Constructor 
	 *
	 */
	public ConcreteRoleInstanciationService()
	{
		this.concreteRoleDescriptor=new ConcreteRoleDescriptor();
	}
	
	/**
	 * 
	 * @param liste
	 */
	@ Transactional (readOnly = false)	
	public void saveInstanciateConcreteRole(List<HashMap<String, Object>> roleDescriptorsToInstanciate)
	{
		for (Iterator iter = roleDescriptorsToInstanciate.iterator(); iter.hasNext();)
		{
			HashMap<String, Object> hm = (HashMap<String, Object>) iter.next();

			//String projectId =(String)this.webSessionService.getAttribute(this.webSessionService.PROJECT_ID);
			//Project p = this.projectService.getProject(projectId);
			
			/*
			- Recupere le parent id de la hashMap
			- je v cherché ds concrete activity service la concrete qui reference ce parentId
			- donc j'ai le concrete qu'il faut
			- je fais setSuperConcreteActivitues(set(maConcreteActivity))
			*/
			
			int nbOccurence = (Integer)hm.get("nbOccurences");
			for (int i=0; i<nbOccurence; i++)
			{
				RoleDescriptor r = this.roleDescriptorService.getRoleDescriptorById((String)hm.get("id"));
				Set<Activity> set = this.breakdownElementService.getSuperActivities(r);
				for (Activity activity : set) {
					this.logger.debug("### RoleDescriptor : "+r.getPresentationName()+" / SuperActivity : "+activity.getPresentationName()+" ###");
				}
				/*
				this.concreteRoleDescriptor.setParticipant(null);
				this.concreteRoleDescriptor.setProject(p);
				this.concreteRoleDescriptor.setConcreteName(r.getPresentationName());
				this.concreteRoleDescriptor.setRoleDescriptor(r);
				this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);
				*/
			}
		}
		//this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao().saveOrUpdateConcreteRoleDescriptor(this.concreteRoleDescriptor);
			
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
}