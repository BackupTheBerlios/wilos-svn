
package woops2.hibernate.task ;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import woops2.model.task.TaskDefinition;

/**
 * /** TaskDefinitionDao manage requests from the system to store TaskDefinition to the database
 * 
 * @author eperico
 * 
 */
public class TaskDefinitionDao extends HibernateDaoSupport {
	/**
	 * Save or update a TaskDefinition
	 * 
	 * @param _taskDefinition
	 */
	public void saveOrUpdateTaskDefinition(TaskDefinition _taskDefinition) {
		this.getHibernateTemplate().saveOrUpdate(_taskDefinition) ;
	}

	/**
	 * @return set <TaskDefinition>
	 */
	@SuppressWarnings("unchecked")
	public List<TaskDefinition> getAllTask() {
		List<TaskDefinition> loadAll = new ArrayList<TaskDefinition>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(TaskDefinition.class));
		} catch (DataAccessException e) {
			logger.error("### TaskDefinitionDao ### --> "+e);
		}
		return loadAll ;
	}

	/**
	 * @param _id
	 * @return TaskDefinition
	 */
	public TaskDefinition getTask(String _id) {
		return (TaskDefinition) this.getHibernateTemplate().get(TaskDefinition.class, _id) ;
	}

	/**
	 * Delete the TaskDefinition
	 * 
	 * @param _taskDefinition
	 */
	public void deleteTask(TaskDefinition _taskDefinition) {
		try{
			this.getHibernateTemplate().delete(_taskDefinition) ;
		}
		catch(Exception e){
			// Catch normally errors when we delete an unexisting TaskDefinition into the
			// db.
			logger.error("#### ERROR #### --- TaskDefinitionDao => deleteTask: trying to delete unexisting object \n" + e) ;
		}
	}
}
