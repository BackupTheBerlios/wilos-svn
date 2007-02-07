package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;

import wilos.model.spem2.guide.Guidance;

public class FillerGuidance extends FillerElement{

	public FillerGuidance(Guidance _e, Node _aNode) {
		super(_e, _aNode);
		String type = _aNode.getAttributes().getNamedItem("xsi:type").getTextContent();
		type = type.substring(4,type.length());		
		_e.setGuideType(type);
		System.out.println(_e.getGuideType());
	}

}
