/**
 * 
 */
package wilos.business.services.spem2.role;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.role.RoleDefinitionDao;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * @author Sebastien
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class RoleDefinitionService {
	
	private RoleDefinitionDao roleDefinitionDao;

	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<Guidance> getGuidances(RoleDefinition _rdef) {

		Set<Guidance> tmp = new HashSet<Guidance>();
		for (Guidance g : _rdef.getGuidances()) {
			tmp.add(g);
		}
		return tmp;
	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<RoleDescriptor> getRoleDescriptors(RoleDefinition _rdef) {

		Set<RoleDescriptor> tmp = new HashSet<RoleDescriptor>();
		for (RoleDescriptor rd : _rdef.getRoleDescriptors()) {
			tmp.add(rd);
		}
		return tmp;
	}

	/**
	 * @return the roleDefinitionDao
	 */
	public RoleDefinitionDao getRoleDefinitionDao() {
		return this.roleDefinitionDao;
	}

	/**
	 * @param _roleDefinitionDao the roleDefinitionDao to set
	 */
	public void setRoleDefinitionDao(RoleDefinitionDao _roleDefinitionDao) {
		this.roleDefinitionDao = _roleDefinitionDao;
	}
}
