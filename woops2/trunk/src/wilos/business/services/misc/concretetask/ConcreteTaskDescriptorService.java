package wilos.business.services.misc.concretetask;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.role.RoleDescriptorService;
import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.hibernate.spem2.task.TaskDescriptorDao;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
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

	private TaskDescriptorDao taskDescriptorDao;

	private TaskDescriptorService taskDescriptorService;

	private RoleDescriptorService roleDescriptorService;

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

		/*FIXME Urgent (project) !!! for (ConcreteTaskDescriptor concreteTaskDescriptor : tempList) {
			if (concreteTaskDescriptor.getProject().getProject_id().equals(_projectId)) {
				returnedList.add(concreteTaskDescriptor);
			}
		}*/

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

		_concreteTaskDescriptor.setState(State.STARTED);
	/*	try {
			_concreteTaskDescriptor.setRealStartingDate(Constantes.DATE_FORMAT
					.parse(Calendar.getInstance().getTime().toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		// save changings.
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}

	/**
	 * When the user click on the button affected.
	 *
	 * @param _concreteTaskDescriptor
	 */

	public void affectedConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor, Participant _user) {

		/*FIXME TaskDescriptor td = _concreteTaskDescriptor.getTaskDescriptor();
		TaskDescriptor tmp = this.taskDescriptorService.getTaskDescriptorDao()
				.getTaskDescriptor(td.getId());
		RoleDescriptor roleDescriptor = tmp.getMainRole();
		roleDescriptor.addParticipant(_user);
		// save changings.
		this.roleDescriptorDao.saveOrUpdateRoleDescriptor(roleDescriptor);*/
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
		/*
		try {
			_concreteTaskDescriptor.setRealFinishingDate(Constantes.DATE_FORMAT
					.parse(Calendar.getInstance().getTime().toString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		// save changings.
		this.concreteTaskDescriptorDao
				.saveOrUpdateConcreteTaskDescriptor(_concreteTaskDescriptor);
	}

	/**
	 *
	 * TODO Method description
	 *
	 * @param _processId
	 * @return
	 */
	public Set<ConcreteTaskDescriptor> getConcreteTaskDescriptorFromProject(
			String _projectId) {
		Set<ConcreteTaskDescriptor> ctds = new HashSet<ConcreteTaskDescriptor>();
		ctds.addAll(this.concreteTaskDescriptorDao
				.getConcreteTaskDescriptorFromProject(_projectId));
		return ctds;
	}

	/**
	 * Visible ob affected buton
	 */
	public boolean affectedVisible(
			ConcreteTaskDescriptor _concreteTaskDescriptor, Participant _user) {

		boolean visi = true;

		/*FIXME TaskDescriptor td = _concreteTaskDescriptor.getTaskDescriptor();
		TaskDescriptor tmp = this.taskDescriptorService.getTaskDescriptorDao().getTaskDescriptor(td.getId());
		RoleDescriptor roleDescriptor = tmp.getMainRole();
		RoleDescriptor tmpRd = this.roleDescriptorService.getRoleDescriptorDao().getRoleDescriptor(roleDescriptor.getId());
		Set<Participant> part = tmpRd.getParticipants();

			for (Participant parti : part) {
				if(parti.getWilosuser_id().equals(_user.getWilosuser_id()))
				{
					visi= false;
				}
			}*/

		return visi;

	}

	/**
	 * Visible ob affected buton
	 */
	public boolean startVisible(
			ConcreteTaskDescriptor _concreteTaskDescriptor, Participant _user) {

		boolean visi = true;

		/*FIXME TaskDescriptor td = _concreteTaskDescriptor.getTaskDescriptor();
		TaskDescriptor tmp = this.taskDescriptorService.getTaskDescriptorDao().getTaskDescriptor(td.getId());
		RoleDescriptor roleDescriptor = tmp.getMainRole();
		RoleDescriptor tmpRd = this.roleDescriptorService.getRoleDescriptorDao().getRoleDescriptor(roleDescriptor.getId());
		Set<Participant> part = tmpRd.getParticipants();
		if(!_concreteTaskDescriptor.getState().equals("Started"))
		{
			for (Participant parti : part) {
				if(parti.getWilosuser_id().equals(_user.getWilosuser_id()))
				{
					visi= false;
				}
			}
		}*/
		return visi;

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

	public void setRoleDescriptorDao(RoleDescriptorDao _roleDescriptorDao) {
		this.roleDescriptorDao = _roleDescriptorDao;
	}

	public TaskDescriptorDao getTaskDescriptorDao() {
		return taskDescriptorDao;
	}

	public void setTaskDescriptorDao(TaskDescriptorDao _taskDescriptorDao) {
		taskDescriptorDao = _taskDescriptorDao;
	}

	public TaskDescriptorService getTaskDescriptorService() {
		return this.taskDescriptorService;
	}

	public void setTaskDescriptorService(
			TaskDescriptorService _taskDescriptorService) {
		this.taskDescriptorService = _taskDescriptorService;
	}

	public RoleDescriptorService getRoleDescriptorService() {
		return roleDescriptorService;
	}

	public void setRoleDescriptorService(RoleDescriptorService roleDescriptorService) {
		this.roleDescriptorService = roleDescriptorService;
	}
}
