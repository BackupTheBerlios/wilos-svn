package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;

import wilos.model.spem2.element.Element;
import wilos.business.services.util.xml.parser.EncodingProcessor;


public class FillerElement {
	Element myElement ;
	Node myNode ;
	
	private static String AttributeId = "id" ;
	private static String AttributeName = "name" ;
	private static String AttributeDescription = "briefDescription" ;
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
		// setting the order
		myElement.setInsertionOrder(++order);
	}
	
	public Element getFilledElement(){
		return (myElement);
	}
	
}
