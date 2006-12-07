
package wilos.model.spem2.methodpackage ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

/**
 * A Process Component is a special Process Package that applies the principles of encapsulation.
 * 
 * TODO hbm.xml corresponding.
 * 
 * @author deder
 * 
 */
public class ProcessComponent extends ProcessPackage {

	public ProcessComponent() {
		super() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodpackage.ProcessPackage#clone()
	 */
	@ Override
	public ProcessComponent clone() throws CloneNotSupportedException {
		ProcessComponent processComponent = new ProcessComponent() ;
		processComponent.copy(this) ;
		return processComponent ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodpackage.ProcessPackage#copy(woops2.model.methodpackage.ProcessPackage)
	 */
	protected void copy(ProcessComponent _processComponent) {
		super.copy(_processComponent) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodpackage.ProcessPackage#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals(Object _obj) {
		if(_obj instanceof ProcessComponent == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		ProcessComponent processComponent = (ProcessComponent) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(processComponent)).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.methodpackage.ProcessPackage#hashCode()
	 */
	@ Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}
}
