package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreteiteration.ConcreteIteration;

public class ConcreteIterationTO extends ConcreteIteration implements Serializable {

	public ConcreteIterationTO() {}

    public ConcreteIterationTO(ConcreteIteration _myConcreteIteration) {
    	this.setId(_myConcreteIteration.getId());
    	this.setConcreteName(_myConcreteIteration.getConcreteName());
    	this.setIteration(new IterationTO(_myConcreteIteration.getIteration()));

    	Set<ConcreteActivity> concreteActivitys = new HashSet<ConcreteActivity>();
//        for (ConcreteActivity cta : _myConcretePhase.getSuperConcreteActivities()) {
//        	concreteActivitys.add(new ConcreteActivityTO(cta));
//        }
    	
    	concreteActivitys = ConcreteActivityTO.getConcreteActivities(_myConcreteIteration);
    	
        this.setSuperConcreteActivities(concreteActivitys);
    }

}
