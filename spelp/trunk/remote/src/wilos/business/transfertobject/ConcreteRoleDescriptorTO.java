package wilos.business.transfertobject;

import java.io.Serializable;

import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;

public class ConcreteRoleDescriptorTO extends ConcreteRoleDescriptor implements Serializable {
	
	/** Creates a new instance of ParticipantTO */
    public ConcreteRoleDescriptorTO() {

    }    
  
    public ConcreteRoleDescriptorTO(ConcreteRoleDescriptor myConcreteRoleDescriptor) {
    	// TODO
    	// this.setId(myConcreteRoleDescriptor.getId());
    	this.setConcreteName(myConcreteRoleDescriptor.getConcreteName());
    	this.setRoleDescriptor(new RoleDescriptorTO(myConcreteRoleDescriptor.getRoleDescriptor()));
    	
    	// TODO this.setSuperConcreteActivities(arg0);
    	
        for (ConcreteTaskDescriptor ctd : myConcreteRoleDescriptor.getConcreteTaskDescriptors()) {
        	this.addConcreteTaskDescriptor(new ConcreteTaskDescriptorTO(ctd));
        }

    }
}
