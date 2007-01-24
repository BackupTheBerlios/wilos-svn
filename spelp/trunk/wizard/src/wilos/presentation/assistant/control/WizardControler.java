package wilos.presentation.assistant.control;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.presentation.assistant.webservices.WizardServicesProxy;
import wilos.utils.Constantes;

public class WizardControler {
	private static WizardControler wc = null;
	
	private WizardControler() {
		
	}
	
	public static WizardControler getInstance() {
		if (wc == null) {
			wc = new WizardControler();
		}
		return wc;
	}
	
	public void startConcreteTaskDescriptor(ConcreteTaskDescriptor ctd) {
		if (ctd.getState() == Constantes.State.READY) {
			WizardServicesProxy wsp = new WizardServicesProxy();
			wsp.startConcreteTaskDescriptor(ctd.getId());
		}
	}
}
