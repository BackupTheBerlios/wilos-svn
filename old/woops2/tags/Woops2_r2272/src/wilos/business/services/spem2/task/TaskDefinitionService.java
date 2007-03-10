/**
 * 
 */
package wilos.business.services.spem2.task;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.task.TaskDefinitionDao;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;

/**
 * @author Sebastien
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class TaskDefinitionService {
	
	private TaskDefinitionDao taskDefinitionDao;
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<Guidance> getGuidances(TaskDefinition _tdef) {

		Set<Guidance> tmp = new HashSet<Guidance>();
		for (Guidance g : _tdef.getGuidances()) {
			tmp.add(g);
		}
		return tmp;
	}
	
	/**
	 *
	 * @param _act
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public SortedSet<Step> getSteps(TaskDefinition _tdef) {

		SortedSet<Step> tmp = new TreeSet<Step>();
		for (Step s : _tdef.getSteps()) {
			tmp.add(s);
		}
		return tmp;
	}

	/**
	 * @return the taskDefinitionDao
	 */
	public TaskDefinitionDao getTaskDefinitionDao() {
		return this.taskDefinitionDao;
	}

	/**
	 * @param _taskDefinitionDao the taskDefinitionDao to set
	 */
	public void setTaskDefinitionDao(TaskDefinitionDao _taskDefinitionDao) {
		this.taskDefinitionDao = _taskDefinitionDao;
	}

}
