
package wilos.hibernate.spem2.element ;

import java.util.ArrayList ;
import java.util.List ;

import org.hibernate.exception.ConstraintViolationException ;
import org.springframework.dao.DataIntegrityViolationException ;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport ;

import wilos.model.spem2.element.Element ;

/**
 * ElementDao manage requests from the system to store Element to the database.
 * 
 * @author deder
 */
public class ElementDao extends HibernateDaoSupport {

	/**
	 * Save or update an element.
	 * 
	 * @param _element
	 */
	public void saveOrUpdateElement(Element _element) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_element) ;
		}
		catch(DataIntegrityViolationException e){
			System.out.print("save in ElementDao: The Exception is " + e.getClass().getName() + "\n") ;
		}
		catch(ConstraintViolationException ex){
			System.out.print("save in ElementDao: The Exception is " + ex.getClass().getName() + "\n") ;
		}
	}

	/**
	 * Return a list of elements.
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Element> getAllElements() {
		List<Element> loadAll = new ArrayList<Element>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Element.class)) ;
		}
		catch(Exception e){
			logger.error("###ElementDao ### --> " + e) ;
		}
		return loadAll ;
	}

	/**
	 * Return the element which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public Element getElement(String _id) {
		return (Element) this.getHibernateTemplate().get(Element.class, _id) ;
	}

	/**
	 * Delete the element.
	 * 
	 * @param _element
	 */
	public void deleteElement(Element _element) {
		try{
			this.getHibernateTemplate().delete(_element) ;
		}
		catch(Exception exception){
			//None. 
			//To inhibit the StaleObjectStateException.
		}
	}
}
