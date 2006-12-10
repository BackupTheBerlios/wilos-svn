package wilos.hibernate.misc.wilosuser;

import java.util.HashSet;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.wilosuser.Administrator;

/**
 * AdministratorDao manage requests from the system to store Administrator into
 * the database.
 * 
 * @author Yoann
 * @author Lopes
 * @author Martial Lapeyre
 */
public class AdministratorDao extends HibernateDaoSupport {

	/**
	 * Save or update an Administator.
	 * 
	 * @param _administrator
	 */
	public void saveOrUpdateAdministrator(Administrator _administrator) {
		this.getHibernateTemplate().saveOrUpdate(_administrator);
	}

	/**
	 * Return a list of administrators.
	 * 
	 * @return
	 */
	public Set<Administrator> getAllAdministrators() {
		Set<Administrator> loadAll = new HashSet<Administrator>();
		loadAll
				.addAll(this.getHibernateTemplate()
						.loadAll(Administrator.class));
		return loadAll;
	}

	/**
	 * Return the administrator which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public Administrator getAdministrator(String _id) {
		return (Administrator) this.getHibernateTemplate().get(
				Administrator.class, _id);
	}

	/**
	 * Delete the administrator.
	 * 
	 * @param _administrator
	 */
	public void deleteAdministrator(Administrator _administrator) {
		try {
			this.getHibernateTemplate().delete(_administrator);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting administrator
			// into the db.
			logger
					.error("#### ERROR #### --- AdministratorDao => deleteAdministrator : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
