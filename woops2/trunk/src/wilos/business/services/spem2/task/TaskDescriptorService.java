package wilos.business.services.spem2.task;

import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
import wilos.hibernate.spem2.task.TaskDescriptorDao;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.task.TaskDescriptor;

/**
 *
 * @author Soosuske
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class TaskDescriptorService {

	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao ;

	private TaskDescriptorDao taskDescriptorDao;

	public void taskDescriptorInstanciation (Project _project, TaskDescriptor td, ConcreteActivity _cact) {

		ConcreteTaskDescriptor ctd = new ConcreteTaskDescriptor();
		
		Set<ConcreteActivity> tmp  = new HashSet<ConcreteActivity>();
		tmp.add(_cact);

		if (td.getPresentationName() == null)
			ctd.setConcreteName(td.getName()) ;
		else
			ctd.setConcreteName(td.getPresentationName());

		ctd.addTaskDescriptor(td);
		ctd.setProject(_project);
		ctd.setSuperConcreteActivities(tmp);

		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(ctd);
		System.out.println("### ConcreteTaskDescriptor sauve");
	}

	public TaskDescriptor getTaskDescriptorById(String _id)
	{
		TaskDescriptor taskDescriptor;
		taskDescriptor = this.taskDescriptorDao.getTaskDescriptor(_id);
		return taskDescriptor;
	}

	public TaskDescriptorDao getTaskDescriptorDao() {
		return taskDescriptorDao;
	}

	public void setTaskDescriptorDao(TaskDescriptorDao taskDescriptorDao) {
		this.taskDescriptorDao = taskDescriptorDao;
	}

	public ConcreteTaskDescriptorDao getConcreteTaskDescriptorDao() {
		return this.concreteTaskDescriptorDao;
	}

	public void setConcreteTaskDescriptorDao(
			ConcreteTaskDescriptorDao _concreteTaskDescriptorDao) {
		this.concreteTaskDescriptorDao = _concreteTaskDescriptorDao;
	}
}
