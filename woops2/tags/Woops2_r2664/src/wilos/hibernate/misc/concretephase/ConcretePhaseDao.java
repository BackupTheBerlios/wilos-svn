package wilos.hibernate.misc.concretephase;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.utils.ExceptionManager;

/**
 * 
 * @author Soosuske
 * 
 */
public class ConcretePhaseDao extends HibernateDaoSupport {
	/**
	 * Save or update a Concretephase
	 * 
	 * @param _concretePhase
	 */
	public void saveOrUpdateConcretePhase(ConcretePhase _concretePhase) {
		try {
			this.getHibernateTemplate().saveOrUpdate(_concretePhase);
		} catch (DataIntegrityViolationException _e) {
			ExceptionManager.getInstance()
					.manageDataIntegrityViolationException(
							this.getClass().getName(), "saveOrUpdateConcretePhase", _e);
		} catch (ConstraintViolationException _ex) {
			ExceptionManager.getInstance().manageConstraintViolationException(
					this.getClass().getName(), "saveOrUpdateConcretePhase", _ex);
		}
	}

	/**
	 * Return a list of Concretephases.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ConcretePhase> getAllConcretePhases() {
		List<ConcretePhase> loadAll = new ArrayList<ConcretePhase>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(ConcretePhase.class));

		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getAllConcretePhases", _e);
		}
		return loadAll;
	}

	/**
	 * Return the Concretephase which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public ConcretePhase getConcretePhase(String _id) {
		return (ConcretePhase) this.getHibernateTemplate().get(ConcretePhase.class, _id);
	}

	@SuppressWarnings("unchecked")
	public List<BreakdownElement> getBreakdownElementsFromPhase(String _concretePhaseId) {
		List bdes = this
				.getHibernateTemplate()
				.find(
						"from BreakdownElement bde join bde.superActivities s where s.id=?",
						_concretePhaseId);
		List<BreakdownElement> listBdes = new ArrayList<BreakdownElement>();
		if (bdes.get(0) instanceof List){
			for (Object o : (ArrayList) bdes.get(0)) {
				if (o instanceof BreakdownElement) {
					BreakdownElement bde = (BreakdownElement) o;
					listBdes.add(bde);
				}
			}
		}
		return listBdes;
	}

	/**
	 * Delete the Concretephase
	 * 
	 * @param _concretePhase
	 */
	public void deleteConcretePhase(ConcretePhase _concretePhase) {
		try {
			this.getHibernateTemplate().delete(_concretePhase);
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "deleteConcretePhase", _e);
		}
	}
}
