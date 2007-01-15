
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import wilos.model.spem2.role.RoleDefinition ;
import wilos.model.spem2.role.RoleDescriptor ;

import com.icesoft.faces.component.tree.IceUserObject ;

/**
 * @author deder
 * 
 */
public class RoleDescriptorNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -7237087664837133322L ;

	public RoleDescriptorNode(RoleDescriptor _roleDescriptor) {
		super() ;
		IceUserObject iceUserObject = new IceUserObject(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_roleDescriptor.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("images/icon_roledescriptor.gif") ;
		iceUserObject.setBranchExpandedIcon("images/icon_roledescriptor.gif") ;

		RoleDefinition roleDefinition = _roleDescriptor.getRoleDefinition() ;
		if(roleDefinition != null){
			this.add(new RoleDefinitionNode(roleDefinition)) ;
		}
	}

}
