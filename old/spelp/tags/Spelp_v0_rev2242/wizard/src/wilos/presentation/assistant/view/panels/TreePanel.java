package wilos.presentation.assistant.view.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTree;

import com.sun.xml.bind.v2.runtime.reflect.ListTransducedAccessorImpl;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.element.Element;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.utils.Constantes;

public class TreePanel extends JScrollPane implements TreeSelectionListener {
		
	private JXTree tree = null ;
	
	public TreePanel(){
		super();
		this.setLayout(new ScrollPaneLayout());
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	public void putTree (JXTree t){
		tree = t ;
		t.setCellRenderer(new WizardTreeRenderer());
		this.setViewportView(t);
		tree.addTreeSelectionListener(this); 
		tree.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent arg0) {
				
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mousePressed(MouseEvent arg0) {
				// selection with right button
				if (arg0.getButton() == MouseEvent.BUTTON3){
					TreePanel.this.tree.setSelectionRow(TreePanel.this.tree.getRowForLocation(arg0.getX(), arg0.getY()));
				}
			}

			public void mouseReleased(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3){
					// displaying contextual menu
					TreePath path = TreePanel.this.tree.getPathForLocation(arg0.getX(), arg0.getY()); 
					
					if (path != null) { 
							if (path.getLastPathComponent() == TreePanel.this.tree.getLastSelectedPathComponent()){
								WizardControler.getInstance().showContextualMenu(arg0);
							}
					}
					
				}
				
			}
			
		});
		//DefaultTreeCellRenderer df;
		//df.getTreeCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}
	
	public JXTree getTree (){
		return tree;
	}
	
	private class WizardMutableTreeNode extends DefaultMutableTreeNode {
		
		public WizardMutableTreeNode(Object element) {
			super();
			this.userObject = element;
		}

		public Object getUserObject() {
			return userObject;
		}
		
		public String toString() {
//			if (userObject instanceof ConcreteRoleDescriptor)
//				return ((ConcreteRoleDescriptor) userObject).getRoleDescriptor().getPresentationName();
//			else if (userObject instanceof Project)
//				return ((Project) userObject).getConcreteName();
//			else if (userObject instanceof ConcreteIteration)
//				return ((ConcreteIteration) userObject).getIteration().getPresentationName();
//			else if (userObject instanceof ConcretePhase)
//				return ((ConcretePhase) userObject).getPhase().getPresentationName();
//			else if (userObject instanceof ConcreteActivity)
//				return ((ConcreteActivity) userObject).getActivity().getPresentationName();
//			else if (userObject instanceof ConcreteTaskDescriptor) 
//				return ((ConcreteTaskDescriptor) userObject).getTaskDescriptor().getPresentationName();
			if (userObject instanceof ConcreteBreakdownElement){
				return ((ConcreteBreakdownElement) userObject).getConcreteName();
			}
			else if (userObject instanceof Element)
				return ((Element)userObject).getName();
			else
				return "";
		}
	}
	
	private class WizardTreeModel extends DefaultTreeModel {
		Participant participant;
		//String root;
		
		public WizardTreeModel(Participant aParticipant) {
			super(null);
			this.participant = aParticipant;
			initTree();
		}
		
		// TODO : actuellement prise en compte d'une super activity a voir pour plusieurs
		private ConcreteActivity getActivity (Set<ConcreteActivity> s){
			ConcreteActivity ca = null ;
			try {
				ca = s.iterator().next();
			}
			catch(Exception e){
				
			}
			
			return ca ;
		}
		
		// init tree initialize all the nodes of the jtree
		private void initTree() {
			TreePanel.this.tree.removeAll();
			WizardStateMachine.getInstance().deleteAllStep() ;
			HashMap<ConcreteActivity, WizardMutableTreeNode> mapActivity = new HashMap<ConcreteActivity, WizardMutableTreeNode>();
			int idStep = 0 ;
			this.root = new DefaultMutableTreeNode(participant.getName());
			ArrayList<Step> tmp = new ArrayList<Step>();
			Set<ConcreteRoleDescriptor> roles = participant.getConcreteRoleDescriptors();
			
			// browse all the concrete roles
			for (ConcreteRoleDescriptor crd : roles) {
				
				DefaultMutableTreeNode precedent = null;
				WizardMutableTreeNode rdWmt = new WizardMutableTreeNode(crd);
				ConcreteActivity ca = getActivity(crd.getSuperConcreteActivities());
				WizardMutableTreeNode nodeAct = null  ;
				// getting all the superactivities from the roles
				if (ca == null){
					// if there is no super activity we add the role to the root
					((DefaultMutableTreeNode)this.root).add(rdWmt);
				}
				else {
					// if there is super activities 
					// we have to add them to the root or to an other super activity
					while (ca != null){
						// search if the activity is already treated as a node
						if (!mapActivity.containsKey(ca)){
							nodeAct = new WizardMutableTreeNode(ca);
							mapActivity.put(ca, nodeAct);
						}
						else {
							nodeAct = mapActivity.get(ca);
						}
						if (precedent == null){
							// if precedent == null we are at the first round 
							// so we had to add the role descriptor
							nodeAct.add(rdWmt);
						}
						else {
							nodeAct.add(precedent);
						}
						ca = getActivity(ca.getSuperConcreteActivities());
						if (ca == null ){
							((DefaultMutableTreeNode)this.root).add(nodeAct);
						}
						precedent = nodeAct ;
					}				
				}
				// brows all the concrete tasks
				for(ConcreteTaskDescriptor ctd : crd.getConcreteTaskDescriptors()) {
					
					WizardMutableTreeNode ctdWmt = new WizardMutableTreeNode(ctd);
					rdWmt.add(ctdWmt);
					// browse all the steps of the task def in task descriptors of concretetaskdescriptor
					if (ctd.getTaskDescriptor().getTaskDefinition() != null) {
						// cloning the steps to have same steps with different states
						for (Step s : ctd.getTaskDescriptor().getTaskDefinition().getSteps()){
							try {
								// creating the clone and store them in a list
								Step sCloned = s.clone();
								sCloned.setGuid(String.valueOf(idStep));
								idStep++;
								tmp.add(sCloned);
								
							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						// removing the steps from the task def and adding the cloned
						TaskDefinition tdef = ctd.getTaskDescriptor().getTaskDefinition() ;
						tdef.removeAllSteps();
						for (Step s : tmp){
							tdef.addStep(s);
						}
						tmp.clear();
						// normal process
						for (Step s : ctd.getTaskDescriptor().getTaskDefinition().getSteps()) {
							WizardMutableTreeNode sWmt;
							sWmt = new WizardMutableTreeNode(s);
							ctdWmt.add(sWmt);
							// managing the steps
							if (ctd.getState().equals(Constantes.State.STARTED)){
								WizardStateMachine.getInstance().addStep(s,WizardStateMachine.STATE_STEP_READY ) ;
							}
							else if (ctd.getState().equals(Constantes.State.FINISHED)){
								WizardStateMachine.getInstance().addStep(s,WizardStateMachine.STATE_STEP_FINISHED ) ;
							}
							else {
								WizardStateMachine.getInstance().addStep(s,WizardStateMachine.STATE_STEP_CREATED ) ;
							}
						}
					}
				}
				//((DefaultMutableTreeNode) this.root).add(new DefaultMutableTreeNode(rd.getName()));
			}

		}

//		public void addTreeModelListener(TreeModelListener arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public Object getChild(Object arg0, int arg1) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		public int getChildCount(Object arg0) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		public int getIndexOfChild(Object arg0, Object arg1) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		public Object getRoot() {
//			// TODO Auto-generated method stub
//			return root;
//		}
//
//		public boolean isLeaf(Object arg0) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		public void removeTreeModelListener(TreeModelListener arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void valueForPathChanged(TreePath arg0, Object arg1) {
//			// TODO Auto-generated method stub
//			
//		}		
	}
	
	// the renderer for the icon and label
	private class WizardTreeRenderer extends DefaultTreeCellRenderer {
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			
			if (value.getClass().getSimpleName().equals("DefaultMutableTreeNode")) {
				this.setIcon(ImagesService.getImageIcon("images.iconParticipant"));
			}
			else {
				Object object = ((WizardMutableTreeNode) value).getUserObject();
				if (object instanceof ConcreteRoleDescriptor) {
					ConcreteRoleDescriptor crd = (ConcreteRoleDescriptor)object ;
					this.setIcon(ImagesService.getImageIcon("images.iconRole"));
				}
				else if(object instanceof ConcreteIteration){
					this.setIcon(ImagesService.getImageIcon("images.iconIteration"));
				}
				else if(object instanceof ConcretePhase){
					this.setIcon(ImagesService.getImageIcon("images.iconPhase"));
				}
				else if(object instanceof ConcreteActivity){
					this.setIcon(ImagesService.getImageIcon("images.iconActivity"));
				}
				else if (object instanceof ConcreteTaskDescriptor) {
					ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor)object ;
					this.setIcon(ImagesService.getImageIcon("images.iconTaskDescriptor"));
					if (ctd.getState() == Constantes.State.STARTED) {
						this.setForeground(WizardStateMachine.COLOR_STARTED);
						this.setIcon(ImagesService.getImageIcon("images.iconTaskDescriptorPlay"));
					}
					else if (ctd.getState().equals(Constantes.State.READY)) {
						this.setForeground(WizardStateMachine.COLOR_READY);
					}
					else if (ctd.getState().equals(Constantes.State.SUSPENDED)) {
						this.setForeground(WizardStateMachine.COLOR_SUSPENDED);
						this.setIcon(ImagesService.getImageIcon("images.iconTaskDescriptorPause"));
					}
					else if (ctd.getState().equals(Constantes.State.FINISHED)) {
						this.setForeground(WizardStateMachine.COLOR_FINISHED);
						this.setIcon(ImagesService.getImageIcon("images.iconTaskDescriptorOver"));
					}
					else if (ctd.getState().equals(Constantes.State.CREATED)) {
						this.setForeground(WizardStateMachine.COLOR_CREATED);
					}
				}
				else if (object instanceof Step) {
					Step s = (Step) object ;
					this.setIcon(ImagesService.getImageIcon("images.iconStep"));
					if (WizardStateMachine.getInstance().getStepState(s) == WizardStateMachine.STATE_STEP_CREATED || WizardStateMachine.getInstance().getStepState(s) == WizardStateMachine.STATE_STEP_READY){
						this.setText(s.getName());
					}	
					else {
						this.setText("<HTML><STRIKE>"  + s.getName() + "</STRIKE></HTML>");
					}
					//WizardStateMachine.getInstance().addStep(s.getTaskDefinition()., WizardStateMachine.)
				}
			}
			
			return this;
		}
	}

	public void setParticipant(Participant participant) {
		WizardTreeModel wtm = new WizardTreeModel(participant);
		tree.setModel(wtm);
	}

	public void valueChanged(TreeSelectionEvent e) {
		if (tree.getLastSelectedPathComponent() != null) {
			Object objet = ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject();
			WizardStateMachine.getInstance().setFocusedObject(objet,WizardControler.getInstance().getDefaultHTML(null));
		}
	}
}
