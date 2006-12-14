
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import wilos.model.spem2.breakdownelement.BreakdownElement ;
import wilos.model.spem2.role.RoleDescriptor ;
import wilos.model.spem2.task.TaskDescriptor ;
import wilos.model.spem2.process.Process ;

import com.icesoft.faces.component.tree.IceUserObject ;

/**
 * @author deder
 * 
 */
public class ProcessNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -4788408717410540076L ;

	public ProcessNode(Process _process) {
		super() ;
		IceUserObject iceUserObject = new IceUserObject(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_process.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("images/icon_process.gif") ;
		iceUserObject.setBranchExpandedIcon("images/icon_process.gif") ;

		for(BreakdownElement breakdownElement : _process.getBreakDownElements()){
			if(breakdownElement instanceof TaskDescriptor){
				TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement ;
				this.add(new TaskDescriptorNode(taskDescriptor)) ;
			}
			else if(breakdownElement instanceof RoleDescriptor){
				RoleDescriptor roleDescriptor = (RoleDescriptor) breakdownElement ;
				this.add(new RoleDescriptorNode(roleDescriptor)) ;
			}
		}
	}
}
