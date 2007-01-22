package wilos.hibernate.misc.wilosuser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * ParticipantDao manage requests from the system to store Participant into the
 * database.
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
/**
 * @author mikamikaze
 *
 */
public class ParticipantDao extends HibernateDaoSupport {

	/**
	 * Save or update an element.
	 * 
	 * @param _element
	 */
	public void saveOrUpdateParticipant(Participant _participant) {
		this.getHibernateTemplate().saveOrUpdate(_participant);
	}

	/**
	 * Return a list of elements.
	 * 
	 * @return
	 */
	public Set<RoleDescriptor> getAllRoles() {
		Set<RoleDescriptor> loadAll = new HashSet<RoleDescriptor>();
		loadAll.addAll(this.getHibernateTemplate().loadAll(RoleDescriptor.class));
		return loadAll;
	}
	
	/**
	 * Return a list of elements.
	 * 
	 * @return
	 */
	public Set<RoleDescriptor> getAllRolesForAParticipant(String _login) {
		Set<RoleDescriptor> loadAll = new HashSet<RoleDescriptor>();
		Participant participant = this.getParticipant(_login);
		List roles = this.getHibernateTemplate().find("from RoleDescriptor role join role.play p where p.id=?",participant.getWilosuser_id());
		List<RoleDescriptor> rolesList = new ArrayList<RoleDescriptor>();
        if (roles.get(0) instanceof List){
                for (Object o : (ArrayList) roles.get(0)) {
                        if (o instanceof RoleDescriptor) {
                        	RoleDescriptor role = (RoleDescriptor) o;
                                rolesList.add(role);
                        }
                }
        }
        loadAll.addAll(rolesList);
		return loadAll;
	}

	/**
	 * Return a set of Participants.
	 * 
	 * @return
	 */
	public Set<Participant> getAllParticipants() {
		Set<Participant> loadAll = new HashSet<Participant>();
		loadAll.addAll(this.getHibernateTemplate().loadAll(Participant.class));
		return loadAll;
	}

	
	/**
	 * TODO
	 * @param _login
	 * @return
	 */
	public Participant getParticipant(String _login){
		ArrayList participants = (ArrayList)this.getHibernateTemplate().find("from Participant p where p.login=?",_login);
		if(participants.size()>0){
			return (Participant)participants.get(0);
		}else{
			return null;
		}
	}
	/**
	 * Delete the element.
	 * 
	 * @param _element
	 */
	public void deleteParticipant(Participant _participant) {
		try {
			this.getHibernateTemplate().delete(_participant);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting element into the db.
			logger.error("#### ERROR #### --- ParticipantDao => deleteParticipant : trying to delete unexisting object \n" + sose);
		}
	}
}
