package wilos.hibernate.spem2.checklist;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.checklist.CheckList;
import wilos.utils.ExceptionManager;

public class CheckListDAO extends HibernateDaoSupport{
	
	/**
	 * Save or update CheckList object
	 * 
	 * @param _checklist
	 */
	public void saveOrUpdateCheckList(CheckList _checklist) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_checklist) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateCheckList", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateCheckList", _ex);
		}
	}

	/**
	 * Get List of all CheckList object
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<CheckList> getAllCheckList() {
		List<CheckList> loadAll = new ArrayList<CheckList>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(CheckList.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllCheckList", _e);
		}
		return loadAll ;
	}

	/**
	 * Get the CheckList from the id
	 * 
	 * @param _id
	 * @return
	 */
	public CheckList getCheckList(String _id) {
		return (CheckList) this.getHibernateTemplate().get(CheckList.class, _id) ;
	}

	/**
	 * Delete a CheckList
	 * 
	 * @param _step
	 */
	public void deleteCheckList(CheckList _checklist) {
		try{
			this.getHibernateTemplate().delete(_checklist) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteCheckList", _e);
		}
	}
}
