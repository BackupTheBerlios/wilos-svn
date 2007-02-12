package wilos.presentation.assistant.control;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jdesktop.swingx.JXTree;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.Step;
import wilos.presentation.assistant.ressources.Bundle;
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
	private Component src = null ;
	private ConcreteTaskDescriptor lastCtd;
	private ArrayList<HTMLViewer> listHTML = new ArrayList<HTMLViewer>() ;
	
	private WizardControler() {
		
	}
	
	public Component getSrc () {
		return src ;
	}
	
	/**
	 * getDefaultHTML give the default HTMLviewer
	 * if it isn't exist it is created
	 * @param p position can be null
	 * @return the htmlViewer created
	 */
	public HTMLViewer getDefaultHTML(Point p){
		HTMLViewer h = null ;
		if (listHTML.size() != 0){
			h = listHTML.get(0).get(p);
		}
		else {
			h = new HTMLViewer(p);
			listHTML.add(h);
			listHTML.get(0).get(p);
		}
		return h;
	}
	
	/**
	 * addHTMLViewer create a new HTMLViewer and display it
	 * @param p the position where the htmlviewer will be display can be null
	 * @return the HTMLViewer created
	 */
	public HTMLViewer addHTMLViewer (Point p){
		HTMLViewer h = new HTMLViewer(p);
		listHTML.add(h);
		return (h) ;
	}
	
	/**
	 * getNewHTMLAction initialize the actions for the opening of HTMLViewer
	 * @return the ActionListener created
	 */
	public ActionListener getNewHTMLAction (){
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// get the position of the last htmlviewer
				Point p = listHTML.get(listHTML.size()-1).getLocation();
				p.y += listHTML.get(listHTML.size()-1).getHeightToolbar() * 2 ;
				p.x += listHTML.get(listHTML.size()-1).getHeightToolbar() ;
				HTMLViewer newHTMLViewer = WizardControler.getInstance().addHTMLViewer(p);
				// 2 case if the action is from a JList we have to delete the panel with the jlist
				// if it is from a node we have to take an element from a node
				if (src instanceof JList){
					WizardStateMachine.getInstance().setFocusedObject(getDefaultHTML(null).getJList().getSelectedValue(),newHTMLViewer);
					newHTMLViewer.trtGuides(null);
				}
				else if (src instanceof JXTree) {
					DefaultMutableTreeNode dmt =  (DefaultMutableTreeNode)WizardControler.getInstance().getTreePanel().getTree().getLastSelectedPathComponent();
					if(dmt != null) {
						WizardStateMachine.getInstance().setFocusedObject(dmt.getUserObject(),newHTMLViewer);		
					}
				}
					
							
			}
		};
		return al ;
	}
	
	/**
	 * showContextualMenu display the contextual menu with correct buttons
	 * @param arg0 the mouse event to delegate (needed for the position of the mouse)
	 */
	public void showContextualMenu (MouseEvent arg0){
		src = arg0.getComponent();
		// if the src is a jlist there is a special treatment not managed by the state machine
		if (src instanceof JList){
			JList list = (JList)src ;
			if (list.getSelectedValue() != null){
				menuContextuel.setButtons(ContextualMenu.INVISIBLE, ContextualMenu.INVISIBLE, ContextualMenu.INVISIBLE, ContextualMenu.ENABLED);
				menuContextuel.show(arg0.getComponent(),arg0.getX(),arg0.getY());
			}
		}
		// else the state machine can do its work
		else {
			menuContextuel.show(arg0.getComponent(),arg0.getX(),arg0.getY());
		}
		
	}
	
	/**
	 * changeHTMLViewerBehavior determine if the HTMLViewer is visible or not
	 * @param newBehavior
	 */
	public void changeHTMLViewerBehavior(boolean newBehavior) {
		showInfo = newBehavior;
		if (showInfo){
			getDefaultHTML(null).viewObject(lastCtd);
		}
		else {
			getDefaultHTML(null).setVisible(false);
		}
		actionBar.setJCheckBoxShowViewerEnabled(newBehavior);
	}
	
	/**
	 * getInstance get the unique instance of the WizardControler
	 * @return the WizardControler
	 */
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
		}
	}
	
	/**
	 * Suspend a concrete task descriptor
	 * @param ctd
	 */	
	public void pauseConcreteTaskDescriptor(ConcreteTaskDescriptor ctd) {
		if (ctd.getState() == Constantes.State.STARTED) {
			WizardServicesProxy.suspendConcreteTaskDescriptor(ctd.getId());
		}
	}
	
	/**
	 * Finish  a concrete task descriptor and finish all of its step if they exist
	 * @param ctd
	 */
	public void finishConcreteTaskDescriptor(ConcreteTaskDescriptor ctd) {
		if (ctd.getState() == Constantes.State.STARTED) {
			WizardServicesProxy.stopConcreteTaskDescriptor(ctd.getId());
		}
	}
	
	/**
	 * initUIElements init the User Interface elements
	 * @param theActionToolBar
	 * @param theTreePanel
	 * @param menu
	 */
	public void initUIElements(ActionBar theActionToolBar,TreePanel theTreePanel, ContextualMenu menu) {;
		actionBar = theActionToolBar ;
		treePanel = theTreePanel;
		menuContextuel = menu ;
		showInfo = true;
		initActions() ;
		
	}
	
	/**
	 * initActions init the actions for buttons play, pause, and finish
	 */
	private void initActions() {
		ActionListener actionPlay = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode dmt =  (DefaultMutableTreeNode)WizardControler.getInstance().getTreePanel().getTree().getLastSelectedPathComponent();
				if(dmt != null) {
					ConcreteTaskDescriptor selectedTask = (ConcreteTaskDescriptor)dmt.getUserObject();
					// if(selectedTask.getId() != null) {
					WizardControler.getInstance().changeHTMLViewerBehavior(true);
					WizardControler.getInstance().startConcreteTaskDescriptor(selectedTask);
					if (selectedTask.getTaskDescriptor().getTaskDefinition() != null){
						for (Step s : selectedTask.getTaskDescriptor().getTaskDefinition().getSteps()){
							if (WizardStateMachine.getInstance().getStepState(s) == WizardStateMachine.getInstance().STATE_STEP_CREATED){
								WizardStateMachine.getInstance().changeStepState(s, WizardStateMachine.STATE_STEP_READY);
							}
		
						}
					}
					selectedTask.setState(Constantes.State.STARTED);
					WizardStateMachine.getInstance().setFocusedObject(selectedTask,null);
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
						// if the task is suspended then the steps can't be finish so they are put in CREATED state
						if (selectedTask.getTaskDescriptor().getTaskDefinition() != null){
							for (Step s : selectedTask.getTaskDescriptor().getTaskDefinition().getSteps()){
								if (WizardStateMachine.getInstance().getStepState(s) != WizardStateMachine.getInstance().STATE_STEP_FINISHED){
									WizardStateMachine.getInstance().changeStepState(s, WizardStateMachine.STATE_STEP_CREATED);
								}
							}
						}
						selectedTask.setState(Constantes.State.SUSPENDED);
						WizardStateMachine.getInstance().setFocusedObject(selectedTask,null);
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

							selectedTask.setState(Constantes.State.FINISHED);
							WizardStateMachine.getInstance().setFocusedObject(selectedTask,null);
							treePanel.getTree().treeDidChange();//WizardControler.getInstance().refreshParticipant();
						//}
					}
					else if (dmt.getUserObject() instanceof Step){
						Step selectedStep = (Step)dmt.getUserObject();
						// if(selectedTask.getId() != null) {
						WizardControler.getInstance().changeHTMLViewerBehavior(true);
						WizardStateMachine.getInstance().changeStepState(selectedStep, WizardStateMachine.STATE_STEP_FINISHED);
						finishTaskIfNecessary((DefaultMutableTreeNode)dmt.getParent());
						treePanel.getTree().treeDidChange();
						//WizardControler.getInstance().refreshParticipant();
						//}
					}
				}
				
			}
			
			private void finishTaskIfNecessary(DefaultMutableTreeNode n){
				if (n.getUserObject() instanceof ConcreteTaskDescriptor){
					ConcreteTaskDescriptor parent = (ConcreteTaskDescriptor)n.getUserObject();
					if (parent.getTaskDescriptor().getTaskDefinition() != null){
						boolean ok = true ;
						for (Step s : parent.getTaskDescriptor().getTaskDefinition().getSteps()){
							ok = ok && (WizardStateMachine.getInstance().getStepState(s) == WizardStateMachine.STATE_STEP_FINISHED);
						}
						if (ok) {
							if (parent.getState().equals(Constantes.State.STARTED)){
								if(JOptionPane.showOptionDialog(treePanel, Bundle.getText("endstep.message"), Bundle.getText("endstep.title"), JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null) == JOptionPane.YES_OPTION){
									finishConcreteTaskDescriptor(parent);
								}
							}
						}
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

	/**
	 * refreshParticipant reload from the server the data of the participant
	 */
	public void refreshParticipant() {
		treePanel.setParticipant(WizardServicesProxy.getParticipant());	
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
