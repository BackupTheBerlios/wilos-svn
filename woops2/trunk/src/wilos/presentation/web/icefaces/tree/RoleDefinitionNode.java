package wilos.presentation.web.icefaces.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import com.icesoft.faces.component.tree.IceUserObject;

import wilos.model.spem2.role.RoleDefinition;

/**
 * @author deder
 *
 */
public class RoleDefinitionNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = 2966157406617774982L ;

	public RoleDefinitionNode(RoleDefinition _roleDefinition){
		super();
		IceUserObject iceUserObject = new IceUserObject(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_roleDefinition.getName()) ;
		iceUserObject.setLeaf(true) ;
		iceUserObject.setLeafIcon("images/tree/icon_role.gif");
	}
	
}
