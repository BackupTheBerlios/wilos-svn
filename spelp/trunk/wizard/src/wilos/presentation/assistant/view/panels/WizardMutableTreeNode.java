package wilos.presentation.assistant.view.panels;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.element.Element;

public class WizardMutableTreeNode extends DefaultMutableTreeNode {
	
//	public boolean equals(Object obj) {
//		return (this.getUserObject().equals(this.getUserObject()));
//	}

	public WizardMutableTreeNode(Object element) {
		super();
		this.userObject = element;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#clone()
	 */
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		WizardMutableTreeNode retour = (WizardMutableTreeNode) super.clone();
		for(int i = 0 ; i < this.getChildCount() ; i++ ){
			retour.add((WizardMutableTreeNode) ((WizardMutableTreeNode) this.getChildAt(i)).clone());
		}
		return retour ;
		
	}

	public Object getUserObject() {
		return userObject;
	}
	
	public String toString() {
//		if (userObject instanceof ConcreteRoleDescriptor)
//			return ((ConcreteRoleDescriptor) userObject).getRoleDescriptor().getPresentationName();
//		else if (userObject instanceof Project)
//			return ((Project) userObject).getConcreteName();
//		else if (userObject instanceof ConcreteIteration)
//			return ((ConcreteIteration) userObject).getIteration().getPresentationName();
//		else if (userObject instanceof ConcretePhase)
//			return ((ConcretePhase) userObject).getPhase().getPresentationName();
//		else if (userObject instanceof ConcreteActivity)
//			return ((ConcreteActivity) userObject).getActivity().getPresentationName();
//		else if (userObject instanceof ConcreteTaskDescriptor) 
//			return ((ConcreteTaskDescriptor) userObject).getTaskDescriptor().getPresentationName();
		if (userObject instanceof ConcreteBreakdownElement){
			return ((ConcreteBreakdownElement) userObject).getConcreteName();
		}
		else if (userObject instanceof Element)
			return ((Element)userObject).getName();
		else
			return "";
	}
}