package wilos.business.transfertobject;

import java.io.Serializable;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.guide.Guideline;

public class ConcreteTaskDescriptorTO extends ConcreteTaskDescriptor implements Serializable {
	/** Creates a new instance of ParticipantTO */
    public ConcreteTaskDescriptorTO() {

    }    
  
    public ConcreteTaskDescriptorTO(ConcreteTaskDescriptor myConcreteTaskDescriptor) {
    	this.setId(myConcreteTaskDescriptor.getId());
    	this.setConcreteName(myConcreteTaskDescriptor.getConcreteName());
    	this.setAccomplishedTime(myConcreteTaskDescriptor.getAccomplishedTime());
    	this.setPlannedFinishingDate(myConcreteTaskDescriptor.getPlannedFinishingDate());
    	this.setPlannedStartingDate(myConcreteTaskDescriptor.getPlannedStartingDate());
    	this.setPlannedTime(myConcreteTaskDescriptor.getPlannedTime());
    	// TODO ConcreteTask a completer
    	this.setState(myConcreteTaskDescriptor.getState());
    }
}