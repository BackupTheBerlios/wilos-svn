package wilos.presentation.assistant.model;

public class WizardServer {
	private String alias;
	private String address;
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public WizardServer(String alias, String address, int id) {
		super();
		this.alias = alias;
		this.address = address;
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
