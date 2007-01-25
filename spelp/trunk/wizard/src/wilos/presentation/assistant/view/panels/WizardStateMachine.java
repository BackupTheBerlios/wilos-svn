package wilos.presentation.assistant.view.panels;

import javax.swing.JPopupMenu;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.main.ActionBar;
import wilos.presentation.assistant.view.main.ContextualMenu;
import wilos.utils.Constantes;

public class WizardStateMachine {
	private final int STATE_NOTHING = 0;
	private final int STATE_PARTICIPANT = 1;
	private final int STATE_TASK_STARTED = 2;
	private final int STATE_TASK_READY = 3;
	private final int STATE_TASK_CREATED = 4;
	private final int STATE_TASK_SUSPENDED = 5;
	private final int STATE_TASK_FINISHED = 6;
	
	private int currentState;
	private boolean showInfo;
	private ConcreteTaskDescriptor lastCtd;
	
	private static WizardStateMachine wsm = null;
	
	private ActionBar actionToolBar;
	private TreePanel treePanel;
	public ContextualMenu menuContextuel ;
	
	private WizardStateMachine() {
		
	}
	
	public JPopupMenu getMenuContextuel (){
		return menuContextuel ;
	}
	
	public TreePanel getTreePanel() {
		return treePanel;
	}

	public void initUIElements(ActionBar theActionToolBar,TreePanel theTreePanel, ContextualMenu menu) {
		actionToolBar = theActionToolBar;
		treePanel = theTreePanel;
		menuContextuel = menu ;
		updateState(STATE_NOTHING);
		showInfo = true;
	}
	
	public static WizardStateMachine getInstance() {
		if (wsm == null) {
			wsm = new WizardStateMachine();
		}
		return wsm;
	}
	
	/*
		en fonction de la selection :
		- participant -> toolbar minimale
		- roleDescriptor -> idem
		- taskDescriptor
			ready -> play
			finished -> neant
			created -> neant
			started -> pause, terminer
			suspended -> play
		
		en fonction des boutons
	 */
	
	public void setFocusedObject(Object object) {
//		if (object.getClass().getSimpleName().equals("Participant")) {
//			System.out.println("pouet");
//			updateState(this.STATE_PARTICIPANT);
//		}
		if (object instanceof ConcreteTaskDescriptor) {
			ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor) object;
			if (ctd.getState() == Constantes.State.STARTED) {
				updateState(this.STATE_TASK_STARTED);
			}
			else if (ctd.getState() == Constantes.State.READY) {
				updateState(this.STATE_TASK_READY);
			}
			else if (ctd.getState() == Constantes.State.CREATED) {
				updateState(this.STATE_TASK_CREATED);
			}
			else if (ctd.getState() == Constantes.State.SUSPENDED) {
				updateState(this.STATE_TASK_SUSPENDED);
			}
			else if (ctd.getState() == Constantes.State.FINISHED) {
				updateState(this.STATE_TASK_FINISHED);
			}
			else {
				updateState(this.STATE_NOTHING);
			}

			lastCtd = ctd;
			
			if (showInfo){
				HTMLViewer.getInstance(null).setConcreteTaskDescriptor(ctd);
			}
			
		}
		else {
			updateState(this.STATE_NOTHING);
		}
	}

	private void updateState(int newState) {
		this.currentState = newState;
		switch (this.currentState) {
		case STATE_PARTICIPANT :
			actionToolBar.setButtons(actionToolBar.INVISIBLE, actionToolBar.INVISIBLE, actionToolBar.INVISIBLE);
			
			break;
		case STATE_NOTHING :
			actionToolBar.setButtons(actionToolBar.INVISIBLE, actionToolBar.INVISIBLE, actionToolBar.INVISIBLE);
			
			break;
		case STATE_TASK_CREATED :	
			actionToolBar.setButtons(actionToolBar.DISABLED, actionToolBar.DISABLED, actionToolBar.DISABLED);
			
			break;
		case STATE_TASK_READY :	
			actionToolBar.setButtons(actionToolBar.ENABLED, actionToolBar.DISABLED, actionToolBar.DISABLED);
			
			break;
		case STATE_TASK_STARTED :	
			actionToolBar.setButtons(actionToolBar.DISABLED, actionToolBar.ENABLED, actionToolBar.ENABLED);
			
			break;
		case STATE_TASK_SUSPENDED :	
			actionToolBar.setButtons(actionToolBar.ENABLED, actionToolBar.DISABLED, actionToolBar.DISABLED);
						
			break;
		case STATE_TASK_FINISHED :	
			actionToolBar.setButtons(actionToolBar.DISABLED, actionToolBar.DISABLED, actionToolBar.DISABLED);
			
			break;
		}
		
	}
	
	public void changeHTMLViewerBehavior(boolean newBehavior) {
		showInfo = newBehavior;
		if (showInfo){
			HTMLViewer.getInstance(null).setConcreteTaskDescriptor(lastCtd);
		}
		else {
			HTMLViewer.getInstance(null).setVisible(false);
		}
		actionToolBar.setJCheckBoxShowViewerEnabled(newBehavior);
	}
}
