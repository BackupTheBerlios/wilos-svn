package wilos.business.services.misc.concretetask;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.misc.wilosuser.ParticipantDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.hibernate.spem2.task.TaskDescriptorDao;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.utils.Constantes;
import wilos.utils.Constantes.State;

/**
 * 
 * @author deder
 * 
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ConcreteTaskDescriptorService {

	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao;
	
	private RoleDescriptorDao roleDescriptorDao;
	
	private TaskDescriptorDao TaskDescriptorDao;

	/**
	 * Return concreteTaskDescriptor for a project list
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ConcreteTaskDescriptor> getConcreteTaskDescriptorsForProject(
			String _projectId) {
		List<ConcreteTaskDescriptor> tempList = this.concreteTaskDescriptorDao
				.getAllConcreteTaskDescriptors();
		List<ConcreteTaskDescriptor> returnedList = new ArrayList<ConcreteTaskDescriptor>();

		for (ConcreteTaskDescriptor concreteTaskDescriptor : tempList) {
			if (concreteTaskDescriptor.getProjectId().equals(_projectId)) {
				returnedList.add(concreteTaskDescriptor);
			}
		}

		return returnedList;
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
		// update changings.
		
		_concreteTaskDescriptor.setState(State.READY);
		try {
			_concreteTaskDescriptor.setRealStartingDate(Constantes.DATE_FORMAT
					.parse(Calendar.getInstance().getTime().toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// save changings.
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}
	
	/**
	 * When the user click on the button affected. The state of the ConcreteTask change
	 * @param _concreteTaskDescriptor
	 */

	public void affectedConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor, Participant _user) {
		
		
		/*RoleDescriptor role = _concreteTaskDescriptor.getTaskDescriptor().getMainRole();
		role.addParticipant(_user);
		*/
		// save changings.
		//this.roleDescriptorDao.saveOrUpdateRoleDescriptor(role);*/
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
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
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
		try {
			_concreteTaskDescriptor.setRealFinishingDate(Constantes.DATE_FORMAT
					.parse(Calendar.getInstance().getTime().toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// save changings.
		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}
	
	/**
	 * 
	 * TODO Method description
	 *
	 * @param _processId
	 * @return
	 */
	public Set<ConcreteTaskDescriptor> getConcreteTaskDescriptorFromProject(String _projectId) {
		Set<ConcreteTaskDescriptor> ctds = new HashSet<ConcreteTaskDescriptor>();
		ctds.addAll(this.concreteTaskDescriptorDao.getConcreteTaskDescriptorFromProject(_projectId));
		return ctds;
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


	public RoleDescriptorDao getRoleDescriptorDao() {
		return roleDescriptorDao;
	}

	public void setRoleDescriptorDao(RoleDescriptorDao roleDescriptorDao) {
		this.roleDescriptorDao = roleDescriptorDao;
	}

	public TaskDescriptorDao getTaskDescriptorDao() {
		return TaskDescriptorDao;
	}

	public void setTaskDescriptorDao(TaskDescriptorDao taskDescriptorDao) {
		TaskDescriptorDao = taskDescriptorDao;
	}
}
