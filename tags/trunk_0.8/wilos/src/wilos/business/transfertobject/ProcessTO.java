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
import java.util.HashSet;
import java.util.Set;

import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.process.Process;

public class ProcessTO extends Process implements Serializable {

	public ProcessTO() {}

	public ProcessTO(Process _process) {
		if (_process != null) {
			this.setDescription(_process.getDescription());
			this.setName(_process.getName());
			this.setGuid(_process.getGuid());
			this.setPresentationName(_process.getPresentationName());
			
			this.setMainDescription(_process.getMainDescription());
	        this.setKeyConsiderations(_process.getKeyConsiderations());
			
			Set<Guidance> guidances = new HashSet<Guidance>();
	        for (Guidance g : _process.getGuidances()) {
	        	guidances.add(new GuidanceTO(g));	        	
	        }
	        this.setGuidances(guidances);
		}
	}
}
