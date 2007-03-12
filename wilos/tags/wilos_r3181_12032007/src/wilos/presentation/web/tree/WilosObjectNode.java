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

package wilos.presentation.web.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import com.icesoft.faces.component.tree.IceUserObject;

/**
 * Extend the IceUserObject in order to add object id, bean name and page Id
 * @see
 * @author garwind
 *
 */
public class WilosObjectNode extends IceUserObject {

	// static fields
	// Project Node
	public final static String PROJECTNODE = "ProjectViewer";
	// Phase Node
	public final static String PHASENODE = "ConcretePhaseViewer";
	// Iteration Node
	public final static String ITERATIONNODE = "ConcreteIterationViewer";
	// ConcreteTask Node
	public final static String CONCRETETASKNODE = "ConcreteTaskViewer";
	// ConcreteRole Node
	public final static String CONCRETEROLENODE = "ConcreteRoleViewer";
	// ConcreteTask Node
	public final static String ACTIVITYNODE = "ConcreteActivityViewer";

	// properties
	private String id = "";

	private String pageId = "";

	private Boolean isSelected = false;

	public WilosObjectNode(DefaultMutableTreeNode arg0) {
		super(arg0);
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

}
