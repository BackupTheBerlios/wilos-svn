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
