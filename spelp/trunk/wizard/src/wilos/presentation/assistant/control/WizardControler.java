package wilos.presentation.assistant.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.Step;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.main.ActionBar;
import wilos.presentation.assistant.view.main.ContextualMenu;
import wilos.presentation.assistant.view.panels.TreePanel;
import wilos.presentation.assistant.view.panels.WizardStateMachine;
import wilos.presentation.assistant.webservices.WizardServicesProxy;
import wilos.utils.Constantes;

public class WizardControler {
	private static WizardControler wc = null;
	private ActionBar actionBar = null;
	private TreePanel treePanel = null ;
	private ContextualMenu menuContextuel = null ;
	private boolean showInfo = true ;
	
	private ConcreteTaskDescriptor lastCtd;
	
	private WizardControler() {
		
	}
	
	public void showContextualMenu (MouseEvent arg0){
		if (WizardStateMachine.getInstance().getCurrentState() != WizardStateMachine.STATE_NOTHING){
			menuContextuel.show(arg0.getComponent(),arg0.getX(),arg0.getY());
		}
	}
	
	public void changeHTMLViewerBehavior(boolean newBehavior) {
		showInfo = newBehavior;
		if (showInfo){
			HTMLViewer.getInstance(null).viewObject(lastCtd);
		}
		else {
			HTMLViewer.getInstance(null).setVisible(false);
		}
		actionBar.setJCheckBoxShowViewerEnabled(newBehavior);
	}
	
	public static WizardControler getInstance() {
		if (wc == null) {
			wc = new WizardControler();
		}
		return wc;
	}
	
	/**
	 * Start a concrete task descriptor
	 * @param ctd
	 */
	public void startConcreteTaskDescriptor(ConcreteTaskDescriptor ctd) {
		if (ctd.getState() == Constantes.State.READY || ctd.getState() == Constantes.State.SUSPENDED ) {
			WizardServicesProxy.startConcreteTaskDescriptor(ctd.getId());
			ctd.setState(Constantes.State.STARTED);
			WizardStateMachine.getInstance().setFocusedObject(ctd);
		}
	}
	
	/**
	 * Suspend a concrete task descriptor
	 * @param ctd
	 */	
	public void pauseConcreteTaskDescriptor(ConcreteTaskDescriptor ctd) {
		if (ctd.getState() == Constantes.State.STARTED) {
			WizardServicesProxy.suspendConcreteTaskDescriptor(ctd.getId());
			ctd.setState(Constantes.State.SUSPENDED);
			WizardStateMachine.getInstance().setFocusedObject(ctd);
		}
	}
	
	/**
	 * Finish  a concrete task descriptor
	 * @param ctd
	 */
	public void finishConcreteTaskDescriptor(ConcreteTaskDescriptor ctd) {
		if (ctd.getState() == Constantes.State.STARTED) {
			WizardServicesProxy.stopConcreteTaskDescriptor(ctd.getId());
			for (Step s : ctd.getTaskDescriptor().getTaskDefinition().getSteps()) {
				WizardStateMachine.getInstance().changeStepState(s, WizardStateMachine.STATE_STEP_FINISHED);
			}
			ctd.setState(Constantes.State.FINISHED);
			WizardStateMachine.getInstance().setFocusedObject(ctd);
		}
	}
	
	public void initUIElements(ActionBar theActionToolBar,TreePanel theTreePanel, ContextualMenu menu) {;
		actionBar = theActionToolBar ;
		treePanel = theTreePanel;
		menuContextuel = menu ;
		showInfo = true;
		initActions() ;
		
	}
	
	private void initActions() {
		ActionListener actionPlay = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode dmt =  (DefaultMutableTreeNode)WizardControler.getInstance().getTreePanel().getTree().getLastSelectedPathComponent();
				
				if(dmt != null) {
					
					ConcreteTaskDescriptor selectedTask = (ConcreteTaskDescriptor)dmt.getUserObject();
					// if(selectedTask.getId() != null) {
					WizardControler.getInstance().changeHTMLViewerBehavior(true);
					WizardControler.getInstance().startConcreteTaskDescriptor(selectedTask);
					treePanel.getTree().treeDidChange();
					//WizardControler.getInstance().refreshParticipant();
					//}					
				}
				
			}
		};
		ActionListener actionPause = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode dmt =  (DefaultMutableTreeNode)WizardControler.getInstance().getTreePanel().getTree().getLastSelectedPathComponent();
				
				if(dmt != null) {
					ConcreteTaskDescriptor selectedTask = (ConcreteTaskDescriptor)dmt.getUserObject();
					//if(selectedTask.getId() != null) {
						WizardControler.getInstance().changeHTMLViewerBehavior(true);
						WizardControler.getInstance().pauseConcreteTaskDescriptor(selectedTask);
						treePanel.getTree().treeDidChange();
						//WizardControler.getInstance().refreshParticipant();
					//}
				}
				
			}
		};
		ActionListener actionFinish = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode dmt =  (DefaultMutableTreeNode)WizardControler.getInstance().getTreePanel().getTree().getLastSelectedPathComponent();
				if(dmt != null) {
					if (dmt.getUserObject() instanceof ConcreteTaskDescriptor){
						ConcreteTaskDescriptor selectedTask = (ConcreteTaskDescriptor)dmt.getUserObject();
						//if(selectedTask.getId() != null) {
							WizardControler.getInstance().changeHTMLViewerBehavior(true);
							WizardControler.getInstance().finishConcreteTaskDescriptor(selectedTask);
							treePanel.getTree().treeDidChange();
							
							
							//WizardControler.getInstance().refreshParticipant();
						//}
					}
					else if (dmt.getUserObject() instanceof Step){
						Step selectedStep = (Step)dmt.getUserObject();
						// if(selectedTask.getId() != null) {
						WizardControler.getInstance().changeHTMLViewerBehavior(true);
						WizardStateMachine.getInstance().changeStepState(selectedStep, WizardStateMachine.STATE_STEP_FINISHED);
						treePanel.getTree().treeDidChange();
						//WizardControler.getInstance().refreshParticipant();
						//}
					}
				}
				
			}
		};
		
		actionBar.getJButtonPlayTask().addActionListener(actionPlay);
		menuContextuel.getJButtonPlayTask().addActionListener(actionPlay);
		actionBar.getJButtonPauseTask().addActionListener(actionPause);
		menuContextuel.getJButtonPauseTask().addActionListener(actionPause);
		actionBar.getJButtonFinished().addActionListener(actionFinish);
		menuContextuel.getJButtonFinished().addActionListener(actionFinish);
	}

	public void refreshParticipant() {
		treePanel.setParticipant(WizardServicesProxy.getParticipant());	
		WizardStateMachine.getInstance().deleteAllStep();
	}

	public static WizardControler getWc() {
		return wc;
	}

	public ActionBar getActionBar() {
		return actionBar;
	}

	public ContextualMenu getMenuContextuel() {
		return menuContextuel;
	}

	public boolean isShowInfo() {
		return showInfo;
	}

	public TreePanel getTreePanel() {
		return treePanel;
	}

	public void setLastCtd(ConcreteTaskDescriptor lastCtd) {
		this.lastCtd = lastCtd;
	}
}
