
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * @author deder
 * 
 */
public class ProcessNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -4788408717410540076L ;
	
	private Process process;
	
	public ProcessNode(Process _process) {
		super() ;
		this.process = _process;
		
		WilosObjectNode iceUserObject = new WilosObjectNode(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.process.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("images/tree/icon_process.gif") ;
		iceUserObject.setBranchExpandedIcon("images/tree/icon_process.gif") ;
		iceUserObject.setObjectId(_process.getId());
		for(BreakdownElement breakdownElement : this.process.getBreakDownElements()){
			if(breakdownElement instanceof TaskDescriptor){
				TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement ;
				this.add(new TaskDescriptorNode(taskDescriptor)) ;
			}
		}
	}
}
