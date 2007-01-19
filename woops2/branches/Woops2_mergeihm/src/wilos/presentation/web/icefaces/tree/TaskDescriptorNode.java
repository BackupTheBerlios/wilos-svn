
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.spem2.task.TaskDescriptor;

/**
 * @author deder
 * 
 */
public class TaskDescriptorNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 7035675523830655414L ;
	
	/**
	 * 
	 * @param _defaultMutableTreeNode
	 * @param _parentTree
	 */
	public TaskDescriptorNode(TaskDescriptor _taskDescriptor) {
		super() ;
		WilosObjectNode iceUserObject = new WilosObjectNode(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_taskDescriptor.getName()) ;
		iceUserObject.setLeaf(true) ;
		//iceUserObject.setBranchContractedIcon("images/icon_taskdescriptor.gif") ;
		//iceUserObject.setBranchExpandedIcon("images/icon_taskdescriptor.gif") ;
		iceUserObject.setLeafIcon("images/tree/icon_taskdescriptor.gif") ;
		iceUserObject.setObjectId(_taskDescriptor.getId());
		/*TaskDefinition taskDefinition = _taskDescriptor.getTaskDefinition() ;
		if(taskDefinition != null){
			this.add(new TaskDefinitionNode(taskDefinition)) ;
		}*/
	}

}
