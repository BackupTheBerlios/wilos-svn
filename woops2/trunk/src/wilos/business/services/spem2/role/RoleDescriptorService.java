package wilos.business.services.spem2.role;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * @author Sebastien
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class RoleDescriptorService {

	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao;

	private RoleDescriptorDao roleDescriptorDao;
	
	private ConcreteActivityService concreteActivityService;

	/**
	 * @return the roleDescriptorDao
	 */
	public RoleDescriptorDao getRoleDescriptorDao() {
		return roleDescriptorDao;
	}

	/**
	 * @param roleDescriptorDao the roleDescriptorDao to set
	 */
	public void setRoleDescriptorDao(RoleDescriptorDao roleDescriptorDao) {
		this.roleDescriptorDao = roleDescriptorDao;
	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<TaskDescriptor> getAdditionalTasks(RoleDescriptor _rd) {

		Set<TaskDescriptor> tmp = new HashSet<TaskDescriptor>();
		for (TaskDescriptor td : _rd.getAdditionalTasks()) {
			tmp.add(td);
		}
		return tmp;
	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<TaskDescriptor> getPrimaryTasks(RoleDescriptor _rd) {

		Set<TaskDescriptor> tmp = new HashSet<TaskDescriptor>();
		for (TaskDescriptor td : _rd.getPrimaryTasks()) {
			tmp.add(td);
		}
		return tmp;
	}

	/**
	 *
	 * @param _project
	 * @param _rd
	 */
	public void roleDescriptorInstanciation (Project _project, RoleDescriptor _rd, ConcreteActivity _cact, int _occ) {

		if (_occ > 0) {
			for (int i = 1;i <= _occ;i++) {
				
				ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();
		
				if (_occ > 1) {
					if (_rd.getPresentationName() == null)
						crd.setConcreteName(_rd.getName() + "_" + (new Integer(i)).toString());
					else
						crd.setConcreteName(_rd.getPresentationName() + "_" + (new Integer(i)).toString());
				} else {
					if (_rd.getPresentationName() == null)
						crd.setConcreteName(_rd.getName());
					else
						crd.setConcreteName(_rd.getPresentationName());
				}
		
				crd.addRoleDescriptor(_rd);
				crd.setProject(_project);
				_cact.setConcreteBreakdownElements(this.concreteActivityService.getConcreteBreakdownElements(_cact));
				crd.addSuperConcreteActivity(_cact);
		
				this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(crd);
				System.out.println("### ConcreteRoleDescriptor sauve");
			}
		}
	}

	public RoleDescriptor getRoleDescriptorById(String _id)
	{
		RoleDescriptor roleDescriptor;
		roleDescriptor = this.roleDescriptorDao.getRoleDescriptor(_id);
		return roleDescriptor;
	}

	public ConcreteRoleDescriptorDao getConcreteRoleDescriptorDao() {
		return concreteRoleDescriptorDao;
	}

	public void setConcreteRoleDescriptorDao(
			ConcreteRoleDescriptorDao concreteRoleDescriptorDao) {
		this.concreteRoleDescriptorDao = concreteRoleDescriptorDao;
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
	public void setConcreteActivityService(
			ConcreteActivityService concreteActivityService) {
		this.concreteActivityService = concreteActivityService;
	}
}
