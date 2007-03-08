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

package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.business.services.util.xml.parser.EncodingProcessor;
import wilos.model.spem2.task.Step;

public class FillerStep extends FillerElement {
	private final String Description = "Description";
	
	public FillerStep(Step _e, Node _aNode) {
		super(_e, _aNode);
		fill();
	}

	private void fill(){
		NodeList myChildNodes = myNode.getChildNodes();
		String buffDesc = null;
		
		boolean trouve = false ;
		for (int i = 0 ; i < myChildNodes.getLength() && !trouve ; i ++){
			if (myChildNodes.item(i).getNodeName().equals(Description)){
				trouve = true ;
				buffDesc = myChildNodes.item(i).getTextContent();
				
			}
		}
		
		myElement.setDescription(EncodingProcessor.cleanString(buffDesc));
	}
}
