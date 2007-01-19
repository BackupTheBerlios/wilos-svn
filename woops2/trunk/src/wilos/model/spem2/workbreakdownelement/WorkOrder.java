package wilos.model.spem2.workbreakdownelement;


public class WorkOrder {
	
	private String id;
	
	private String linkType;
	
	public WorkOrder(){
		//None.
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
