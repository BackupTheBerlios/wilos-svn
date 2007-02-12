package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.business.services.util.xml.parser.XMLParser;
import wilos.model.spem2.guide.Guidance;

public class FillerGuidance extends FillerElement{
	private static String presentationName = "presentationName";
	
	public FillerGuidance(Guidance _e, Node _aNode) {
		super(_e, _aNode);
		fill();
	}
	
	private void fill() {
		String type = myNode.getAttributes().getNamedItem("xsi:type").getTextContent();
		type = type.substring(4,type.length());		
		((Guidance) myElement).setType(type);
		((Guidance) myElement).setPresentationName(myNode.getAttributes().getNamedItem(presentationName).getNodeValue());
		
		NodeList l = myNode.getChildNodes();
		String mainDescription = "";
		for (int i=0; i<l.getLength(); i++) {			
			if (l.item(i).getNodeName().equals(XMLParser.presentation)) {
				NodeList listDesc = l.item(i).getChildNodes();
				for (int j = 0; j < listDesc.getLength();j++) {
					if (listDesc.item(j).getNodeName().equals(XMLParser.maindescription)) {
						mainDescription = listDesc.item(j).getTextContent() ;						
					}
				}
			}			
		}
		
		((Guidance) myElement).setDescription(mainDescription);
	}
	
}
