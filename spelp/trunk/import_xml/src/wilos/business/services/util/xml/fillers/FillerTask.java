package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.business.services.util.xml.parser.EncodingProcessor;
import wilos.model.spem2.task.TaskDefinition;

public class FillerTask extends FillerElement
{
	private static String NodePresentation = "Presentation";
	private static String NodeAlternatives = "Alternatives";
	private static String NodePurpose = "Purpose";
	
	public FillerTask(TaskDefinition _e, Node _aNode) {
		super(_e, _aNode);
		fill();
	}
	
	public void fill()
	{
		// searching and setting the Alternatives and Purpose
		NodeList myNodeList = myNode.getChildNodes();
		NodeList nodePresentationList = null;
		Node nodePresentation = null;
		String alternatives = "";
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
				// Search for the Purpose node
				if (nodePresentationList.item(i).getNodeName().equals(NodePurpose)) {
					purposes = nodePresentationList.item(i).getTextContent();
				}
			}
		}
		
		((TaskDefinition)myElement).setAlternatives(alternatives);
		((TaskDefinition)myElement).setPurpose(purposes);
	}
}
