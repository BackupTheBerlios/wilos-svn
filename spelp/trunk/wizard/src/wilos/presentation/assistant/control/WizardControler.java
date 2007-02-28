package wilos.presentation.assistant.control;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jdesktop.swingx.JXTree;

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
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.main.ActionBar;
import wilos.presentation.assistant.view.main.ContextualMenu;
import wilos.presentation.assistant.view.panels.TreePanel;
import wilos.presentation.assistant.view.panels.WizardMutableTreeNode;
import wilos.presentation.assistant.view.panels.WizardStateMachine;
import wilos.presentation.assistant.view.panels.WizardTreeModel;
import wilos.presentation.assistant.webservices.WizardServicesProxy;
import wilos.utils.Constantes;

public class WizardControler {
	private static WizardControler wc = null;
	private ActionBar actionBar = null;
	private TreePanel treePanel = null ;
	private ContextualMenu menuContextuel = null ;
	private boolean showInfo = true ;
	private Component src = null ;
	private Object lastCtd;
	private ArrayList<HTMLViewer> listHTML = new ArrayList<HTMLViewer>() ;
	private Vector<Runnable> listThread = new Vector<Runnable>() ;
	// TODO ajouter les attributs langue + getters setters
	private long timeToRefresh = 5 ;				// tps en secondes
	private Thread currentRefreshThread = null ;
	private Thread currentThread = null ;
	private Runnable currentRefreshRunnable = null ;
	private int flagThread ;
	private Object MUTEX = new Object() ;
	private WizardControler() {
		
	}
	
	public Component getSrc () {
		return src ;
	}
	
	
	public synchronized void launchRefreshTread (){
		currentThread = new Thread (new Runnable(){
			public void run() {
				synchronized (MUTEX) {
					WizardControler.getInstance().connectToServer(this);
					WizardControler.this.flagThread = 1 ;
					updateTree(null,null);
					WizardControler.getInstance().disconnectToServer(this);
				}
			}
		});
		currentThread.start();
	}
	
	public synchronized void launchBackgroundThreadForTree(){
		currentRefreshThread = new Thread (new Runnable(){
			public void run() {
				currentRefreshRunnable = this ;
				while (WizardControler.getInstance().getTimeToRefresh() != 0){
					try {
						Thread.sleep(WizardControler.getInstance().getTimeToRefresh());
						launchRefreshTread();
						currentThread.join() ;
					} catch (InterruptedException e) {
						timeToRefresh = 0 ;
					}
				}
			}
		});
		currentRefreshThread.start();
	}
		
	private String getId(Object userObject){
		if (userObject instanceof ConcreteRoleDescriptor)
			return ((ConcreteRoleDescriptor) userObject).getId();
		else if (userObject instanceof Project)
			return ((Project) userObject).getId();
		else if (userObject instanceof ConcreteIteration)
			return ((ConcreteIteration) userObject).getId();
		else if (userObject instanceof ConcretePhase)
			return ((ConcretePhase) userObject).getId();
		else if (userObject instanceof ConcreteActivity)
			return ((ConcreteActivity) userObject).getId();
		else if (userObject instanceof ConcreteTaskDescriptor) 
			return ((ConcreteTaskDescriptor) userObject).getId();
		else if (userObject instanceof ConcreteBreakdownElement)
			return ((ConcreteBreakdownElement) userObject).getId();
		else if (userObject instanceof Element)
			return ((Element)userObject).getId();
		else return "" ;
	}
	
	private String getState (Object userObject) {
		if (userObject instanceof ConcreteTaskDescriptor){
			return ((ConcreteTaskDescriptor) userObject).getState();
		}
		return "" ;
	}

