package wilos.business.services.checklist;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import wilos.hibernate.spem2.checklist.CheckListDAO;
import wilos.model.spem2.checklist.CheckList;

@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class CheckListService {
	private CheckListDAO checkListDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public CheckList getCheckList(String _id) {
		return this.checkListDao.getCheckList(_id) ;
	}

	public List<CheckList> getAllCheckList() {
		return this.checkListDao.getAllCheckList() ;
	}

	public void saveCheckList(CheckList _checklist) {
		this.checkListDao.saveOrUpdateCheckList(_checklist) ;
	}

	public void deleteCheckList(CheckList _checklist) {
		this.checkListDao.deleteCheckList(_checklist) ;
	}

	/**
	 * Getter of checkListDAO.
	 * 
	 * @return the checkListDAO.
	 */
	public CheckListDAO getCheckListDao() {
		return this.checkListDao ;
	}

	/**
	 * Setter of checkListDAO.
	 * 
	 * @param _checklistDao
	 *            The checklistDAO to set.
	 */
	public void setGuidanceDao(CheckListDAO _checklistDao) {
		this.checkListDao = _checklistDao ;
	}
}