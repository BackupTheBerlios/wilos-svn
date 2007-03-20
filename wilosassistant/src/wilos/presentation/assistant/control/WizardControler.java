/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.presentation.assistant.control;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

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
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.task.Step;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.main.ActionBar;
import wilos.presentation.assistant.view.main.ContextualMenu;
import wilos.presentation.assistant.view.main.DownLoadFrame;
import wilos.presentation.assistant.view.main.WizardMainFrame;
import wilos.presentation.assistant.view.panels.InfoPanel;
import wilos.presentation.assistant.view.panels.ListenerTime;
import wilos.presentation.assistant.view.panels.TreePanel;
import wilos.presentation.assistant.view.panels.WizardMutableTreeNode;
import wilos.presentation.assistant.view.panels.WizardStateMachine;
import wilos.presentation.assistant.view.panels.WizardTreeModel;
import wilos.presentation.assistant.webservices.WizardServicesProxy;
import wilos.utils.Constantes;

public class WizardControler {
	private static final int DELETION = 0;
	private static final int ADDITION = 1;
	private static WizardControler wc = null;
	private ActionBar actionBar = null;
	private TreePanel treePanel = null ;
	private InfoPanel infoPanel = null ;
	private ContextualMenu menuContextuel = null ;
	private boolean showInfo = true ;
	private Component src = null ;
	private Object lastCtd;
	private ArrayList<HTMLViewer> listHTML = new ArrayList<HTMLViewer>() ;
	private Vector<Runnable> listThread = new Vector<Runnable>() ;
	private Locale lang = Locale.getDefault();
	private long timeToRefresh;				// tps en secondes
	private Thread currentRefreshThread = null ;
	private Thread currentThread = null ;
	private Runnable currentRefreshRunnable = null ;
	private int flagThread ;
	private Integer nbTasksTaskStarted =  new Integer(0) ;
	private Object MUTEX = new Object() ;
	private TimeThread currentTask = new TimeThread();
	private Object MUTEX_REFRESH_TIME = new Object() ;
	private Thread threadTime = null ;
	private WizardMainFrame wizardMainFrame;
	private WizardControler() {
		
	}
	
	
	public Component getSrc () {
		return src ;
	}
	