	/**
	 * updateTree get the tree updated from the server and modify the current
	 *
	 */
	private void updateTree(DefaultMutableTreeNode newNode, DefaultMutableTreeNode actualNode) {
		boolean trouve = false ;
		// getting the actual model
		WizardTreeModel model = (WizardTreeModel) treePanel.getTree().getModel() ;
		// if it is the first execution
		if (newNode == null && actualNode == null){
			// get the participant on the server
			Participant p = WizardServicesProxy.getParticipant() ;
			WizardTreeModel newModel = new WizardTreeModel(p,false);
			// creating the model from this participant 
			newNode = (DefaultMutableTreeNode)newModel.getRoot();
			actualNode = (DefaultMutableTreeNode)model.getRoot();
		}
		WizardMutableTreeNode tmpNewNode = null ;
		WizardMutableTreeNode tmpActualNode = null ;
			
		// managing if there is a deletion
		if (flagThread != 0 && actualNode.getChildCount() > newNode.getChildCount()){
			int diff = actualNode.getChildCount() - newNode.getChildCount();
			int nbdiff = 0 ;
			for (Enumeration e = actualNode.children() ; nbdiff < diff && e.hasMoreElements() ;){
				tmpActualNode = (WizardMutableTreeNode) e.nextElement();
				trouve = false ;
				for (Enumeration e2 = newNode.children() ; e2.hasMoreElements() ;){
					tmpNewNode = (WizardMutableTreeNode) e2.nextElement();
//					if (start){
//						trouve = trouve || tmpNewNode.getUserObject().equals(tmpActualNode.getUserObject());
						String id1,id2 ;
						id1 = getId(tmpNewNode.getUserObject()) ;
						id2 = getId(tmpActualNode.getUserObject()) ;
						trouve = trouve || ((id1 != null) && id1.equals(id2));
				}
				if (!trouve){
					model.removeNodeFromParent(tmpActualNode);
					nbdiff++;
				}
			}
		}
		//	for each project on server => check state modification or new branch
		for (Enumeration e = newNode.children() ; flagThread != 0 && e.hasMoreElements() ;){
			tmpNewNode = (WizardMutableTreeNode) e.nextElement();
			trouve = false ;
			for (Enumeration e2 = actualNode.children() ; !trouve &&  e2.hasMoreElements() ;){
				tmpActualNode = (WizardMutableTreeNode) e2.nextElement() ;
					String id1,id2 ;
					id1 = getId(tmpNewNode.getUserObject()) ;
					id2 = getId(tmpActualNode.getUserObject()) ;
					trouve = (id1 != null) && id1.equals(id2);
			}
			// if the node has been found
			if (flagThread != 0 && trouve){
				// manage the state
				if (!(getState(tmpNewNode.getUserObject()).equals(getState(tmpActualNode.getUserObject())))){
					if (tmpActualNode.getUserObject() instanceof ConcreteTaskDescriptor) {
						ConcreteTaskDescriptor ctdactual = (ConcreteTaskDescriptor) tmpActualNode.getUserObject() ;
						ConcreteTaskDescriptor ctdnew = (ConcreteTaskDescriptor) tmpNewNode.getUserObject() ;
						updateTreeVisualAndState(ctdactual, ctdnew.getState(),true);
					}
				}
				// recursive call for the children
				updateTree(tmpNewNode,tmpActualNode);
				trouve = false ;
			}
			// if the node is new we add it in the model
			else if(!trouve){
				if (!(tmpNewNode.getUserObject() instanceof Step)){
					WizardMutableTreeNode nodeToInsert = (WizardMutableTreeNode) tmpNewNode.clone() ;
					model.insertNodeInto(nodeToInsert,actualNode, newNode.getIndex(tmpNewNode));
				}
			}
		}
	}
		
	public long getTimeToRefresh() {
		// TODO Auto-generated method stub
		return timeToRefresh * 1000 ;
	}
	
	public void setTimeToRefresh(long value){
		timeToRefresh = value ;
	}

	/**
	 * connectToServer indics that a thread is connecting to a server 
	 * this method will change the icon of the toolbar the thread must give this in parameter 
	 * @param r
	 */
	public synchronized void connectToServer(Runnable r) {
		 if (listThread.size() == 0){
			actionBar.getJLabelConnect().setIcon(ImagesService.getImageIcon("images.connection.active"));
		}
		listThread.add(r);
	}
	
