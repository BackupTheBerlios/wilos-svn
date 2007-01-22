
package wilos.hibernate.spem2.phase ;

import java.util.ArrayList ;
import java.util.List ;

import org.hibernate.exception.ConstraintViolationException ;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException ;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport ;

import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.phase.Phase ;
import wilos.utils.ExceptionManager;

/**
 * 
 * @author Soosuske
 * 
 */
public class PhaseDao extends HibernateDaoSupport {
	/**
	 * Save or update an phase
	 * 
	 * @param _phase
	 */
	public void saveOrUpdatePhase(Phase _phase) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_phase) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdatePhase", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdatePhase", _ex);
		}
	}

	/**
	 * Return a list of phases.
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Phase> getAllPhases() {
		List<Phase> loadAll = new ArrayList<Phase>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Phase.class)) ;

		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllPhases", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the phase which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public Phase getPhase(String _id) {
		return (Phase) this.getHibernateTemplate().get(Phase.class, _id) ;
	}
	
	@ SuppressWarnings ("unchecked")
	public List<BreakdownElement> getBreakdownElementsFromPhase(String _phaseId) {
		List<BreakdownElement> bdes = this.getHibernateTemplate().find("from BreakdownElement bde join bde.superActivities s where s.id=?", _phaseId) ;
		return bdes;
	}

	/**
	 * Delete the phase
	 * 
	 * @param _phase
	 */
	public void deletePhase(Phase _phase) {
		try{
			this.getHibernateTemplate().delete(_phase) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deletePhase", _e);
		}
	}
}