	/**
	 * method to launch the thread at the application start
	 * used in loginpanel
	 */
	public void launchFirstThread() {
		WizardControler.getInstance().setAllTasksSuspended(null);
		OptionsParser op = new OptionsParser();
		setTimeToRefresh(op.getRefreshTime()) ;
		WizardControler.getInstance().launchBackgroundThreadForTree();
		/*thread launch*/
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
	
	
	/**
	 * downloadTread
	 * @return the current thread
	 */
	public synchronized Thread downloadThread (final Guidance g, final String file, final String pathFileToDownload){

		Thread	monThread = new Thread (new Runnable(){
				public void run() {
						
						WizardControler.getInstance().connectToServer(this);
						
						// Code: appel de la webService pour telecharger
						// affichage d une fenetre de telechargement
						DownLoadFrame df = new DownLoadFrame(file, pathFileToDownload);
						
						byte [] file = WizardServicesProxy.getGuidanceAttachmentContent(g.getGuid());
						if (file != null) {
						
							// Reconstruction du fichier en local
							FileOutputStream f;
							try {
								f = new FileOutputStream(pathFileToDownload);
								File aFile = new File(pathFileToDownload);
								if (aFile.exists()){
									aFile.delete() ;
								}
								try {
									f.write(file);
									f.close();
								} 
								catch (IOException e) {
									new ExceptionManager(e);
								}
							} catch (FileNotFoundException e1) {
								new ExceptionManager(e1);
							}							
							df.endOfTreatment() ;							
						}
						else {
						
							df.dispose();
							JOptionPane.showMessageDialog(null, Bundle.getText("wizardControler.errorMessage"),Bundle.getText("ErrorDialog.title"), JOptionPane.ERROR_MESSAGE);
						}
						WizardControler.getInstance().disconnectToServer(this);
				}
			});
		monThread.start();
		return monThread;
	}
	
	/**
	 * Create a thread for calling the wizard service proxy
	 * @param taskGuid
	 * @param newTime
	 */
	public synchronized void setAccomplishedTimeByTask(final String taskGuid,final float newTime) {
		Thread aThread = new Thread (new Runnable(){
			public void run() {
				WizardControler.getInstance().connectToServer(this);
				WizardServicesProxy.setAccomplishedTimeByTask(taskGuid, newTime);
				WizardControler.getInstance().disconnectToServer(this);
			}
		}) ;
		aThread.start() ;
	}
	
	/**
	 * Create a thread for calling the wizard service proxy
	 * @param taskGuid
	 * @param newTime
	 */
	public synchronized void setRemainingTimeByTask(final String taskGuid,final float newTime) {
		Thread aThread = new Thread (new Runnable(){
			public void run() {
				WizardControler.getInstance().connectToServer(this);
				WizardServicesProxy.setRemainingTimeByTask(taskGuid, newTime);
				WizardControler.getInstance().disconnectToServer(this);
			}
		}) ;
		aThread.start() ;
	}
	
	public synchronized void launchBackgroundThreadForTree(){
		currentRefreshThread = new Thread (new Runnable(){
			public void run() {
				currentRefreshRunnable = this ;
				while (WizardControler.getInstance().getTimeToRefresh() != 0){
					try {
						Thread.sleep(getTimeToRefresh());
						launchRefreshTread();
						currentThread.join() ;
					} catch (InterruptedException e) {
						
						///timeToRefresh = 0 ;
					}
				}
			}
		});
		currentRefreshThread.start();
	}
		
	private String getId(Object userObject){
//		if (userObject instanceof ConcreteRoleDescriptor)
//			return ((ConcreteRoleDescriptor) userObject).getId();
//		else if (userObject instanceof Project)
//			return ((Project) userObject).getId();
//		else if (userObject instanceof ConcreteIteration)
//			return ((ConcreteIteration) userObject).getId();
//		else if (userObject instanceof ConcretePhase)
//			return ((ConcretePhase) userObject).getId();
//		else if (userObject instanceof ConcreteActivity)
//			return ((ConcreteActivity) userObject).getId();
//		else if (userObject instanceof ConcreteTaskDescriptor) 
//			return ((ConcreteTaskDescriptor) userObject).getId();
//		else if (userObject instanceof ConcreteBreakdownElement)
//			return ((ConcreteBreakdownElement) userObject).getId();
		if (userObject instanceof ConcreteBreakdownElement)
			return ((ConcreteBreakdownElement)userObject).getId();
		else if (userObject instanceof Element)
			return ((Element)userObject).getId();
		else return "" ;
	}
	
	private String getGuidForDisplaying(Object userObject){
		if (userObject instanceof ConcreteRoleDescriptor)
			return ((ConcreteRoleDescriptor) userObject).getRoleDescriptor().getRoleDefinition().getGuid();
		else if (userObject instanceof ConcreteIteration)
			return ((ConcreteIteration) userObject).getIteration().getGuid();
		else if (userObject instanceof ConcretePhase)
			return ((ConcretePhase) userObject).getPhase().getGuid();
		else if (userObject instanceof Project)
			return null ;
		else if (userObject instanceof ConcreteActivity)
			return ((ConcreteActivity) userObject).getActivity().getGuid();
		else if (userObject instanceof ConcreteTaskDescriptor) {
			ConcreteTaskDescriptor ctd = ((ConcreteTaskDescriptor) userObject);
			if (ctd.getTaskDescriptor().getTaskDefinition() != null){
				return ctd.getTaskDescriptor().getTaskDefinition().getGuid();
			}
			else {
				return null ;
			}
		}
		else if (userObject instanceof ConcreteBreakdownElement)
			return ((ConcreteBreakdownElement) userObject).getBreakdownElement().getGuid();
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
		String id1= "" ,id2 = "";
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
						id1 = getId(tmpNewNode.getUserObject()) ;
						id2 = getId(tmpActualNode.getUserObject()) ;
						trouve = trouve || ((id1 != null) && id1.equals(id2));
				}
				if (!trouve){
					manageStatesStep(tmpActualNode, DELETION);
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
					manageStatesStep(nodeToInsert, ADDITION);
					model.insertNodeInto(nodeToInsert,actualNode, newNode.getIndex(tmpNewNode));
				}
			}
		}
	}
		
	private void manageStatesStep (WizardMutableTreeNode node, int type) {
		for (Enumeration<WizardMutableTreeNode> e = node.children() ; e.hasMoreElements();){
			WizardMutableTreeNode child = e.nextElement() ;
			if (child.getUserObject() instanceof Step){
				Step s = (Step)child.getUserObject();
				switch (type){
					case DELETION :
							WizardStateMachine.getInstance().deleteStep(s);
						break;
					case ADDITION :
						ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor) ((WizardMutableTreeNode)child.getParent()).getUserObject() ;
						if (ctd.getState().equals(Constantes.State.STARTED)){
							WizardStateMachine.getInstance().addStep(s,WizardStateMachine.STATE_STEP_READY ) ;
						}
						else if (ctd.getState().equals(Constantes.State.FINISHED)){
							WizardStateMachine.getInstance().addStep(s,WizardStateMachine.STATE_STEP_FINISHED ) ;
						}
						else {
							WizardStateMachine.getInstance().addStep(s,WizardStateMachine.STATE_STEP_CREATED ) ;
						}
						break ;
				}
			}
			else {
				manageStatesStep(child, type);
			}
		}
	}
	
	public Object getElementByGuid(String guid,MutableTreeNode node) {
		// getting the actual model
		boolean trouve = false ; 
		WizardTreeModel model = (WizardTreeModel) treePanel.getTree().getModel() ;
		Object retour = null;
		// if it is the first execution
		if (node == null) {
			node = (MutableTreeNode) model.getRoot() ;
		}
		//	for each project on server => check state modification or new branch
		for (Enumeration e = node.children() ; !trouve && e.hasMoreElements() ;){
			WizardMutableTreeNode tmpNode = (WizardMutableTreeNode) e.nextElement();
			// if the node has been found
			// manage the state
			String guidNode = getGuidForDisplaying(tmpNode.getUserObject());
			if (guidNode != null && guidNode.equals(guid)){
				retour = tmpNode.getUserObject() ;
				trouve = (retour != null) ;
			}
			// if it is not found maybe it is a guidance
			else if (tmpNode.getUserObject() instanceof ConcreteTaskDescriptor) {
				ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor)tmpNode.getUserObject();
				if (ctd.getTaskDescriptor().getTaskDefinition() != null && ctd.getTaskDescriptor().getTaskDefinition().getGuidances() != null){
					for (Guidance g : ctd.getTaskDescriptor().getTaskDefinition().getGuidances()){
						guidNode = g.getGuid() ;
						if (guidNode != null && guidNode.equals(guid)){
							retour = g ;
							trouve = (retour != null) ;
						}
					}
				}
			}
			if (!trouve) {
				retour = getElementByGuid(guid,tmpNode);
				trouve = (retour != null) ;
			}
		}
		return retour;
	}
	
	/**
	 * updateTree get the tree updated from the server and modify the current
	 *
	 */
	public void setAllTasksSuspended(MutableTreeNode node) {
		// getting the actual model
		WizardTreeModel model = (WizardTreeModel) treePanel.getTree().getModel() ;
		// if it is the first execution
		if (node == null) {
			node = (MutableTreeNode) model.getRoot() ;
		}
		//	for each project on server => check state modification or new branch
		for (Enumeration e = node.children() ; e.hasMoreElements() ;){
			WizardMutableTreeNode tmpNode = (WizardMutableTreeNode) e.nextElement();
			// if the node has been found
			// manage the state
			if (getState(tmpNode.getUserObject()).equals(Constantes.State.STARTED)){
				if (tmpNode.getUserObject() instanceof ConcreteTaskDescriptor) {
					ConcreteTaskDescriptor ctdactual = (ConcreteTaskDescriptor) tmpNode.getUserObject() ;
					nbTasksTaskStarted = 1 ;
					pauseConcreteTaskDescriptor(ctdactual,true);
					updateTreeVisualAndState(ctdactual, Constantes.State.SUSPENDED,true);
				}
			}
			// recursive call for the children
			setAllTasksSuspended(tmpNode);
		}
	}
	
	public long getTimeToRefresh() {
		// TODO Auto-generated method stub
		int retour = 0 ;
		synchronized (MUTEX_REFRESH_TIME) {
			retour = (int) (timeToRefresh) ;
		}
		return retour ;
	}
	
	public void setTimeToRefresh(long value){
		synchronized (MUTEX_REFRESH_TIME) {
			timeToRefresh = value * 60000 ;
		}
		
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
	
	public Point positionHTMLShifted (){
		Point p = listHTML.get(listHTML.size()-1).getLocation();
		p.y += listHTML.get(listHTML.size()-1).getHeightToolbar() * 2 ;
		p.x += listHTML.get(listHTML.size()-1).getHeightToolbar() ;
		return p ;
	}
	
	/**
	 * getNewHTMLAction initialize the actions for the opening of HTMLViewer
	 * @return the ActionListener created
	 */
	public ActionListener getNewHTMLAction (){
		ActionListener al = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// get the position of the last htmlviewer
				
				HTMLViewer newHTMLViewer = WizardControler.getInstance().addHTMLViewer(positionHTMLShifted());
				// 2 case if the action is from a JList we have to delete the panel with the jlist
				// if it is from a node we have to take an element from a node
				if (src instanceof JList){
					WizardStateMachine.getInstance().setFocusedObject(getDefaultHTML(null).getJList().getSelectedValue(),newHTMLViewer);
					//newHTMLViewer.trtGuides(null);
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
	public synchronized void startConcreteTaskDescriptor(ConcreteTaskDescriptor ctd,boolean batch) {
		if (ctd.getState() == Constantes.State.READY || ctd.getState() == Constantes.State.SUSPENDED ) {
			RunnableTaskEvent traitement = new RunnableTaskEvent (RunnableTaskEvent.START,ctd,batch);
			(new Thread (traitement)).start();
		}
	}
	
	/**
	 * Suspend a concrete task descriptor
	 * @param ctd
	 */	
	public synchronized void pauseConcreteTaskDescriptor(ConcreteTaskDescriptor ctd,boolean batch) {
		if (ctd.getState() == Constantes.State.STARTED) {
			RunnableTaskEvent traitement = new RunnableTaskEvent (RunnableTaskEvent.SUSPEND,ctd,batch);
			(new Thread (traitement)).start();
		}
	}
	
	/**
	 * Finish  a concrete task descriptor and finish all of its step if they exist
	 * @param ctd
	 */
	public synchronized void finishConcreteTaskDescriptor(ConcreteTaskDescriptor ctd,boolean batch) {
		if (ctd.getState() == Constantes.State.STARTED) {
			RunnableTaskEvent traitement = new RunnableTaskEvent (RunnableTaskEvent.FINISH,ctd,batch);
			(new Thread (traitement)).start();
		}
	}
	
	/**
	 * initUIElements init the User Interface elements
	 * @param theActionToolBar
	 * @param theTreePanel
	 * @param menu
	 */
	public void initUIElements(WizardMainFrame mainFrame,ActionBar theActionToolBar,TreePanel theTreePanel, ContextualMenu menu,InfoPanel info) {;
		actionBar = theActionToolBar ;
		treePanel = theTreePanel;
		menuContextuel = menu ;
		infoPanel = info ;
		showInfo = true;
		wizardMainFrame = mainFrame ;
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
			WizardControler.getInstance().startConcreteTaskDescriptor(selectedTask,false);
			//WizardControler.getInstance().refreshParticipant();
			//}					
		}
	}
	
	private void pauseTask() {
		DefaultMutableTreeNode dmt =  (DefaultMutableTreeNode)WizardControler.getInstance().getTreePanel().getTree().getLastSelectedPathComponent();
		if(dmt != null) {
			ConcreteTaskDescriptor selectedTask = (ConcreteTaskDescriptor)dmt.getUserObject();
			//if(selectedTask.getId() != null) {
				WizardControler.getInstance().pauseConcreteTaskDescriptor(selectedTask,false);
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
				WizardControler.getInstance().finishConcreteTaskDescriptor(selectedTask,false);
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
							WizardControler.getInstance().finishConcreteTaskDescriptor(parent,true);
							parent.setState(Constantes.State.FINISHED);
							WizardStateMachine.getInstance().setFocusedObject(parent,null);
						}
					}
				}
			}
		}
	}
	
	private void saveTimes() {
		infoPanel.commitTextFields();
		ConcreteTaskDescriptor c =(ConcreteTaskDescriptor)WizardControler.getInstance().getLastCtd();
		float tps = infoPanel.getAccomplishTimeForUpload() ;
		WizardControler.getInstance().setAccomplishedTimeByTask(c.getId(),tps);
		c.setAccomplishedTime(tps);
		
		//remaining times
		tps = infoPanel.getRemainingTimeForUpload() ;
		WizardControler.getInstance().setRemainingTimeByTask(c.getId(),tps);
		c.setRemainingTime(tps);
		
		infoPanel.reinitializeTimes() ;
		threadTime = null ;
	}
	
	public void saveTimes(ConcreteTaskDescriptor ctd,float remaining, float accomplish,boolean saveAccomplish){
		WizardControler.getInstance().setRemainingTimeByTask(ctd.getId(),remaining);
		ctd.setRemainingTime(remaining);
		infoPanel.getTps_restant().setValue(infoPanel.transformInSeconds(remaining));
		if (saveAccomplish){
			WizardControler.getInstance().setAccomplishedTimeByTask(ctd.getId(),accomplish);
			ctd.setAccomplishedTime(accomplish);
			infoPanel.getTps().setValue(infoPanel.transformInSeconds(accomplish));
		}
	}
	
	/**
	 * initActions init the actions for buttons play, pause, and finish
	 */
	private void initActions() {
		ActionListener actionPlay = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				playTask();
				TimeThread timeThread = new TimeThread();
				threadTime = new Thread(timeThread);
				timeThread.addListenerTime(infoPanel);
			    ConcreteTaskDescriptor c =(ConcreteTaskDescriptor)WizardControler.getInstance().getLastCtd();
		        threadTime.start();
				InfoPanel.setCurrentTimedTask(c);							
			}			
		};
		ActionListener actionPause = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				threadTime.interrupt();
				pauseTask();
				saveTimes();	
				
