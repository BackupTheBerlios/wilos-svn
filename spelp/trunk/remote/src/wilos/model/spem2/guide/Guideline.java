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
	private TaskDefinition taskDefinition;
	
	public Guideline() {
		this.taskDefinition = new TaskDefinition();
	}
	
	/**
	 * @param _taskdefinition
	 *            the taskdefinition to be linked to
	 */
	public void addTaskDefinition(TaskDefinition _taskdefinition) {
		this.taskDefinition = _taskdefinition ;
		/* TODO A modifier quand jar woops2 mis a jour */
		/*_taskdefinition.getProjects().add(this);*/
	}

	/**
	 * @param _process
	 *            the process to be unlinked to
	 */
	public void removeFromTaskDefinition(TaskDefinition _taskdefinition) {
		this.taskDefinition = null ;
		/* TODO A modifier quand jar woops2 mis a jour */
		/*_taskdefinition.getProjects().remove(this);*/
	}

	public TaskDefinition getTaskDefinition() {
		return taskDefinition;
	}

	public void setTaskDefinition(TaskDefinition _taskdefinition) {
		this.taskDefinition = _taskdefinition;
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
		this.setTaskDefinition(_guideline.getTaskDefinition()) ;
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
		return new EqualsBuilder().appendSuper(super.equals(guideline)).append(this.taskDefinition, guideline.taskDefinition).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.breakdownelement.BreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.taskDefinition).toHashCode() ;
	}
}
