package wilos.hibernate.spem2.task;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.task.TaskDescriptor;

/**
 * TaskDescriptorDao manage requests from the system to store TaskDescriptor to
 * the database
 * 
 * @author eperico
 * 
 */
public class TaskDescriptorDao extends HibernateDaoSupport {

	/**
	 * Save or update a TaskDescriptor
	 * 
	 * @param _taskdescriptor
	 */
	public void saveOrUpdateTaskDescriptor(TaskDescriptor _taskdescriptor) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_taskdescriptor) ;
			//this.getHibernateTemplate().flush() ;

		}
		catch(DataIntegrityViolationException e){
			System.out.print("save in TaskDescriptorDao The Exception is " + e.getClass().getName() + "\n") ;
		}
		catch(ConstraintViolationException ex){
			System.out.print("save in TaskDescriptorDao: The Exception is " + ex.getClass().getName() + "\n") ;
		}
	}

	/**
	 * Return a set of TaskDescriptor
	 * 
	 * @return set <TaskDescriptor>
	 */
	@SuppressWarnings("unchecked")
	public List<TaskDescriptor> getAllTaskDescriptor() {
		List<TaskDescriptor> loadAll = new ArrayList<TaskDescriptor>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(TaskDescriptor.class));
		} catch (Exception e) {
			logger.error("###TaskDescriptorDao ### --> "+e);
		}
		return loadAll;
	}

	/**
	 * Return the TaskDescriptor which have the id _id
	 * 
	 * @param _id
	 * @return TaskDescriptor
	 */
	public TaskDescriptor getTaskDescriptor(String _id) {
		return (TaskDescriptor) this.getHibernateTemplate().get(
				TaskDescriptor.class, _id);
	}

	/**
	 * Delete the TaskDescriptor
	 * 
	 * @param _taskdescriptor
	 */
	public void deleteTaskDescriptor(TaskDescriptor _taskdescriptor) {
		try {
			this.getHibernateTemplate().delete(_taskdescriptor);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting TaskDescriptor
			// into the db.
			logger.error("#### ERROR #### --- TaskDescriptorDao => deleteTaskDescriptor : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
