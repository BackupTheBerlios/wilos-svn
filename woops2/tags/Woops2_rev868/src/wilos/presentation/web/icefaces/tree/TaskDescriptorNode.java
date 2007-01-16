
package wilos.presentation.web.icefaces.tree ;

import wilos.model.spem2.task.TaskDescriptor;

import com.icesoft.faces.component.tree.IceUserObject;

/**
 * @author deder
 * 
 */
public class TaskDescriptorNode extends BasicNode {

	private static final long serialVersionUID = 7035675523830655414L ;
	
	/**
	 * 
	 * @param _defaultMutableTreeNode
	 * @param _parentTree
	 */
	public TaskDescriptorNode(TaskDescriptor _taskDescriptor) {
		super() ;
		super.setBasicNodeId(_taskDescriptor.getId());
		IceUserObject iceUserObject = new IceUserObject(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_taskDescriptor.getName()) ;
		iceUserObject.setLeaf(true) ;
		//iceUserObject.setBranchContractedIcon("images/icon_taskdescriptor.gif") ;
		//iceUserObject.setBranchExpandedIcon("images/icon_taskdescriptor.gif") ;
		iceUserObject.setLeafIcon("images/tree/icon_taskdescriptor.gif") ;

		/*TaskDefinition taskDefinition = _taskDescriptor.getTaskDefinition() ;
		if(taskDefinition != null){
			this.add(new TaskDefinitionNode(taskDefinition)) ;
		}*/
	}

}
