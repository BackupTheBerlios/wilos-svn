package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.element.Element;
import wilos.model.spem2.task.TaskDefinition;

public class FillerActivity extends FillerWorkBreakDownElement {
	private static String NodePresentation = "Presentation";
	private static String NodeAlternatives = "Alternatives";
	private static String NodeHowToStaff = "HowToStaff";
	private static String NodePurpose = "Purpose";
	
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
		
		// searching for the Presentation node
		for (int i = 0 ; i < myNodeList.getLength() && nodePresentation == null ; i++) {
			if (myNodeList.item(i).getNodeName().equals(NodePresentation)) {
				nodePresentation = myNodeList.item(i);
			}
		}
		if (nodePresentation != null) {
			// If the presentation node has been founded
			nodePresentationList = nodePresentation.getChildNodes();
			for (int i = 0 ; i < nodePresentationList.getLength() && (alternatives.equals("") || purposes.equals("")); i++) {
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
			}
		}
		
		((Activity)myElement).setAlternatives(alternatives);
		((Activity)myElement).setHowToStaff(howToStaff);
		((Activity)myElement).setPurpose(purposes);
	}
}
