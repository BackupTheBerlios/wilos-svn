package wilos.presentation.assistant.model;

public class WizardServer {
	private String alias;
	private String address;
	
	public WizardServer(String alias, String address) {
		super();
		this.alias = alias;
		this.address = address;
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
