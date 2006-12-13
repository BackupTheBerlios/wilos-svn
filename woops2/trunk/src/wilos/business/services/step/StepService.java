package wilos.business.services.step;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.task.StepDao;
import wilos.model.spem2.task.Step;

/**
 * 
 * @author Soosuske
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class StepService {
	private StepDao stepDao ;
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/**
	 * Return activities list
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Step> getStepsFromTask(String _id) {
		List<Step> tempList =  this.stepDao.getAllSteps();
		List<Step> returnedList =  new ArrayList<Step>();
		
		for (Step ls : tempList) {
			if (ls.getTaskDefinition().getId().equals(_id)) {
				returnedList.add(ls);
				logger.debug("###StepDao ### added => "+ls);
			}	
		}
		return returnedList;
	}

	/**
	 * Getter of StepDao.
	 *
	 * @return the stepDao.
	 */
	public StepDao getStepDao() {
		return this.stepDao ;
	}

	/**
	 * Setter of stepDao.
	 *
	 * @param _stepDao The stepDao to set.
	 */
	public void setStepDao(StepDao _stepDao) {
		this.stepDao = _stepDao ;
	}
}
