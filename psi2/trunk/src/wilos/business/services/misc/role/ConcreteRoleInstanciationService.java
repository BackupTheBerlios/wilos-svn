package wilos.business.services.misc.role;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ConcreteRoleInstanciationService {
	
	private ConcreteRoleDescriptorService concreteRoleDescriptorService;
	private WebSessionService webSessionService;
	private ProjectService projectService;
	private RoleDescriptorService roleDescriptorService;
	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao;
	
	private ConcreteRoleDescriptor concrete;
	
	public ConcreteRoleInstanciationService()
	{
		this.concrete=new ConcreteRoleDescriptor();
	}
		
	public void saveInstanciateConcreteRole(List<HashMap<String, String>> liste){
		for (Iterator iter = liste.iterator(); iter.hasNext();) {
			HashMap<String, String> hm=new HashMap<String, String>();
			hm= (HashMap<String, String>) iter.next();
			
			
			int nboccurance=new Integer(hm.get("nbOccurences").toString());
			
			for (int i=0;i<nboccurance; i++) {
			
			String projectId=(String)this.webSessionService.getAttribute(WebSessionService.PROJECT_ID);
			Project p = this.projectService.getProject(projectId);
			RoleDescriptor r = this.roleDescriptorService.getRoleDescriptorById(hm.get("id").toString());
			
			concrete.setParticipant(null);
			concrete.setProject(p);
			concrete.setConcreteName("testtte"+i);
			concrete.setRoleDescriptor(r);
			this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(concrete);
			
			}
		}	
		
	this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao().saveOrUpdateConcreteRoleDescriptor(concrete);	
	}



	public ConcreteRoleDescriptor getConcrete() {
		return concrete;
	}



	public void setConcrete(ConcreteRoleDescriptor concrete) {
		this.concrete = concrete;
	}



	public ConcreteRoleDescriptorDao getConcreteRoleDescriptorDao() {
		return concreteRoleDescriptorDao;
	}



	public void setConcreteRoleDescriptorDao(
			ConcreteRoleDescriptorDao concreteRoleDescriptorDao) {
		this.concreteRoleDescriptorDao = concreteRoleDescriptorDao;
	}



	public ConcreteRoleDescriptorService getConcreteRoleDescriptorService() {
		return concreteRoleDescriptorService;
	}



	public void setConcreteRoleDescriptorService(
			ConcreteRoleDescriptorService concreteRoleDescriptorService) {
		this.concreteRoleDescriptorService = concreteRoleDescriptorService;
	}



	public ProjectService getProjectService() {
		return projectService;
	}



	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}



	public RoleDescriptorService getRoleDescriptorService() {
		return roleDescriptorService;
	}



	public void setRoleDescriptorService(RoleDescriptorService roleDescriptorService) {
		this.roleDescriptorService = roleDescriptorService;
	}



	public WebSessionService getWebSessionService() {
		return webSessionService;
	}



	public void setWebSessionService(WebSessionService webSessionService) {
		this.webSessionService = webSessionService;
	}
	
	
	
	
}