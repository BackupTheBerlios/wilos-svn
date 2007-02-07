package wilos.business.services.spem2.role;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
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

	/**
	 *
	 * @param _project
	 * @param _rd
	 */
	public void roleDescriptorInstanciation (Project _project, RoleDescriptor _rd) {

		ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();

		if (_rd.getPresentationName() == null)
			crd.setConcreteName(_rd.getName()) ;
		else
			crd.setConcreteName(_rd.getPresentationName());
		
		crd.addRoleDescriptor(_rd);

		this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(crd);
	}

	public ConcreteRoleDescriptorDao getConcreteRoleDescriptorDao() {
		return concreteRoleDescriptorDao;
	}

	public void setConcreteRoleDescriptorDao(
			ConcreteRoleDescriptorDao concreteRoleDescriptorDao) {
		this.concreteRoleDescriptorDao = concreteRoleDescriptorDao;
	}
}
