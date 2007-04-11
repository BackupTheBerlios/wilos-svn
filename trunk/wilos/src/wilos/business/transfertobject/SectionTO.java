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

package wilos.business.transfertobject;

import java.io.Serializable;

import wilos.model.spem2.section.Section;

public class SectionTO extends Section implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3478890824306139556L;

	public SectionTO() {
		
	}
	
	public SectionTO(Section mySection) {
		this.setName(mySection.getName());
		this.setDescription(mySection.getDescription());
		this.setGuid(mySection.getGuid());
		
		this.setMainDescription(mySection.getMainDescription());
        this.setKeyConsiderations(mySection.getKeyConsiderations());
	}
	
	
}
