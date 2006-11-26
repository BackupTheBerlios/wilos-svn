
package woops2.business.wilosuser ;

import java.util.Set ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.springframework.transaction.annotation.Propagation ;
import org.springframework.transaction.annotation.Transactional ;

import woops2.hibernate.wilosuser.ParticipantDao;
import woops2.model.role.RoleDescriptor ;
import woops2.model.wilosuser.Participant;

/**
 * This class represents ... TODO
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class ParticipantService {

	private ParticipantDao participantDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Return participant roles
	 * 
	 * @return the roles 
	 */
	@ Transactional (readOnly = true)
	public Set<RoleDescriptor> getRolesList() {
		return this.participantDao.getAllRoles();
	}

	
	/**
	 * Check if the login is already used
	 *
	 * @param _login
	 * @return True is the login is already present 
	 */
	public boolean loginExist(String _login) {
		boolean found = false;
		Set<Participant> setParticipant = this.participantDao.getAllParticipants() ;
		for (Participant parti : setParticipant) {
			if(parti.getLogin().equals(_login)){
				found = true;
			}	
		}
		return found;
	}

	/**
	 * Save participant
	 * 
	 * @param _participant
	 */
	public void saveParticipant(Participant _participant) {
		participantDao.saveOrUpdateParticipant(_participant) ;
	}

	/**
	 * Setter of participantDao.
	 * 
	 * @param _participantDao
	 *            The participantDao to set.
	 */
	public void setParticipantDao(ParticipantDao _participantDao) {
		this.participantDao = _participantDao ;
	}
}
