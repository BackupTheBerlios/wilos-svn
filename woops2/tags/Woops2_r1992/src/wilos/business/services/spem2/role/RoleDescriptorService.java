package wilos.business.services.spem2.role;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * @author Sebastien
 *
 * This class represents ... TODO
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class RoleDescriptorService {

	private ConcreteRoleDescriptorDao concreteRoleDescriptorDao;

	private RoleDescriptorDao roleDescriptorDao;

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
	 * @param _project
	 * @param _rd
	 */
	public void roleDescriptorInstanciation (Project _project, RoleDescriptor _rd, ConcreteActivity _cact) {

		ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();
		
		Set<ConcreteActivity> tmp  = new HashSet<ConcreteActivity>();
		tmp.add(_cact);

		if (_rd.getPresentationName() == null)
			crd.setConcreteName(_rd.getName()) ;
		else
			crd.setConcreteName(_rd.getPresentationName());

		crd.addRoleDescriptor(_rd);
		crd.setProject(_project);
		crd.setSuperConcreteActivities(tmp);

		this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(crd);
		System.out.println("### ConcreteRoleDescriptor sauve");
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
}