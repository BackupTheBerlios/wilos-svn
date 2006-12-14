
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import com.icesoft.faces.component.tree.IceUserObject ;

import wilos.model.spem2.task.Step ;
import wilos.model.spem2.task.TaskDefinition ;

/**
 * @author deder
 * 
 */
public class TaskDefinitionNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 6523912299822006358L ;

	public TaskDefinitionNode(TaskDefinition _taskDefinition) {
		super() ;
		IceUserObject iceUserObject = new IceUserObject(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_taskDefinition.getName()) ;
		iceUserObject.setLeaf(false) ;
		iceUserObject.setBranchContractedIcon("images/icon_task.gif") ;
		iceUserObject.setBranchExpandedIcon("images/icon_task.gif") ;

		for(Step step : _taskDefinition.getSteps()){
			if(step != null){
				this.add(new StepNode(step)) ;
			}
		}
	}

}
