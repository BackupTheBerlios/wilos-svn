package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;

import wilos.model.spem2.guide.Guidance;

public class FillerGuidance extends FillerElement{

	public FillerGuidance(Guidance _e, Node _aNode) {
		super(_e, _aNode);
		String type = _aNode.getAttributes().getNamedItem("xsi:type").getTextContent();
		type = type.substring(4,type.length());		
		_e.setGuideType(type);
		fill(type);
	}

	private void fill(String type) {
		Guidance aGuidance = new Guidance();
		try {
			aGuidance = (Guidance)myElement.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		aGuidance.setGuideType(type);
		myElement = aGuidance;
		
	}
	
}
