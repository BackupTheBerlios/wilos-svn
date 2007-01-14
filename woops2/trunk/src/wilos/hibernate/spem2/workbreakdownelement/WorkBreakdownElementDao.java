
package wilos.hibernate.spem2.workbreakdownelement ;

import java.util.ArrayList ;
import java.util.List ;

import org.hibernate.exception.ConstraintViolationException ;
import org.springframework.dao.DataAccessException ;
import org.springframework.dao.DataIntegrityViolationException ;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport ;

import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement ;
import wilos.utils.ExceptionManager;

/**
 * @author Sebastien
 * 
 * WorkBreakdownElementDao manage requests from the system to store wbdes to the database
 * 
 */
public class WorkBreakdownElementDao extends HibernateDaoSupport {

	/**
	 * Save or update an activity
	 * 
	 * @param _activity
	 */
	public void saveOrUpdateWorkBreakdownElement(WorkBreakdownElement _workBreakdownElement) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_workBreakdownElement) ;
			// this.getHibernateTemplate().flush() ;

		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateWorkBreakdownElement", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateWorkBreakdownElement", _ex);
		}
	}

	/**
	 * Return a list of activities
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<WorkBreakdownElement> getAllWorkBreakdownElements() {
		List<WorkBreakdownElement> loadAll = new ArrayList<WorkBreakdownElement>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(WorkBreakdownElement.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllWorkBreakdownElements", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the activity which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public WorkBreakdownElement getWorkBreakdownElement(String _id) {
		return (WorkBreakdownElement) this.getHibernateTemplate().get(WorkBreakdownElement.class, _id) ;
	}

	/**
	 * Delete the activity
	 * 
	 * @param _activity
	 */
	public void deleteWorkBreakdownElement(WorkBreakdownElement _workBreakdownElement) {
		try{
			this.getHibernateTemplate().delete(_workBreakdownElement) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteWorkBreakdownElement", _e);
		}
	}
}
