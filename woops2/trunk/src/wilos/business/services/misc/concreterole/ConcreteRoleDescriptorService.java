package wilos.business.services.misc.concreterole;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
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

		private ConcreteActivityService concretActivityService;

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

		public List<ConcreteActivity> getSuperConcreteActivities(String _crdid) {
			ConcreteRoleDescriptor crd = this.getConcreteRoleDescriptorById(_crdid);
			List<ConcreteActivity> listTmp = this.concretActivityService.getAllConcreteActivities();
			List<ConcreteActivity> listToReturn = new ArrayList<ConcreteActivity>();

			for(ConcreteActivity ca : listTmp){
				if(ca.getConcreteBreakdownElements().contains(crd)){
					listToReturn.add(ca);
				}
			}

			return listToReturn;
		}

		public ConcreteRoleDescriptor getConcreteRoleDescriptorById(String _id){
			return this.concreteRoleDescriptorDao.getConcreteRoleDescriptor(_id);
		}

		public ConcreteRoleDescriptorDao getConcreteRoleDescriptorDao() {
			return concreteRoleDescriptorDao;
		}

		public void setConcreteRoleDescriptorDao(
				ConcreteRoleDescriptorDao _concreteRoleDescriptorDao) {
			this.concreteRoleDescriptorDao = _concreteRoleDescriptorDao;
		}

		/**
		 * @return the concretActivityService
		 */
		public ConcreteActivityService getConcretActivityService() {
			return concretActivityService;
		}

		/**
		 * @param concretActivityService the concretActivityService to set
		 */
		public void setConcretActivityService(
				ConcreteActivityService _concretActivityService) {
			this.concretActivityService = _concretActivityService;
		}
}
