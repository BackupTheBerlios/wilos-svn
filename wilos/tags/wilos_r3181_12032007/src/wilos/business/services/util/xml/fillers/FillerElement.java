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

import wilos.model.spem2.element.Element;
import wilos.business.services.util.xml.parser.EncodingProcessor;


public class FillerElement {
	Element myElement ;
	Node myNode ;
	
	private static String AttributeId = "id" ;
	private static String AttributeName = "name" ;
	private static String AttributeDescription = "briefDescription" ;
	private static String NodePresentation = "Presentation";
	private static String NodeMainDescription = "MainDescription";
	private static String NodeKeyConsiderations = "KeyConsiderations";
	//	 data to manage the insertion order
	private static int order ; 	
	
	public static void initializeOrder() {
		order = 0 ;
	}
	
	public FillerElement (Element _e, Node _aNode) {
		try {
			myElement = _e.clone() ;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		myNode = _aNode ;
		fill();
	}
	
	private void fill(){
		// setting the id
		myElement.setGuid(myNode.getAttributes().getNamedItem(AttributeId).getNodeValue());
		// setting the name
		myElement.setName(myNode.getAttributes().getNamedItem(AttributeName).getNodeValue());
		// setting the description
		myElement.setDescription(EncodingProcessor.cleanString(myNode.getAttributes().getNamedItem(AttributeDescription).getNodeValue()));
		
		// searching and setting the MainDescription and KeyConsiderations
		NodeList myNodeList = myNode.getChildNodes();
		NodeList nodePresentationList = null;
		Node nodePresentation = null;
		String mainDescription = "";
		String keyConsiderations = "";
		// searching for the Presentation node
		for (int i = 0 ; i < myNodeList.getLength() && nodePresentation == null ; i++) {
			if (myNodeList.item(i).getNodeName().equals(NodePresentation)) {
				nodePresentation = myNodeList.item(i);
			}
		}
		if (nodePresentation != null) {
			// If the presentation node has been founded
			nodePresentationList = nodePresentation.getChildNodes();
			for (int i = 0 ; i < nodePresentationList.getLength() && (mainDescription.equals("") || keyConsiderations.equals("")); i++) {
				// Search for the MainDescription node
				if (nodePresentationList.item(i).getNodeName().equals(NodeMainDescription)) {
					mainDescription = nodePresentationList.item(i).getTextContent();
				}
				// Search for the KeyConsiderations node
				if (nodePresentationList.item(i).getNodeName().equals(NodeKeyConsiderations)) {
					keyConsiderations = nodePresentationList.item(i).getTextContent();
				}
			}
		}
		
		myElement.setMainDescription(mainDescription);
		myElement.setKeyConsiderations(keyConsiderations);
		
		// setting the order
		myElement.setInsertionOrder(++order);
	}
	
	public Element getFilledElement(){
		return (myElement);
	}
	
	public void setNode(Node _node) {
		this.myNode = _node;
	}
	
}
