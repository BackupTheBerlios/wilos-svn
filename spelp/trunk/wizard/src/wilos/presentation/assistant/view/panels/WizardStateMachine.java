package wilos.presentation.assistant.view.panels;

import java.awt.Color;
import java.util.Iterator;
import java.util.Set;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.main.ActionBar;
import wilos.utils.Constantes;

public class WizardStateMachine {
	private static final int STATE_PARTICIPANT = 1;
	private static final int STATE_NOTHING = 0;
	private static final int STATE_TASK_STARTED = 2;
	private static final int STATE_TASK_READY = 3;
	private static final int STATE_TASK_CREATED = 4;
	private int currentState;
	
	private static WizardStateMachine wsm = null;
	
	private ActionBar actionToolBar;
	
	private WizardStateMachine() {
		
	}
	
	public void initUIElements(ActionBar theActionToolBar) {
		actionToolBar = theActionToolBar;
		updateState(STATE_NOTHING);
		
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
		if (object.getClass().getSimpleName().equals("Participant")) {
			updateState(this.STATE_PARTICIPANT);
		}
		if (object instanceof TaskDescriptor) {
			Set<ConcreteTaskDescriptor> ctds = ((TaskDescriptor) object).getConcreteTaskDescriptors();
			Iterator<ConcreteTaskDescriptor> it = ctds.iterator();
			
			if (it.hasNext()) {
				System.out.println("ohe !!!");
				ConcreteTaskDescriptor ctd = it.next();
				if (ctd.getState() == Constantes.State.STARTED) {
					updateState(this.STATE_TASK_STARTED);
				}
				else if (ctd.getState() == Constantes.State.READY) {
					updateState(this.STATE_TASK_READY);
				}
				else if (ctd.getState() == Constantes.State.CREATED) {
					updateState(this.STATE_TASK_CREATED);
				}
			}
			HTMLViewer.getInstance(null).setBreakDownElement((TaskDescriptor) object);
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
		}
		
	}
}
