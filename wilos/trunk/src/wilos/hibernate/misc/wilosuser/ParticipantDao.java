package wilos.hibernate.misc.wilosuser;

import java.util.HashSet;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 *
 * This class represents ... TODO
 *
 */
public class ParticipantDao extends HibernateDaoSupport {

	/**
	 * Save or update an element.
	 * 
	 * @param _element
	 */
	public void saveOrUpdateParticipant (Participant _participant) {
		this.getHibernateTemplate().saveOrUpdate(_participant) ;
	}

	/**
	 * Return a list of elements.
	 * 
	 * @return
	 */
	public Set <RoleDescriptor> getAllRoles () {
		Set <RoleDescriptor> loadAll = new HashSet<RoleDescriptor>() ;
		loadAll.addAll(this.getHibernateTemplate().loadAll(RoleDescriptor.class)) ;
		return loadAll ;
	}

	/**
	 * Return a set of Participants.
	 * 
	 * @return
	 */
	public Set <Participant> getAllParticipants () {
		Set <Participant> loadAll = new HashSet<Participant>() ;
		loadAll.addAll(this.getHibernateTemplate().loadAll(Participant.class)) ;
		return loadAll ;
	}
	
	/**
	 * Return the element which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public Participant getParticipant (String _id) {
		return (Participant) this.getHibernateTemplate().get(Participant.class, _id) ;
	}

	/**
	 * Delete the element.
	 * 
	 * @param _element
	 */
	public void deleteParticipant (Participant _participant) {
		try {
			this.getHibernateTemplate().delete(_participant) ;
		}
		catch (Exception sose) {
			// Catch normally errors when we delete an unexisting element into the db.
			logger.error("#### ERROR #### --- ParticipantDao => deleteParticipant : trying to delete unexisting object \n" + sose) ;
		}
	}
}
