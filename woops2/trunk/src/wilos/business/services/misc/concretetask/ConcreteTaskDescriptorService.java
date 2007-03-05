package wilos.business.services.misc.concretetask;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.utils.Constantes.State;

/**
 *
 * @author Soosuske
 * @author deder
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ConcreteTaskDescriptorService {

	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao;

	private TaskDescriptorService taskDescriptorService;

	private RoleDescriptorService roleDescriptorService;

	private ConcreteRoleDescriptorService concreteRoleDescriptorService;
	


	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Return concreteTaskDescriptor for a project list
	 *
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ConcreteTaskDescriptor> getAllConcreteTaskDescriptorsForProject(
			String _projectId) {
		return this.getConcreteTaskDescriptorDao()
				.getAllConcreteTaskDescriptorsForProject(_projectId);
	}

	/**
	 * 
	 * @param _concreteTaskDescriptorId
	 * @return
	 */
	public ConcreteTaskDescriptor getConcreteTaskDescriptor(
			String _concreteTaskDescriptorId) {
		return this.getConcreteTaskDescriptorDao().getConcreteTaskDescriptor(
				_concreteTaskDescriptorId);
	}

	/**
	 * Save the ConcreteTaskDescriptor modifications into database
	 * @param _concreteTaskDescriptor
	 */
	public void updateConcreteTaskDescriptor(ConcreteTaskDescriptor _concreteTaskDescriptor)
	{
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}

	/**
	 * Start the ConcreteTaskDescriptor and save into the data base changings
	 * (i.e. State, realStartingDate).
	 *
	 * @param _concreteTaskDescriptor
	 *            The ConcreteTaskDescriptor to start.
	 */
	public void startConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {

		_concreteTaskDescriptor.setState(State.STARTED);


		// save changings.
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}
	
	/**
	 * 
	 */
	public void removeConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		
		Set <ConcreteActivity> superConcreteActivities =
						_concreteTaskDescriptor.getSuperConcreteActivities() ;
		TaskDescriptor taskDescriptor = _concreteTaskDescriptor.getTaskDescriptor() ;
		
		TaskDescriptor td2 =
			this.taskDescriptorService.getTaskDescriptorById(taskDescriptor.getId()) ;
		
		ConcreteRoleDescriptor concreteRoleDescriptor =
						_concreteTaskDescriptor.getMainConcreteRoleDescriptor() ;
		
		ConcreteRoleDescriptor crd2 =
			this.concreteRoleDescriptorService
					.getConcreteRoleDescriptorById(concreteRoleDescriptor.getId()) ;
		
		for (ConcreteActivity sca : superConcreteActivities) {
			sca.removeConcreteBreakdownElement(_concreteTaskDescriptor) ;
		}
		
		crd2.removeConcreteTaskDescriptor(_concreteTaskDescriptor) ;
		
		td2.removeConcreteTaskDescriptor(_concreteTaskDescriptor) ;
	}

	/**
	 * When the user click on the button affected.
	 *
	 * @param _concreteTaskDescriptor
	 */
	@Transactional(readOnly = false)
	public ConcreteTaskDescriptor affectedConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor, Participant _user) {
		ConcreteRoleDescriptor concreteRoleDescriptor = new ConcreteRoleDescriptor();
		
		
		TaskDescriptor tmp = _concreteTaskDescriptor.getTaskDescriptor();
		RoleDescriptor tmpRoleDescriptor;
		TaskDescriptor td = this.taskDescriptorService.getTaskDescriptorById(tmp.getId());

		tmpRoleDescriptor = td.getMainRole();


		RoleDescriptor rd = this.roleDescriptorService.getRoleDescriptorById(tmpRoleDescriptor.getId());
		// recuperation des deux listes.
		Set<ConcreteRoleDescriptor> listeRd = rd.getConcreteRoleDescriptors();

		// on parcours les deux liste afin de trouver le bon
		// concreteRoledescriptor
		for (ConcreteRoleDescriptor tmpListeRd : listeRd) {

			ConcreteRoleDescriptor crd = this.concreteRoleDescriptorService.getConcreteRoleDescriptorById(tmpListeRd.getId());
			//logger.debug("idddddddd "+crd.getParticipant().getWilosuser_id());
			if(crd.getParticipant() != null)
			{
				if(crd.getParticipant().getWilosuser_id().equals(_user.getWilosuser_id()))
				{
					concreteRoleDescriptor = tmpListeRd;
				}
			}
		}
		ConcreteRoleDescriptor crd = this.concreteRoleDescriptorService.getConcreteRoleDescriptorById(concreteRoleDescriptor.getId());
		
		_concreteTaskDescriptor.addConcreteRoleDescriptor(crd);
		return _concreteTaskDescriptor;
	}

	/**
	 * 
	 * @param _concreteTaskDescriptor
	 */
	public void affectedState(ConcreteTaskDescriptor _concreteTaskDescriptor)
	{
		_concreteTaskDescriptor.setState(State.READY);

		this.updateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}
	
	/**
	 * Suspend the ConcreteTaskDescriptor and save into the data base changings
	 * (i.e. State).
	 *
	 * @param _concreteTaskDescriptor
	 *            The ConcreteTaskDescriptor to start.
	 */
	public void suspendConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		// update changings.
		_concreteTaskDescriptor.setState(State.SUSPENDED);

		// save changings.
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}

	/**
	 * Finish the ConcreteTaskDescriptor and save into the data base changings
	 * (i.e. State, realFinishingDate).
	 *
	 * @param _concreteTaskDescriptor
	 *            The ConcreteTaskDescriptor to start.
	 */
	public void finishConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		// update changings.
		_concreteTaskDescriptor.setState(State.FINISHED);

		// save changings.
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}

	/**
	 * 
	 * @param _concreteTaskDescriptor
	 */
	public void saveConcreteTaskDescriptor(ConcreteTaskDescriptor _concreteTaskDescriptor) {
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}

	/**
	 * Dissociates a participant from a ConcreteTask
	 * 
	 * @param _concreteTaskDescriptor the ConcreteTaskDescriptor to be dissociated
	 * @param _participant the Participant to dissociate
	 */
	public void dissociateConcreteTaskDescriptor(ConcreteTaskDescriptor _concreteTaskDescriptor) {
		ConcreteRoleDescriptor cmrd = _concreteTaskDescriptor.getMainConcreteRoleDescriptor();
		
		cmrd.removeConcreteTaskDescriptor(_concreteTaskDescriptor);
		_concreteTaskDescriptor.setState(State.CREATED);
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
		this.concreteRoleDescriptorService.saveConcreteRoleDescriptor(cmrd);
	}
	
	/**
	 * Getter of taskDescriptorDao.
	 *
	 * @return the taskDescriptorDao.
	 */
	public ConcreteTaskDescriptorDao getConcreteTaskDescriptorDao() {
		return this.concreteTaskDescriptorDao;
	}


	/**
	 * Setter of taskDescriptorDao.
	 *
	 * @param _taskDescriptorDao
	 *            The taskDescriptorDao to set.
	 */
	public void setConcreteTaskDescriptorDao(
			ConcreteTaskDescriptorDao _concreteTaskDescriptorDao) {
		this.concreteTaskDescriptorDao = _concreteTaskDescriptorDao;
	}

	/**
	 * 
	 * @return
	 */
	public RoleDescriptorService getRoleDescriptorService() {
		return roleDescriptorService;
	}

	/**
	 * 
	 * @param roleDescriptorService
	 */
	public void setRoleDescriptorService(
			RoleDescriptorService roleDescriptorService) {
		this.roleDescriptorService = roleDescriptorService;
	}

	/**
	 * 
	 * @return
	 */
	public TaskDescriptorService getTaskDescriptorService() {
		return taskDescriptorService;
	}

	/**
	 * 
	 * @param taskDescriptorService
	 */
	public void setTaskDescriptorService(
			TaskDescriptorService taskDescriptorService) {
		this.taskDescriptorService = taskDescriptorService;
	}

	/**
	 * @return the concreteRoleDescriptorService
	 */
	public ConcreteRoleDescriptorService getConcreteRoleDescriptorService() {
		return concreteRoleDescriptorService;
	}

	/**
	 * @param concreteRoleDescriptorService the concreteRoleDescriptorService to set
	 */
	public void setConcreteRoleDescriptorService(
			ConcreteRoleDescriptorService concreteRoleDescriptorService) {
		this.concreteRoleDescriptorService = concreteRoleDescriptorService;
	}
}
