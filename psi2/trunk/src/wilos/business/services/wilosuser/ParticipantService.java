package wilos.business.services.wilosuser;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.project.ProjectDao;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.business.util.Security;

/**
 * The services associated to the Participant
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ParticipantService {

	private ParticipantDao participantDao;
	private ProjectDao projectDao;

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Return all the roles
	 * 
	 * @return the roles
	 */
	@Transactional(readOnly = true)
	public Set<RoleDescriptor> getRolesList() {
		//TODO: A deplacer dans le RoleService
		return this.participantDao.getAllRoles();
	}
	
	/**
	 * Return participants list
	 * 
	 * @return the list of participants
	 */
	@Transactional(readOnly = true)
	public Set<Participant> getParticipants() {
		return this.participantDao.getAllParticipants();
	}

	/**
	 * Save participant
	 * 
	 * @param _participant
	 */
	public void saveParticipant(Participant _participant) {
		_participant.setPassword(Security.encode(_participant.getPassword()));
		participantDao.saveOrUpdateParticipant(_participant);
	}

	/**
	 * Setter of participantDao.
	 * 
	 * @param _participantDao
	 *            The participantDao to set.
	 */
	public void setParticipantDao(ParticipantDao _participantDao) {
		this.participantDao = _participantDao;
	}

	/**
	 * Getter of ParticipantDao.
	 * @return the ParticipantDao
	 */
	public ParticipantDao getParticipantDao() {
		return this.participantDao;
	}
	
	/**
	 * 
	 * return the list of project where a participant is affected to
	 *
	 * @param participant
	 * 				the participant which the affected to project are returned
	 * @return list of project where the participant is affected to
	 */
	public Set<Project> getProjects(Participant participant)
	{
		return participant.getAffectedProjectList();
	}

	/**
	 * Getter of projectDao.
	 *
	 * @return the projectDao.
	 */
	public ProjectDao getProjectDao() {
		return this.projectDao ;
	}

	/**
	 * Setter of projectDao.
	 *
	 * @param _projectDao The projectDao to set.
	 */
	public void setProjectDao(ProjectDao _projectDao) {
		this.projectDao = _projectDao ;
	}
}
