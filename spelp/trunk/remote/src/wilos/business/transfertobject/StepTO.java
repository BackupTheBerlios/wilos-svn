/*
 *
 * StepTO.java
 *
 * Created on 9 décembre 2006, 14:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.business.transfertobject;

import java.io.Serializable;
import wilos.model.spem2.task.Step;

/**
 *
 * @author toine
 */
public class StepTO extends Step implements Serializable{


    //TaskDefinition t = new TaskDefinition().

    
    /** Creates a new instance of StepTO */
    public StepTO() {
        
        
    }
    
    public StepTO(Step myStep) {        
        this.setName(myStep.getName());
        this.setGuid(myStep.getGuid());
        this.setDescription(myStep.getDescription());    
        
    }
  

}
