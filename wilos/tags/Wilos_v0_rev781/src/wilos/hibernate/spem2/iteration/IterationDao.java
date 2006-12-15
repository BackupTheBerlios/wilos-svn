package wilos.hibernate.spem2.iteration;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.iteration.Iteration;

/**
 * IterationDao manage requests from the system to store iterations to the database
 * 
 * @author soosuske
 */
public class IterationDao  extends HibernateDaoSupport{
	/**
	 * Save or update an iteration
	 * 
	 * @param _iteration
	 */
	public void saveOrUpdateIteration(Iteration _iteration) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_iteration) ;

		}
		catch(DataIntegrityViolationException e){
			System.out.print("save in IterationDao: The Exception is " + e.getClass().getName() + "\n") ;
		}
		catch(ConstraintViolationException ex){
			System.out.print("save in IterationDao: The Exception is " + ex.getClass().getName() + "\n") ;
		}
	}

	/**
	 * Return a list of iterations.
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Iteration> getAllIterations() {
		List<Iteration> loadAll = new ArrayList<Iteration>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Iteration.class)) ;

		}
		catch(Exception e){
			logger.error("###IterationDao ### --> " + e) ;
		}
		return loadAll ;
	}

	/**
	 * Return the iteration which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public Iteration getIteration(String _id) {
		return (Iteration) this.getHibernateTemplate().get(Iteration.class, _id) ;
	}

	/**
	 * Delete the iteration
	 * 
	 * @param _iteration
	 */
	public void deleteIteration(Iteration _iteration) {
		if(this.getIteration(_iteration.getId()) != null)
			this.getHibernateTemplate().delete(_iteration) ;
	}
}
