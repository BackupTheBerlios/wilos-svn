package wilos.business.transfertobject;

import java.util.HashSet;
import java.util.Set;

import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcreteRoleDescriptorTO extends ConcreteRoleDescriptor {
	
	/** Creates a new instance of ParticipantTO */
    public ConcreteRoleDescriptorTO() {

    }    
  
    public ConcreteRoleDescriptorTO(ConcreteRoleDescriptor myConcreteRoleDescriptor) {
    	// TODO
    	// this.setId(myConcreteRoleDescriptor.getId());
    	this.setConcreteName(myConcreteRoleDescriptor.getConcreteName());
    	this.setRoleDescriptor(new RoleDescriptorTO(myConcreteRoleDescriptor.getRoleDescriptor()));
    	
        for (ConcreteTaskDescriptor ctd : myConcreteRoleDescriptor.getConcreteTaskDescriptors()) {
        	this.addConcreteTaskDescriptor(new ConcreteTaskDescriptorTO(ctd));
        }

    }
}
