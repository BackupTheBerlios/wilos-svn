package wilos.hibernate.misc.concreteworkbreakdownelement;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.utils.ExceptionManager;

/**
 * @author Sylvain
 * 
 * ConcreteWorkBreakdownElementDao manage requests from the system to store wbdes to the database
 * 
 */

public class ConcreteWorkBreakdownElementDao extends HibernateDaoSupport {
	/**
	 * Save or update an activity
	 * 
	 * @param _activity
	 */
	public void saveOrUpdateConcreteWorkBreakdownElement(ConcreteWorkBreakdownElement _concreteworkBreakdownElement) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_concreteworkBreakdownElement) ;
			// this.getHibernateTemplate().flush() ;

		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateConcreteWorkBreakdownElement", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateConcreteWorkBreakdownElement", _ex);
		}
	}

	/**
	 * Return a list of activities
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<ConcreteWorkBreakdownElement> getAllConcreteWorkBreakdownElements() {
		List<ConcreteWorkBreakdownElement> loadAll = new ArrayList<ConcreteWorkBreakdownElement>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(ConcreteWorkBreakdownElement.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllConcreteWorkBreakdownElements", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the activity which has the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public ConcreteWorkBreakdownElement getConcreteWorkBreakdownElement(String _id) {
		return (ConcreteWorkBreakdownElement) this.getHibernateTemplate().get(ConcreteWorkBreakdownElement.class, _id) ;
	}

	/**
	 * Delete the activity
	 * 
	 * @param _activity
	 */
	public void deleteConcreteWorkBreakdownElement(ConcreteWorkBreakdownElement _concreteworkBreakdownElement) {
		try{
			this.getHibernateTemplate().delete(_concreteworkBreakdownElement) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteConcreteWorkBreakdownElement", _e);
		}
	}
}
