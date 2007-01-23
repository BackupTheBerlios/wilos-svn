package wilos.presentation.web.tree;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcreteTaskDescriptorNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -2090785940515259986L;
	
	/**
	 * 
	 * @param roleDescriptors 
	 * @param _defaultMutableTreeNode
	 * @param _parentTree
	 */
	public ConcreteTaskDescriptorNode(ConcreteTaskDescriptor _concreteTaskDescriptor, Set<RoleDescriptor> roleDescriptors) {
		super() ;
		WilosObjectNode iceUserObject = new WilosObjectNode(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_concreteTaskDescriptor.getConcreteName()) ;
		iceUserObject.setLeaf(true) ;
		//iceUserObject.setBranchContractedIcon("images/icon_taskdescriptor.gif") ;
		//iceUserObject.setBranchExpandedIcon("images/icon_taskdescriptor.gif") ;
		iceUserObject.setLeafIcon("images/tree/icon_taskdescriptor.gif") ;
		// node information
		iceUserObject.setId(_concreteTaskDescriptor.getId());
		iceUserObject.setPageId(WilosObjectNode.CONCRETETASKNODE);
		
		/*TaskDefinition taskDefinition = _taskDescriptor.getTaskDefinition() ;
		if(taskDefinition != null){
			this.add(new TaskDefinitionNode(taskDefinition)) ;
		}*/
	}

}
