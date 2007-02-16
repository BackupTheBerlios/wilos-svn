package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    	
    	Set<ConcreteActivity> concreteActivitys = new HashSet<ConcreteActivity>();
        for (ConcreteActivity cta : myConcreteRoleDescriptor.getSuperConcreteActivities()) {
        	concreteActivitys.add(new ConcreteActivityTO(cta));
        }
        this.setSuperConcreteActivities(concreteActivitys);
    	
        Set<ConcreteTaskDescriptor> concreteTaskDescriptors = new HashSet<ConcreteTaskDescriptor>();
        for (ConcreteTaskDescriptor ctd : myConcreteRoleDescriptor.getConcreteTaskDescriptors()) {
        	concreteTaskDescriptors.add(new ConcreteTaskDescriptorTO(ctd));
        }
        this.setConcreteTaskDescriptors(concreteTaskDescriptors);

    }
}