package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.element.Element;

public class FillerActivity extends FillerBreakDownElement {

	public FillerActivity(Element _e, Node _aNode) {
		super(_e, _aNode);
		fill();
	}
	
	private void fill() {
		
	}
}
