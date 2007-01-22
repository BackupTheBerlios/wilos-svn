package wilos.presentation.web.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;

public class ConcreteTaskDescriptorNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -2090785940515259986L;
	
	/**
	 * 
	 * @param _defaultMutableTreeNode
	 * @param _parentTree
	 */
	public ConcreteTaskDescriptorNode(ConcreteTaskDescriptor _concreteTaskDescriptor) {
		super() ;
		WilosObjectNode iceUserObject = new WilosObjectNode(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_concreteTaskDescriptor.getConcreteName()) ;
		iceUserObject.setLeaf(true) ;
		//iceUserObject.setBranchContractedIcon("images/icon_taskdescriptor.gif") ;
		//iceUserObject.setBranchExpandedIcon("images/icon_taskdescriptor.gif") ;
		iceUserObject.setLeafIcon("images/tree/icon_taskdescriptor.gif") ;
		iceUserObject.setObjectId(_concreteTaskDescriptor.getId());
		/*TaskDefinition taskDefinition = _taskDescriptor.getTaskDefinition() ;
		if(taskDefinition != null){
			this.add(new TaskDefinitionNode(taskDefinition)) ;
		}*/
	}

}
