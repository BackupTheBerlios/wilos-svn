
package wilos.hibernate.spem2.task ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.task.TaskDefinition;

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
		try{
			this.getHibernateTemplate().saveOrUpdate(_taskDefinition) ;
			//this.getHibernateTemplate().flush() ;

		}
		catch(DataIntegrityViolationException e){
			System.out.print("save in TaskDefinitionDao: The Exception is " + e.getClass().getName() + "\n") ;

		}
		catch(ConstraintViolationException ex){
			System.out.print("save in TaskDefinitionDao: The Exception is " + ex.getClass().getName() + "\n") ;
		}
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
			logger.error("###TaskDefinitionDao ### --> "+e);
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
