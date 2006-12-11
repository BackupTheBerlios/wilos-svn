package wilos.business.services.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.wilosuser.ParticipantService;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;
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
		ArrayList<RoleDescriptor> rolesList = new ArrayList<RoleDescriptor>(this.roleDescriptorDao.getAllRoleDescriptor());
		return rolesList;
	}

	/**
	 * Function that make some test on transactionnal lazy loadings
	 * 
	 * 
	 * public void Test(){ Activity a =
	 * this.participantDao.getActivityFromPrefix("test"); if (a == null){ a =
	 * new Activity(); a.setPrefix("test");
	 * this.activityDao.saveOrUpdateActivity(a); BreakdownElement b = new
	 * BreakdownElement(); this.activityDao.getHibernateTemplate().save(b);
	 * a.getBreakDownElements().add(b);
	 * this.activityDao.saveOrUpdateActivity(a); } List<BreakdownElement> liste =
	 * new ArrayList<BreakdownElement>(a.getBreakDownElements());
	 * logger.debug("### ActivityManager - TEST ### liste size =
	 * "+liste.size()); for (BreakdownElement b : liste){
	 * System.out.println("b="+b); } }
	 */

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
	
	@Transactional(readOnly = true)
	public HashMap<RoleDescriptor,Boolean> getRolesForAParticipant()
	{
		RoleDescriptor globalRoleTemp;
		//RoleDescriptor partRoleTemp;
		Boolean test;
		HashMap<RoleDescriptor,Boolean> participantRoles = new HashMap<RoleDescriptor,Boolean>();
		/*for(Iterator iter = this.getRolesDescriptor().iterator(); iter.hasNext();){
			roleTemp = (RoleDescriptor)iter.next();
			participantRoles.put(roleTemp,test);//,false);			
		}*/
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		HttpSession sess = req.getSession() ;
		WilosUser user = (WilosUser) sess.getAttribute("wilosUser") ;
		String user_login = user.getLogin();
		/*if(user instanceof Participant)
		{*/
			Participant currentParticipant = this.participantDao.getParticipant(user_login);
			for(Iterator globalRolesIter = this.getRolesDescriptor().iterator(); globalRolesIter.hasNext();)
			{
				globalRoleTemp = (RoleDescriptor)globalRolesIter.next();
				
				if(currentParticipant.getRolesListForAProject().contains(globalRoleTemp))
				{
					test = new Boolean(true);
					participantRoles.put(globalRoleTemp, test);
				} else {
					test = new Boolean(false);
					participantRoles.put(globalRoleTemp, test);
				}
			}
		//}
		/*Recuperation du participant dans la session*/
		/*Participant currentParticipant = FacesContext.getCurrentInstance();
		 *for(Iterator iter = currentParticipant.getRolesListForAProject();iter.hasNext();)
		 *{
		 *	roleTemp = (RoleDescriptor)iter.next();
		 *	if(participantRoles.containsKey(roleTemp))
		 *	{
		 *		participantRoles.put(roleTemp,true);
		 *	}
		 *}*/
		return participantRoles;
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
	 *	@return the page name where navigation has to be redirected to
	 */
	public String saveParticipantRoles(HashMap<String, Boolean> rolesParticipant) {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		HttpSession sess = req.getSession() ;
		WilosUser user = (WilosUser) sess.getAttribute("wilosUser") ;
		String user_login = user.getLogin();
		Participant currentParticipant = this.participantDao.getParticipant(user_login);
		
		for (Iterator rolesIter = rolesParticipant.keySet().iterator(); rolesIter.hasNext();) {
			String roleName = (String) rolesIter.next();
			if(rolesParticipant.get(roleName).booleanValue())
			{
				RoleDescriptor roleDescriptor = this.roleDescriptorDao.getRoleDescriptor(roleName);
				currentParticipant.addToRoleDescriptor(roleDescriptor);
			}
		}
		return "welcome";
	}
}
