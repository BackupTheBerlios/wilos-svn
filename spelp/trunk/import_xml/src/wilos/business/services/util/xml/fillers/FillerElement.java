package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;

import wilos.model.spem2.element.Element;

public class FillerElement {
	Element myElement ;
	Node myNode ;
	
	private static String AttributeId = "id" ;
	private static String AttributeName = "name" ;
	private static String AttributeDescription = "briefDescription" ;
		
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
		myElement.setDescription(myNode.getAttributes().getNamedItem(AttributeDescription).getNodeValue());
	}
	
	public Element getFilledElement(){
		return (myElement);
	}
	
}
