/*
 * ParticipantTO.java
 *
 * Created on 9 décembre 2006, 14:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;

/**
 *
 * @author toine
 */
public class ParticipantTO extends Participant implements Serializable {
    
    /** Creates a new instance of ParticipantTO */
    public ParticipantTO() {

    }
    
  
    public ParticipantTO(Participant myParticipant) {
        this.setName(myParticipant.getName());
        this.setLogin(myParticipant.getLogin());
        this.setPassword(myParticipant.getPassword());
        this.setFirstname(myParticipant.getFirstname());
        this.setEmailAddress(myParticipant.getEmailAddress());  
        
        Set<ConcreteRoleDescriptor> concreteRoleDescriptors = new HashSet<ConcreteRoleDescriptor>();
        for (ConcreteRoleDescriptor crd : myParticipant.getConcreteRoleDescriptors()) {
        	concreteRoleDescriptors.add(new ConcreteRoleDescriptorTO(crd));
        }
        this.setConcreteRoleDescriptors(concreteRoleDescriptors);
    }

}
