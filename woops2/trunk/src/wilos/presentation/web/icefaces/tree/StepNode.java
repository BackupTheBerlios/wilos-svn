
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultMutableTreeNode ;

import com.icesoft.faces.component.tree.IceUserObject ;

import wilos.model.spem2.task.Step ;

/**
 * @author deder
 * 
 */
public class StepNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5925894594033986547L ;

	public StepNode(Step _step) {
		super() ;
		IceUserObject iceUserObject = new IceUserObject(this) ;
		this.setUserObject(iceUserObject) ;

		iceUserObject.setText(_step.getName()) ;
		iceUserObject.setLeaf(true) ;
		iceUserObject.setLeafIcon("images/tree/icon_step.gif") ;
	}

}
