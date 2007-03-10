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
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.element.Element;

public class FillerActivity extends FillerWorkBreakDownElement {
	private static String NodePresentation = "Presentation";
	private static String NodeAlternatives = "Alternatives";
	private static String NodeHowToStaff = "HowToStaff";
	private static String NodePurpose = "Purpose";
	private static String NodeMainDescription = "MainDescription";
	private static String NodeKeyConsiderations = "KeyConsiderations";
	private static String NodeDescription = "briefDescription" ;
	
	public FillerActivity(Element _e, Node _aNode) {
		super(_e, _aNode);
		fill();
	}
	
	private void fill() {
		// searching and setting the Alternatives, HowToStaff, and Purpose
		NodeList myNodeList = myNode.getChildNodes();
		NodeList nodePresentationList = null;
		Node nodePresentation = null;
		String alternatives = "";
		String howToStaff = "";
		String purposes = "";
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
			for (int i = 0 ; i < nodePresentationList.getLength() && (alternatives.equals("") || howToStaff.equals("") || purposes.equals("")); i++) {
				// Search for the Alternatives node
				if (nodePresentationList.item(i).getNodeName().equals(NodeAlternatives)) {
					alternatives = nodePresentationList.item(i).getTextContent();
				}
				// Search for the HowToStaff node
				if (nodePresentationList.item(i).getNodeName().equals(NodeHowToStaff)) {
					howToStaff = nodePresentationList.item(i).getTextContent();
				}
				// Search for the Purpose node
				if (nodePresentationList.item(i).getNodeName().equals(NodePurpose)) {
					purposes = nodePresentationList.item(i).getTextContent();
				}
				
				
				// We need it in the case that this fill is called by auxiliaryFill
				
				if (nodePresentationList.item(i).getNodeName().equals(NodeMainDescription)) {
					mainDescription = nodePresentationList.item(i).getTextContent();
				}
				// Search for the KeyConsiderations node
				if (nodePresentationList.item(i).getNodeName().equals(NodeKeyConsiderations)) {
					keyConsiderations = nodePresentationList.item(i).getTextContent();
				}				
			}
		}
		
		((Activity)myElement).setAlternatives(alternatives);
		((Activity)myElement).setHowToStaff(howToStaff);
		((Activity)myElement).setPurpose(purposes);
		((Activity)myElement).setMainDescription(mainDescription);
		((Activity)myElement).setKeyConsiderations(keyConsiderations);
	}

	public void auxilliaryFill(Node node) {
		this.myNode = node;
		this.fill();
		
		myElement.setDescription(EncodingProcessor.cleanString(myNode.getAttributes().getNamedItem(NodeDescription).getNodeValue()));
	}
}
