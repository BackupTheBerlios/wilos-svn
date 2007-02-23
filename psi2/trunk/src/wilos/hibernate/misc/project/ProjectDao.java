package wilos.hibernate.misc.project;

import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import wilos.model.misc.project.Project;
import wilos.utils.ExceptionManager;

/**
 * ProjectDao manage requests from the system to store Project into the
 * database.
 * 
 * @author martial
 */
public class ProjectDao extends HibernateDaoSupport {
	/**
	 * Save or update an project.
	 * 
	 * @param _project
	 */
	public void saveOrUpdateProject(Project _project) {
		this.getHibernateTemplate().saveOrUpdate(_project);
	}

	/**
	 * Return a list of project.
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public Set<Project> getAllProjects() {
		Set<Project> loadAll = new HashSet<Project>();
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Project.class));
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllProject", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the project which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public Project getProject(String _id) {
		return (Project) this.getHibernateTemplate().get(Project.class, _id);
	}

	/**
	 * Delete the project.
	 * 
	 * @param _project
	 */
	public void deleteProject(Project _project) {
		try {
			this.getHibernateTemplate().delete(_project);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting project into the db.
			logger.error("#### ERROR #### --- ProjectDao => deleteProject : trying to delete unexisting object \n" + sose);
		}
	}
}
