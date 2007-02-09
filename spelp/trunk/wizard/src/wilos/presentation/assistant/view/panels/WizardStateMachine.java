package wilos.presentation.assistant.view.panels;

import java.util.HashMap;
import java.util.Observable;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.Step;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.main.WizardMainFrame;
import wilos.utils.Constantes;

public class WizardStateMachine extends Observable{
	public static final int STATE_NOTHING = 0;
	public static final int STATE_PARTICIPANT = 1;
	public static final int STATE_TASK_STARTED = 2;
	public static final int STATE_TASK_READY = 3;
	public static final int STATE_TASK_CREATED = 4;
	public static final int STATE_TASK_SUSPENDED = 5;
	public static final int STATE_TASK_FINISHED = 6;

	public static final int STATE_STEP_CREATED = 7;
	public static final int STATE_STEP_READY = 8;
	public static final int STATE_STEP_FINISHED = 9;
	
	private int currentState = 0;
	
	private HashMap<Step,Integer> stepState = new HashMap<Step,Integer> ();
	
	private static WizardStateMachine wsm = null;
			
	private WizardStateMachine() {
		
	}
	
	/**
	 * Add a Step and its state.
	 * @param s step to add
	 * @param state state of the Step
	 */
	public void addStep (Step s, int state) {
		this.stepState.put(s, new Integer(state));
	}
	
	
	/**
	 * Change the state of the given Step.
	 * @param s step to change
	 * @param state new state of the step
	 */
	public void changeStepState (Step s, int state) {
		if (this.stepState.containsKey(s))
		{
			this.stepState.put(s, new Integer(state));
			this.currentState = state ;
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	/**
	 * Check the current state of the given Step.
	 * @param s Step to check
	 * @return state of the Step
	 */
	public int getStepState (Step s)
	{
		return this.stepState.get(s).intValue();
	}
	
	/**
	 * Delete all the Step of the Step state list.
	 */
	public void deleteAllStep ()
	{
		this.stepState.clear();
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
	
	public void setFocusedObject(Object object,HTMLViewer h) {
//		if (object.getClass().getSimpleName().equals("Participant")) {
//			System.out.println("pouet");
//			updateState(this.STATE_PARTICIPANT);
//		}
		if (h == null){
			h = WizardControler.getInstance().getDefaultHTML(null);
		}
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
				h.get(null).viewObject(ctd);
			}
		}
		else if (object instanceof Step)
		{
			Step s = (Step)object ;
			switch (getStepState(s)){
				case  STATE_STEP_CREATED : 
					updateState(STATE_STEP_CREATED);
					break ;
				case STATE_STEP_FINISHED :
					updateState(STATE_STEP_FINISHED);
					break ;
				case STATE_STEP_READY :
					updateState(STATE_STEP_READY);
					break ;
			}
			h.get(null).viewObject(object);
			
		}
		else {
			if (WizardControler.getInstance().isShowInfo()){
				h.get(null).viewObject(object);
				updateState(STATE_NOTHING);
			}
		}
	}
	
	public void maj (){
		this.setChanged();
		notifyObservers();
	}
	
	private void updateState(int newState) {
		this.currentState = newState;
//		 notifying all the objects managed by the state machine
		maj();
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
