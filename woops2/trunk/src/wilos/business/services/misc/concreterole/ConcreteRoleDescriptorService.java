package wilos.business.services.misc.concreterole;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;

/**
*
* @author eperico
*
*/
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ConcreteRoleDescriptorService {

		private ConcreteRoleDescriptorDao concreteRoleDescriptorDao;
		
		protected final Log logger = LogFactory.getLog(this.getClass()) ;
		
		/**
		 * Return concreteRoleDescriptor for a project list
		 *
		 * @return
		 */
		@Transactional(readOnly = true)
		public List<ConcreteRoleDescriptor> getConcreteRoleDescriptorsForProject(String _projectId) {
			List<ConcreteRoleDescriptor> roleDescriptorsList = this.concreteRoleDescriptorDao.getAllConcreteRoleDescriptors();
			List<ConcreteRoleDescriptor> returnedList = new ArrayList<ConcreteRoleDescriptor>();

			for (ConcreteRoleDescriptor concreteRoleDescriptor : roleDescriptorsList) {
				if (concreteRoleDescriptor.getProjectId().equals(_projectId)) {
					returnedList.add(concreteRoleDescriptor);
				}
			}

			return returnedList;
		}

		public ConcreteRoleDescriptorDao getConcreteRoleDescriptorDao() {
			return concreteRoleDescriptorDao;
		}

		public void setConcreteRoleDescriptorDao(
				ConcreteRoleDescriptorDao _concreteRoleDescriptorDao) {
			this.concreteRoleDescriptorDao = _concreteRoleDescriptorDao;
		}
}
