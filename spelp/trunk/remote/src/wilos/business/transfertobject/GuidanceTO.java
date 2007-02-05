package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.role.RoleDescriptor;

public class GuidanceTO extends Guidance implements Serializable{
	
	/** Creates a new instance of ParticipantTO */
    public GuidanceTO() {

    }    
  
    public GuidanceTO(Guidance myGuidance) {
        this.setName(myGuidance.getName());
        this.setGuid(myGuidance.getGuid());
        this.setDescription(myGuidance.getDescription());
    }
}
