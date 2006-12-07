/**
 * 
 */
package wilos.presentation.web;

import javax.swing.tree.DefaultMutableTreeNode;

import com.icesoft.faces.component.tree.IceUserObject;

import wilos.model.spem2.task.Step;

/**
 * @author deder
 *
 */
public class StepNode extends DefaultMutableTreeNode{
	
	private static final long serialVersionUID = -5925894594033986547L ;

	public StepNode(Step _step){
		DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode() ;
		IceUserObject iceUserObject = new IceUserObject(branchNode) ;
		branchNode.setUserObject(iceUserObject) ;

		iceUserObject.setText(_step.getName()) ;
		iceUserObject.setLeaf(true) ;
		iceUserObject.setBranchContractedIcon("xmlhttp/css/xp/css-images/tree_folder_close.gif") ;
		iceUserObject.setBranchExpandedIcon("xmlhttp/css/xp/css-images/tree_folder_open.gif") ;
		iceUserObject.setExpanded(true) ;
	}

}
