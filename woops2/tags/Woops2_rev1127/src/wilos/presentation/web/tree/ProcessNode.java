
package wilos.presentation.web.tree ;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
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
		iceUserObject.setId(this.process.getId());
		for(BreakdownElement breakdownElement : this.process.getBreakDownElements()){
			if(breakdownElement instanceof Phase){
				this.add(new PhaseNode((Phase)breakdownElement)) ;
			}
			else if(breakdownElement instanceof Iteration){
				this.add(new IterationNode((Iteration)breakdownElement)) ;
			}
			else if(breakdownElement instanceof Activity){
				this.add(new ActivityNode((Activity)breakdownElement)) ;
			}
			//TODO Change with ConcreteTaskDescriptorNode !!!
			else if(breakdownElement instanceof TaskDescriptor){
				this.add(new TaskDescriptorNode((TaskDescriptor)breakdownElement)) ;
			}
		}
	}
}
