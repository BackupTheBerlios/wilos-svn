package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import wilos.model.spem2.task.Step;

public class FillerStep extends FillerElement {
	private final String Description = "Description";
	
	public FillerStep(Step _e, Node _aNode) {
		super(_e, _aNode);
		fill();
	}

	private void fill(){
		NodeList myChildNodes = myNode.getChildNodes();
		String buffDesc = null;
		
		boolean trouve = false ;
		for (int i = 0 ; i < myChildNodes.getLength() && !trouve ; i ++){
			if (myChildNodes.item(i).getNodeName().equals(Description)){
				trouve = true ;
				buffDesc = myChildNodes.item(i).getTextContent();
				
			}
		}
		
		myElement.setDescription(buffDesc);
	}
}
