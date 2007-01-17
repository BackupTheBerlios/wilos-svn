
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import wilos.model.spem2.activity.Activity ;
import wilos.model.spem2.breakdownelement.BreakdownElement ;
import wilos.model.spem2.iteration.Iteration ;
import wilos.model.spem2.phase.Phase ;
import wilos.model.spem2.role.RoleDescriptor ;
import wilos.model.spem2.task.TaskDescriptor ;

import com.icesoft.faces.component.tree.IceUserObject ;

/**
 * @author deder
 * 
 */
public class PhaseNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -3026183997971694312L ;

	public PhaseNode(Phase _phase) {
		super() ;
		IceUserObject iceUserObject = new IceUserObject(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_phase.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("images/icon_phase.gif") ;
		iceUserObject.setBranchExpandedIcon("images/icon_phase.gif") ;

		for(BreakdownElement breakdownElement : _phase.getBreakDownElements()){
			if(breakdownElement instanceof Iteration){
				Iteration iteration = (Iteration) breakdownElement ;
				this.add(new IterationNode(iteration)) ;
			}
			else if(breakdownElement instanceof Activity){
				Activity activity = (Activity) breakdownElement ;
				this.add(new ActivityNode(activity)) ;
			}
			else if(breakdownElement instanceof TaskDescriptor){
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
