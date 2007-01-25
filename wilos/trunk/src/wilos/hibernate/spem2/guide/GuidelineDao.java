package wilos.hibernate.spem2.guide;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.guide.Guideline;
import wilos.utils.ExceptionManager;

public class GuidelineDao extends HibernateDaoSupport {
	
	/**
	 * Save or update Guideline object
	 * 
	 * @param _guideline
	 */
	public void saveOrUpdateGuideline(Guideline _guideline) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_guideline) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateGuideline", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateGuideline", _ex);
		}
	}

	/**
	 * Get List of all step object
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Guideline> getAllGuidelines() {
		List<Guideline> loadAll = new ArrayList<Guideline>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Guideline.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllGuidelines", _e);
		}
		return loadAll ;
	}

	/**
	 * Get the step from the id
	 * 
	 * @param _id
	 * @return
	 */
	public Guideline getGuideline(String _id) {
		return (Guideline) this.getHibernateTemplate().get(Guideline.class, _id) ;
	}

	/**
	 * Delete a step
	 * 
	 * @param _step
	 */
	public void deleteGuideline(Guideline _guideline) {
		try{
			this.getHibernateTemplate().delete(_guideline) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteGuideline", _e);
		}
	}
}
