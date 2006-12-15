
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

import com.icesoft.faces.component.tree.IceUserObject;

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
			if(breakdownElement instanceof Phase){
				Phase phase = (Phase) breakdownElement ;
				this.add(new PhaseNode(phase)) ;
			}
			if(breakdownElement instanceof Iteration){
				Iteration iteration = (Iteration) breakdownElement ;
				this.add(new IterationNode(iteration)) ;
			}
			else if(breakdownElement instanceof Activity){
				Activity activity = (Activity) breakdownElement ;
				this.add(new ActivityNode(activity)) ;
			}
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
