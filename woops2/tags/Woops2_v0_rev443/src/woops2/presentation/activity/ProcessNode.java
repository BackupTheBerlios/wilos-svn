/**
 * 
 */
package woops2.presentation.activity;

import javax.swing.tree.DefaultMutableTreeNode;

import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.process.Process;
import woops2.model.role.RoleDescriptor;
import woops2.model.task.TaskDescriptor;

import com.icesoft.faces.component.tree.IceUserObject;

/**
 * @author deder
 *
 */
public class ProcessNode extends DefaultMutableTreeNode{

	private static final long serialVersionUID = -4788408717410540076L ;

	public ProcessNode(Process _process){
		DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode() ;
		IceUserObject iceUserObject = new IceUserObject(branchNode) ;
		branchNode.setUserObject(iceUserObject) ;

		iceUserObject.setText(_process.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("xmlhttp/css/xp/css-images/tree_folder_close.gif") ;
		iceUserObject.setBranchExpandedIcon("xmlhttp/css/xp/css-images/tree_folder_open.gif") ;
		iceUserObject.setExpanded(true) ;

		for (BreakdownElement breakdownElement : _process.getBreakDownElements()) {
			if (breakdownElement instanceof TaskDescriptor) {
				TaskDescriptor taskDescriptor = (TaskDescriptor)breakdownElement;
				branchNode.add(new TaskDescriptorNode(taskDescriptor));
			}
			else if(breakdownElement instanceof RoleDescriptor) {
				RoleDescriptor roleDescriptor = (RoleDescriptor)breakdownElement;
				branchNode.add(new RoleDescriptorNode(roleDescriptor));
			}
		}
	}
	
}
