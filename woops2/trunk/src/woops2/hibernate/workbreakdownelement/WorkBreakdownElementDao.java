package woops2.hibernate.workbreakdownelement;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import woops2.model.workbreakdownelement.WorkBreakdownElement;

/**
 * @author Sebastien
 * 
 * WorkBreakdownElementDao manage requests from the system to store wbdes
 * to the database
 * 
 */
public class WorkBreakdownElementDao extends HibernateDaoSupport {

	/**
	 * Save or update an activity
	 * 
	 * @param _activity
	 */
	public void saveOrUpdateWorkBreakdownElement(
			WorkBreakdownElement _workBreakdownElement) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_workBreakdownElement) ;
			//this.getHibernateTemplate().flush() ;

		}
		catch(DataIntegrityViolationException e){
			System.out.print("save in WBdEDao: The Exception is " + e.getClass().getName() + "\n") ;
			//e.printStackTrace() ;
		}
		catch(ConstraintViolationException ex){
			System.out.print("save in WBdEDao: The Exception is " + ex.getClass().getName() + "\n") ;
			//ex.printStackTrace() ;
		}
	}

	/**
	 * Return a list of activities
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WorkBreakdownElement> getAllWorkBreakdownElements() {
		List<WorkBreakdownElement> loadAll = new ArrayList<WorkBreakdownElement>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(
					WorkBreakdownElement.class));
		} catch (DataAccessException e) {
			logger.error("###WorkBreakdownElementDao ### --> " + e);
		}
		return loadAll;
	}

	/**
	 * Return the activity which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public WorkBreakdownElement getWorkBreakdownElement(String _id) {
		return (WorkBreakdownElement) this.getHibernateTemplate().get(
				WorkBreakdownElement.class, _id);
	}

	/**
	 * Delete the activity
	 * 
	 * @param _activity
	 */
	public void deleteWorkBreakdownElement(
			WorkBreakdownElement _workBreakdownElement) {
		try {
			this.getHibernateTemplate().delete(_workBreakdownElement);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting activity into
			// the db.
			logger
					.error("#### ERROR #### --- WorkBreakdownElementDao => deleteWorkBreakdownElement : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
