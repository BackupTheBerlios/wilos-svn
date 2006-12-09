/*
 * TaskDescriptorTO.java
 *
 * Created on 9 décembre 2006, 14:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import org.hibernate.mapping.Set;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;

/**
 *
 * @author toine
 */
public class TaskDescriptorTO extends TaskDescriptor implements Serializable{
    

    /** Creates a new instance of TaskDescriptorTO */
    public TaskDescriptorTO() {
    }

    
    public TaskDescriptorTO(TaskDescriptor myTaskDescriptor) {
        this.setName(myTaskDescriptor.getName());
        this.setPrefix(myTaskDescriptor.getPrefix());
        this.setGuid(myTaskDescriptor.getGuid());
        this.setDescription(myTaskDescriptor.getDescription());
        this.setPresentationName(myTaskDescriptor.getPresentationName());       
        this.setTaskDefinition(new TaskDefinitionTO(myTaskDescriptor.getTaskDefinition()));
    }

    
//TaskDefinition t = new TaskDefinition().
    //TaskDescriptor t = new TaskDescriptor().get
   
            

}
