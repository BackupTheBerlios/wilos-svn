package wilos.model.misc.project;
/**
 * @author martial
 * 
 * This class represents a project.
 * 
 */
public class Project {
	private String name ;

	/**
	 * Constructor.
	 * 
	 * @param _name
	 */
	public Project(String _name) {
		this.name = _name ;
	}
	
	/**
	 * Default constructor.
	 * 
	 */
	public Project() {
	}
	
	
	/**
	 * Getter of name.
	 *
	 * @return the name.
	 */
	public String getName() {
		return this.name ;
	}

	/**
	 * Setter of name.
	 *
	 * @param _name The name to set.
	 */
	public void setName(String _name) {
		this.name = _name ;
	}
	
	
}
