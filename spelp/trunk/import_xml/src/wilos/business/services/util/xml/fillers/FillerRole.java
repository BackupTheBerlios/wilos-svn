package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.role.RoleDefinition;

public class FillerRole extends FillerElement {
	private static String NodePresentation = "Presentation";
	private static String NodeAssignmentApproaches = "AssignmentApproaches";
	private static String NodeSkills = "Skills";
	private static String NodeSynonyms = "Synonyms";

	public FillerRole(RoleDefinition _e, Node _aNode) {
		super(_e, _aNode);
		fill();
	}
	
	public void fill() {
		// searching and setting the Alternatives, HowToStaff, and Purpose
		NodeList myNodeList = myNode.getChildNodes();
		NodeList nodePresentationList = null;
		Node nodePresentation = null;
		String assignmentApproaches = "";
		String skills = "";
		String synonyms = "";
		
		// searching for the Presentation node
		for (int i = 0 ; i < myNodeList.getLength() && nodePresentation == null ; i++) {
			if (myNodeList.item(i).getNodeName().equals(NodePresentation)) {
				nodePresentation = myNodeList.item(i);
			}
		}
		if (nodePresentation != null) {
			// If the presentation node has been founded
			nodePresentationList = nodePresentation.getChildNodes();
			for (int i = 0 ; i < nodePresentationList.getLength() && (assignmentApproaches.equals("") || skills.equals("") || synonyms.equals("")); i++) {
				// Search for the Alternatives node
				if (nodePresentationList.item(i).getNodeName().equals(NodeAssignmentApproaches)) {
					assignmentApproaches = nodePresentationList.item(i).getTextContent();
				}
				// Search for the HowToStaff node
				if (nodePresentationList.item(i).getNodeName().equals(NodeSkills)) {
					skills = nodePresentationList.item(i).getTextContent();
				}
				// Search for the Purpose node
				if (nodePresentationList.item(i).getNodeName().equals(NodeSynonyms)) {
					synonyms = nodePresentationList.item(i).getTextContent();
				}
			}
		}
		
		((RoleDefinition)myElement).setAssignmentApproaches(assignmentApproaches);
		((RoleDefinition)myElement).setSkills(skills);
		((RoleDefinition)myElement).setSynonyms(synonyms);
	}
}
