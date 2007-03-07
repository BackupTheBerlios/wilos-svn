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
