
package wilos.business.services.spem2.breakdownelement ;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.hibernate.misc.concretebreakdownelement.ConcreteBreakdownElementDao;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;

/**
 * BreakdownElement is a transactional class that manages operations about breakdownelement,
 * @author Soosuske
 *
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class BreakdownElementService {

	private ConcreteBreakdownElementDao concreteBreakdownElementDao ;

	private ActivityService activityService;

	/**
	 * Instanciates a BreakdownElement
	 * @param _project project for which the BreakdownElement shall be instanciated
	 * @param _bde BreakdownElement to instanciate
	 */
	public void breakdownElementInstanciation (Project _project, BreakdownElement _bde) {

		ConcreteBreakdownElement cbe = new ConcreteBreakdownElement();

		if (_bde.getPresentationName() == null)
			cbe.setConcreteName(_bde.getName()) ;
		else
			cbe.setConcreteName(_bde.getPresentationName());

		cbe.addBreakdownElement(_bde);

		/* TODO verifier code par un M1 :) */
		/* instanciating and adding all the ConcreteActivities included in the breakdownelement */
		for (Activity act : _bde.getSuperActivities()) {
			if (act.getBreakdownElements().size() == 0) {
				this.activityService.activityInstanciation(_project, act);
			}
			_bde.addAllConcreteBreakdownElements(act.getConcreteBreakdownElements());
		}

		this.concreteBreakdownElementDao.saveOrUpdateConcreteBreakdownElement(cbe);
	}

	/**
	 * Getter of concreteBreakdownElementDao
	 *
	 * @return the concreteBreakdownElementDao
	 */
	public ConcreteBreakdownElementDao getConcreteBreakdownElementDao() {
		return concreteBreakdownElementDao;
	}

	/**
	 * Setter of concreteBreakdownElementDao
	 *
	 * @param concreteBreakdownElementDao the concreteBreakdownElementDao to set
	 */
	public void setConcreteBreakdownElementDao(
			ConcreteBreakdownElementDao concreteBreakdownElementDao) {
		this.concreteBreakdownElementDao = concreteBreakdownElementDao;
	}

	/**
	 * @return the activityService
	 */
	public ActivityService getActivityService() {
		return activityService;
	}

	/**
	 * @param activityService the activityService to set
	 */
	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

}
