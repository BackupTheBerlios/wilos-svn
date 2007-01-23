package wilos.business.services.spem2.task;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.task.TaskDefinitionDao;
import wilos.model.spem2.task.TaskDefinition;

/**
 * @author deder
 *
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class TaskDefinitionService {
	
	private TaskDefinitionDao taskDefinitionDao;
	
	public void saveTaskDefinition(TaskDefinition _taskDefinition){
		
	}

	/**
	 * @return the taskDefinitionDao
	 */
	public TaskDefinitionDao getTaskDefinitionDao() {
		return this.taskDefinitionDao ;
	}

	/**
	 * Setter of taskDefinitionDao.
	 *
	 * @param _taskDefinitionDao The taskDefinitionDao to set.
	 */
	public void setTaskDefinitionDao(TaskDefinitionDao _taskDefinitionDao) {
		this.taskDefinitionDao = _taskDefinitionDao ;
	}
	
	
}
