package wilos.business.services.spem2.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.spem2.task.TaskDescriptorDao;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.task.TaskDescriptor;

/**
 *
 * @author Soosuske
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class TaskDescriptorService {

	private TaskDescriptorDao taskDescriptorDao ;

	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Return activities list
	 *
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TaskDescriptor> getTaskDescriptorsFromProcess(String _id) {
		List<TaskDescriptor> tempList =  this.taskDescriptorDao.getAllTaskDescriptors() ;
		List<TaskDescriptor> returnedList =  new ArrayList<TaskDescriptor>();
		boolean flag = false;

		for (TaskDescriptor rd : tempList) {
			flag = false;
			for (Activity a : rd.getSuperActivities()) {
				if (a.getId().equals(_id)) {
					flag = true;
					break;
				}
			}
			if (flag){
				returnedList.add(rd);
				logger.debug("###TaskDescriptorDao ### added => "+rd);
			}
		}
		return returnedList;
	}

	/**
	 * TODO Method description
	 *
	 * @param _td
	 */
	public void taskDescriptorInstanciation (Project _project, TaskDescriptor td) {

		ConcreteTaskDescriptor ctd = new ConcreteTaskDescriptor();

		/*FIXME concretetaskdescriptor
		ctd.setConcreteName(td.getPresentationName());
		ctd.addTaskDescriptor(td);*/

		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(ctd);
	}

	/**
	 * Getter of taskDescriptorDao.
	 *
	 * @return the taskDescriptorDao.
	 */
	public TaskDescriptorDao getTaskDescriptorDao() {
		return this.taskDescriptorDao ;
	}

	/**
	 * Setter of taskDescriptorDao.
	 *
	 * @param _taskDescriptorDao The taskDescriptorDao to set.
	 */
	public void setTaskDescriptorDao(TaskDescriptorDao _taskDescriptorDao) {
		this.taskDescriptorDao = _taskDescriptorDao ;
	}

	public ConcreteTaskDescriptorDao getConcreteTaskDescriptorDao() {
		return this.concreteTaskDescriptorDao;
	}

	public void setConcreteTaskDescriptorDao(
			ConcreteTaskDescriptorDao _concreteTaskDescriptorDao) {
		this.concreteTaskDescriptorDao = _concreteTaskDescriptorDao;
	}
}
