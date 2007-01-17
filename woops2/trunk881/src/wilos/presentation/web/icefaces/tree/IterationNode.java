
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import wilos.model.spem2.activity.Activity ;
import wilos.model.spem2.breakdownelement.BreakdownElement ;
import wilos.model.spem2.iteration.Iteration ;
import wilos.model.spem2.role.RoleDescriptor ;
import wilos.model.spem2.task.TaskDescriptor ;

import com.icesoft.faces.component.tree.IceUserObject ;

/**
 * @author deder
 * 
 */
public class IterationNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 8166859940706432509L ;

	public IterationNode(Iteration _iteration) {
		super() ;
		IceUserObject iceUserObject = new IceUserObject(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_iteration.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("images/tree/icon_iteration.gif") ;
		iceUserObject.setBranchExpandedIcon("images/tree/icon_iteration.gif") ;

		for(BreakdownElement breakdownElement : _iteration.getBreakDownElements()){
			if(breakdownElement instanceof Activity){
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
