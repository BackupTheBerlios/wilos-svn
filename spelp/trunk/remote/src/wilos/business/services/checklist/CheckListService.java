package wilos.business.services.checklist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.checklist.CheckListDao;
import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.section.Section;

@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class CheckListService {
	private CheckListDao checkListDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public Set<Section> getSections(CheckList _checklist) {
		Set<Section> result = new HashSet<Section>();
		for (Section s :checkListDao.getCheckList(_checklist.getId()).getSections()) {
			result.add(s);
		}		
		return result;
	}
	
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
	public CheckListDao getCheckListDao() {
		return this.checkListDao ;
	}

	/**
	 * Setter of checkListDAO.
	 * 
	 * @param _checklistDao
	 *            The checklistDAO to set.
	 */
	public void setGuidanceDao(CheckListDao _checklistDao) {
		this.checkListDao = _checklistDao ;
	}

	public void setCheckListDao(CheckListDao checkListDao) {
		this.checkListDao = checkListDao;
	}
}
