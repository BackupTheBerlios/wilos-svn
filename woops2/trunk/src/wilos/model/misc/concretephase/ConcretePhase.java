package wilos.model.misc.concretephase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.spem2.phase.Phase;

/**
 *
 * @author Soosuske
 *
 */
public class ConcretePhase extends ConcreteActivity implements Cloneable{

	Phase phase;

	/**
	 * Default constructor
	 *
	 */
	public ConcretePhase() {
		super() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public ConcretePhase clone() throws CloneNotSupportedException {
		ConcretePhase concretePhase = new ConcretePhase() ;
		concretePhase.copy(this) ;
		return concretePhase ;
	}

	/**
	 * Copy the object.
	 *
	 * @param _cnocretePhase
	 *            The concretePhase to copy.
	 */
	protected void copy(final ConcretePhase _concretePhase) {
		super.copy(_concretePhase) ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.activity.Activity#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof ConcretePhase == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		ConcretePhase concretePhase = (ConcretePhase) obj ;
		return new EqualsBuilder().appendSuper(super.equals(concretePhase)).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wilos.model.misc.concretePhase.Concrete#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}

	/**
	 * add a phase
	 * @param phase
	 */
	public void addToPhase(Phase phase){
		   this.phase = phase;
		   phase.getConcretePhases().add(this);
		}

	/**
	 * remove a phase
	 * @param phase
	 */
	public void removeFromPhase(Phase phase){
		   this.phase = null;
		   phase.getConcretePhases().remove(this);
		}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

}

