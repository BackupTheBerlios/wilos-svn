package wilos.business.transfertobject;

import java.io.Serializable;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.activity.Activity;

public class ConcreteActivityTO extends ConcreteActivity implements Serializable{
	
	/** Creates a new instance of ParticipantTO */
    public ConcreteActivityTO() {

    }    
  
    public ConcreteActivityTO(ConcreteActivity _myConcreteActivity) {
    	// TODO
    	// this.setId(myConcreteRoleDescriptor.getId());
    	this.setConcreteName(_myConcreteActivity.getConcreteName());
    	this.setActivity(new ActivityTO(_myConcreteActivity.getActivity()));
    }

}
