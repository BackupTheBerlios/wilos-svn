package wilos.business.services.guide;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.guide.GuidelineDao;
import wilos.hibernate.spem2.task.StepDao;
import wilos.model.spem2.guide.Guideline;
import wilos.model.spem2.task.Step;

/**
 * 
 * @author Nicolas CASTEL
 * 
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class GuidelineService {
	private GuidelineDao guidelineDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public Guideline getGuideline(String _id) {
		return this.guidelineDao.getGuideline(_id) ;
	}

	public List<Guideline> getAllGuidelines() {
		return this.guidelineDao.getAllGuidelines() ;
	}

	public void saveGuideline(Guideline _guideline) {
		this.guidelineDao.saveOrUpdateGuideline(_guideline) ;
	}

	public void deleteGuideline(Guideline _guideline) {
		this.guidelineDao.deleteGuideline(_guideline) ;
	}

	/**
	 * Getter of guidelineDao.
	 * 
	 * @return the guidelineDao.
	 */
	public GuidelineDao getGuidelineDao() {
		return this.guidelineDao ;
	}

	/**
	 * Setter of guidelineDao.
	 * 
	 * @param _guidelineDao
	 *            The guidelineDao to set.
	 */
	public void setGuidelineDao(GuidelineDao _guidelineDao) {
		this.guidelineDao = _guidelineDao ;
	}
}
