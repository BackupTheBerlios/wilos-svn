package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;

import wilos.model.spem2.guide.Guidance;

public class FillerGuidance extends FillerElement{
	private static String presentationName = "presentationName";
	
	public FillerGuidance(Guidance _e, Node _aNode) {
		super(_e, _aNode);
		String type = _aNode.getAttributes().getNamedItem("xsi:type").getTextContent();
		type = type.substring(4,type.length());		
		_e.setType(type);
		fill();
	}
	
	private void fill() {
		String type = myNode.getAttributes().getNamedItem("xsi:type").getTextContent();
		type = type.substring(4,type.length());		
		((Guidance) myElement).setType(type);
		((Guidance) myElement).setPresentationName(myNode.getAttributes().getNamedItem(presentationName).getNodeValue());
	}
	
}
