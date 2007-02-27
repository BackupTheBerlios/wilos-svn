package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretephase.ConcretePhase;

public class ConcretePhaseTO extends ConcretePhase implements Serializable {

	public ConcretePhaseTO() {}

    public ConcretePhaseTO(ConcretePhase _myConcretePhase) {
    	this.setId(_myConcretePhase.getId());
    	this.setConcreteName(_myConcretePhase.getConcreteName());
    	this.setPhase(new PhaseTO(_myConcretePhase.getPhase()));

    	Set<ConcreteActivity> concreteActivitys = new HashSet<ConcreteActivity>();
//        for (ConcreteActivity cta : _myConcretePhase.getSuperConcreteActivities()) {
//        	concreteActivitys.add(new ConcreteActivityTO(cta));
//        }
    	
    	concreteActivitys = ConcreteActivityTO.getConcreteActivities(_myConcretePhase);
    	
        this.setSuperConcreteActivities(concreteActivitys);
    }

}
