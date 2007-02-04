package wilos.presentation.assistant.view.panels;

import java.util.Observable;

import javax.swing.JPopupMenu;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.main.ActionBar;
import wilos.presentation.assistant.view.main.ContextualMenu;
import wilos.presentation.assistant.webservices.WizardServicesProxy;
import wilos.utils.Constantes;

public class WizardStateMachine extends Observable{
	public static final int STATE_NOTHING = 0;
	public static final int STATE_PARTICIPANT = 1;
	public static final int STATE_TASK_STARTED = 2;
	public static final int STATE_TASK_READY = 3;
	public static final int STATE_TASK_CREATED = 4;
	public static final int STATE_TASK_SUSPENDED = 5;
	public static final int STATE_TASK_FINISHED = 6;
		
	private int currentState = 0;
	
	
	private static WizardStateMachine wsm = null;
			
	private WizardStateMachine() {
		
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
				updateState(STATE_TASK_STARTED);
			}
			else if (ctd.getState() == Constantes.State.READY) {
				updateState(STATE_TASK_READY);
			}
			else if (ctd.getState() == Constantes.State.CREATED) {
				updateState(STATE_TASK_CREATED);
			}
			else if (ctd.getState() == Constantes.State.SUSPENDED) {
				updateState(STATE_TASK_SUSPENDED);
			}
			else if (ctd.getState() == Constantes.State.FINISHED) {
				updateState(STATE_TASK_FINISHED);
			}
			else {
				updateState(STATE_NOTHING);
			}

			WizardControler.getInstance().setLastCtd(ctd);
			
			if (WizardControler.getInstance().isShowInfo()){
				HTMLViewer.getInstance(null).viewObject(ctd);
			}
			
		}
		else {
			if (WizardControler.getInstance().isShowInfo()){
				HTMLViewer.getInstance(null).viewObject(object);
				updateState(STATE_NOTHING);
			}
		}
		// notifying all the objects managed by the state machine
		this.setChanged();
		notifyObservers();
	}

	private void updateState(int newState) {
		this.currentState = newState;
//		switch (this.currentState) {
//		case STATE_PARTICIPANT :
//			actionToolBar.setButtons(actionToolBar.INVISIBLE, actionToolBar.INVISIBLE, actionToolBar.INVISIBLE);
//			
//			break;
//		case STATE_NOTHING :
//			actionToolBar.setButtons(actionToolBar.INVISIBLE, actionToolBar.INVISIBLE, actionToolBar.INVISIBLE);
//			
//			break;
//		case STATE_TASK_CREATED :	
//			actionToolBar.setButtons(actionToolBar.DISABLED, actionToolBar.DISABLED, actionToolBar.DISABLED);
//			
//			break;
//		case STATE_TASK_READY :	
//			actionToolBar.setButtons(actionToolBar.ENABLED, actionToolBar.DISABLED, actionToolBar.DISABLED);
//			
//			break;
//		case STATE_TASK_STARTED :	
//			actionToolBar.setButtons(actionToolBar.DISABLED, actionToolBar.ENABLED, actionToolBar.ENABLED);
//			
//			break;
//		case STATE_TASK_SUSPENDED :	
//			actionToolBar.setButtons(actionToolBar.ENABLED, actionToolBar.DISABLED, actionToolBar.DISABLED);
//						
//			break;
//		case STATE_TASK_FINISHED :	
//			actionToolBar.setButtons(actionToolBar.DISABLED, actionToolBar.DISABLED, actionToolBar.DISABLED);
//			
//			break;
//		}
	}
	
	

	public int getCurrentState (){
		return currentState ;
	}

}