	/**
	 * disconnectToServer indics that a thread is disconnecting to a server 
	 * this method will change the icon of the toolbar the thread must give this in parameter 
	 * @param r
	 */
	public synchronized void disconnectToServer(Runnable r) {
		listThread.remove(r);
		if (listThread.size() == 0){
			actionBar.getJLabelConnect().setIcon(ImagesService.getImageIcon("images.connection.idle"));
		}
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
	 * Icon
	 * @param h
	 */
	public void closeHTMLViewer (HTMLViewer h){			
		listHTML.remove(h);
	}
	
	/**
	 * iconifyAllHTMLViewers hide all the htmlviewers
	 */
	public void iconifyAllHTMLViewers(){
		for (HTMLViewer h : listHTML){
			h.setExtendedState(JFrame.ICONIFIED);
		}
	}
	
	/**
	 * deiconifyAllHTMLViewers show all the htmlviewers
	 */
	public void deiconifyAllHTMLViewers(){
		for (HTMLViewer h : listHTML){
			h.setExtendedState(JFrame.NORMAL);
		}
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
	public synchronized void startConcreteTaskDescriptor(ConcreteTaskDescriptor ctd) {
		if (ctd.getState() == Constantes.State.READY || ctd.getState() == Constantes.State.SUSPENDED ) {
			RunnableTaskEvent traitement = new RunnableTaskEvent (RunnableTaskEvent.START,ctd);
			(new Thread (traitement)).start();
		}
	}
	
	/**
	 * Suspend a concrete task descriptor
	 * @param ctd
	 */	
	public synchronized void pauseConcreteTaskDescriptor(ConcreteTaskDescriptor ctd) {
		if (ctd.getState() == Constantes.State.STARTED) {
			RunnableTaskEvent traitement = new RunnableTaskEvent (RunnableTaskEvent.SUSPEND,ctd);
			(new Thread (traitement)).start();
		}
	}
	
	/**
	 * Finish  a concrete task descriptor and finish all of its step if they exist
	 * @param ctd
	 */
	public synchronized void finishConcreteTaskDescriptor(ConcreteTaskDescriptor ctd) {
		if (ctd.getState() == Constantes.State.STARTED) {
			RunnableTaskEvent traitement = new RunnableTaskEvent (RunnableTaskEvent.FINISH,ctd);
			(new Thread (traitement)).start();
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

	private void updateTreeVisualAndState(ConcreteTaskDescriptor selectedTask,String state, boolean batch) {
		if (state.equals(Constantes.State.STARTED)){
			if (selectedTask.getTaskDescriptor().getTaskDefinition() != null){
				for (Step s : selectedTask.getTaskDescriptor().getTaskDefinition().getSteps()){
					if (WizardStateMachine.getInstance().getStepState(s) == WizardStateMachine.STATE_STEP_CREATED){
						WizardStateMachine.getInstance().changeStepState(s, WizardStateMachine.STATE_STEP_READY);
					}

				}
			}
			selectedTask.setState(state);
		}
		else if (state.equals(Constantes.State.SUSPENDED)){
			if (selectedTask.getTaskDescriptor().getTaskDefinition() != null){
				for (Step s : selectedTask.getTaskDescriptor().getTaskDefinition().getSteps()){
					if (WizardStateMachine.getInstance().getStepState(s) != WizardStateMachine.STATE_STEP_FINISHED){
						WizardStateMachine.getInstance().changeStepState(s, WizardStateMachine.STATE_STEP_CREATED);
					}
				}
			}
			selectedTask.setState(state);
		}
		else if (state.equals(Constantes.State.FINISHED)){
			if (selectedTask.getTaskDescriptor().getTaskDefinition() != null){
				for (Step s : selectedTask.getTaskDescriptor().getTaskDefinition().getSteps()){
					WizardStateMachine.getInstance().changeStepState(s, WizardStateMachine.STATE_TASK_FINISHED);
				}
			}
			selectedTask.setState(state);
		}
		if (!batch){
			WizardControler.getInstance().changeHTMLViewerBehavior(true);
			WizardStateMachine.getInstance().setFocusedObject(selectedTask,null);
		}
		treePanel.getTree().treeDidChange();
	}
	
	private void playTask() {
		DefaultMutableTreeNode dmt =  (DefaultMutableTreeNode)WizardControler.getInstance().getTreePanel().getTree().getLastSelectedPathComponent();
		if(dmt != null) {
			ConcreteTaskDescriptor selectedTask = (ConcreteTaskDescriptor)dmt.getUserObject();
			// if(selectedTask.getId() != null) {
			WizardControler.getInstance().startConcreteTaskDescriptor(selectedTask);
			//WizardControler.getInstance().refreshParticipant();
			//}					
		}
	}
	
	private void pauseTask() {
		DefaultMutableTreeNode dmt =  (DefaultMutableTreeNode)WizardControler.getInstance().getTreePanel().getTree().getLastSelectedPathComponent();
		if(dmt != null) {
			ConcreteTaskDescriptor selectedTask = (ConcreteTaskDescriptor)dmt.getUserObject();
			//if(selectedTask.getId() != null) {
				WizardControler.getInstance().pauseConcreteTaskDescriptor(selectedTask);
				//WizardControler.getInstance().refreshParticipant();
			//}
		}
	}
	
	private void stopTask() {
		DefaultMutableTreeNode dmt =  (DefaultMutableTreeNode)WizardControler.getInstance().getTreePanel().getTree().getLastSelectedPathComponent();
		if(dmt != null) {
			if (dmt.getUserObject() instanceof ConcreteTaskDescriptor){
				ConcreteTaskDescriptor selectedTask = (ConcreteTaskDescriptor)dmt.getUserObject();
				//if(selectedTask.getId() != null) {
				WizardControler.getInstance().finishConcreteTaskDescriptor(selectedTask);
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
							WizardControler.getInstance().changeHTMLViewerBehavior(true);
							WizardControler.getInstance().finishConcreteTaskDescriptor(parent);
							parent.setState(Constantes.State.FINISHED);
							WizardStateMachine.getInstance().setFocusedObject(parent,null);
						}
					}
				}
			}
		}
	}
	
	/**
	 * initActions init the actions for buttons play, pause, and finish
	 */
	private void initActions() {
		ActionListener actionPlay = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				playTask();
				
			}			
		};
		ActionListener actionPause = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pauseTask();
				
			}
		};
		ActionListener actionFinish = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				stopTask();
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
		launchRefreshTread() ;
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

	public void setLastCtd(Object o) {
		this.lastCtd = o;
	}
	
	public void cancelrefreshThread (){
		if (currentRefreshThread != null){
			currentRefreshThread.interrupt() ;
			disconnectToServer(currentRefreshRunnable);
		}
	}
	
	private void interruptRefreshThread (){
		if (currentRefreshThread != null){
			flagThread = 0 ;
			disconnectToServer(currentRefreshRunnable);
		}
	}
	
	private class RunnableTaskEvent implements Runnable {
		private static final int START = 0;
		private static final int SUSPEND = 1;
		private static final int FINISH = 2;
		
		private int type;
		private ConcreteTaskDescriptor taskId;
		
		public RunnableTaskEvent (int t, ConcreteTaskDescriptor ctd) {
			type = t;
			taskId = ctd;
		}
		
		public void run () {
			interruptRefreshThread();
			synchronized(taskId){
				WizardControler.getInstance().connectToServer(this);
				String state = "" ;
				switch (type)
				{
					case START:
						WizardServicesProxy.startConcreteTaskDescriptor(taskId.getId());
						state = Constantes.State.STARTED ;
						break;
					case SUSPEND:
						WizardServicesProxy.suspendConcreteTaskDescriptor(taskId.getId());
						state = Constantes.State.SUSPENDED ;
						break;
					case FINISH:
						WizardServicesProxy.stopConcreteTaskDescriptor(taskId.getId());
						state = Constantes.State.FINISHED ;
				}
				WizardControler.this.updateTreeVisualAndState(taskId, state,false);
				WizardControler.getInstance().disconnectToServer(this);
			}
		}
	}
}
