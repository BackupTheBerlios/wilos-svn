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
        
        
        /*for (TaskDescriptor td : myRoleDescriptor.getPrimaryTasks()) {
        	 this.addPrimaryTask(new TaskDescriptorTO(td));
        }*/

      
       /* for (TaskDescriptor td : myRoleDescriptor.getAdditionalTasks()) {
        	this.addAdditionalTask(new TaskDescriptorTO(td));
        }*/

        if(myRoleDescriptor.getRoleDefinition() != null) this.setRoleDefinition(new RoleDefinitionTO(myRoleDescriptor.getRoleDefinition()));
        if(this.getRoleDefinition() != null && this.getDescription().length()==0)  this.setDescription(this.getRoleDefinition().getDescription());
    }
    
}
