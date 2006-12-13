package wilos.hibernate.spem2.phase;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.phase.Phase;
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
			//this.getHibernateTemplate().flush() ;

		}
		catch(DataIntegrityViolationException e){
			System.out.print("save in PhaseDao: The Exception is " + e.getClass().getName() + "\n") ;
		}
		catch(ConstraintViolationException ex){
			System.out.print("save in PhaseDao: The Exception is " + ex.getClass().getName() + "\n") ;
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
		catch(Exception e){
			logger.error("###PhaseDao ### --> " + e) ;
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

	/**
	 * Delete the phase
	 * 
	 * @param _phase
	 */
	public void deletePhase(Phase _phase) {
		if(this.getPhase(_phase.getId()) != null)
			this.getHibernateTemplate().delete(_phase) ;
	}
}
