
package woops2.business.wilosuser ;

import java.util.Set ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.springframework.transaction.annotation.Propagation ;
import org.springframework.transaction.annotation.Transactional ;

import woops2.hibernate.wilosuser.ParticipantDao;
import woops2.model.role.RoleDescriptor ;
import woops2.model.wilosuser.Participant;
import woops2.business.wilosuser.Security;

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
	 * Save participant
	 * 
	 * @param _participant
	 */
	public void saveParticipant(Participant _participant) {
		_participant.setPassword(Security.encode(_participant.getPassword()));
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

	public ParticipantDao getParticipantDao() {
		return this.participantDao ;
	}
}
