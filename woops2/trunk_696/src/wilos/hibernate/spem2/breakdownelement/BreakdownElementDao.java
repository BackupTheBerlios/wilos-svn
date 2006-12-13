package wilos.hibernate.spem2.breakdownelement;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.breakdownelement.BreakdownElement;

/**
 * BreakdownElementDao manage requests from the system to store BreakdownElement
 * to the database.
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
			//this.getHibernateTemplate().flush() ;

		}
		catch(DataIntegrityViolationException e){
			System.out.print("save in BreakdownElementDao: The Exception is " + e.getClass().getName() + "\n") ;
		}
		catch(ConstraintViolationException ex){
			System.out.print("save in BreakdownElementDao: The Exception is " + ex.getClass().getName() + "\n") ;
		}
	}

	/**
	 * Return a list of bdes.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BreakdownElement> getAllBreakdownElements() {
		List<BreakdownElement> loadAll = new ArrayList<BreakdownElement>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(BreakdownElement.class));
		} catch (Exception e) {
			logger.error("###BreakdownElementDao ### --> "+e);
		}
		return loadAll;
	}

	/**
	 * Return the bde which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public BreakdownElement getBreakdownElement(String _id) {
		return (BreakdownElement) this.getHibernateTemplate().get(
				BreakdownElement.class, _id);
	}

	/**
	 * Delete the bde.
	 * 
	 * @param _bde
	 */
	public void deleteBreakdownElement(BreakdownElement _bde) {
		try {
			this.getHibernateTemplate().delete(_bde);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting element into
			// the db.
			logger.error("#### ERROR #### --- BreakdownElementdao => deleteBreakdownElement : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
