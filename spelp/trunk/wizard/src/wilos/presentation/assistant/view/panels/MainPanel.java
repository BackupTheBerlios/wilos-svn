package wilos.presentation.assistant.view.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import wilos.model.spem2.role.RoleDescriptor;
import wilos.presentation.assistant.view.main.ActionBar;
import wilos.presentation.assistant.view.main.MainFrame;

public class MainPanel extends JPanel{
	WorkTabbedPane onglets ;
	WorkPane workPane;
	
	ActionBar barre = new ActionBar() ;
	
	// Partie Menu 
	JMenuBar menuBar; 
	JMenu fileMenu;
	JMenu toolsMenu;
	JMenu helpMenu;
	
		
	public MainPanel (MainFrame m,ArrayList<RoleDescriptor> r) {
		super();
		this.setLayout(new BorderLayout());
		onglets = WorkTabbedPane.getInstance(m,r);
		workPane = WorkPane.getInstance(m, r);
		
		//this.add(getMenuBar(), BorderLayout.NORTH);
		this.add(workPane,BorderLayout.CENTER);
		//this.add(barre,BorderLayout.WEST);
	}
	
	
	public JMenuBar getMenuBar() {
		menuBar = new JMenuBar();
		
		toolsMenu = new JMenu("Outils");
		fileMenu = new JMenu("Fichier");
		helpMenu = new JMenu("Aide");
		
		menuBar.add(fileMenu);
		menuBar.add(toolsMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}
}
