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

import javax.swing.JPanel;

import wilos.model.spem2.role.RoleDescriptor;
import wilos.presentation.assistant.view.main.MainFrame;
import wilos.presentation.assistant.view.panels.tabs.TaskPanel;

public class WorkPane extends JPanel {
	private static WorkPane workPane;
	private TaskPanel taskPane;
	InfoPanel infoPanel;
	
	private WorkPane(MainFrame m, ArrayList<RoleDescriptor> r) {
		super();
		infoPanel = new InfoPanel();
		this.add(new TaskPanel(m,r), BorderLayout.NORTH);
		//this.add(infoPanel, BorderLayout.SOUTH);
		
	}

	public static WorkPane getInstance(MainFrame m, ArrayList<RoleDescriptor> r) {
		if (workPane == null){
			workPane = new WorkPane ( m,r);
		}
		return workPane ;
	}

}
