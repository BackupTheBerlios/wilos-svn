
package wilos.business.services.breakdownelement ;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.breakdownelement.BreakdownElementDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;

/**
 * 
 * @author Soosuske
 * 
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class BreakdownElementService {
	private BreakdownElementDao breakdownElementDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Return activities list
	 * 
	 * @return
	 */
	@ Transactional (readOnly = true)
	public List<BreakdownElement> getBreakdownElementsFromProcess(String _id) {
		List<BreakdownElement> tempList = this.breakdownElementDao.getAllBreakdownElements() ;
		List<BreakdownElement> returnedList = new ArrayList<BreakdownElement>() ;
		boolean flag = false ;

		for(BreakdownElement bde : tempList){
			flag = false ;
			for(Activity a : bde.getActivities()){
				if(a.getId().equals(_id)){
					flag = true ;
					break ;
				}
			}
			if(flag){
				returnedList.add(bde) ;
			}
		}
		return returnedList ;
	}

	/**
	 * @return the breakDownElementDao
	 */
	public BreakdownElementDao getBreakdownElementDao() {
		return this.breakdownElementDao ;
	}

	/**
	 * Setter of breakDownElementDao.
	 * 
	 * @param _breakDownElementDao
	 *            The breakDownElementDao to set.
	 */
	public void setBreakdownElementDao(BreakdownElementDao _breakdownElementDao) {
		this.breakdownElementDao = _breakdownElementDao ;
	}
}
