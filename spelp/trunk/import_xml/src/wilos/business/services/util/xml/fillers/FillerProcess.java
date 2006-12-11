package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;

import wilos.model.spem2.element.Element;

public class FillerProcess extends FillerActivity {

	public FillerProcess(Element _e, Node _aNode) {
		super(_e, _aNode);
		this.fill();
		System.out.println("Process, my name : " + this.getFilledElement().getName());
	}
	
	protected void fill() {
		
	}
}
