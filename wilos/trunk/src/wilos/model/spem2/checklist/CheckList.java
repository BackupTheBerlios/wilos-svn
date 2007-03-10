/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/

package wilos.model.spem2.checklist;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.section.Section;
import wilos.model.spem2.task.TaskDefinition;

public class CheckList extends Guidance{
	
	/* Collection of Sections */
	Set<Section> sections;
	
	public CheckList() {		
		this.sections = new HashSet<Section>();
	}
	
	
	@ Override
	public CheckList clone() throws CloneNotSupportedException {
		CheckList checklist = new CheckList() ;
		checklist.copy(this) ;
		return checklist ;
	}
	
	
	protected void copy(final CheckList _checklist) {
		super.copy(_checklist) ;
		this.getSections().addAll(_checklist.getSections());
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CheckList == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}

		CheckList checklist = (CheckList) obj ;
		return new EqualsBuilder().appendSuper(super.equals(checklist)).append(this.getSections(),checklist.getSections()).isEquals() ;
	}
	
	
	 public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode();
	} 

	/**
	 * @return the sections
	 */
	public Set<Section> getSections() {
		return sections;
	}

	/**
	 * @param sections the sections to set
	 */
	public void setSections(Set<Section> _sections) {
		this.sections = _sections;
	}
	
	/**
	 * addSection
	 * @param _section
	 */
	public void addSection(Section _section) {
		this.getSections().add(_section);
	}
	
	/**
	 * removeSection
	 * @param _section
	 */
	public void removeSection(Section _section) {
		this.getSections().remove(_section);
	}
	
	/**
	 * addAllSection
	 * @param _sections
	 */
	public void addAllSection(Set<Section> _sections) {
		for (Section s : _sections) {
			s.addCheckList(this);
		}
	}
	
	/**
	 * removeAllSection
	 *
	 */
	public void removeAllSection() {
		for (Section s : this.getSections()) {
			s.removeCheckList(this);
		}			
		this.getSections().clear();
	}
	
}
