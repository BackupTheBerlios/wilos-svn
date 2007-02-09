/*
 * TaskDefinitionTO.java
 *
 * Created on 9 décembre 2006, 14:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;

/**
 *
 * @author toine
 */
public class TaskDefinitionTO extends TaskDefinition implements Serializable {
    
    
    /** Creates a new instance of TaskDefinitionTO */
    public TaskDefinitionTO() {
        
    }
    
    public TaskDefinitionTO(TaskDefinition myTD) {
        this.setName(myTD.getName());
        this.setGuid(myTD.getGuid());
        this.setDescription(myTD.getDescription());                
        
       /* SortedSet<Step> stepTos= new HashSet<Step>();
        for (Step s : myTD.getSteps()) {
            stepTos.add(new StepTO(s));
        }
        this.addAllSteps(stepTos);*/
       
        
        Set<Guidance> guidances = new HashSet<Guidance>();
        for (Guidance g : myTD.getGuidances()) {
        	 guidances.add(new GuidanceTO(g));
        }
        this.addAllGuidances(guidances);
    }

    
    

}
