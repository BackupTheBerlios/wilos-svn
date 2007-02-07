package wilos.business.transfertobject;

import java.io.Serializable;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;

public class ConcreteRoleDescriptorTO extends ConcreteRoleDescriptor implements Serializable {
	
	/** Creates a new instance of ParticipantTO */
    public ConcreteRoleDescriptorTO() {

    }    
  
    public ConcreteRoleDescriptorTO(ConcreteRoleDescriptor myConcreteRoleDescriptor) {
    	this.setId(myConcreteRoleDescriptor.getId());
    	this.setConcreteName(myConcreteRoleDescriptor.getConcreteName());
    	this.setRoleDescriptor(new RoleDescriptorTO(myConcreteRoleDescriptor.getRoleDescriptor()));
    	
        for (ConcreteActivity cta : myConcreteRoleDescriptor.getSuperConcreteActivities()) {
        	this.addSuperConcreteActivity(new ConcreteActivityTO(cta));
        }
    	
        for (ConcreteTaskDescriptor ctd : myConcreteRoleDescriptor.getConcreteTaskDescriptors()) {
        	this.addConcreteTaskDescriptor(new ConcreteTaskDescriptorTO(ctd));
        }

    }
}
