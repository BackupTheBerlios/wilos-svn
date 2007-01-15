
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import wilos.model.spem2.activity.Activity ;
import wilos.model.spem2.breakdownelement.BreakdownElement ;
import wilos.model.spem2.role.RoleDescriptor ;
import wilos.model.spem2.task.TaskDescriptor ;

import com.icesoft.faces.component.tree.IceUserObject ;

/**
 * @author deder
 * 
 */
public class ActivityNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 8063131475728659941L ;

	public ActivityNode(Activity _activity) {
		super() ;
		IceUserObject iceUserObject = new IceUserObject(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_activity.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("images/icon_activity.gif") ;
		iceUserObject.setBranchExpandedIcon("images/icon_activity.gif") ;

		for(BreakdownElement breakdownElement : _activity.getBreakDownElements()){
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
