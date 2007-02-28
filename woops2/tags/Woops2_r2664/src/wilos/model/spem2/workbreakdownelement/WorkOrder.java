package wilos.model.spem2.workbreakdownelement;


public class WorkOrder {
	
	private String linkType;
	
	private WorkBreakdownElement predecessor;
	
	private WorkBreakdownElement successor;
	
	public WorkOrder(){
		//None.
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public WorkBreakdownElement getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(WorkBreakdownElement predecessors) {
		this.predecessor = predecessors;
	}

	public WorkBreakdownElement getSuccessor() {
		return successor;
	}

	public void setSuccessor(WorkBreakdownElement successors) {
		this.successor = successors;
	}

}
