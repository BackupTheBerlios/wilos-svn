package spelp.xml.fillers;

import org.w3c.dom.Node;

import woops2.model.breakdownelement.BreakdownElement;

public class FillerBreakDownElement extends FillerElement {
	private static String prefix = "prefix" ;
	private static String isPlanned = "isPlanned" ;
	private static String hasMultipleOccurrences = "hasMultipleOccurrences" ;
	private static String isOptional = "isOptional" ;
		
	public FillerBreakDownElement(BreakdownElement _e, Node _aNode) {
		super(_e, _aNode);
		fill() ;
	}
	
	private void fill () {
		((BreakdownElement)myElement).setPrefix(myNode.getAttributes().getNamedItem(prefix).getNodeValue());
		// setting the isPlanned
		((BreakdownElement)myElement).setIsPlanned(Boolean.valueOf(myNode.getAttributes().getNamedItem(isPlanned).getNodeValue()));
		// setting the hasMultipleOccurrences		
		((BreakdownElement)myElement).setHasMultipleOccurrences((Boolean.valueOf(myNode.getAttributes().getNamedItem(hasMultipleOccurrences).getNodeValue())));
		// setting the isOptional		
		((BreakdownElement)myElement).setIsOptional((Boolean.valueOf(myNode.getAttributes().getNamedItem(isOptional).getNodeValue())));
	}
}
