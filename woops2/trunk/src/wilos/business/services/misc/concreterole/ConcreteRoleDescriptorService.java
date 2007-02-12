package wilos.business.services.misc.concreterole;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;

/**
*
* @author eperico
*
*/
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ConcreteRoleDescriptorService {

		private ConcreteRoleDescriptorDao concreteRoleDescriptorDao;

		/**
		 * Return concreteRoleDescriptor for a project list
		 *
		 * @return
		 */
		@Transactional(readOnly = true)
		public List<ConcreteRoleDescriptor> getAllConcreteRoleDescriptorsForProject(String _projectId) {
			return this.getConcreteRoleDescriptorDao().getAllConcreteRoleDescriptorsForProject(_projectId);
		}

		public List<ConcreteTaskDescriptor> getAllConcreteTaskDescriptorsForConcreteRoleDescriptor(ConcreteRoleDescriptor _concreteRoleDescriptor) {
			return this.concreteRoleDescriptorDao.getAllConcreteTaskDescriptorsForConcreteRoleDescriptor(_concreteRoleDescriptor.getId());
		}

		public ConcreteRoleDescriptorDao getConcreteRoleDescriptorDao() {
			return concreteRoleDescriptorDao;
		}

		public void setConcreteRoleDescriptorDao(
				ConcreteRoleDescriptorDao _concreteRoleDescriptorDao) {
			this.concreteRoleDescriptorDao = _concreteRoleDescriptorDao;
		}
}
