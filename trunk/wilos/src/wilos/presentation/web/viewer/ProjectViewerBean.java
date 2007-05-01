/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
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

package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.project.Project;

public class ProjectViewerBean extends ViewerBean {

	private Project project;

	public void saveName() {
		// add the check of the fact that this name already exists in the db.
		if (this.project.getConcreteName().trim().length() == 0) {
			// re-put the former concrete name.
			Project tmp = super.getProjectService().getProject(
					this.project.getId());
			this.project.setConcreteName(tmp.getConcreteName());

			// add error message.
			super.getWebCommonService().addErrorMessage(
					this.getWebCommonService().getStringFromBundle(
							"viewer.err.checkNameBySaving"));
		} else if (this.presentationNameAlreadyExists(this.project.getConcreteName(), this.project.getId())) {
			// re-put the former concrete name.
			Project tmp = super.getProjectService().getProject(
					this.project.getId());
			this.project.setConcreteName(tmp.getConcreteName());

			// add error message.
			super.getWebCommonService().addErrorMessage(
					this.getWebCommonService().getStringFromBundle(
							"component.projectcreate.err.projectalreadyexists"));
		} else {
			super.getProjectService().saveProject(this.project);

			// Refresh the treebean.
			super.refreshProjectTree();

			// put the name text editor to disable.
			super.setNameIsEditable(false);

			// successful message.
			super.getWebCommonService().addInfoMessage(
					this.getWebCommonService().getStringFromBundle(
							"viewer.visibility.successMessage"));
		}
	}
	
	private boolean presentationNameAlreadyExists(String _concreteName, String _projectId) {
		for(Project project : super.getProjectService().getAllProjects())
			if((project.getConcreteName().equals(_concreteName))&&(!_projectId.equals(project.getId())))
				return true;
		return false;
	}

	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsList() {
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>();
		if (this.project != null)
			list.addAll(this.project.getConcreteBreakdownElements());

		// Filter to obtain only concreteworkbreakdownelement (without
		// concreterole).
		List<ConcreteBreakdownElement> cwbdes = new ArrayList<ConcreteBreakdownElement>();
		for (ConcreteBreakdownElement cbde : list)
			if (cbde instanceof ConcreteWorkBreakdownElement)
				cwbdes.add(cbde);

		return cwbdes;
	}

	public void saveProject() {
		super.getConcreteBreakdownElementService()
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(
						this.project);

		// successful message.
		super.getWebCommonService().addInfoMessage(
				this.getWebCommonService().getStringFromBundle(
						"viewer.visibility.successMessage"));

		// Reload the treebean.
		super.refreshProjectTree();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}