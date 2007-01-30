package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.guide.Guideline;
import wilos.model.spem2.role.RoleDescriptor;

public class GuidelineTO extends Guideline implements Serializable{
	
	/** Creates a new instance of ParticipantTO */
    public GuidelineTO() {

    }    
  
    public GuidelineTO(Guideline myGuideline) {
        this.setName(myGuideline.getName());
        this.setGuid(myGuideline.getGuid());
        this.setDescription(myGuideline.getDescription());
    }
}
