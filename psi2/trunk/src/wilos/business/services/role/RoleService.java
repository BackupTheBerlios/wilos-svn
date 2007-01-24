package wilos.business.services.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * The services associated to the Role
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class RoleService {

	private RoleDescriptorDao roleDescriptorDao ;
	private ParticipantDao participantDao;

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * Return participant roles
	 * 
	 * @return the roles
	 */
	@Transactional(readOnly = true)
	public List<RoleDescriptor> getRolesDescriptor() {
		
		ArrayList<RoleDescriptor> rolesList = new ArrayList<RoleDescriptor>(this.roleDescriptorDao.getAllRoleDescriptors());
		return rolesList;
	}

	/**
	 * Save roleDescriptor
	 * 
	 * @param _roleDescriptor
	 */
	public void saveRoleDescriptor(RoleDescriptor _roleDescriptor) {
		roleDescriptorDao.saveOrUpdateRoleDescriptor(_roleDescriptor);
	}

	/**
	 * Setter of roleDescriptorDao.
	 * 
	 * @param _roleDescriptorDao
	 *            The roleDescriptorDao to set.
	 */
	public void setRoleDescriptorDao(RoleDescriptorDao _roleDescriptorDao) {
		this.roleDescriptorDao = _roleDescriptorDao;
	}
	
	
	/**
	 * Getting the roles list of the participant witch login is provided.   
	 *
	 * @return An list with each roleDescriptor and if the participant is owning this role
	 */
	@Transactional(readOnly = true)
	public HashMap<RoleDescriptor,Boolean> getRolesForAParticipant(String _user_login){
		RoleDescriptor globalRoleTemp;
		Boolean test;
		HashMap<RoleDescriptor,Boolean> participantRoles = new HashMap<RoleDescriptor,Boolean>();
		
		Participant currentParticipant = this.participantDao.getParticipant(_user_login);
		for(Iterator globalRolesIter = this.getRolesDescriptor().iterator(); globalRolesIter.hasNext();)
		{
			globalRoleTemp = (RoleDescriptor)globalRolesIter.next();
			
			if(currentParticipant.getRolesListForAProject().contains(globalRoleTemp))
			{
				test = new Boolean(true);
				if(!participantRoles.containsKey(globalRoleTemp))
					participantRoles.put(globalRoleTemp, test);
			} else {
				test = new Boolean(false);
				if(!participantRoles.containsKey(globalRoleTemp))
					participantRoles.put(globalRoleTemp, test);
			}
		}
		return participantRoles;
	}
	
	public Set<RoleDescriptor> getAffectedRolesForAParticipant(String _login){
		Set<RoleDescriptor> rolesSetForAParticipant = new HashSet<RoleDescriptor>();
		HashMap<RoleDescriptor, Boolean> rolesForAParticipant = this.getRolesForAParticipant(_login);
		for(Iterator iter = rolesForAParticipant.keySet().iterator(); iter.hasNext();){
			RoleDescriptor currentRole = (RoleDescriptor) iter.next() ;
			if(((Boolean)rolesForAParticipant.get(currentRole)).booleanValue()){
				rolesSetForAParticipant.add(currentRole);
			}
		}
		return rolesSetForAParticipant;
	}

	/**
	 * Getter of participantDao.
	 *
	 * @return the participantDao.
	 */
	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	/**
	 * Setter of participantDao.
	 * 
	 * @param participantDao
	 *            The participantDao to set.
	 */
	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	/**
	 * Getter of roleDescriptorDao.
	 *
	 * @return the roleDescriptorDao.
	 */
	public RoleDescriptorDao getRoleDescriptorDao() {
		return roleDescriptorDao;
	}

	/**
	 * Save roles affectation for a participant.
	 *
	 * @return the page name where navigation has to be redirected to
	 * TODO: voir un peu ce retour qui craint
	 */
	public String saveParticipantRoles(HashMap<String, Boolean> rolesParticipant,String _user_login) {
		Participant currentParticipant = this.participantDao.getParticipant(_user_login);
		
		for (Iterator rolesIter = rolesParticipant.keySet().iterator(); rolesIter.hasNext();) {
			String roleName = (String) rolesIter.next();
			RoleDescriptor roleDescriptor = this.getRoleDescriptorByName(roleName);
			if(roleDescriptor!=null)
			{
				if(rolesParticipant.get(roleName).booleanValue())		
					currentParticipant.addToRoleDescriptor(roleDescriptor);
				else
					currentParticipant.removeFromRoleDescriptor(roleDescriptor);
			}
		}
		return "";
	}
	
	
	/**
	 * Method to get the RoleDescriptor that match with the RoleName provided
	 *
	 * @param roleName
	 * @return The RoleDescriptor
	 */
	public RoleDescriptor getRoleDescriptorByName(String roleName){
		for(Iterator roleIter = this.getRolesDescriptor().iterator(); roleIter.hasNext();){
			RoleDescriptor roleDescriptor = (RoleDescriptor) roleIter.next() ;
			if(roleDescriptor.getName().equals(roleName))
				return roleDescriptor;			
		}
		return null;
		
	}
}
