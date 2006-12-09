/*
 * RoleDescriptorTO.java
 *
 * Created on 9 décembre 2006, 14:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import wilos.business.transfertobject.TaskDescriptorTO;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

/**
 *
 * @author toine
 */
public class RoleDescriptorTO extends RoleDescriptor implements Serializable{
    
            
            
    /** Creates a new instance of RoleDescriptorTO */
    public RoleDescriptorTO() {
    }
    
    public RoleDescriptorTO(RoleDescriptor myRoleDescriptor) {
        this.setName(myRoleDescriptor.getName());
        this.setPrefix(myRoleDescriptor.getPrefix());
        this.setPresentationName(myRoleDescriptor.getPresentationName());
        this.setGuid(myRoleDescriptor.getGuid());
        this.setDescription(myRoleDescriptor.getDescription());        
        Set<TaskDescriptor> taskDescriptorPTos= new HashSet<TaskDescriptor>();
        for (TaskDescriptor td : myRoleDescriptor.getPrimaryTasks()) {
            taskDescriptorPTos.add(new TaskDescriptorTO(td));
        }
        this.addAllPrimaryTasks(taskDescriptorPTos);
        
        Set<TaskDescriptor> taskDescriptorATos= new HashSet<TaskDescriptor>();
        for (TaskDescriptor td : myRoleDescriptor.getAdditionalTasks()) {
            taskDescriptorATos.add(new TaskDescriptorTO(td));
        }
        this.addAllAdditionalTasks(taskDescriptorATos);
        
    }
    
}
