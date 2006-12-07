
package wilos.hibernate.misc.wilosuser ;

import java.util.HashSet;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.wilosuser.WilosUser;

/**
 * WilosUserDao manage requests from the system to store WilosUser to the database.
 * 
 * @author Yoann Lopes
 * @author Martial Lapeyre
 */
public class WilosUserDao extends HibernateDaoSupport {

	/**
	 * Save or update an wilosuser.
	 * 
	 * @param _wilosuser
	 */
	public void saveOrUpdateWilosUser(WilosUser _wilosuser) {
		this.getHibernateTemplate().saveOrUpdate(_wilosuser) ;
	}

	/**
	 * Return a list of wilosusers.
	 * 
	 * @return
	 */
	public Set<WilosUser> getAllWilosUsers() {
		Set<WilosUser> loadAll = new HashSet<WilosUser>() ;
		loadAll.addAll(this.getHibernateTemplate().loadAll(WilosUser.class)) ;
		return loadAll ;
	}

	/**
	 * Return the wilosuser which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public WilosUser getWilosUser(String _id) {
		return (WilosUser) this.getHibernateTemplate().get(WilosUser.class, _id) ;
	}

	/**
	 * Delete the wilosuser.
	 * 
	 * @param _wilosuser
	 */
	public void deleteWilosUser(WilosUser _wilosuser) {
		try{
			this.getHibernateTemplate().delete(_wilosuser) ;
		}
		catch(Exception sose){
			// Catch normally errors when we delete an unexisting wilosuser into the db.
			logger.error("#### ERROR #### --- WilosUserDao => deleteWilosUser : trying to delete unexisting object \n" + sose) ;
		}
	}
}
