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
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

/**
 *
 * @author Soosuske
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class TaskDescriptorService {

	private ConcreteTaskDescriptorDao concreteTaskDescriptorDao;

	private TaskDescriptorDao taskDescriptorDao;

	/**
	 * 
	 * @param _project
	 * @param _td
	 * @param _cact
	 * @param _occ
	 */
	public void taskDescriptorInstanciation (Project _project, TaskDescriptor _td, ConcreteActivity _cact, int _occ) {

		if (_occ > 0) {
			for (int i = 1;i <= _occ;i++) {
				
				ConcreteTaskDescriptor ctd = new ConcreteTaskDescriptor();
		
				if (_occ > 1) {
					if (_td.getPresentationName() == null)
						ctd.setConcreteName(_td.getName() + "_" + (new Integer(i)).toString());
					else
						ctd.setConcreteName(_td.getPresentationName() + "_" + (new Integer(i)).toString());
				} else {
					if (_td.getPresentationName() == null)
						ctd.setConcreteName(_td.getName());
					else
						ctd.setConcreteName(_td.getPresentationName());
				}
		
				ctd.addTaskDescriptor(_td);
				ctd.setProject(_project);
				ctd.addSuperConcreteActivity(_cact);
		
				this.concreteTaskDescriptorDao.saveOrUpdateConcreteTaskDescriptor(ctd);
				System.out.println("### ConcreteTaskDescriptor sauve");
			}
		}

	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<RoleDescriptor> getAdditionalRoles(TaskDescriptor _td) {

		Set<RoleDescriptor> tmp = new HashSet<RoleDescriptor>();
		for (RoleDescriptor rd : _td.getAdditionalRoles()) {
			tmp.add(rd);
		}
		return tmp;
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
