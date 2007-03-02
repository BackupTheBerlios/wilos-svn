package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
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
        this.setAttachment(myGuidance.getAttachment());
     
    }
}
