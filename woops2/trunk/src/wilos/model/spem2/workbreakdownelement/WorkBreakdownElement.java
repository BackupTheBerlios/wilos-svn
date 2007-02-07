package wilos.model.spem2.workbreakdownelement;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.spem2.breakdownelement.BreakdownElement;

/**
 *
 * A Work Breakdown Element is a special Breakdown Element that provides
 * specific properties for Breakdown Elements that represent or refer to Work
 * Definitions.
 *
 * @author eperico
 *
 */
public class WorkBreakdownElement extends BreakdownElement implements Cloneable {

	private Boolean isRepeatable;

	private Boolean isOngoing;

	private Boolean isEvenDriven;

	private Set<WorkBreakdownElement> predecessors;

	private Set<WorkBreakdownElement> successors;

	private Set<ConcreteWorkBreakdownElement> concreteWorkBreakdownElements;

	public WorkBreakdownElement() {
		super();
		this.isEvenDriven = false;
		this.isOngoing = false;
		this.isRepeatable = false;
		this.predecessors = new HashSet<WorkBreakdownElement>();
		this.successors = new HashSet<WorkBreakdownElement>();
		this.concreteWorkBreakdownElements = new HashSet<ConcreteWorkBreakdownElement>() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.spem2.workbreakdownelement.WorkBreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof BreakdownElement == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		WorkBreakdownElement workBreakdownElement = (WorkBreakdownElement) obj;
		return new EqualsBuilder().appendSuper(
				super.equals(workBreakdownElement)).append(this.isEvenDriven,
				workBreakdownElement.isEvenDriven).append(this.isOngoing,
				workBreakdownElement.isOngoing).append(this.isRepeatable,
				workBreakdownElement.isRepeatable).append(this.successors,
				workBreakdownElement.successors).append(this.predecessors,
				workBreakdownElement.predecessors).append(this.concreteWorkBreakdownElements,
				workBreakdownElement.concreteWorkBreakdownElements)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.spem2.workbreakdownelement.WorkBreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.append(this.isEvenDriven).append(this.isOngoing).append(
						this.isRepeatable).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public WorkBreakdownElement clone() throws CloneNotSupportedException {
		WorkBreakdownElement workBreakdownElement = new WorkBreakdownElement();
		workBreakdownElement.copy(this);
		return workBreakdownElement;
	}
	
	/**
	 * Copies the _workBreakdownElement into this.
	 * @param _workBreakdownElement the WorkBreakdownElement to copy
	 */
	protected void copy(final WorkBreakdownElement _workBreakdownElement) {
		super.copy(_workBreakdownElement);
		this.setIsEvenDriven(_workBreakdownElement.getIsEvenDriven());
		this.setIsOngoing(_workBreakdownElement.getIsOngoing());
		this.setIsRepeatable(_workBreakdownElement.getIsRepeatable());
		this.setConcreteWorkBreakdownElements(_workBreakdownElement.getConcreteWorkBreakdownElements());
		this.setPredecessors(_workBreakdownElement.getPredecessors());
		this.setSuccessors(_workBreakdownElement.getSuccessors());
		
	}

	/**
	 * Adds a successor to the successors collection of a wbde.
	 *
	 * @param _workBreakdownElements
	 *            The successor to add.
	 */
	public void addSuccessor(WorkBreakdownElement _workBreakdownElement) {
		this.successors.add(_workBreakdownElement);
		_workBreakdownElement.predecessors.add(this);
	}

	/**
	 * Removes from a wbde one of the successor of this wbde.
	 *
	 * @param _workBreakdownElements
	 *            The successor to remove.
	 */
	public void removeSuccessor(WorkBreakdownElement _workBreakdownElement) {
		_workBreakdownElement.predecessors.remove(this);
		this.successors.remove(_workBreakdownElement);
	}

	/**
	 * Adds a successor collection to the successors collection of a wbde.
	 *
	 * @param _workBreakdownElements
	 *            The set of successors to add.
	 */
	public void addAllSuccessors(
			Set<WorkBreakdownElement> _workBreakdownElements) {
		for (WorkBreakdownElement wbde : _workBreakdownElements) {
			wbde.addPredecessor(this);
		}
	}

	/**
	 * Removes all the successors from a wbde.
	 *
	 */
	public void removeAllSuccessors() {
		for (WorkBreakdownElement wbde : this.getSuccessors())
			wbde.getPredecessors().remove(this);
		this.getSuccessors().clear();
	}

	/**
	 * Adds a predecessor to the predecessors collection of a wbde.
	 *
	 * @param _workBreakdownElements
	 *            The predecessor to add.
	 */
	public void addPredecessor(WorkBreakdownElement _workBreakdownElement) {
		this.predecessors.add(_workBreakdownElement);
		_workBreakdownElement.successors.add(this);
	}

	/**
	 * Removes from a wbde one of its predecessor
	 *
	 * @param _workBreakdownElements
	 *            The predecessor to remove.
	 */
	public void removePredecessor(WorkBreakdownElement _workBreakdownElement) {
		_workBreakdownElement.successors.remove(this);
		this.predecessors.remove(_workBreakdownElement);
	}

	/**
	 * Adds a predecessor collection to the predecessors collection of a wbde.
	 *
	 * @param _workBreakdownElements
	 *            The set of predecessors to add.
	 */
	public void addAllPredecessors(
			Set<WorkBreakdownElement> _workBreakdownElements) {
		for (WorkBreakdownElement wbde : _workBreakdownElements) {
			wbde.addSuccessor(this);
		}
	}

	/**
	 * Removes all the predecessors from a wbde.
	 *
	 */
	public void removeAllPredecessors() {
		for (WorkBreakdownElement wbde : this.getPredecessors())
			wbde.getSuccessors().remove(this);
		this.getPredecessors().clear();
	}

	/**
	 * Getter of isEvenDriven.
	 *
	 * @return the isEvenDriven.
	 */
	public Boolean getIsEvenDriven() {
		return this.isEvenDriven;
	}

	/**
	 * Setter of isEvenDriven.
	 *
	 * @param _isEvenDriven
	 *            The isEvenDriven to set.
	 */
	public void setIsEvenDriven(Boolean _isEvenDriven) {
		this.isEvenDriven = _isEvenDriven;
	}

	/**
	 * Getter of isOngoing.
	 *
	 * @return the isOngoing.
	 */
	public Boolean getIsOngoing() {
		return this.isOngoing;
	}

	/**
	 * Setter of isOngoing.
	 *
	 * @param _isOngoing
	 *            The isOngoing to set.
	 */
	public void setIsOngoing(Boolean _isOngoing) {
		this.isOngoing = _isOngoing;
	}

	/**
	 * Getter of isRepeatable.
	 *
	 * @return the isRepeatable.
	 */
	public Boolean getIsRepeatable() {
		return this.isRepeatable;
	}

	/**
	 * Setter of isRepeatable.
	 *
	 * @param _isRepeatable
	 *            The isRepeatable to set.
	 */
	public void setIsRepeatable(Boolean _isRepeatable) {
		this.isRepeatable = _isRepeatable;
	}

	/**
	 * Getter of the predecessors
	 *  
	 * @return Set of predecessors
	 */
	public Set<WorkBreakdownElement> getPredecessors() {
		return predecessors;
	}
	
	/**
	 * Setter of the Set of predecessors
	 * 
	 * @param predecessors Set of predecessors.
	 */
	public void setPredecessors(Set<WorkBreakdownElement> predecessors) {
		this.predecessors = predecessors;
	}

	/**
	 * Getter of the successors
	 * 
	 * @return Set of successors
	 */
	public Set<WorkBreakdownElement> getSuccessors() {
		return successors;
	}

	/**
	 * Setter of the Set of successors
	 * 
	 * @param successors Set of successors
	 */
	public void setSuccessors(Set<WorkBreakdownElement> successors) {
		this.successors = successors;
	}

	/**
	 * Getter of the Set of concreteWorkBreakdownElements
	 * 
	 * @return Set of concreteWorkBreakdownElements
	 */
	public Set<ConcreteWorkBreakdownElement> getConcreteWorkBreakdownElements() {
		return this.concreteWorkBreakdownElements;
	}

	/**
	 * Setter of the Set of concreteWorkBreakdownElements
	 * 
	 * @param _concreteWorkBreakdownElements Set of concreteWorkBreakdownElements
	 */
	public void setConcreteWorkBreakdownElements(
			Set<ConcreteWorkBreakdownElement> _concreteWorkBreakdownElements) {
		this.concreteWorkBreakdownElements = _concreteWorkBreakdownElements;
	}
	
	/*
	 * relation between WorkBreakdownElement and ConcreteWorkBreakdownElement.
	 *
	 */

	/**
	 * Adds a concreteWorkBreakdownElements to the Set of _concreteWorkBreakdownElement
	 * 
	 * @param _concreteWorkBreakdownElement Element to add
	 */
	public void addConcreteWorkBreakdownElement(
			ConcreteWorkBreakdownElement _concreteWorkBreakdownElement) {
		this.concreteWorkBreakdownElements.add(_concreteWorkBreakdownElement);
		_concreteWorkBreakdownElement.addWorkBreakdownElement(this);
	}

	/**
	 * Removes a ConcreteWorkBreakdownElement from the Set
	 * 
	 * @param _concreteWorkBreakdownElement Element to remove
	 */
	public void removeConcreteWorkBreakdownElement(
			ConcreteWorkBreakdownElement _concreteWorkBreakdownElement) {
		_concreteWorkBreakdownElement.removeWorkBreakdownElement(this);
		this.concreteWorkBreakdownElements.remove(_concreteWorkBreakdownElement);
	}

	/**
	 * Adds a Set of ConcreteWorkBreakdownElement
	 * 
	 * @param _concreteWorkBreakdownElements Set of elements to add
	 */
	public void addAllConcreteWorkBreakdownElements(
			Set<ConcreteWorkBreakdownElement> _concreteWorkBreakdownElements) {
		for (ConcreteWorkBreakdownElement cwbde : _concreteWorkBreakdownElements) {
			cwbde.addWorkBreakdownElement(this);
		}
	}

	/**
	 * Removes all the ConcreteWorkBreakdownElement
	 */
	public void removeAllConcreteWorkBreakdownElements() {
		for (ConcreteWorkBreakdownElement cwbde : this.getConcreteWorkBreakdownElements())
			cwbde.setWorkBreakdownElement(null);
		this.getConcreteWorkBreakdownElements().clear();
	}
}
