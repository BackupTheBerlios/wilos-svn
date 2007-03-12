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

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.spem2.guide.Guidance;

public class GuidanceTO extends Guidance implements Serializable{
	
	/** Creates a new instance of ParticipantTO */
    public GuidanceTO() {

    }    
  
    public GuidanceTO(Guidance myGuidance) {
        this.setName(myGuidance.getName());
        this.setGuid(myGuidance.getGuid());
        this.setDescription(myGuidance.getDescription());
        this.setType(myGuidance.getType());
        this.setPresentationName(myGuidance.getPresentationName());
        this.setAttachment(myGuidance.getAttachment());
     
        this.setMainDescription(myGuidance.getMainDescription());
        this.setKeyConsiderations(myGuidance.getKeyConsiderations());
    }
}
