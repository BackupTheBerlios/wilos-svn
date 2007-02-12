package wilos.model.misc.concreteworkbreakdownelement;

/**
 * 
 * @author Sebastien
 *
 */
public class ConcreteWorkOrder {

	private String concreteLinkType;
	
	private ConcreteWorkBreakdownElement concretePredecessor;
	
	private ConcreteWorkBreakdownElement concreteSuccessor;
	
	public ConcreteWorkOrder(){
		//None.
	}

	/**
	 * @return the concretePredecessor
	 */
	public ConcreteWorkBreakdownElement getConcretePredecessor() {
		return this.concretePredecessor;
	}

	/**
	 * @param concretePredecessor the concretePredecessor to set
	 */
	public void setConcretePredecessor(
			ConcreteWorkBreakdownElement _concretePredecessor) {
		this.concretePredecessor = _concretePredecessor;
	}

	/**
	 * @return the concreteSuccessor
	 */
	public ConcreteWorkBreakdownElement getConcreteSuccessor() {
		return this.concreteSuccessor;
	}

	/**
	 * @param concreteSuccessor the concreteSuccessor to set
	 */
	public void setConcreteSuccessor(ConcreteWorkBreakdownElement concreteSuccessor) {
		this.concreteSuccessor = concreteSuccessor;
	}

	public String getConcreteLinkType() {
		return this.concreteLinkType;
	}

	public void setConcreteLinkType(String _concreteLinkType) {
		this.concreteLinkType = _concreteLinkType;
	}
	
}
