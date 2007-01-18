package wilos.hibernate.misc.wilosuser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.wilosuser.ProcessManager;
import wilos.model.misc.wilosuser.ProjectDirector;

/**
 * ProjectDirectorDao manage requests from the system to store WilosUser into
 * the database.
 * 
 * @author Yoann Lopes
 * @author Martial Lapeyre
 */
public class ProjectDirectorDao extends HibernateDaoSupport {

	/**
	 * Save or update an projectdirector.
	 * 
	 * @param _projectdirector
	 */
	public void saveOrUpdateProjectDirector(ProjectDirector _projectdirector) {
		this.getHibernateTemplate().saveOrUpdate(_projectdirector);
	}

	/**
	 * Return a list of projectdirectors.
	 * 
	 * @return
	 */
	public Set<ProjectDirector> getAllProjectDirectors() {
		Set<ProjectDirector> loadAll = new HashSet<ProjectDirector>();
		loadAll.addAll(this.getHibernateTemplate().loadAll(ProjectDirector.class));
		return loadAll;
	}

	/**
	 * Return the projectdirector which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public ProjectDirector getProjectDirector(String _login) {
		ArrayList projectDirectors = (ArrayList)this.getHibernateTemplate().find("from ProjectDirector pd where pd.login=?",_login);
		if(projectDirectors.size()>0){
			return (ProjectDirector)projectDirectors.get(0);
		}else{
			return null;
		}
	}

	/**
	 * Delete the projectdirector.
	 * 
	 * @param _projectdirector
	 */
	public void deleteProjectDirector(ProjectDirector _projectdirector) {
		try {
			this.getHibernateTemplate().delete(_projectdirector);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting
			// projectdirector into the db.
			logger
					.error("#### ERROR #### --- ProjectDirectorDao => deleteProjectDirector : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
