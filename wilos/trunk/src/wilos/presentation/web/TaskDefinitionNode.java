/**
 * 
 */
package wilos.presentation.web;

import javax.swing.tree.DefaultMutableTreeNode;

import com.icesoft.faces.component.tree.IceUserObject;

import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;

/**
 * @author deder
 *
 */
public class TaskDefinitionNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = 6523912299822006358L ;

	public TaskDefinitionNode(TaskDefinition _taskDefinition){
		DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode() ;
		IceUserObject iceUserObject = new IceUserObject(branchNode) ;
		branchNode.setUserObject(iceUserObject) ;

		iceUserObject.setText(_taskDefinition.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("xmlhttp/css/xp/css-images/tree_folder_close.gif") ;
		iceUserObject.setBranchExpandedIcon("xmlhttp/css/xp/css-images/tree_folder_open.gif") ;
		iceUserObject.setExpanded(true) ;

		for (Step step : _taskDefinition.getSteps()) {
			if (step != null) {
				branchNode.add(new StepNode(step));
			}
		}
	}
	
}