//				String tps = infoPanel.getTps().getText();
//				int occ=0;
//				String min = "";
//				String heure = "";
//				
//				for (int i = 0; i<tps.length()&&occ!=2;i++)
//				{
//					if(tps.charAt(i)==':' )occ++;
//					if(occ==0&&tps.charAt(i)!=':' )heure+=tps.charAt(i);
//					if(occ==1&&tps.charAt(i)!=':' )min+=tps.charAt(i);
					
//				}
//				System.out.println(tps);
//				System.out.println(heure);
//				System.out.println(min);
//				float newTime = new Float(new Integer(heure)+new Float(new Integer(min)/100*60));
				//System.out.println(new Float(new Float(new Integer(heure))+new Float((new Integer(min)*100/60))));
				//WizardServicesProxy.setRemainingTimeByTask(c.getTaskDescriptor().getGuid(), newTime);
			
				
			}

			
		};
		ActionListener actionFinish = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				threadTime.interrupt();
				stopTask();
				saveTimes();
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
	
	public int getNbThreadStarted (){
		synchronized (nbTasksTaskStarted){
			return nbTasksTaskStarted.intValue() ;
		}
	}
	
	private class RunnableTaskEvent implements Runnable {
		private static final int START = 0;
		private static final int SUSPEND = 1;
		private static final int FINISH = 2;
		
		private int type;
		private boolean batch ;
		private ConcreteTaskDescriptor taskId;
		
		public RunnableTaskEvent (int t, ConcreteTaskDescriptor ctd,boolean batchMode) {
			type = t;
			taskId = ctd;
			batch = batchMode ;
		}
		
		public void run () {
			interruptRefreshThread();
			synchronized(taskId){
				WizardControler.getInstance().connectToServer(this);
				String state = "" ;
				switch (type)
				{
					case START:
						synchronized (nbTasksTaskStarted) {
							if (nbTasksTaskStarted.intValue() == 0) {
								WizardServicesProxy.startConcreteTaskDescriptor(taskId.getId());
								nbTasksTaskStarted = new Integer(1);
								state = Constantes.State.STARTED ;
							}
						}
						break;
					case SUSPEND:
						synchronized (nbTasksTaskStarted) {
							if (nbTasksTaskStarted.intValue() == 1){
								WizardServicesProxy.suspendConcreteTaskDescriptor(taskId.getId());
								nbTasksTaskStarted = new Integer(0);
								state = Constantes.State.SUSPENDED ;
							}
						}
						break;
					case FINISH:
						WizardServicesProxy.stopConcreteTaskDescriptor(taskId.getId());
						state = Constantes.State.FINISHED ;
						synchronized (nbTasksTaskStarted) {
							if (nbTasksTaskStarted.intValue() == 1){
								nbTasksTaskStarted = new Integer(0);
							}
						}
				}
				WizardControler.this.updateTreeVisualAndState(taskId, state,batch);
				WizardControler.getInstance().disconnectToServer(this);
			}
		}
	}
	
	private class TimeThread implements Runnable
	{
		private Vector<ListenerTime> listListener = new Vector<ListenerTime>();
		private Thread monThread;
		private boolean continuer = false;
		private long debut;
		
		public void addListenerTime(ListenerTime l){
			listListener.add(l);
		}
		public TimeThread() {
		// TODO Auto-generated constructor stub
		
		{
			
			continuer = true;
							
			}
		}
//		public void init(long d)
//		{
//			debut += d;		
//		}
		public void arreter()
		{
			if (monThread.isAlive())
			{
				continuer = false;
			}
		}
//		public long getDebut()
//		{
//			return debut;
//		}
		
		public void raz()
		{
			debut = 0;
			monThread = null;
		}
		public void run()
		{
			debut = System.currentTimeMillis();		
			while(continuer)
			{
					try
					{
						monThread.sleep(1000);
						long tps_passe = System.currentTimeMillis() - debut;
						long tempsEnvoi = (int)tps_passe/1000 ;
						notifyListeners(tempsEnvoi);			
//							for (int i=0;i	<time.length()&&occu!=2;i++) 
//							{
//								if (time.charAt(i)==':')occu++;
//								if(occu==0&&time.charAt(i)!=':')heure+=time.charAt(i);
//								if(occu==1&&time.charAt(i)!=':')min+=time.charAt(i);
//							}
//							
//							tps_passe = tps_passe / 1000;
//							if (tps_passe==5)
//							{
//								tps_passe = 0;
//								debut = System.currentTimeMillis();
//								Integer i = new Integer(min);							
//								min = String.valueOf(new Integer (i+1));
//							}
//							if (new Integer(heure)==60)
//							{
//								debut = System.currentTimeMillis();
//								tps_passe = 0;
//								min = new String("0");
//								Integer i = new Integer(heure+1);	
//								heure = String.valueOf(i);
//							}
//						
//							if (getLastCtd() == InfoPanel.getCurrentTimedTask()){
//								infoPanel.getTps().setText(String.valueOf(heure+":"+min+":"+tps_passe));
//							}
					}
					catch(InterruptedException e)
					{
						continuer = false ;
					}
			}
		}

		private void notifyListeners(long tps_passe) {
			for (ListenerTime l: listListener){
				l.putValue(tps_passe);
			}
			
			
		}
	}
	
	public Object getLastCtd (){
		return lastCtd ;
	}

	public Locale getLang() {
		return lang;
	}

	public void setLang(Locale lang) {
		this.lang = lang;
	}
	
	public int getDecimalValueInMinutes (float value) {
		//int min1 = Math.round(value-(float)value);
		int min1 ;
		float tmp = value - (int)value ;
		min1 =Math.round(tmp * 60) ;
		return min1 ;
	}
	
	public JFrame getMainFrameForDialogs (){
		return wizardMainFrame ;
	}
}
