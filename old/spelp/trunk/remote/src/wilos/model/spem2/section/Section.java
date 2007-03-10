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

package wilos.model.spem2.section;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.checklist.CheckList;
import wilos.model.spem2.element.Element;

public class Section extends Element {
	
	/* the attached CheckList */
	private CheckList checklist;

	public Section() {
		super();
	}
	
	protected void copy(final Section _section) {
		super.copy(_section) ;
		this.setChecklist(_section.getChecklist());
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Section == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}

		Section checklist = (Section) obj ;
		return new EqualsBuilder().appendSuper(super.equals(checklist)).append(this.getChecklist(),checklist.getChecklist()).isEquals() ;
	}
	
	
	 public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode();
	} 

	/**
	 * @return the checklist
	 */
	public CheckList getChecklist() {
		return checklist;
	}

	/**
	 * @param checklist the checklist to set
	 */
	public void setChecklist(CheckList checklist) {
		this.checklist = checklist;
	}
	
	/**
	 * addCheckList
	 * @param _checklist
	 */
	public void addCheckList(CheckList _checklist) {
		_checklist.getSections().add(this);		
	}
	
	/**
	 * removeCheckList
	 * @param _checklist
	 */
	public void removeCheckList(CheckList _checklist) {
		_checklist.getSections().remove(this);
	}
	
}
