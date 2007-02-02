package wilos.presentation.assistant.control;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.main.ActionBar;
import wilos.presentation.assistant.view.main.ContextualMenu;
import wilos.presentation.assistant.view.panels.TreePanel;
import wilos.presentation.assistant.webservices.WizardServicesProxy;
import wilos.utils.Constantes;

public class WizardControler {
	private static WizardControler wc = null;
	private ActionBar actionBar = null;
	private TreePanel treePanel = null ;
	private ContextualMenu menuContextuel = null ;
	private boolean showInfo = true ;
	
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
			WizardServicesProxy.startConcreteTaskDescriptor(ctd.getId());
		}
	}
	
	public void initUIElements(ActionBar theActionToolBar,TreePanel theTreePanel, ContextualMenu menu) {
		//actionToolBar = theActionToolBar;
		actionBar = theActionToolBar ;
		treePanel = theTreePanel;
		menuContextuel = menu ;
		showInfo = true;
	}
	
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
}
