/**
 * 
 */

package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;

import com.icesoft.faces.component.tree.IceUserObject ;

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

		DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode() ;
		IceUserObject iceUserObject = new IceUserObject(branchNode) ;
		branchNode.setUserObject(iceUserObject) ;

		iceUserObject.setText(_taskDescriptor.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("xmlhttp/css/xp/css-images/tree_folder_close.gif") ;
		iceUserObject.setBranchExpandedIcon("xmlhttp/css/xp/css-images/tree_folder_open.gif") ;
		iceUserObject.setExpanded(true) ;

		TaskDefinition taskDefinition = _taskDescriptor.getTaskDefinition() ;
		if(taskDefinition != null){
			branchNode.add(new TaskDefinitionNode(taskDefinition));
		}
	}

}
