package woops2.business.participant;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import woops2.hibernate.participant.ParticipantDao;
import woops2.model.participant.Participant;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ParticipantManager {

	
private ParticipantDao participantDao ;
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/**
	 * Return participant list
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public Set<Participant> getParticipantsList() {
		return this.participantDao.getAllParticipants();
	}
	
	/**
	 * Function that make some test on transactionnal lazy loadings
	 *
	 
	public void Test(){
		Activity a = this.participantDao.getActivityFromPrefix("test");
		if (a == null){
			a = new Activity();
			a.setPrefix("test");
			this.activityDao.saveOrUpdateActivity(a);
			BreakdownElement b = new BreakdownElement();
			this.activityDao.getHibernateTemplate().save(b);
			a.getBreakDownElements().add(b);
			this.activityDao.saveOrUpdateActivity(a);
		}
		List<BreakdownElement> liste = new ArrayList<BreakdownElement>(a.getBreakDownElements());
		logger.debug("### ActivityManager - TEST ###  liste size = "+liste.size());
		for (BreakdownElement b : liste){
			System.out.println("b="+b);
		}
	}*/
	
	/**
	 * Save participant
	 * 
	 * @param _participant
	 */
	public void saveParticipant(Participant _participant) {
		participantDao.saveOrUpdateParticipant(_participant) ;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
}
