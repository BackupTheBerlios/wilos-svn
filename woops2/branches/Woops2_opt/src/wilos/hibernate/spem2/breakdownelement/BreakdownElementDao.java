
package wilos.hibernate.spem2.breakdownelement ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.utils.ExceptionManager;

/**
 * BreakdownElementDao manage requests from the system to store BreakdownElement to the database.
 * 
 * @author deder
 */
public class BreakdownElementDao extends HibernateDaoSupport {

	/**
	 * Save or update an bde.
	 * 
	 * @param _bde
	 */
	public void saveOrUpdateBreakdownElement(BreakdownElement _bde) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_bde) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateBreakdownElement", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateBreakdownElement", _ex);
		}
	}

	/**
	 * Return a list of bdes.
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<BreakdownElement> getAllBreakdownElements() {
		//this.getHibernateTemplate().setCacheQueries(true);
		List<BreakdownElement> loadAll = new ArrayList<BreakdownElement>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(BreakdownElement.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllBreakdownElements", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the bde which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public BreakdownElement getBreakdownElement(String _id) {
		//this.getHibernateTemplate().setCacheQueries(true);
		return (BreakdownElement) this.getHibernateTemplate().get(BreakdownElement.class, _id) ;
	}

	/**
	 * Delete the bde.
	 * 
	 * @param _bde
	 */
	public void deleteBreakdownElement(BreakdownElement _bde) {
		try{
			this.getHibernateTemplate().delete(_bde) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteBreakdownElement", _e);
		}
	}
}
