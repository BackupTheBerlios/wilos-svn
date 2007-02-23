package wilos.business.services.section;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.section.SectionDAO;
import wilos.model.spem2.section.Section;

@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class SectionService {
	private SectionDAO sectionDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public Section getSection(String _id) {
		return this.sectionDao.getSection(_id) ;
	}

	public List<Section> getAllSections() {
		return this.sectionDao.getAllSection() ;
	}

	public void saveSection(Section _section) {
		this.sectionDao.saveOrUpdateSection(_section) ;
	}

	public void deleteGuidance(Section _section) {
		this.sectionDao.deleteSection(_section) ;
	}

	/**
	 * Getter of sectionDAO.
	 * 
	 * @return the sectionDAO.
	 */
	public SectionDAO getSectionDao() {
		return this.sectionDao ;
	}

	/**
	 * Setter of sectionDao.
	 * 
	 * @param _sectionDAO
	 *            The sectionDAO to set.
	 */
	public void setSectionDao(SectionDAO _sectionDAO) {
		this.sectionDao = _sectionDAO ;
	}
}

