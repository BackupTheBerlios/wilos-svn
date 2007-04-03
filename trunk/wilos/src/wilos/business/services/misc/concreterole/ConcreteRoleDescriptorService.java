/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.business.services.misc.concreterole;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.hibernate.misc.concreterole.ConcreteRoleDescriptorDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
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

		private ConcreteActivityService concreteActivityService;
		
		private RoleDescriptorService roleDescriptorService;
		
		
		
		public void saveConcreteRoleDescriptor(ConcreteRoleDescriptor _concreteRoleDescriptor) {
			this.concreteRoleDescriptorDao.saveOrUpdateConcreteRoleDescriptor(_concreteRoleDescriptor);
		}
		
		public List<ConcreteRoleDescriptor> getAllConcreteRoleDescriptors() {
			return this.getConcreteRoleDescriptorDao().getAllConcreteRoleDescriptors();
		}
		
		public List<ConcreteTaskDescriptor> getPrimaryConcreteTaskDescriptors(String _concreteRoleId){
			return this.concreteRoleDescriptorDao.getPrimaryConcreteTaskDescriptors(_concreteRoleId);
		}

		/**
		 * Return concreteRoleDescriptor for a project list
		 *
		 * @return
		 */
		@Transactional(readOnly = true)
		public List<ConcreteRoleDescriptor> getAllConcreteRoleDescriptorsForProject(String _projectId) {
			return this.getConcreteRoleDescriptorDao().getAllConcreteRoleDescriptorsForProject(_projectId);
		}

		/**
		 * 
		 * @param _concreteRoleDescriptor
		 * @return
		 */
		public List<ConcreteTaskDescriptor> getAllConcreteTaskDescriptorsForConcreteRoleDescriptor(ConcreteRoleDescriptor _concreteRoleDescriptor) {
			return this.concreteRoleDescriptorDao.getAllConcreteTaskDescriptorsForConcreteRoleDescriptor(_concreteRoleDescriptor.getId());
		}

		/**
		 * 
		 * @param _crdid
		 * @return
		 */
		public List<ConcreteActivity> getSuperConcreteActivities(String _crdid) {
			ConcreteRoleDescriptor crd = this.getConcreteRoleDescriptorById(_crdid);
			List<ConcreteActivity> listTmp = this.concreteActivityService.getAllConcreteActivities();
			List<ConcreteActivity> listToReturn = new ArrayList<ConcreteActivity>();

			for(ConcreteActivity ca : listTmp){
				if(ca.getConcreteBreakdownElements().contains(crd)){
					listToReturn.add(ca);
				}
			}

			return listToReturn;
		}
		
		/**
		 * delete a concreteRoleDescriptor
		 */
		@Transactional(readOnly = false)
		public void removeConcreteRoleDescriptor(ConcreteRoleDescriptor _concreteRoledescriptor)
		{
			this.concreteRoleDescriptorDao.getHibernateTemplate().saveOrUpdate(_concreteRoledescriptor);
			
			//super activities automaintenance
			for (ConcreteActivity sca : _concreteRoledescriptor.getSuperConcreteActivities())
			{
				sca.getConcreteBreakdownElements().remove(_concreteRoledescriptor);
				this.concreteActivityService.getConcreteActivityDao().saveOrUpdateConcreteActivity(sca);
			}
			
			//no automaintenance for the taskdescriptor because the deleting of a concreteRoleDscriptor implies it has been not affected
			//this kind of automaintenance implies spring circular dependencies
			/*
			//concreteTasks automaintenance
			if (_concreteRoledescriptor.getPrimaryConcreteTaskDescriptors() != null)
			{
				for (ConcreteTaskDescriptor tmpConcreteTaskDescriptor : _concreteRoledescriptor.getPrimaryConcreteTaskDescriptors())
				{
					this.concreteTaskDescriptorService.getConcreteTaskDescriptorDao().getHibernateTemplate().saveOrUpdate(tmpConcreteTaskDescriptor);
					tmpConcreteTaskDescriptor.removeConcreteRoleDescriptor(_concreteRoledescriptor);
					this.concreteTaskDescriptorService.getConcreteTaskDescriptorDao().saveOrUpdateConcreteTaskDescriptor(tmpConcreteTaskDescriptor);
				}
			}
			*/
			//RoleDescriptor automaintenance
			RoleDescriptor rd = _concreteRoledescriptor.getRoleDescriptor();
			this.roleDescriptorService.getRoleDescriptorDao().getHibernateTemplate().saveOrUpdate(rd);
			rd.removeConcreteRoleDescriptor(_concreteRoledescriptor);
			this.roleDescriptorService.getRoleDescriptorDao().saveOrUpdateRoleDescriptor(rd);
			
			//deleting
			this.getConcreteRoleDescriptorDao().deleteConcreteRoleDescriptor(_concreteRoledescriptor);
		}

		/**
		 * 
		 * @param _id
		 * @return
		 */
		public ConcreteRoleDescriptor getConcreteRoleDescriptorById(String _id){
			return this.concreteRoleDescriptorDao.getConcreteRoleDescriptor(_id);
		}

		/**
		 * 
		 * @return
		 */
		public ConcreteRoleDescriptorDao getConcreteRoleDescriptorDao() {
			return concreteRoleDescriptorDao;
		}

		/**
		 * 
		 * @param _concreteRoleDescriptorDao
		 */
		public void setConcreteRoleDescriptorDao(
				ConcreteRoleDescriptorDao _concreteRoleDescriptorDao) {
			this.concreteRoleDescriptorDao = _concreteRoleDescriptorDao;
		}

		/**
		 * @return the concretActivityService
		 */
		public ConcreteActivityService getConcreteActivityService() {
			return concreteActivityService;
		}

		/**
		 * @param concretActivityService the concretActivityService to set
		 */
		public void setConcreteActivityService(ConcreteActivityService _concreteActivityService) {
			this.concreteActivityService = _concreteActivityService;
		}

		/**
		 * 
		 * @param _roleDescriptorId
		 * @return
		 */
		public List<ConcreteRoleDescriptor> getAllConcreteRoleDescriptorForARoleDescriptor(
				String _roleDescriptorId) {
			return this.concreteRoleDescriptorDao.getAllConcreteRoleDescriptorsForARoleDescriptor(_roleDescriptorId);
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
}
