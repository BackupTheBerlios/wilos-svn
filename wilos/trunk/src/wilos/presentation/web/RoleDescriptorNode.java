/**
 * 
 */
package wilos.presentation.web;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;

import com.icesoft.faces.component.tree.IceUserObject;

/**
 * @author deder
 *
 */
public class RoleDescriptorNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = -7237087664837133322L ;

	public RoleDescriptorNode(RoleDescriptor _roleDescriptor){
		DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode() ;
		IceUserObject iceUserObject = new IceUserObject(branchNode) ;
		branchNode.setUserObject(iceUserObject) ;

		iceUserObject.setText(_roleDescriptor.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("xmlhttp/css/xp/css-images/tree_folder_close.gif") ;
		iceUserObject.setBranchExpandedIcon("xmlhttp/css/xp/css-images/tree_folder_open.gif") ;
		iceUserObject.setExpanded(true) ;

		RoleDefinition roleDefinition = _roleDescriptor.getRoleDefinition() ;
		if(roleDefinition != null){
			branchNode.add(new RoleDefinitionNode(roleDefinition));
		}
	}
	
}
