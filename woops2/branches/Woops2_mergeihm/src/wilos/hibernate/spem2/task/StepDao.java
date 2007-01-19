
package wilos.hibernate.spem2.task ;

import java.util.ArrayList ;
import java.util.List ;

import org.hibernate.exception.ConstraintViolationException ;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException ;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport ;

import wilos.model.spem2.task.Step ;
import wilos.utils.ExceptionManager;

/**
 * A Step is a Section and Work Definition that is used to organize Tasks into parts or subunits of
 * work
 * 
 * @author garwind
 */
public class StepDao extends HibernateDaoSupport {

	/**
	 * Save or update Step object
	 * 
	 * @param _step
	 */
	public void saveOrUpdateStep(Step _step) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_step) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateStep", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateStep", _ex);
		}
	}

	/**
	 * Get List of all step object
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Step> getAllSteps() {
		List<Step> loadAll = new ArrayList<Step>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Step.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllSteps", _e);
		}
		return loadAll ;
	}

	/**
	 * Get the step from the id
	 * 
	 * @param _id
	 * @return
	 */
	public Step getStep(String _id) {
		return (Step) this.getHibernateTemplate().get(Step.class, _id) ;
	}

	/**
	 * Delete a step
	 * 
	 * @param _step
	 */
	public void deleteStep(Step _step) {
		try{
			this.getHibernateTemplate().delete(_step) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteStep", _e);
		}
	}
}
