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
import javax.swing.SwingUtilities;
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
				if (SwingUtilities.isRightMouseButton(arg0)){
					TreePanel.this.tree.setSelectionRow(TreePanel.this.tree.getRowForLocation(arg0.getX(), arg0.getY()));
				}
			}

			public void mouseReleased(MouseEvent arg0) {
				if (SwingUtilities.isRightMouseButton(arg0)){
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

	public void setParticipant(Participant participant) {
		WizardTreeModel wtm = new WizardTreeModel(participant,true);
		tree.setModel(wtm);
		WizardControler.getInstance().launchBackgroundThreadForTree();
	}

	public void valueChanged(TreeSelectionEvent e) {
		if (tree.getLastSelectedPathComponent() != null) {
			Object objet = ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject();
			WizardStateMachine.getInstance().setFocusedObject(objet,WizardControler.getInstance().getDefaultHTML(null));
		}
	}
	
//	 the renderer for the icon and label
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
}
