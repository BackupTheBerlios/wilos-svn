package wilos.model.misc.concretetask;

import wilos.model.spem2.task.TaskDescriptor;
import wilos.utils.Constantes.State;

public class ConcreteTaskDescriptor extends TaskDescriptor {
	
	private State state;
	
	public ConcreteTaskDescriptor(){
		super();
		//TODO initialisation de l'état de cette tache concrete.
		this.state = State.CREATED;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}
