package wilos.business.transfertobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.role.RoleDefinition;

public class RoleDefinitionTO extends RoleDefinition implements Serializable{
    /** Creates a new instance of RoleDefinitionTO */
    public RoleDefinitionTO() {
    }
    public RoleDefinitionTO(RoleDefinition myRoleDefinition) {
        this.setName(myRoleDefinition.getName());
        this.setGuid(myRoleDefinition.getGuid());
        this.setDescription(myRoleDefinition.getDescription()); 
        
      
        Set<Guidance> guidances = new HashSet<Guidance>();
        for (Guidance g : myRoleDefinition.getGuidances()) {
        	if (g instanceof CheckList)
        		guidances.add(new CheckListTO((CheckList)g));	
        	else
        		guidances.add(new GuidanceTO(g));	        	
        }
        this.setGuidances(guidances);

    }
}
