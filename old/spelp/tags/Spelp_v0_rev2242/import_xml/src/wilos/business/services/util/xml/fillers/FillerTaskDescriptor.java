package wilos.business.services.util.xml.fillers;

import org.w3c.dom.Node;

import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.task.TaskDescriptor;

public class FillerTaskDescriptor extends FillerWorkBreakDownElement {

	public FillerTaskDescriptor(BreakdownElement returnedBde, Node _aNode) {
		super(returnedBde, _aNode);
	}

}
