package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.guide.Guidance;

public class ConcreteActivityTO extends ConcreteActivity implements Serializable{
	
	/** Creates a new instance of ParticipantTO */
    public ConcreteActivityTO() {

    }    
  
    public ConcreteActivityTO(ConcreteActivity _myConcreteActivity) {
    	this.setId(_myConcreteActivity.getId());
    	this.setConcreteName(_myConcreteActivity.getConcreteName());
    	this.setActivity(new ActivityTO(_myConcreteActivity.getActivity()));

    	Set<ConcreteActivity> concreteActivitys = new HashSet<ConcreteActivity>();
        for (ConcreteActivity cta : _myConcreteActivity.getSuperConcreteActivities()) {
        	concreteActivitys.add(new ConcreteActivityTO(cta));
        }
        this.setSuperConcreteActivities(concreteActivitys);
    }

}
