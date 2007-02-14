package wilos.business.services.misc.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
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
public class ConcreteRoleAffectationService {

	private ParticipantDao participantDao;
	private ConcreteRoleDescriptorService concreteRoleDescriptorService;

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Transactional(readOnly = true)
	public List<ConcreteRoleDescriptor> getAllConcreteRolesDescriptorsForActivity(String _activityId, String _projectId){
		List<ConcreteRoleDescriptor> concreteRDList = new ArrayList<ConcreteRoleDescriptor>();
		List<ConcreteRoleDescriptor> globalCRD = this.concreteRoleDescriptorService.getAllConcreteRoleDescriptorsForProject(_projectId);
		for(ConcreteRoleDescriptor concreteRD : globalCRD){
			concreteRD = this.concreteRoleDescriptorService.getConcreteRoleDescriptorById(concreteRD.getId());
			List<ConcreteActivity> globalCA = new ArrayList<ConcreteActivity>();
			globalCA.addAll(concreteRD.getSuperConcreteActivities());
			for(Iterator iter = globalCA.iterator(); iter.hasNext();){
				ConcreteBreakdownElement ca = (ConcreteBreakdownElement) iter.next() ;
				if(ca.getId().equals(_activityId)){
					concreteRDList.add(concreteRD);
				}
			}
		}
		return concreteRDList;
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
	 * Save roles affectation for a participant.
	 *
	 * @return the page name where navigation has to be redirected to
	 * TODO: voir un peu ce retour qui craint
	 */
	public String saveParticipantConcreteRoles(HashMap<String, Object> rolesParticipant,String _wilosUserId) {
		Participant currentParticipant = this.participantDao.getParticipantById(_wilosUserId);
		
		ConcreteRoleDescriptor concreteRoleDescriptor = this.concreteRoleDescriptorService.getConcreteRoleDescriptorById((String)rolesParticipant.get("concreteId"));
			if((Boolean)rolesParticipant.get("affected")){
				currentParticipant.addConcreteRoleDescriptor(concreteRoleDescriptor);
			}
			else{
				currentParticipant.removeConcreteRoleDescriptor(concreteRoleDescriptor);
			}
			this.concreteRoleDescriptorService.getConcreteRoleDescriptorDao().saveOrUpdateConcreteRoleDescriptor(concreteRoleDescriptor);
		return "";
	}

	/**
	 * Getter of concreteRoleDescriptorService.
	 *
	 * @return the concreteRoleDescriptorService.
	 */
	public ConcreteRoleDescriptorService getConcreteRoleDescriptorService() {
		return this.concreteRoleDescriptorService ;
	}

	/**
	 * Setter of concreteRoleDescriptorService.
	 *
	 * @param _concreteRoleDescriptorService The concreteRoleDescriptorService to set.
	 */
	public void setConcreteRoleDescriptorService(ConcreteRoleDescriptorService _concreteRoleDescriptorService) {
		this.concreteRoleDescriptorService = _concreteRoleDescriptorService ;
	}

	
	/**
	 * TODO Method description
	 *
	 * @param _wilosUserId
	 * @param _concreteId
	 * @return
	 */
	@Transactional(readOnly = true)
	public Boolean getParticipantAffectationForConcreteRoleDescriptor(String _wilosUserId, String _concreteId) {
		ConcreteRoleDescriptor crd = this.concreteRoleDescriptorService.getConcreteRoleDescriptorById(_concreteId);
		if(crd.getParticipant() != null){
			if(crd.getParticipant().getWilosuser_id().equals(_wilosUserId))
				return true;
			else
				return false;
		}
		return false;
		
	}
}
