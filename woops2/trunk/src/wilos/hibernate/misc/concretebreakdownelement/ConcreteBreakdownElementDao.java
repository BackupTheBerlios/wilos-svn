
package wilos.hibernate.misc.concretebreakdownelement ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.utils.ExceptionManager;

/**
 * ConcreteBreakdownElementDao manages requests from the system to store ConcreteBreakdownElement to the database.
 * 
 * @author nanawel
 */
public class ConcreteBreakdownElementDao extends HibernateDaoSupport {

	/**
	 * Saves or updates a cbde.
	 * 
	 * @param _cbde
	 */
	public void saveOrUpdateConcreteBreakdownElement(ConcreteBreakdownElement _cbde) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_cbde) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateConcreteBreakdownElement", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateConcreteBreakdownElement", _ex);
		}
	}

	/**
	 * Returns a list of cbdes.
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<ConcreteBreakdownElement> getAllConcreteBreakdownElements() {
		List<ConcreteBreakdownElement> loadAll = new ArrayList<ConcreteBreakdownElement>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(ConcreteBreakdownElement.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllConcreteBreakdownElements", _e);
		}
		return loadAll ;
	}

	/**
	 * Returns the cbde which has the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public ConcreteBreakdownElement getConcreteBreakdownElement(String _id) {
		return (ConcreteBreakdownElement) this.getHibernateTemplate().get(ConcreteBreakdownElement.class, _id) ;
	}

	/**
	 * Deletes the cbde.
	 * 
	 * @param _cbde
	 */
	public void deleteConcreteBreakdownElement(ConcreteBreakdownElement _cbde) {
		try{
			this.getHibernateTemplate().delete(_cbde) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteConcreteBreakdownElement", _e);
		}
	}
}
