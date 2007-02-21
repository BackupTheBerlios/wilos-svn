package wilos.hibernate.spem2.section;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.section.Section;
import wilos.utils.ExceptionManager;

public class SectionDAO extends HibernateDaoSupport{
	
	/**
	 * Save or update Section object
	 * 
	 * @param _section
	 */
	public void saveOrUpdateSection(Section _section) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_section) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateSection", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateSection", _ex);
		}
	}

	/**
	 * Get List of all section object
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Section> getAllSection() {
		List<Section> loadAll = new ArrayList<Section>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Section.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllSection", _e);
		}
		return loadAll ;
	}

	/**
	 * Get the Section from the id
	 * 
	 * @param _id
	 * @return
	 */
	public Section getSection(String _id) {
		return (Section) this.getHibernateTemplate().get(Section.class, _id) ;
	}

	/**
	 * Delete a Section
	 * 
	 * @param _step
	 */
	public void deleteSection(Section _section) {
		try{
			this.getHibernateTemplate().delete(_section) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteSection", _e);
		}
	}

}
