
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import wilos.model.spem2.breakdownelement.BreakdownElement ;
import wilos.model.spem2.process.Process ;
import wilos.model.spem2.task.TaskDescriptor ;

import com.icesoft.faces.component.tree.IceUserObject ;

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
		
		IceUserObject iceUserObject = new IceUserObject(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(this.process.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("images/icon_process.gif") ;
		iceUserObject.setBranchExpandedIcon("images/icon_process.gif") ;

		
	}
	
	public ProcessNode obtainTasksFromProcess(){
		for(BreakdownElement breakdownElement : this.process.getBreakDownElements()){
			if(breakdownElement instanceof TaskDescriptor){
				TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement ;
				this.add(new TaskDescriptorNode(taskDescriptor)) ;
			}
		}
		return this;
	}
	
	public ProcessNode obtainTasksForARoleFromProcess(){
		for(BreakdownElement breakdownElement : this.process.getBreakDownElements()){
			if(breakdownElement instanceof TaskDescriptor){
				TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement ;
				this.add(new TaskDescriptorNode(taskDescriptor)) ;
			}
		}
		return this;
	}
}
