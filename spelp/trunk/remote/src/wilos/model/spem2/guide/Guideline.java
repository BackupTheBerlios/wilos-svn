package wilos.model.spem2.guide;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.element.Element;
import wilos.model.spem2.task.TaskDefinition;

/**
 * This class represents a Guideline.
 * 
 * @author Nicolas CASTEL
 */
public class Guideline extends Element {
	private TaskDefinition taskdefinition;
	
	public Guideline() {
		this.taskdefinition = new TaskDefinition();
	}
	
	/**
	 * @param _taskdefinition
	 *            the taskdefinition to be linked to
	 */
	public void addTaskDefinition(TaskDefinition _taskdefinition) {
		this.taskdefinition = _taskdefinition ;
		_taskdefinition.getGuidelines().add(this);
	}

	/**
	 * @param _process
	 *            the process to be unlinked to
	 */
	public void removeFromTaskDefinition(TaskDefinition _taskdefinition) {
		this.taskdefinition = null ;
		_taskdefinition.getGuidelines().remove(this);
	}

	public TaskDefinition getTaskdefinition() {
		return taskdefinition;
	}

	public void setTaskdefinition(TaskDefinition _taskdefinition) {
		this.taskdefinition = _taskdefinition;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Guideline clone() throws CloneNotSupportedException {
		Guideline guideline = new Guideline() ;
		guideline.copy(this) ;
		return guideline ;
	}
	
	/**
	 * Copy the object.
	 * 
	 * @param _guideline
	 *            The Guideline to copy.
	 */
	protected void copy(final Guideline _guideline) {
		super.copy(_guideline) ;
		this.setTaskdefinition(_guideline.getTaskdefinition()) ;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.breakdownelement.BreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Guideline == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}

		Guideline guideline = (Guideline) obj ;
		return new EqualsBuilder().appendSuper(super.equals(guideline)).append(this.taskdefinition, guideline.taskdefinition).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.breakdownelement.BreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.taskdefinition).toHashCode() ;
	}
}
