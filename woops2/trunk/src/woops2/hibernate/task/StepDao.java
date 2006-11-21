
package woops2.hibernate.task ;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import woops2.model.task.Step;

/**
 * TODO finir commentaires
 * 
 * @author garwind
 * 
 */
public class StepDao extends HibernateDaoSupport {

	public void saveOrUpdateStep(Step _step) {
		this.getHibernateTemplate().saveOrUpdate(_step) ;
	}
	
	@SuppressWarnings("unchecked")
	public List<Step> getAllSteps() {
		List<Step> loadAll = new ArrayList<Step>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(Step.class));
		} catch (Exception e) {
			logger.error("### StepDao ### --> "+e);
		}
		return loadAll ;
	}

	public Step getStep(String _id) {
		return (Step) this.getHibernateTemplate().get(Step.class, _id) ;
	}

	public void deleteStep(Step _step) {
		try{
			this.getHibernateTemplate().delete(_step) ;
		}
		catch(Exception e){
			// Catch normally errors when we delete an unexisting activity into
			// the db.
			logger.error("#### ERROR #### --- StepDao => deleteStep : trying to delete unexisting object \n" + e) ;
		}
	}
}
