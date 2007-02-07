package wilos.business.transfertobject;

import java.io.Serializable;

import wilos.model.spem2.guide.Guidance;

public class GuidanceTO extends Guidance implements Serializable{
	
	/** Creates a new instance of ParticipantTO */
    public GuidanceTO() {

    }    
  
    public GuidanceTO(Guidance myGuidance) {
        this.setName(myGuidance.getName());
        this.setGuid(myGuidance.getGuid());
        this.setDescription(myGuidance.getDescription());
        this.setType(myGuidance.getType());
        this.setPresentationName(myGuidance.getPresentationName());
        this.setActivity(myGuidance.getActivity());
        this.setRoledefinition(myGuidance.getRoledefinition());
        this.setTaskdefinition(myGuidance.getTaskdefinition());
    }
}
