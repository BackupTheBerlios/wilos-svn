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

	//private Set<WorkBreakdownElement> predecessors;	
	private Set<WorkOrder> predecessors;
	
	//private Set<WorkBreakdownElement> successors;
	private Set<WorkOrder> successors;

	private Set<ConcreteWorkBreakdownElement> concreteWorkBreakdownElements;

	public WorkBreakdownElement() {
		super();
		this.isEvenDriven = false;
		this.isOngoing = false;
		this.isRepeatable = false;
		this.predecessors = new HashSet<WorkOrder>();
		this.successors = new HashSet<WorkOrder>();
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
	
	/*
	 * relation between WorkBreakdownElement and WorkOrder.
	 *
	 */
	
	/**
	 * Add a succesor to the successors collection of a workOrder
	 * 
	 * @param _workOrder
	 */
	public void addSuccessor(WorkOrder _workOrder) {
		this.successors.add(_workOrder);
		_workOrder.setPredecessor(this);
	}
	
	/**
	 * Remove from a wbde one of these successor.
	 *
	 * @param _workOrder
	 *            The successor to remove.
	 */
	public void removeSuccessor(WorkOrder _workOrder) {
		_workOrder.setPredecessor(null);
		this.successors.remove(_workOrder);
	}
	
	/**
	 * Add a successors collection into the WorkOrder successors collection.
	 *
	 * @param _workOrder
	 *            The set of successors to add.
	 */	
	public void addAllSuccessors(Set<WorkOrder> _workOrder) {
		for (WorkOrder wo : _workOrder) {
			this.addSuccessor(wo);
		}
	}

	/**
	 * Remove from an wbde all its WorkOrder successors.
	 *
	 */	
	public void removeAllSuccessors() {
		for (WorkOrder wo : this.getSuccessors())
			// FIXME a vérifier
			wo.setPredecessor(null);
			this.getSuccessors().clear();
	}
	
	/**
	 * Add a predecessor into the WorkOrder predecessors collection.
	 *
	 * @param _workOrder
	 *            The predecessor to add.
	 */
	public void addPredecessor(WorkOrder _workOrder) {
		this.predecessors.add(_workOrder);
		_workOrder.setSuccessor(this);
	}
	
	/**
	 * Remove from a wbde one of these predecessor.
	 *
	 * @param _workOrder
	 *            The predecessor to remove.
	 */
	public void removePredecessor(WorkOrder _workOrder) {
		_workOrder.setSuccessor(null);
		this.predecessors.remove(_workOrder);
	}

	/**
	 * Add a predecessor collection into the WorkOrder predecessors collection.
	 *
	 * @param _workOrder
	 *            The set of predecessors to add.
	 */
	public void addAllPredecessors(Set<WorkOrder> _workOrder) {
		for (WorkOrder wo : _workOrder) {
			this.addPredecessor(wo);
		}
	}

	/**
	 * Remove from an wbde all its WorkOrder successors.
	 *
	 */
	public void removeAllPredecessors() {
		for (WorkOrder wo : this.getPredecessors())
			// FIXME a vérifier
			wo.setSuccessor(null);
			this.getPredecessors().clear();
	}
	
	/*
	 * relation between WorkBreakdownElement and ConcreteWorkBreakdownElement.
	 *
	 */

	public void addConcreteWorkBreakdownElement(
			ConcreteWorkBreakdownElement _concreteWorkBreakdownElement) {
		this.concreteWorkBreakdownElements.add(_concreteWorkBreakdownElement);
		_concreteWorkBreakdownElement.addWorkBreakdownElement(this);
	}

	public void removeConcreteWorkBreakdownElement(
			ConcreteWorkBreakdownElement _concreteWorkBreakdownElement) {
		_concreteWorkBreakdownElement.removeWorkBreakdownElement(this);
		this.concreteWorkBreakdownElements.remove(_concreteWorkBreakdownElement);
	}

	public void addAllConcreteWorkBreakdownElements(
			Set<ConcreteWorkBreakdownElement> _concreteWorkBreakdownElements) {
		for (ConcreteWorkBreakdownElement cwbde : _concreteWorkBreakdownElements) {
			cwbde.addWorkBreakdownElement(this);
		}
	}

	public void removeAllConcreteWorkBreakdownElements() {
		for (ConcreteWorkBreakdownElement cwbde : this.getConcreteWorkBreakdownElements())
			cwbde.setWorkBreakdownElement(null);
		this.getConcreteWorkBreakdownElements().clear();
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
	 * @return the predecessors
	 */
	public Set<WorkOrder> getPredecessors() {
		return predecessors;
	}

	/**
	 * @param predecessors the predecessors to set
	 */
	public void setPredecessors(Set<WorkOrder> _predecessors) {
		this.predecessors = _predecessors;
	}

	/**
	 * @return the successors
	 */
	public Set<WorkOrder> getSuccessors() {
		return successors;
	}

	/**
	 * @param successors the successors to set
	 */
	public void setSuccessors(Set<WorkOrder> _successors) {
		this.successors = _successors;
	}

	public Set<ConcreteWorkBreakdownElement> getConcreteWorkBreakdownElements() {
		return this.concreteWorkBreakdownElements;
	}

	public void setConcreteWorkBreakdownElements(
			Set<ConcreteWorkBreakdownElement> _concreteWorkBreakdownElements) {
		this.concreteWorkBreakdownElements = _concreteWorkBreakdownElements;
	}
}
