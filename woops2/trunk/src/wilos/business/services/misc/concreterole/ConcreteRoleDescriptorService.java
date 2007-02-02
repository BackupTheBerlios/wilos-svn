package wilos.business.services.misc.concreterole;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.role.RoleDescriptor;

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
				if (concreteRoleDescriptor.getProject().getProject_id().equals(_projectId)) {
					returnedList.add(concreteRoleDescriptor);
				}
			}

			return returnedList;
		}

		/**
		 * TODO Method description
		 *
		 * @param _td
		 */
		/*public void roleDescriptorInstanciation (String _projectId, RoleDescriptor rd) {

			ConcreteRoleDescriptor crd = new ConcreteRoleDescriptor();

			crd.setConcreteName(rd.getPresentationName());
			crd.setProject(_projectId);
			crd.addRoleDescriptor(rd);

			this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(ctd);
		}*/

		public ConcreteRoleDescriptorDao getConcreteRoleDescriptorDao() {
			return concreteRoleDescriptorDao;
		}

		public void setConcreteRoleDescriptorDao(
				ConcreteRoleDescriptorDao _concreteRoleDescriptorDao) {
			this.concreteRoleDescriptorDao = _concreteRoleDescriptorDao;
		}
}
