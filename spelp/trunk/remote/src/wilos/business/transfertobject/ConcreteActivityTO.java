package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.project.Project;

public class ConcreteActivityTO extends ConcreteActivity implements Serializable{
	
	/** Creates a new instance of ParticipantTO */
    public ConcreteActivityTO() {

    }    
  
    public ConcreteActivityTO(ConcreteActivity _myConcreteActivity) {
    	this.setId(_myConcreteActivity.getId());
    	this.setConcreteName(_myConcreteActivity.getConcreteName());
    	this.setActivity(new ActivityTO(_myConcreteActivity.getActivity()));

    	Set<ConcreteActivity> concreteActivitys = new HashSet<ConcreteActivity>();
  
    	/*for (ConcreteActivity cta : _myConcreteActivity.getSuperConcreteActivities()) {
        	concreteActivitys.add(new ConcreteActivityTO(cta));
        }*/
    	concreteActivitys = getConcreteActivities(_myConcreteActivity);
 
    	this.setSuperConcreteActivities(concreteActivitys);
    }

    
    public static HashSet<ConcreteActivity> getConcreteActivities(ConcreteBreakdownElement ca) {

    	HashSet<ConcreteActivity> concreteActivities = new HashSet<ConcreteActivity>();
        for (ConcreteActivity cta : ca.getSuperConcreteActivities()) {
        	if (cta instanceof ConcreteIteration) {
        		concreteActivities.add(new ConcreteIterationTO((ConcreteIteration)cta));
        		//concreteActivities.add(new ConcreteActivityTO((ConcreteIteration)cta));
        	} else {
        		if (cta instanceof ConcretePhase) {
	        		concreteActivities.add(new ConcretePhaseTO((ConcretePhase)cta));
        			//concreteActivities.add(new ConcreteActivityTO((ConcretePhase)cta));
        		} else {
        			if (cta instanceof Project) {
    	        		concreteActivities.add(new ProjectTO((Project)cta));
            			//concreteActivities.add(new ConcreteActivityTO((ConcretePhase)cta));
            		} else {
            			concreteActivities.add(new ConcreteActivityTO(cta));
            		}
        		}
        	}
        }
		return concreteActivities;
    }
	
	
	
	
	
	
	
	
	
	
	
/*	/** Creates a new instance of ParticipantTO *
    public ConcreteActivityTO() {

    }    
  
    public ConcreteActivityTO(ConcreteActivity _myConcreteActivity) {
    	this.setId(_myConcreteActivity.getId());
    	this.setConcreteName(_myConcreteActivity.getConcreteName());
    	this.setActivity(new ActivityTO(_myConcreteActivity.getActivity()));

    	Set<ConcreteActivity> concreteActivitys = new HashSet<ConcreteActivity>();
        for (ConcreteActivity cta : _myConcreteActivity.getSuperConcreteActivities()) {
        	concreteActivitys.add(new ConcreteActivityTO(cta));
        }
        this.setSuperConcreteActivities(concreteActivitys);
    }
*/
	
	
	
}
