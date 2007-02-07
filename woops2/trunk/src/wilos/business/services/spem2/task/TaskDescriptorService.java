package wilos.business.services.spem2.task;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.misc.concretetask.ConcreteTaskDescriptorDao;
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

	public void taskDescriptorInstanciation (Project _project, TaskDescriptor td) {

		ConcreteTaskDescriptor ctd = new ConcreteTaskDescriptor();

		if (td.getPresentationName() == null)
			ctd.setConcreteName(td.getName()) ;
		else
			ctd.setConcreteName(td.getPresentationName());
		
		ctd.addTaskDescriptor(td);

		this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(ctd);
	}

	public ConcreteTaskDescriptorDao getConcreteTaskDescriptorDao() {
		return this.concreteTaskDescriptorDao;
	}

	public void setConcreteTaskDescriptorDao(
			ConcreteTaskDescriptorDao _concreteTaskDescriptorDao) {
		this.concreteTaskDescriptorDao = _concreteTaskDescriptorDao;
	}
}
