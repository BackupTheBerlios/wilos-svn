package wilos.business.services.guide;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.guide.GuidanceDao;
import wilos.model.spem2.guide.Guidance;

/**
 * 
 * @author Nicolas CASTEL
 * 
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class GuidanceService {
	private GuidanceDao guidanceDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public Guidance getGuidance(String _id) {
		return this.guidanceDao.getGuidance(_id) ;
	}

	public List<Guidance> getAllGuidances() {
		return this.guidanceDao.getAllGuidances() ;
	}

	public void saveGuidance(Guidance _guidance) {
		this.guidanceDao.saveOrUpdateGuidance(_guidance) ;
	}

	public void deleteGuidance(Guidance _guidance) {
		this.guidanceDao.deleteGuidance(_guidance) ;
	}
	
	public Guidance getGuidanceFromGuid(String _guid) {
		return this.guidanceDao.getGuidanceFromGuid(_guid);
	}

	/**
	 * Getter of guidelineDao.
	 * 
	 * @return the guidelineDao.
	 */
	public GuidanceDao getGuidanceDao() {
		return this.guidanceDao ;
	}

	/**
	 * Setter of guidelineDao.
	 * 
	 * @param _guidanceDao
	 *            The guidelineDao to set.
	 */
	public void setGuidanceDao(GuidanceDao _guidanceDao) {
		this.guidanceDao = _guidanceDao ;
	}
}
