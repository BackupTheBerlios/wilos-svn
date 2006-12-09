package wilos.presentation.assistant.view.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import wilos.model.spem2.role.RoleDescriptor;
import wilos.presentation.assistant.view.main.ActionBar;
import wilos.presentation.assistant.view.main.MainFrame;

public class MainPanel extends JPanel{
	WorkTabbedPane onglets ;
	ActionBar barre = new ActionBar() ;
		
	public MainPanel (MainFrame m,ArrayList<RoleDescriptor> r) {
		super();
		this.setLayout(new BorderLayout());
		onglets = WorkTabbedPane.getInstance(m,r);
		this.add(onglets,BorderLayout.CENTER);
		this.add(barre,BorderLayout.WEST);
	}
}
